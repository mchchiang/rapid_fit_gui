package rapidFit;

import java.awt.*;
import java.math.BigInteger;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class RapidFitEditor extends JFrame {
	private int width = 700;
	private int height = 500;
	private RapidFitEditorMenuBar menuBar;
	private JTabbedPane tabs;
	private XMLReader reader;
	private ObjectFactory of = new ObjectFactory();
	
	//all panels
	private ParameterSetPanel paramSetPanel;
	private CommonPropertiesPanel commonPhaseSpacePanel;
	private FittingPanel fitPanel;
	
	protected RapidFitType root;
	private Container content = getContentPane();
	
	
	public RapidFitEditor (){
		setTitle("Rapid Fit Editor");
		setSize(width,height);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//read xml
		reader = new XMLReader();
		root = reader.readFile("trial2.xml", "src/rapidFit/RapidFit.xsd");
		
		/*
		 * Instantiate a new object for any of the major classes that are null
		 */
		if (root.getParameterSet() == null) root.setParameterSet(new ParameterSetType());
		if (root.getPrecalculator() == null) root.setPrecalculator(new PrecalculatorType());
		if (root.getFitFunction() == null) root.setFitFunction(new FitFunctionType());
		if (root.getMinimiser() == null) root.setMinimiser(new MinimiserType());
		if (root.getNumberRepeats() == null) root.setNumberRepeats(BigInteger.valueOf(1));
		if (root.getCommonPDF() == null) {
			root.setCommonPDF(new PDFOperatorType());
			PDFType pdf = new PDFType();
			pdf.setName("null");
			root.getCommonPDF().getProdPDFOrNormalisedSumPDFOrPDF().add(
					of.createPDFOperatorTypePDF(pdf));
		}
		if (root.getCommonPhaseSpace() == null){
			root.setCommonPhaseSpace(new CommonPhaseSpaceType());
			root.getCommonPhaseSpace().setPhaseSpaceBoundary(new PhaseSpaceBoundaryType());
		}
		
		//create panels
		paramSetPanel = new ParameterSetPanel(root.getParameterSet());
		
		fitPanel = new FittingPanel(root.getFitFunction(), 
				root.getMinimiser(), root.getPrecalculator());
		
		commonPhaseSpacePanel = new CommonPropertiesPanel(
					root.getCommonPhaseSpace().getPhaseSpaceBoundary(),
					root.getCommonPDF());
		
		
		tabs = new JTabbedPane();
		tabs.addTab("Parameter Set", paramSetPanel);
		tabs.addTab("Fitting Options", fitPanel);
		tabs.addTab("Common Properties", commonPhaseSpacePanel);
		
		
		content = getContentPane();
		content.add(tabs, BorderLayout.CENTER);
		
		menuBar = new RapidFitEditorMenuBar(this);
		this.setJMenuBar(menuBar);
		pack();
		setVisible(true);
	}	
	
	public static void main (String [] args){
		new RapidFitEditor();
	}
	
}
