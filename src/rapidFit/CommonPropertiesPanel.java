package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class CommonPropertiesPanel extends JPanel implements ActionListener {
	//variables for common phase space
	private PhaseSpaceBoundaryType phaseSpaceBoundary = null;
	
	private JPanel controlPanel;
	private JPanel phaseSpacePanel;
	
	private JButton btnAddObs;
	private JButton btnRemoveObs;
	
	private DataPanel<ObservableType> dataPanel;
	
	//variable for common PDF
	private PDFOperatorType commonPDFTreeRoot;
	private String pdfExpression;
	private JButton btnEditPDF;
	private JPanel pdfPanel;
	
	private JTextPane txtPDFExpression;
	private JScrollPane expressionScrollPane;
	
	private ArrayList<PDFType> listOfPDFs;
	
	public CommonPropertiesPanel(PhaseSpaceBoundaryType phaseBound, PDFOperatorType comPDFTreeRoot){
		if (phaseBound != null){
			phaseSpaceBoundary = phaseBound;
		} else {
			ObjectFactory of = new ObjectFactory();
			phaseSpaceBoundary = of.createPhaseSpaceBoundaryType();
		}
		
		dataPanel = new DataPanel<ObservableType>
		(ObservableType.class, phaseSpaceBoundary.getObservable(), null);
		
		btnAddObs = new JButton("Add Observable");
		btnAddObs.addActionListener(this);
		btnRemoveObs = new JButton("Remove Observable(s)");
		btnRemoveObs.addActionListener(this);
		
		controlPanel = new JPanel();
		controlPanel.add(btnAddObs);
		controlPanel.add(btnRemoveObs);
		
		phaseSpacePanel = new JPanel();
		phaseSpacePanel.setLayout(new BorderLayout());
		phaseSpacePanel.add(dataPanel, BorderLayout.CENTER);
		phaseSpacePanel.add(controlPanel, BorderLayout.SOUTH);
		phaseSpacePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common Phase Space</h3></html>"));
		
		commonPDFTreeRoot = comPDFTreeRoot;
		pdfExpression = PDFParser.convertToExpression(
				commonPDFTreeRoot.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
		
		//get list of pdfs
		listOfPDFs = PDFParser.getListOfPDFs(
				commonPDFTreeRoot.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
		ArrayList<String> pdfNames = new ArrayList<String>();
		for (PDFType pdf : listOfPDFs){
			pdfNames.add(pdf.getName());
		}
		
		txtPDFExpression = new JTextPane(new ExpressionStyledDocument(pdfNames));
		txtPDFExpression.setText(pdfExpression);
		txtPDFExpression.setPreferredSize(new Dimension(300, 50));
		txtPDFExpression.setEditable(false);
		txtPDFExpression.setFocusable(false);
		
		expressionScrollPane = new JScrollPane(txtPDFExpression);
		
		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(this);
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(expressionScrollPane, BorderLayout.CENTER);
		pdfPanel.add(btnEditPDF, BorderLayout.EAST);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common PDF</h3></html>"));
		
		this.setLayout(new BorderLayout());
		this.add(phaseSpacePanel, BorderLayout.CENTER);
		this.add(pdfPanel, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddObs){
			dataPanel.addRow();
			
		} else if (e.getSource() == btnRemoveObs){
			dataPanel.removeSelectedRows();
			
		} else if (e.getSource() == btnEditPDF){
			PDFBuilder pdfBuilder = new PDFBuilder(
					listOfPDFs, commonPDFTreeRoot, pdfExpression);
			pdfBuilder.setVisible(true);
			
			//update expression to the latest one
			pdfExpression = PDFParser.convertToExpression(
					commonPDFTreeRoot.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			txtPDFExpression.setText(pdfExpression);
		}
	}
}
