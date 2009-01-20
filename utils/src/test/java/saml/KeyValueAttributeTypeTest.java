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

package saml;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import oasis.names.tc.saml._1_0.assertion.AnyType;
import oasis.names.tc.saml._1_0.assertion.AssertionType;
import oasis.names.tc.saml._1_0.assertion.AttributeStatementType;
import oasis.names.tc.saml._1_0.assertion.AttributeType;
import oasis.names.tc.saml._1_0.assertion.NameIdentifierType;
import oasis.names.tc.saml._1_0.assertion.ObjectFactory;
import oasis.names.tc.saml._1_0.assertion.StatementAbstractType;
import oasis.names.tc.saml._1_0.assertion.SubjectType;
import org.junit.Ignore;
import org.junit.Test;
import org.w3._2000._09.xmldsig_.KeyValueType;
import org.w3._2000._09.xmldsig_.RSAKeyValueType;
import org.w3c.dom.Element;

/**
 *
 * @author Clemens Orthacker <clemens.orthacker@iaik.tugraz.at>
 */
@Ignore
public class KeyValueAttributeTypeTest {

  @Test
  public void testAttrStatement() throws FileNotFoundException, JAXBException {
    JAXBContext ctx = JAXBContext.newInstance(ObjectFactory.class, org.w3._2000._09.xmldsig_.ObjectFactory.class);
    Unmarshaller um = ctx.createUnmarshaller();

    JAXBElement<AssertionType> assertion = (JAXBElement<AssertionType>) um.unmarshal(new File("/home/clemens/workspace/schema-base/src/main/schema/test/saml10.xml"));
    List<StatementAbstractType> statements = assertion.getValue().getStatementOrSubjectStatementOrAuthenticationStatement();
    for (StatementAbstractType stmt : statements) {
      if (stmt instanceof AttributeStatementType) {
        System.out.println("AttributeStatement");
        List<AttributeType> attrs = ((AttributeStatementType) stmt).getAttribute();
        for (AttributeType attr : attrs) {
          List<AnyType> attrValue = attr.getAttributeValue();
          System.out.println(attrValue.size() + " AttributeValue(s)");
          for (AnyType attrValueT : attrValue) {
            List<Object> attrValueContent = attrValueT.getContent();
            System.out.println("  AttributeValue: " + attrValueContent.size() + " child nodes");
            for (Object node : attrValueContent) {
              if (node instanceof String) {
                System.out.println("   - CDATA: " + node);
              } else if (node instanceof Element) {
                System.out.println("   - DOM Element: " + ((Element)node).getTagName());
              } else {
                System.out.println("   - " + node.getClass());
              }
            }
          }

        }
      }
    }
  }

  @Test
  public void testAttributeStatement() throws JAXBException {

    org.w3._2000._09.xmldsig_.ObjectFactory dsOF = new org.w3._2000._09.xmldsig_.ObjectFactory();
    RSAKeyValueType rsaKeyValueType = dsOF.createRSAKeyValueType();
    rsaKeyValueType.setExponent("1234".getBytes());
    rsaKeyValueType.setModulus("5678".getBytes());

    JAXBElement<RSAKeyValueType> rsaKeyValue = dsOF.createRSAKeyValue(rsaKeyValueType);


//    KeyValueType kvT = dsOF.createKeyValueType();
//    kvT.getContent().add(rsaKeyValue);
//    JAXBElement<KeyValueType> kv = dsOF.createKeyValue(kvT);

    ObjectFactory saml10OF = new ObjectFactory();
    AssertionType assertionT = saml10OF.createAssertionType();
    
    AttributeStatementType attrStatementT = saml10OF.createAttributeStatementType();
    NameIdentifierType nameIdT = saml10OF.createNameIdentifierType();
    nameIdT.setFormat("format");
    nameIdT.setNameQualifier("qualifier");
    nameIdT.setValue("value");
    JAXBElement<NameIdentifierType> subjNameId = saml10OF.createNameIdentifier(nameIdT);
    SubjectType subjT = saml10OF.createSubjectType();
    subjT.getContent().add(subjNameId);
    attrStatementT.setSubject(subjT);


    AttributeType attrT = saml10OF.createAttributeType();
//    QName keyVal = new QName("testNS", "keyVal");
    attrT.setAttributeName("RSAkeyvalue");
    attrT.setAttributeNamespace("lskdfjlk");
    AnyType attrValueT = saml10OF.createAnyType();
    attrValueT.getContent().add(rsaKeyValue);
    attrT.getAttributeValue().add(attrValueT); //kv); //keyValue); //new JAXBElement(keyVal, declaredType, attrT))
    attrStatementT.getAttribute().add(attrT);
    assertionT.getStatementOrSubjectStatementOrAuthenticationStatement().add(attrStatementT);
    JAXBElement<AssertionType> assertion = saml10OF.createAssertion(assertionT);

    JAXBContext ctx = JAXBContext.newInstance(saml10OF.getClass());
    Marshaller m = ctx.createMarshaller();
    m.marshal(assertion, System.out);
  }
}
