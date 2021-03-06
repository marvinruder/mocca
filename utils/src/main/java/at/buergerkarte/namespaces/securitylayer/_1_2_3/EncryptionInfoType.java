//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.01 at 04:42:31 PM CEST 
//


package at.buergerkarte.namespaces.securitylayer._1_2_3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for EncryptionInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EncryptionInfoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="EncryptionEnvironment" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}Base64XMLOptRefContentType"/&gt;
 *         &lt;element name="EncryptedKeyLocation" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="ParentSelector" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
 *                 &lt;attribute name="NodeCount" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="Supplement" type="{http://www.buergerkarte.at/namespaces/securitylayer/1.2#}DataObjectAssociationType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EncryptionInfoType", propOrder = {
    "encryptionEnvironment",
    "encryptedKeyLocation",
    "supplement"
})
public class EncryptionInfoType {

    @XmlElement(name = "EncryptionEnvironment", required = true)
    protected Base64XMLOptRefContentType encryptionEnvironment;
    @XmlElement(name = "EncryptedKeyLocation")
    protected EncryptionInfoType.EncryptedKeyLocation encryptedKeyLocation;
    @XmlElement(name = "Supplement")
    protected List<DataObjectAssociationType> supplement;

    /**
     * Gets the value of the encryptionEnvironment property.
     * 
     * @return
     *     possible object is
     *     {@link Base64XMLOptRefContentType }
     *     
     */
    public Base64XMLOptRefContentType getEncryptionEnvironment() {
        return encryptionEnvironment;
    }

    /**
     * Sets the value of the encryptionEnvironment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Base64XMLOptRefContentType }
     *     
     */
    public void setEncryptionEnvironment(Base64XMLOptRefContentType value) {
        this.encryptionEnvironment = value;
    }

    /**
     * Gets the value of the encryptedKeyLocation property.
     * 
     * @return
     *     possible object is
     *     {@link EncryptionInfoType.EncryptedKeyLocation }
     *     
     */
    public EncryptionInfoType.EncryptedKeyLocation getEncryptedKeyLocation() {
        return encryptedKeyLocation;
    }

    /**
     * Sets the value of the encryptedKeyLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncryptionInfoType.EncryptedKeyLocation }
     *     
     */
    public void setEncryptedKeyLocation(EncryptionInfoType.EncryptedKeyLocation value) {
        this.encryptedKeyLocation = value;
    }

    /**
     * Gets the value of the supplement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supplement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupplement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DataObjectAssociationType }
     * 
     * 
     */
    public List<DataObjectAssociationType> getSupplement() {
        if (supplement == null) {
            supplement = new ArrayList<DataObjectAssociationType>();
        }
        return this.supplement;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;attribute name="ParentSelector" use="required" type="{http://www.w3.org/2001/XMLSchema}token" /&gt;
     *       &lt;attribute name="NodeCount" use="required" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class EncryptedKeyLocation {

        @XmlAttribute(name = "ParentSelector", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String parentSelector;
        @XmlAttribute(name = "NodeCount", required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger nodeCount;

        /**
         * Gets the value of the parentSelector property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getParentSelector() {
            return parentSelector;
        }

        /**
         * Sets the value of the parentSelector property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setParentSelector(String value) {
            this.parentSelector = value;
        }

        /**
         * Gets the value of the nodeCount property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNodeCount() {
            return nodeCount;
        }

        /**
         * Sets the value of the nodeCount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNodeCount(BigInteger value) {
            this.nodeCount = value;
        }

    }

}
