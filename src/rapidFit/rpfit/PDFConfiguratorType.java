//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.27 at 05:05:34 PM BST 
//


package rapidFit.rpfit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PDFConfiguratorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PDFConfiguratorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParameterSubstitution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppendParameterNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConfigurationParameter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AppendAllOthers" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PDFConfiguratorType", propOrder = {
    "parameterSubstitution",
    "appendParameterNames",
    "configurationParameter",
    "appendAllOthers"
})
public class PDFConfiguratorType {

    @XmlElement(name = "ParameterSubstitution")
    protected String parameterSubstitution;
    @XmlElement(name = "AppendParameterNames")
    protected String appendParameterNames;
    @XmlElement(name = "ConfigurationParameter")
    protected String configurationParameter;
    @XmlElement(name = "AppendAllOthers")
    protected String appendAllOthers;

    /**
     * Gets the value of the parameterSubstitution property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParameterSubstitution() {
        return parameterSubstitution;
    }

    /**
     * Sets the value of the parameterSubstitution property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParameterSubstitution(String value) {
        this.parameterSubstitution = value;
    }

    /**
     * Gets the value of the appendParameterNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppendParameterNames() {
        return appendParameterNames;
    }

    /**
     * Sets the value of the appendParameterNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppendParameterNames(String value) {
        this.appendParameterNames = value;
    }

    /**
     * Gets the value of the configurationParameter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationParameter() {
        return configurationParameter;
    }

    /**
     * Sets the value of the configurationParameter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationParameter(String value) {
        this.configurationParameter = value;
    }

    /**
     * Gets the value of the appendAllOthers property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAppendAllOthers() {
        return appendAllOthers;
    }

    /**
     * Sets the value of the appendAllOthers property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAppendAllOthers(String value) {
        this.appendAllOthers = value;
    }

}
