//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.29 at 11:47:00 AM BST 
//


package rapidFit.model;

import java.io.Serializable;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ComponentProjectionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComponentProjectionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="DataBins" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="PDFPoints" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="LogY" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LogX" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="WidthKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ColorKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="StyleKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CompNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ComponentNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CombinationNames" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Xmax" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Xmin" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Ymax" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="Ymin" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="XTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="YTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrustNumerical" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="CalcChi2" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="DrawPull" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LimitPulls" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AddLHCb" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AddRightLHCb" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="LegendTextSize" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="TopRightLegend" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TopLeftLegend" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="BottomRightLegend" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="BottomLeftLegend" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="NoLegend" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="UseSpline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Threads" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="FixedIntegrationPoints" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="UseGSLNumericalIntegration" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PlotAllCombinations" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="DefaultDiscreteValue" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="XaxisTitleScale" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="XaxisLabelScale" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="YaxisTitleScale" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="YaxisLabelScale" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="OnlyUseCombination" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="OnlyUseComponent" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ComponentProjectionType", propOrder = {

})
public class ComponentProjectionType
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "DataBins")
    protected BigInteger dataBins;
    @XmlElement(name = "PDFPoints")
    protected BigInteger pdfPoints;
    @XmlElement(name = "LogY")
    protected Boolean logY;
    @XmlElement(name = "LogX")
    protected Boolean logX;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "WidthKey")
    protected String widthKey;
    @XmlElement(name = "ColorKey")
    protected String colorKey;
    @XmlElement(name = "StyleKey")
    protected String styleKey;
    @XmlElement(name = "Title")
    protected String title;
    @XmlElement(name = "CompNames")
    protected String compNames;
    @XmlElement(name = "ComponentNames")
    protected String componentNames;
    @XmlElement(name = "CombinationNames")
    protected String combinationNames;
    @XmlElement(name = "Xmax", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double xmax;
    @XmlElement(name = "Xmin", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double xmin;
    @XmlElement(name = "Ymax", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double ymax;
    @XmlElement(name = "Ymin", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double ymin;
    @XmlElement(name = "XTitle")
    protected String xTitle;
    @XmlElement(name = "YTitle")
    protected String yTitle;
    @XmlElement(name = "TrustNumerical")
    protected Boolean trustNumerical;
    @XmlElement(name = "CalcChi2")
    protected Boolean calcChi2;
    @XmlElement(name = "DrawPull")
    protected Boolean drawPull;
    @XmlElement(name = "LimitPulls")
    protected Boolean limitPulls;
    @XmlElement(name = "AddLHCb")
    protected Boolean addLHCb;
    @XmlElement(name = "AddRightLHCb")
    protected Boolean addRightLHCb;
    @XmlElement(name = "LegendTextSize", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double legendTextSize;
    @XmlElement(name = "TopRightLegend")
    protected Boolean topRightLegend;
    @XmlElement(name = "TopLeftLegend")
    protected Boolean topLeftLegend;
    @XmlElement(name = "BottomRightLegend")
    protected Boolean bottomRightLegend;
    @XmlElement(name = "BottomLeftLegend")
    protected Boolean bottomLeftLegend;
    @XmlElement(name = "NoLegend")
    protected Boolean noLegend;
    @XmlElement(name = "UseSpline")
    protected Boolean useSpline;
    @XmlElement(name = "Threads")
    protected BigInteger threads;
    @XmlElement(name = "FixedIntegrationPoints")
    protected BigInteger fixedIntegrationPoints;
    @XmlElement(name = "UseGSLNumericalIntegration")
    protected Boolean useGSLNumericalIntegration;
    @XmlElement(name = "PlotAllCombinations")
    protected Boolean plotAllCombinations;
    @XmlElement(name = "DefaultDiscreteValue", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double defaultDiscreteValue;
    @XmlElement(name = "XaxisTitleScale", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double xaxisTitleScale;
    @XmlElement(name = "XaxisLabelScale", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double xaxisLabelScale;
    @XmlElement(name = "YaxisTitleScale", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double yaxisTitleScale;
    @XmlElement(name = "YaxisLabelScale", type = String.class)
    @XmlJavaTypeAdapter(Adapter1 .class)
    @XmlSchemaType(name = "double")
    protected Double yaxisLabelScale;
    @XmlElement(name = "OnlyUseCombination")
    protected BigInteger onlyUseCombination;
    @XmlElement(name = "OnlyUseComponent")
    protected BigInteger onlyUseComponent;

    /**
     * Gets the value of the dataBins property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDataBins() {
        return dataBins;
    }

    /**
     * Sets the value of the dataBins property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDataBins(BigInteger value) {
        this.dataBins = value;
    }

    /**
     * Gets the value of the pdfPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPDFPoints() {
        return pdfPoints;
    }

    /**
     * Sets the value of the pdfPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPDFPoints(BigInteger value) {
        this.pdfPoints = value;
    }

    /**
     * Gets the value of the logY property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLogY() {
        return logY;
    }

    /**
     * Sets the value of the logY property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLogY(Boolean value) {
        this.logY = value;
    }

    /**
     * Gets the value of the logX property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLogX() {
        return logX;
    }

    /**
     * Sets the value of the logX property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLogX(Boolean value) {
        this.logX = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the widthKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidthKey() {
        return widthKey;
    }

    /**
     * Sets the value of the widthKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidthKey(String value) {
        this.widthKey = value;
    }

    /**
     * Gets the value of the colorKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getColorKey() {
        return colorKey;
    }

    /**
     * Sets the value of the colorKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setColorKey(String value) {
        this.colorKey = value;
    }

    /**
     * Gets the value of the styleKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStyleKey() {
        return styleKey;
    }

    /**
     * Sets the value of the styleKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStyleKey(String value) {
        this.styleKey = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the compNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompNames() {
        return compNames;
    }

    /**
     * Sets the value of the compNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompNames(String value) {
        this.compNames = value;
    }

    /**
     * Gets the value of the componentNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComponentNames() {
        return componentNames;
    }

    /**
     * Sets the value of the componentNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComponentNames(String value) {
        this.componentNames = value;
    }

    /**
     * Gets the value of the combinationNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombinationNames() {
        return combinationNames;
    }

    /**
     * Sets the value of the combinationNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombinationNames(String value) {
        this.combinationNames = value;
    }

    /**
     * Gets the value of the xmax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getXmax() {
        return xmax;
    }

    /**
     * Sets the value of the xmax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmax(Double value) {
        this.xmax = value;
    }

    /**
     * Gets the value of the xmin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getXmin() {
        return xmin;
    }

    /**
     * Sets the value of the xmin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmin(Double value) {
        this.xmin = value;
    }

    /**
     * Gets the value of the ymax property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getYmax() {
        return ymax;
    }

    /**
     * Sets the value of the ymax property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYmax(Double value) {
        this.ymax = value;
    }

    /**
     * Gets the value of the ymin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getYmin() {
        return ymin;
    }

    /**
     * Sets the value of the ymin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYmin(Double value) {
        this.ymin = value;
    }

    /**
     * Gets the value of the xTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXTitle() {
        return xTitle;
    }

    /**
     * Sets the value of the xTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXTitle(String value) {
        this.xTitle = value;
    }

    /**
     * Gets the value of the yTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYTitle() {
        return yTitle;
    }

    /**
     * Sets the value of the yTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYTitle(String value) {
        this.yTitle = value;
    }

    /**
     * Gets the value of the trustNumerical property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTrustNumerical() {
        return trustNumerical;
    }

    /**
     * Sets the value of the trustNumerical property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTrustNumerical(Boolean value) {
        this.trustNumerical = value;
    }

    /**
     * Gets the value of the calcChi2 property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCalcChi2() {
        return calcChi2;
    }

    /**
     * Sets the value of the calcChi2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCalcChi2(Boolean value) {
        this.calcChi2 = value;
    }

    /**
     * Gets the value of the drawPull property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDrawPull() {
        return drawPull;
    }

    /**
     * Sets the value of the drawPull property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDrawPull(Boolean value) {
        this.drawPull = value;
    }

    /**
     * Gets the value of the limitPulls property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLimitPulls() {
        return limitPulls;
    }

    /**
     * Sets the value of the limitPulls property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLimitPulls(Boolean value) {
        this.limitPulls = value;
    }

    /**
     * Gets the value of the addLHCb property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddLHCb() {
        return addLHCb;
    }

    /**
     * Sets the value of the addLHCb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddLHCb(Boolean value) {
        this.addLHCb = value;
    }

    /**
     * Gets the value of the addRightLHCb property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAddRightLHCb() {
        return addRightLHCb;
    }

    /**
     * Sets the value of the addRightLHCb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAddRightLHCb(Boolean value) {
        this.addRightLHCb = value;
    }

    /**
     * Gets the value of the legendTextSize property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getLegendTextSize() {
        return legendTextSize;
    }

    /**
     * Sets the value of the legendTextSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegendTextSize(Double value) {
        this.legendTextSize = value;
    }

    /**
     * Gets the value of the topRightLegend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTopRightLegend() {
        return topRightLegend;
    }

    /**
     * Sets the value of the topRightLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTopRightLegend(Boolean value) {
        this.topRightLegend = value;
    }

    /**
     * Gets the value of the topLeftLegend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTopLeftLegend() {
        return topLeftLegend;
    }

    /**
     * Sets the value of the topLeftLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTopLeftLegend(Boolean value) {
        this.topLeftLegend = value;
    }

    /**
     * Gets the value of the bottomRightLegend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBottomRightLegend() {
        return bottomRightLegend;
    }

    /**
     * Sets the value of the bottomRightLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBottomRightLegend(Boolean value) {
        this.bottomRightLegend = value;
    }

    /**
     * Gets the value of the bottomLeftLegend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBottomLeftLegend() {
        return bottomLeftLegend;
    }

    /**
     * Sets the value of the bottomLeftLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBottomLeftLegend(Boolean value) {
        this.bottomLeftLegend = value;
    }

    /**
     * Gets the value of the noLegend property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoLegend() {
        return noLegend;
    }

    /**
     * Sets the value of the noLegend property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoLegend(Boolean value) {
        this.noLegend = value;
    }

    /**
     * Gets the value of the useSpline property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseSpline() {
        return useSpline;
    }

    /**
     * Sets the value of the useSpline property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseSpline(Boolean value) {
        this.useSpline = value;
    }

    /**
     * Gets the value of the threads property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getThreads() {
        return threads;
    }

    /**
     * Sets the value of the threads property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setThreads(BigInteger value) {
        this.threads = value;
    }

    /**
     * Gets the value of the fixedIntegrationPoints property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFixedIntegrationPoints() {
        return fixedIntegrationPoints;
    }

    /**
     * Sets the value of the fixedIntegrationPoints property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFixedIntegrationPoints(BigInteger value) {
        this.fixedIntegrationPoints = value;
    }

    /**
     * Gets the value of the useGSLNumericalIntegration property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUseGSLNumericalIntegration() {
        return useGSLNumericalIntegration;
    }

    /**
     * Sets the value of the useGSLNumericalIntegration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUseGSLNumericalIntegration(Boolean value) {
        this.useGSLNumericalIntegration = value;
    }

    /**
     * Gets the value of the plotAllCombinations property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPlotAllCombinations() {
        return plotAllCombinations;
    }

    /**
     * Sets the value of the plotAllCombinations property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPlotAllCombinations(Boolean value) {
        this.plotAllCombinations = value;
    }

    /**
     * Gets the value of the defaultDiscreteValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getDefaultDiscreteValue() {
        return defaultDiscreteValue;
    }

    /**
     * Sets the value of the defaultDiscreteValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultDiscreteValue(Double value) {
        this.defaultDiscreteValue = value;
    }

    /**
     * Gets the value of the xaxisTitleScale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getXaxisTitleScale() {
        return xaxisTitleScale;
    }

    /**
     * Sets the value of the xaxisTitleScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXaxisTitleScale(Double value) {
        this.xaxisTitleScale = value;
    }

    /**
     * Gets the value of the xaxisLabelScale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getXaxisLabelScale() {
        return xaxisLabelScale;
    }

    /**
     * Sets the value of the xaxisLabelScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXaxisLabelScale(Double value) {
        this.xaxisLabelScale = value;
    }

    /**
     * Gets the value of the yaxisTitleScale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getYaxisTitleScale() {
        return yaxisTitleScale;
    }

    /**
     * Sets the value of the yaxisTitleScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYaxisTitleScale(Double value) {
        this.yaxisTitleScale = value;
    }

    /**
     * Gets the value of the yaxisLabelScale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Double getYaxisLabelScale() {
        return yaxisLabelScale;
    }

    /**
     * Sets the value of the yaxisLabelScale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setYaxisLabelScale(Double value) {
        this.yaxisLabelScale = value;
    }

    /**
     * Gets the value of the onlyUseCombination property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOnlyUseCombination() {
        return onlyUseCombination;
    }

    /**
     * Sets the value of the onlyUseCombination property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOnlyUseCombination(BigInteger value) {
        this.onlyUseCombination = value;
    }

    /**
     * Gets the value of the onlyUseComponent property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getOnlyUseComponent() {
        return onlyUseComponent;
    }

    /**
     * Sets the value of the onlyUseComponent property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setOnlyUseComponent(BigInteger value) {
        this.onlyUseComponent = value;
    }

}
