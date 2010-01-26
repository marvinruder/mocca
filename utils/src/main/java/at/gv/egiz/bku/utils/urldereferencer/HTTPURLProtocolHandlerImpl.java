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
package at.gv.egiz.bku.utils.urldereferencer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HTTPURLProtocolHandlerImpl implements URLProtocolHandler {

  private static Log log = LogFactory.getLog(HTTPURLProtocolHandlerImpl.class);

  public final static String HTTP = "http";
  public final static String HTTPS = "https";
  public final static String FORMDATA = "formdata";
  public final static String[] PROTOCOLS = { HTTP, HTTPS, FORMDATA };

  private HostnameVerifier hostnameVerifier;
  private SSLSocketFactory sslSocketFactory;

  public StreamData dereference(String aUrl, URLDereferencerContext aContext)
      throws IOException {
    String urlString = aUrl.toLowerCase().trim();
    if (urlString.startsWith(FORMDATA)) {
      log.debug("Requested to dereference a formdata url");
      return dereferenceFormData(aUrl, aContext);
    }

    URL url = new URL(aUrl);
    if ((!HTTP.equalsIgnoreCase(url.getProtocol()) && (!HTTPS
        .equalsIgnoreCase(url.getProtocol())))) {
      throw new InvalidParameterException("Url " + aUrl + " not supported");
    }
    return dereferenceHTTP(url);
  }

  protected StreamData dereferenceHTTP(URL url) throws IOException {
    log.debug("Dereferencing url: " + url);
    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
    if (httpConn instanceof HttpsURLConnection) {
      log.trace("Detected ssl connection");
      HttpsURLConnection https = (HttpsURLConnection) httpConn;
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
      log.trace("No secure connection with: "+url+ " class="+httpConn.getClass());
    }
    log.trace("Successfully opened connection");
    return new StreamData(url.toString(), httpConn.getContentType(), httpConn
        .getInputStream());
  }

  /**
   * 
   * @param aUrl
   * @param aContext
   * @return
   * @throws IOException if the data cannot be found or reading the stream failed.
   */
  protected StreamData dereferenceFormData(String aUrl,
      URLDereferencerContext aContext) throws IOException {
    log.debug("Dereferencing formdata url: " + aUrl);
    String[] parts = aUrl.split(":", 2);
    FormDataURLSupplier supplier = (FormDataURLSupplier) aContext
        .getProperty(FormDataURLSupplier.PROPERTY_KEY_NAME);
    if (supplier == null) {
      throw new NullPointerException(
          "No FormdataUrlSupplier found in provided context");
    }
    String contentType = supplier.getFormDataContentType(parts[1]);
    InputStream is = supplier.getFormData(parts[1]);
    if (is != null) {
      return new StreamData(aUrl, contentType, is);
    }
     throw new IOException("Cannot dereference url: formdata not found");
  }

  @Override
  public void setHostnameVerifier(HostnameVerifier hostnameVerifier) {
    this.hostnameVerifier = hostnameVerifier;
  }

  @Override
  public void setSSLSocketFactory(SSLSocketFactory socketFactory) {
    this.sslSocketFactory = socketFactory;
  }

}