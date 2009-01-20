/*
 * Copyright 2008 Federal Chancellery Austria and
 * Graz University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.gv.egiz.bku.binding;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import at.gv.egiz.bku.binding.multipart.InputStreamPartSource;
import at.gv.egiz.bku.binding.multipart.SLResultPart;
import at.gv.egiz.bku.slcommands.SLResult;
import at.gv.egiz.bku.slcommands.SLResult.SLResultType;
import at.gv.egiz.bku.slexceptions.SLRuntimeException;
import at.gv.egiz.bku.utils.binding.Protocol;

/**
 * not thread-safe thus newInsance always returns a new object
 * 
 */
public class DataUrlConnectionImpl implements DataUrlConnectionSPI {

  private final static Log log = LogFactory.getLog(DataUrlConnectionImpl.class);

  public final static Protocol[] SUPPORTED_PROTOCOLS = { Protocol.HTTP,
      Protocol.HTTPS };

  protected X509Certificate serverCertificate;
  protected Protocol protocol;
  protected URL url;
  private HttpURLConnection connection;
  protected Map<String, String> requestHttpHeaders;
  protected ArrayList<Part> formParams;
  protected String boundary;
  protected Properties config = null;
  protected SSLSocketFactory sslSocketFactory;
  protected HostnameVerifier hostnameVerifier;

  protected DataUrlResponse result;

  public String getProtocol() {
    if (protocol == null) {
      return null;
    }
    return protocol.toString();
  }

  /**
   * opens a connection sets the headers gets the server certificate
   * 
   * @throws java.net.SocketTimeoutException
   * @throws java.io.IOException
   * @pre url != null
   * @pre httpHeaders != null
   */
  public void connect() throws SocketTimeoutException, IOException {
    connection = (HttpURLConnection) url.openConnection();
    if (connection instanceof HttpsURLConnection) {
      log.trace("Detected ssl connection");
      HttpsURLConnection https = (HttpsURLConnection) connection;
      if (sslSocketFactory != null) {
        log.debug("Setting custom ssl socket factory for ssl connection");
        https.setSSLSocketFactory(sslSocketFactory);
      } else {
        log.trace("No custom socket factory set");
      }
      if (hostnameVerifier != null) {
        log.debug("Setting custom hostname verifier");
        https.setHostnameVerifier(hostnameVerifier);
      }
    } else {
      log.trace("No secure connection with: "+url+ " class="+connection.getClass());
    }
    connection.setDoOutput(true);
    Set<String> headers = requestHttpHeaders.keySet();
    Iterator<String> headerIt = headers.iterator();
    while (headerIt.hasNext()) {
      String name = headerIt.next();
      connection.setRequestProperty(name, requestHttpHeaders.get(name));
    }
    log.trace("Connecting to: " + url);
    connection.connect();
    if (connection instanceof HttpsURLConnection) {
      HttpsURLConnection ssl = (HttpsURLConnection) connection;
      X509Certificate[] certs = (X509Certificate[]) ssl.getServerCertificates();
      if ((certs != null) && (certs.length >= 1)) {
        log.trace("Server certificate: " + certs[0]);
        serverCertificate = certs[0];
      }
    }
  }

  public X509Certificate getServerCertificate() {
    return serverCertificate;
  }

  public void setHTTPHeader(String name, String value) {
    if (name != null && value != null) {
      requestHttpHeaders.put(name, value);
    }
  }

  public void setHTTPFormParameter(String name, InputStream data,
      String contentType, String charSet, String transferEncoding) {
    InputStreamPartSource source = new InputStreamPartSource(null, data);
    FilePart formParam = new FilePart(name, source, contentType, charSet);
    if (transferEncoding != null) {
      formParam.setTransferEncoding(transferEncoding);
    } else {
      formParam.setTransferEncoding(null);
    }
    formParams.add(formParam);
  }

