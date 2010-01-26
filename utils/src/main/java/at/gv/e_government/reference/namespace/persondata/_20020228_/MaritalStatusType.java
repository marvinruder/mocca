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
// Generated on: 2008.07.21 at 09:43:54 AM GMT 
//


package at.gv.e_government.reference.namespace.persondata._20020228_;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MaritalStatusType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MaritalStatusType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="single"/>
 *     &lt;enumeration value="married"/>
 *     &lt;enumeration value="divorced"/>
 *     &lt;enumeration value="widowed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MaritalStatusType")
@XmlEnum
public enum MaritalStatusType {

    @XmlEnumValue("single")
    SINGLE("single"),
    @XmlEnumValue("married")
    MARRIED("married"),
    @XmlEnumValue("divorced")
    DIVORCED("divorced"),
    @XmlEnumValue("widowed")
    WIDOWED("widowed");
    private final String value;

    MaritalStatusType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MaritalStatusType fromValue(String v) {
        for (MaritalStatusType c: MaritalStatusType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
