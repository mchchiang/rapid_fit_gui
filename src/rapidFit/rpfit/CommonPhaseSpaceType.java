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
 * <p>Java class for CommonPhaseSpaceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommonPhaseSpaceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
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
@XmlType(name = "CommonPhaseSpaceType", propOrder = {
    "phaseSpaceBoundary"
})
public class CommonPhaseSpaceType {

    @XmlElement(name = "PhaseSpaceBoundary", required = true)
    protected PhaseSpaceBoundaryType phaseSpaceBoundary;

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
