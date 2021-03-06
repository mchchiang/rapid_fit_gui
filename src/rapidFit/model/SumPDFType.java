//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.29 at 11:47:00 AM BST 
//


package rapidFit.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SumPDFType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SumPDFType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FractionName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;choice maxOccurs="2" minOccurs="2">
 *           &lt;element name="ProdPDF" type="{}ProdPDFType"/>
 *           &lt;element name="NormalisedSumPDF" type="{}SumPDFType"/>
 *           &lt;element name="PDF" type="{}PDFType"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SumPDFType", propOrder = {
    "fractionName",
    "prodPDFOrNormalisedSumPDFOrPDF"
})
public class SumPDFType implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "FractionName")
    protected String fractionName;
    @XmlElements({
        @XmlElement(name = "ProdPDF", type = ProdPDFType.class),
        @XmlElement(name = "NormalisedSumPDF", type = SumPDFType.class),
        @XmlElement(name = "PDF", type = PDFType.class)
    })
    protected List<Serializable> prodPDFOrNormalisedSumPDFOrPDF;

    /**
     * Gets the value of the fractionName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFractionName() {
        return fractionName;
    }

    /**
     * Sets the value of the fractionName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFractionName(String value) {
        this.fractionName = value;
    }

    /**
     * Gets the value of the prodPDFOrNormalisedSumPDFOrPDF property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prodPDFOrNormalisedSumPDFOrPDF property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProdPDFOrNormalisedSumPDFOrPDF().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProdPDFType }
     * {@link SumPDFType }
     * {@link PDFType }
     * 
     * 
     */
    public List<Serializable> getProdPDFOrNormalisedSumPDFOrPDF() {
        if (prodPDFOrNormalisedSumPDFOrPDF == null) {
            prodPDFOrNormalisedSumPDFOrPDF = new ArrayList<Serializable>();
        }
        return this.prodPDFOrNormalisedSumPDFOrPDF;
    }

}
