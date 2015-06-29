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
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PDFOperatorType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PDFOperatorType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="2" minOccurs="2">
 *         &lt;element name="ProdPDF" type="{}PDFOperatorType"/>
 *         &lt;element name="NormalisedSumPDF" type="{}PDFOperatorType"/>
 *         &lt;element name="PDF" type="{}PDFType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PDFOperatorType", propOrder = {
    "prodPDFOrNormalisedSumPDFOrPDF"
})
public class PDFOperatorType implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElementRefs({
        @XmlElementRef(name = "NormalisedSumPDF", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "PDF", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "ProdPDF", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<? extends Serializable>> prodPDFOrNormalisedSumPDFOrPDF;

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
     * {@link JAXBElement }{@code <}{@link PDFType }{@code >}
     * {@link JAXBElement }{@code <}{@link PDFOperatorType }{@code >}
     * {@link JAXBElement }{@code <}{@link PDFOperatorType }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends Serializable>> getProdPDFOrNormalisedSumPDFOrPDF() {
        if (prodPDFOrNormalisedSumPDFOrPDF == null) {
            prodPDFOrNormalisedSumPDFOrPDF = new ArrayList<JAXBElement<? extends Serializable>>();
        }
        return this.prodPDFOrNormalisedSumPDFOrPDF;
    }

}
