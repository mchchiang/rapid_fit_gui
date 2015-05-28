//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.28 at 09:17:35 AM BST 
//


package rapidFit.rpfit;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DataSetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataSetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CutString" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FileName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NTuplePath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NumberEvents" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="PDFConfigurator" type="{}PDFConfiguratorType" minOccurs="0"/>
 *         &lt;element name="StartingEntry" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="PhaseSpaceBoundary" type="{}PhaseSpaceBoundaryType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataSetType", propOrder = {
    "source",
    "cutString",
    "fileName",
    "nTuplePath",
    "numberEvents",
    "pdfConfigurator",
    "startingEntry",
    "phaseSpaceBoundary"
})
public class DataSetType {

    @XmlElement(name = "Source", required = true)
    protected String source;
    @XmlElement(name = "CutString")
    protected String cutString;
    @XmlElement(name = "FileName", required = true)
    protected String fileName;
    @XmlElement(name = "NTuplePath")
    protected String nTuplePath;
    @XmlElement(name = "NumberEvents")
    protected BigInteger numberEvents;
    @XmlElement(name = "PDFConfigurator")
    protected PDFConfiguratorType pdfConfigurator;
    @XmlElement(name = "StartingEntry")
    protected BigInteger startingEntry;
    @XmlElement(name = "PhaseSpaceBoundary", required = true)
    protected PhaseSpaceBoundaryType phaseSpaceBoundary;

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the cutString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCutString() {
        return cutString;
    }

    /**
     * Sets the value of the cutString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCutString(String value) {
        this.cutString = value;
    }

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the nTuplePath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNTuplePath() {
        return nTuplePath;
    }

    /**
     * Sets the value of the nTuplePath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNTuplePath(String value) {
        this.nTuplePath = value;
    }

    /**
     * Gets the value of the numberEvents property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberEvents() {
        return numberEvents;
    }

    /**
     * Sets the value of the numberEvents property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberEvents(BigInteger value) {
        this.numberEvents = value;
    }

    /**
     * Gets the value of the pdfConfigurator property.
     * 
     * @return
     *     possible object is
     *     {@link PDFConfiguratorType }
     *     
     */
    public PDFConfiguratorType getPDFConfigurator() {
        return pdfConfigurator;
    }

    /**
     * Sets the value of the pdfConfigurator property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFConfiguratorType }
     *     
     */
    public void setPDFConfigurator(PDFConfiguratorType value) {
        this.pdfConfigurator = value;
    }

    /**
     * Gets the value of the startingEntry property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStartingEntry() {
        return startingEntry;
    }

    /**
     * Sets the value of the startingEntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStartingEntry(BigInteger value) {
        this.startingEntry = value;
    }

    /**
     * Gets the value of the phaseSpaceBoundary property.
     * 
     * @return
     *     possible object is
     *     {@link PhaseSpaceBoundaryType }
     *     
     */
    public PhaseSpaceBoundaryType getPhaseSpaceBoundary() {
        return phaseSpaceBoundary;
    }

    /**
     * Sets the value of the phaseSpaceBoundary property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhaseSpaceBoundaryType }
     *     
     */
    public void setPhaseSpaceBoundary(PhaseSpaceBoundaryType value) {
        this.phaseSpaceBoundary = value;
    }

}