  /**
   * send all formParameters
   * 
   * @throws java.io.IOException
   */
  public void transmit(SLResult slResult) throws IOException {
    SLResultPart slResultPart = new SLResultPart(slResult,
        XML_RESPONSE_ENCODING);
    if (slResult.getResultType() == SLResultType.XML) {
      slResultPart.setTransferEncoding(null);
      slResultPart.setContentType(slResult.getMimeType());
      slResultPart.setCharSet(XML_RESPONSE_ENCODING);
    } else {
      slResultPart.setTransferEncoding(null);
      slResultPart.setContentType(slResult.getMimeType());
    }
    formParams.add(slResultPart);

    OutputStream os = connection.getOutputStream();
    log.trace("Sending data");
    Part[] parts = new Part[formParams.size()];
    Part.sendParts(os, formParams.toArray(parts), boundary.getBytes());
    os.close();
    // MultipartRequestEntity PostMethod
    InputStream is = null;
    try {
      is = connection.getInputStream();
    } catch (IOException iox) {
      log.info(iox);
    }
    log.trace("Reading response");
    result = new DataUrlResponse(url.toString(), connection.getResponseCode(),
        is);
    Map<String, String> responseHttpHeaders = new HashMap<String, String>();
    Map<String, List<String>> httpHeaders = connection.getHeaderFields();
    for (Iterator<String> keyIt = httpHeaders.keySet().iterator(); keyIt
        .hasNext();) {
      String key = keyIt.next();
      StringBuffer value = new StringBuffer();
      for (String val : httpHeaders.get(key)) {
        value.append(val);
        value.append(HttpUtil.SEPERATOR[0]);
      }
      String valString = value.substring(0, value.length() - 1);
      if ((key != null) && (value.length() > 0)) {
        responseHttpHeaders.put(key, valString);
      }
    }
    result.setResponseHttpHeaders(responseHttpHeaders);
  }

  @Override
  public DataUrlResponse getResponse() throws IOException {
    return result;
  }

  /**
   * inits protocol, url, httpHeaders, formParams
   * 
   * @param url
   *          must not be null
   */
  @Override
  public void init(URL url) {

    for (int i = 0; i < SUPPORTED_PROTOCOLS.length; i++) {
      if (SUPPORTED_PROTOCOLS[i].toString().equalsIgnoreCase(url.getProtocol())) {
        protocol = SUPPORTED_PROTOCOLS[i];
        break;
      }
    }
    if (protocol == null) {
      throw new SLRuntimeException("Protocol " + url.getProtocol()
          + " not supported for data url");
    }
    this.url = url;
    boundary = "--" + IdFactory.getInstance().createId().toString();
    requestHttpHeaders = new HashMap<String, String>();
    if ((config != null)
        && (config.getProperty(USER_AGENT_PROPERTY_KEY) != null)) {
      requestHttpHeaders.put(HttpUtil.HTTP_HEADER_USER_AGENT, config
          .getProperty(USER_AGENT_PROPERTY_KEY));
    } else {
      requestHttpHeaders
          .put(HttpUtil.HTTP_HEADER_USER_AGENT, DEFAULT_USERAGENT);

    }
    requestHttpHeaders.put(HttpUtil.HTTP_HEADER_CONTENT_TYPE,
        HttpUtil.MULTIPART_FOTMDATA + HttpUtil.SEPERATOR[0]
            + HttpUtil.MULTIPART_FOTMDATA_BOUNDARY + "=" + boundary);

    formParams = new ArrayList<Part>();
    StringPart responseType = new StringPart(FORMPARAM_RESPONSETYPE,
        DEFAULT_RESPONSETYPE);
    responseType.setCharSet("UTF-8");
    responseType.setTransferEncoding(null);
    formParams.add(responseType);
  }

  @Override
  public DataUrlConnectionSPI newInstance() {
    DataUrlConnectionSPI uc = new DataUrlConnectionImpl();
    uc.setConfiguration(config);
    uc.setSSLSocketFactory(sslSocketFactory);
    return uc;
  }

  @Override
  public URL getUrl() {
    return url;
  }

  @Override
  public void setConfiguration(Properties config) {
    this.config = config;
  }

  @Override
  public void setSSLSocketFactory(SSLSocketFactory socketFactory) {
    this.sslSocketFactory = socketFactory;
  }
  
  @Override
  public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
    this.hostnameVerifier = hostnameVerifier;
  }
}