package rapidFit;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class CommonPropertiesPanel extends JPanel {
	//variables for common phase space
	private PhaseSpaceBoundaryType phaseSpaceBoundary = null;
	
	private DataTablePanel<ObservableType> obsDataTablePanel;
	
	//variable for common PDF
	private PDFExpressionType commonPDFTreeRoot;
	private PDFViewer pdfPanel;
	
	private List<PhysicsParameterType> parameters;
	
	public CommonPropertiesPanel(List<PhysicsParameterType> params, 
			PhaseSpaceBoundaryType phaseBound, PDFExpressionType comPDFTreeRoot){
		
		phaseSpaceBoundary = phaseBound;
		parameters = params;
		
		obsDataTablePanel = new DataTablePanel<ObservableType>(
				ObservableType.class,
				phaseSpaceBoundary.getObservable(), null,
				"Add Observable", "Remove Observable(s)",
				"Duplicate Observable(s)");
		
		obsDataTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common Phase Space</h3></html>"));
		
		//============================================================================
		//common PDF panel
		
		commonPDFTreeRoot = comPDFTreeRoot;
		
		pdfPanel = new PDFViewer(commonPDFTreeRoot, parameters);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common PDF</h3></html>"));
		
		this.setLayout(new GridLayout(2,0));
		this.add(obsDataTablePanel);
		this.add(pdfPanel);
	}
}
