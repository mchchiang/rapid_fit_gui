package rapidFit;

import java.util.*;
import java.util.List;
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
	private FitConstraintPanel fitConstraintPanel;
	private FitDataSetPanel fitDataSetPanel;
	private OutputPanel outputPanel;
	private PDFTreePanel pdfTreePanel;
	
	protected RapidFitType root;
	private Container content = getContentPane();
	
	
	public RapidFitEditor (){
		setTitle("Rapid Fit Editor");
		setSize(width,height);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//read xml
		reader = new XMLReader();
		root = reader.readFile("/Users/MichaelChiang/Dropbox/"
				+ "Edinburgh/Courses/Year 2/Summer_Project/rapid_fit_gui/trial2.out.xml", 
				"/Users/MichaelChiang/Dropbox/Edinburgh/Courses/Year 2/Summer_Project/"
				+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd");
		
		/*
		 * Instantiate a new object for any of the major classes that are null
		 */
		if (root.getParameterSet() == null) root.setParameterSet(new ParameterSetType());
		if (root.getPrecalculator() == null) root.setPrecalculator(new PrecalculatorType());
		if (root.getFitFunction() == null) root.setFitFunction(new FitFunctionType());
		if (root.getMinimiser() == null) root.setMinimiser(new MinimiserType());
		if (root.getNumberRepeats() == null) root.setNumberRepeats(BigInteger.valueOf(1));
		if (root.getCommonPDF() == null) {
			root.setCommonPDF(new PDFExpressionType());
			PDFType pdf = new PDFType();
			pdf.setName("null");
			root.getCommonPDF().setPDF(pdf);
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
		
		//separate actual fit and fit constraints
		List<ToFitType> toFits = root.getToFit();
		ArrayList<ToFitType> constraints = new ArrayList<ToFitType>();
		ArrayList<ToFitType> actualFits = new ArrayList<ToFitType>();
		
		Iterator<ToFitType> it = toFits.iterator();
		
		while(it.hasNext()){
			ToFitType fit = it.next();
			//is actual fit
			if (fit.getConstraintFunction() != null){
				constraints.add(fit);	
				it.remove();
			} else {
				actualFits.add(fit);
			}
		}

		fitConstraintPanel = new FitConstraintPanel(toFits, constraints);
		fitDataSetPanel = new FitDataSetPanel(toFits, actualFits);
		outputPanel = new OutputPanel();
		pdfTreePanel = new PDFTreePanel(root.getCommonPDF());
		
		tabs = new JTabbedPane();
		tabs.addTab("Parameter Set", paramSetPanel);
		tabs.addTab("Fitting Options", fitPanel);
		tabs.addTab("Common Properties", commonPhaseSpacePanel);
		tabs.addTab("Fit Constraints", fitConstraintPanel);
		tabs.addTab("Data Sets", fitDataSetPanel);
		tabs.addTab("Output Options", outputPanel);
		//tabs.addTab("PDF tree", pdfTreePanel);
		
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
