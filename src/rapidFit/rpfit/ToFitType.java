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
 * <p>Java class for ToFitType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ToFitType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="ConstraintFunction" type="{}ConstraintFunctionType"/>
 *         &lt;group ref="{}ActualFitGroup"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ToFitType", propOrder = {
    "constraintFunction",
    "prodPDF",
    "normalisedSumPDF",
    "pdf",
    "dataSet"
})
public class ToFitType {

    @XmlElement(name = "ConstraintFunction")
    protected ConstraintFunctionType constraintFunction;
    @XmlElement(name = "ProdPDF")
    protected PDFOperatorType prodPDF;
    @XmlElement(name = "NormalisedSumPDF")
    protected PDFOperatorType normalisedSumPDF;
    @XmlElement(name = "PDF")
    protected PDFType pdf;
    @XmlElement(name = "DataSet")
    protected DataSetType dataSet;

    /**
     * Gets the value of the constraintFunction property.
     * 
     * @return
     *     possible object is
     *     {@link ConstraintFunctionType }
     *     
     */
    public ConstraintFunctionType getConstraintFunction() {
        return constraintFunction;
    }

    /**
     * Sets the value of the constraintFunction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConstraintFunctionType }
     *     
     */
    public void setConstraintFunction(ConstraintFunctionType value) {
        this.constraintFunction = value;
    }

    /**
     * Gets the value of the prodPDF property.
     * 
     * @return
     *     possible object is
     *     {@link PDFOperatorType }
     *     
     */
    public PDFOperatorType getProdPDF() {
        return prodPDF;
    }

    /**
     * Sets the value of the prodPDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFOperatorType }
     *     
     */
    public void setProdPDF(PDFOperatorType value) {
        this.prodPDF = value;
    }

    /**
     * Gets the value of the normalisedSumPDF property.
     * 
     * @return
     *     possible object is
     *     {@link PDFOperatorType }
     *     
     */
    public PDFOperatorType getNormalisedSumPDF() {
        return normalisedSumPDF;
    }

    /**
     * Sets the value of the normalisedSumPDF property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFOperatorType }
     *     
     */
    public void setNormalisedSumPDF(PDFOperatorType value) {
        this.normalisedSumPDF = value;
    }

    /**
     * Gets the value of the pdf property.
     * 
     * @return
     *     possible object is
     *     {@link PDFType }
     *     
     */
    public PDFType getPDF() {
        return pdf;
    }

    /**
     * Sets the value of the pdf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDFType }
     *     
     */
    public void setPDF(PDFType value) {
        this.pdf = value;
    }

    /**
     * Gets the value of the dataSet property.
     * 
     * @return
     *     possible object is
     *     {@link DataSetType }
     *     
     */
    public DataSetType getDataSet() {
        return dataSet;
    }

    /**
     * Sets the value of the dataSet property.
     * 
     * @param value
     *     allowed object is
     *     {@link DataSetType }
     *     
     */
    public void setDataSet(DataSetType value) {
        this.dataSet = value;
    }

}
