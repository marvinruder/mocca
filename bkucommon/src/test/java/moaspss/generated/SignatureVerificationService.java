/*
 * Copyright 2011 by Graz University of Technology, Austria
 * MOCCA has been developed by the E-Government Innovation Center EGIZ, a joint
 * initiative of the Federal Chancellery Austria and Graz University of Technology.
 *
 * Licensed under the EUPL, Version 1.1 or - as soon they will be approved by
 * the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * http://www.osor.eu/eupl/
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 *
 * This product combines work with different licenses. See the "NOTICE" text
 * file for details on the various modules and licenses.
 * The "NOTICE" text file is part of the distribution. Any derivative works
 * that you distribute must include a readable copy of the "NOTICE" text file.
 */



package moaspss.generated;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.3-b02-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "SignatureVerificationService", targetNamespace = "http://reference.e-government.gv.at/namespace/moa/wsdl/20020822#", wsdlLocation = "file:/home/clemens/workspace/bku/bkucommon/src/test/wsdl/MOA-SPSS-1.3.wsdl")
public class SignatureVerificationService
    extends Service
{

    private final static URL SIGNATUREVERIFICATIONSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(moaspss.generated.SignatureVerificationService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = moaspss.generated.SignatureVerificationService.class.getResource(".");
            url = new URL(baseUrl, "file:/home/clemens/workspace/bku/bkucommon/src/test/wsdl/MOA-SPSS-1.3.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/home/clemens/workspace/bku/bkucommon/src/test/wsdl/MOA-SPSS-1.3.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SIGNATUREVERIFICATIONSERVICE_WSDL_LOCATION = url;
    }

    public SignatureVerificationService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SignatureVerificationService() {
        super(SIGNATUREVERIFICATIONSERVICE_WSDL_LOCATION, new QName("http://reference.e-government.gv.at/namespace/moa/wsdl/20020822#", "SignatureVerificationService"));
    }

    /**
     * 
     * @return
     *     returns SignatureVerificationPortType
     */
    @WebEndpoint(name = "SignatureVerificationPort")
    public SignatureVerificationPortType getSignatureVerificationPort() {
        return super.getPort(new QName("http://reference.e-government.gv.at/namespace/moa/wsdl/20020822#", "SignatureVerificationPort"), SignatureVerificationPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns SignatureVerificationPortType
     */
    @WebEndpoint(name = "SignatureVerificationPort")
    public SignatureVerificationPortType getSignatureVerificationPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://reference.e-government.gv.at/namespace/moa/wsdl/20020822#", "SignatureVerificationPort"), SignatureVerificationPortType.class, features);
    }

}
