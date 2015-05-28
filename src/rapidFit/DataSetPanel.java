package rapidFit;

import javax.xml.bind.*;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class DataSetPanel extends JPanel implements ActionListener{
	
	private DataSetType dataSet;
	
	private AttributePanel<DataSetType> details;
	
	private JCheckBox cbCommonPhaseSpace;
	private JCheckBox cbCommonPDF;
	
	private DataPanel<ObservableType> phaseSpaceDataPanel;
	private JPanel phaseSpacePanel;
	private JPanel phaseSpaceOptionPanel;
	
	private JButton btnAddObservable;
	private JButton btnRemoveObservable;
	
	//variable for common PDF
	private Object pdfRoot;
	private JTextPane txtPDFExpression;
	private String pdfExpression;
	private JButton btnEditPDF;
	private JPanel pdfPanel;
	private ArrayList<PDFType> listOfPDFs;
	
	private JScrollPane expressionScrollPane;
	
	private JLabel lblUseCommonPhaseSpace;
	
	public DataSetPanel(){
		JLabel lblNoData = new JLabel("There is no data set selected.");
		add(lblNoData);
	}
	
	public DataSetPanel (ToFitType fit){
		dataSet = fit.getDataSet();
		
		ArrayList<String> ignoreAttr = new ArrayList<String>();
		ignoreAttr.add("PhaseSpaceBoundary");
		ignoreAttr.add("CommonPhaseSpace");
		
		details = new AttributePanel<DataSetType>(
				DataSetType.class, dataSet, "Details", ignoreAttr);
		details.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Details</h3></html>"));
		
		cbCommonPhaseSpace = new JCheckBox();
		cbCommonPhaseSpace.addActionListener(this);
		cbCommonPhaseSpace.setSelected(false);
		
		lblUseCommonPhaseSpace = new JLabel("Use Common Phase Space");
		
		btnAddObservable = new JButton("Add Observable");
		btnAddObservable.addActionListener(this);
		btnRemoveObservable = new JButton("Remove Observable");
		btnRemoveObservable.addActionListener(this);
		
		phaseSpaceOptionPanel = new JPanel();
		phaseSpaceOptionPanel.add(lblUseCommonPhaseSpace);
		phaseSpaceOptionPanel.add(cbCommonPhaseSpace);
		phaseSpaceOptionPanel.add(btnAddObservable);
		phaseSpaceOptionPanel.add(btnRemoveObservable);
		
		//using common phase space
		if (dataSet.getCommonPhaseSpace() != null){
			cbCommonPhaseSpace.setSelected(true);
			phaseSpaceDataPanel = new DataPanel<ObservableType>(
					ObservableType.class, 
					dataSet.getCommonPhaseSpace().getObservable(), null);
			
		} else {
			phaseSpaceDataPanel = new DataPanel<ObservableType>(
					ObservableType.class, 
					dataSet.getPhaseSpaceBoundary().getObservable(), null);
		}
		
		phaseSpacePanel = new JPanel();
		phaseSpacePanel.setLayout(new BorderLayout());
		phaseSpacePanel.add(phaseSpaceDataPanel, BorderLayout.CENTER);
		phaseSpacePanel.add(phaseSpaceOptionPanel, BorderLayout.SOUTH);
		
		phaseSpacePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Phase Space</h3></html>"));
		
		//check if common pdf is used
		cbCommonPDF = new JCheckBox();
		cbCommonPDF.setSelected(false);
		
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(cbCommonPDF, BorderLayout.NORTH);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF</h3></html>"));
		
		//if it is a common pdf
		if (fit.isCommonPDF() != null && fit.isCommonPDF()){
			cbCommonPDF.setSelected(true);
			
		} else {
			ObjectFactory of = new ObjectFactory();
			JAXBElement<?> pdfTag;
			if (fit.getNormalisedSumPDF() != null){
				//pdfRoot = fit.getNormalisedSumPDF();
				pdfTag = of.createPDFOperatorTypeNormalisedSumPDF(
						fit.getNormalisedSumPDF());
			} else if (fit.getProdPDF() != null){
				//pdfRoot = fit.getProdPDF();
				pdfTag = of.createPDFOperatorTypeProdPDF(
						fit.getProdPDF());
			} else {
				//pdfRoot = fit.getPDF();
				pdfTag = of.createPDFOperatorTypePDF(
						fit.getPDF());
			}
			
			pdfExpression = PDFParser.convertToExpression(pdfTag);
			
			//get list of pdfs
			listOfPDFs = PDFParser.getListOfPDFs(pdfTag);
			ArrayList<String> pdfNames = new ArrayList<String>();
			for (PDFType pdf : listOfPDFs){
				pdfNames.add(pdf.getName());
			}
			
			txtPDFExpression = new JTextPane(
					new ExpressionStyledDocument(pdfNames));
			txtPDFExpression.setText(pdfExpression);
			txtPDFExpression.setPreferredSize(new Dimension(300, 50));
			txtPDFExpression.setEditable(false);
			txtPDFExpression.setFocusable(false);
			
			expressionScrollPane = new JScrollPane(txtPDFExpression);
			
			btnEditPDF = new JButton("Edit PDF");
			btnEditPDF.addActionListener(this);
			
			pdfPanel.add(expressionScrollPane, BorderLayout.CENTER);
			pdfPanel.add(btnEditPDF, BorderLayout.EAST);
		}
		
		//set layout for the components
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		//for details panel
		c.insets = new Insets(-10,10,0,10);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.3;
		add(details, c);
		
		//for phase space panel
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,10,0,10);
		c.ipadx = 10;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(phaseSpacePanel, c);
		
		//for pdf panel
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,10,0,10);
		c.ipadx = 10;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 0.1;
		add(pdfPanel, c);
		
		setBorder(BorderFactory.createTitledBorder("<html><h3>Data Set</h3></html>"));
	}
	
	public void switchPhaseSpacePanel(){
		if (cbCommonPhaseSpace.isSelected()){
			PhaseSpaceBoundaryType space = new PhaseSpaceBoundaryType();
			
			dataSet.setPhaseSpaceBoundary(null);
		} else {
			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cbCommonPhaseSpace){
			int result = JOptionPane.NO_OPTION;
			if (cbCommonPhaseSpace.isSelected()){
				result = JOptionPane.showOptionDialog(this, 
						"Are you sure you want to switch common phase space?\n"
						+ "All observables in this table will be treated\n"
						+ "as amendments to the common phase space.", 
						"Phase Space Switch Confirmation", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.NO_OPTION || 
					result == JOptionPane.CLOSED_OPTION){
					cbCommonPhaseSpace.setSelected(false);
				}
			} else {
				result = JOptionPane.showOptionDialog(this, 
						"Are you sure you want to switch to individual phase space?\n"
						+ "All observables in this table will be added to the\n"
						+ " individual phase space.", "Phase Space Switch Confirmation", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.NO_OPTION || 
						result == JOptionPane.CLOSED_OPTION){
					cbCommonPhaseSpace.setSelected(true);
				}
			}
		}
	}
}
