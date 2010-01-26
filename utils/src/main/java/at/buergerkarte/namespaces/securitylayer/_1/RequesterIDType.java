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
//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-520 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.07.25 at 10:41:37 AM GMT 
//


package at.buergerkarte.namespaces.securitylayer._1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for RequesterIDType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequesterIDType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.buergerkarte.at/namespaces/securitylayer/1.2#>RequesterIDSimpleType">
 *       &lt;attribute name="AuthenticationClass" use="required" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}AuthenticationClassType" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequesterIDType", propOrder = {
    "value"
})
public class RequesterIDType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "AuthenticationClass", required = true)
    protected AuthenticationClassType authenticationClass;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the authenticationClass property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationClassType }
     *     
     */
    public AuthenticationClassType getAuthenticationClass() {
        return authenticationClass;
    }

    /**
     * Sets the value of the authenticationClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationClassType }
     *     
     */
    public void setAuthenticationClass(AuthenticationClassType value) {
        this.authenticationClass = value;
    }

}
