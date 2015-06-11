//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.10 at 01:24:54 PM BST 
//


package rapidFit.rpfit;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the rapidFit.rpfit package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RapidFit_QNAME = new QName("", "RapidFit");
    private final static QName _PDFOperatorTypePDF_QNAME = new QName("", "PDF");
    private final static QName _PDFOperatorTypeProdPDF_QNAME = new QName("", "ProdPDF");
    private final static QName _PDFOperatorTypeNormalisedSumPDF_QNAME = new QName("", "NormalisedSumPDF");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: rapidFit.rpfit
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RapidFitType }
     * 
     */
    public RapidFitType createRapidFitType() {
        return new RapidFitType();
    }

    /**
     * Create an instance of {@link PDFConfiguratorType }
     * 
     */
    public PDFConfiguratorType createPDFConfiguratorType() {
        return new PDFConfiguratorType();
    }

    /**
     * Create an instance of {@link ConstraintFunctionType }
     * 
     */
    public ConstraintFunctionType createConstraintFunctionType() {
        return new ConstraintFunctionType();
    }

    /**
     * Create an instance of {@link DataSetType }
     * 
     */
    public DataSetType createDataSetType() {
        return new DataSetType();
    }

    /**
     * Create an instance of {@link ScanParamType }
     * 
     */
    public ScanParamType createScanParamType() {
        return new ScanParamType();
    }

    /**
     * Create an instance of {@link PhysicsParameterType }
     * 
     */
    public PhysicsParameterType createPhysicsParameterType() {
        return new PhysicsParameterType();
    }

    /**
     * Create an instance of {@link PDFType }
     * 
     */
    public PDFType createPDFType() {
        return new PDFType();
    }

    /**
     * Create an instance of {@link PDFOperatorType }
     * 
     */
    public PDFOperatorType createPDFOperatorType() {
        return new PDFOperatorType();
    }

    /**
     * Create an instance of {@link PrecalculatorType }
     * 
     */
    public PrecalculatorType createPrecalculatorType() {
        return new PrecalculatorType();
    }

    /**
     * Create an instance of {@link ParameterSetType }
     * 
     */
    public ParameterSetType createParameterSetType() {
        return new ParameterSetType();
    }

    /**
     * Create an instance of {@link ExternalConstMatrixType }
     * 
     */
    public ExternalConstMatrixType createExternalConstMatrixType() {
        return new ExternalConstMatrixType();
    }

    /**
     * Create an instance of {@link ObservableType }
     * 
     */
    public ObservableType createObservableType() {
        return new ObservableType();
    }

    /**
     * Create an instance of {@link ComponentProjectionType }
     * 
     */
    public ComponentProjectionType createComponentProjectionType() {
        return new ComponentProjectionType();
    }

    /**
     * Create an instance of {@link ProdPDFType }
     * 
     */
    public ProdPDFType createProdPDFType() {
        return new ProdPDFType();
    }

    /**
     * Create an instance of {@link PhaseSpaceBoundaryType }
     * 
     */
    public PhaseSpaceBoundaryType createPhaseSpaceBoundaryType() {
        return new PhaseSpaceBoundaryType();
    }

    /**
     * Create an instance of {@link CommonPhaseSpaceType }
     * 
     */
    public CommonPhaseSpaceType createCommonPhaseSpaceType() {
        return new CommonPhaseSpaceType();
    }

    /**
     * Create an instance of {@link OutputType }
     * 
     */
    public OutputType createOutputType() {
        return new OutputType();
    }

    /**
     * Create an instance of {@link FitFunctionType }
     * 
     */
    public FitFunctionType createFitFunctionType() {
        return new FitFunctionType();
    }

    /**
     * Create an instance of {@link ExternalConstraintType }
     * 
     */
    public ExternalConstraintType createExternalConstraintType() {
        return new ExternalConstraintType();
    }

    /**
     * Create an instance of {@link TwoDScanType }
     * 
     */
    public TwoDScanType createTwoDScanType() {
        return new TwoDScanType();
    }

    /**
     * Create an instance of {@link ToFitType }
     * 
     */
    public ToFitType createToFitType() {
        return new ToFitType();
    }

    /**
     * Create an instance of {@link PDFExpressionType }
     * 
     */
    public PDFExpressionType createPDFExpressionType() {
        return new PDFExpressionType();
    }

    /**
     * Create an instance of {@link MinimiserType }
     * 
     */
    public MinimiserType createMinimiserType() {
        return new MinimiserType();
    }

    /**
     * Create an instance of {@link SumPDFType }
     * 
     */
    public SumPDFType createSumPDFType() {
        return new SumPDFType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RapidFitType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RapidFit")
    public JAXBElement<RapidFitType> createRapidFit(RapidFitType value) {
        return new JAXBElement<RapidFitType>(_RapidFit_QNAME, RapidFitType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PDFType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PDF", scope = PDFOperatorType.class)
    public JAXBElement<PDFType> createPDFOperatorTypePDF(PDFType value) {
        return new JAXBElement<PDFType>(_PDFOperatorTypePDF_QNAME, PDFType.class, PDFOperatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PDFOperatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ProdPDF", scope = PDFOperatorType.class)
    public JAXBElement<PDFOperatorType> createPDFOperatorTypeProdPDF(PDFOperatorType value) {
        return new JAXBElement<PDFOperatorType>(_PDFOperatorTypeProdPDF_QNAME, PDFOperatorType.class, PDFOperatorType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PDFOperatorType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NormalisedSumPDF", scope = PDFOperatorType.class)
    public JAXBElement<PDFOperatorType> createPDFOperatorTypeNormalisedSumPDF(PDFOperatorType value) {
        return new JAXBElement<PDFOperatorType>(_PDFOperatorTypeNormalisedSumPDF_QNAME, PDFOperatorType.class, PDFOperatorType.class, value);
    }

}
