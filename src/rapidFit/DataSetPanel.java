package rapidFit;

import javax.xml.bind.*;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class DataSetPanel extends JPanel implements ActionListener{
	private ToFitType fit;
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
	private JPanel pdfOptionPanel;
	private ArrayList<PDFType> listOfPDFs;
	private AttributePanel<PDFConfiguratorType> pdfConfigPanel;
	
	private JScrollPane expressionScrollPane;
	
	private JLabel lblUseCommonPhaseSpace;
	private JLabel lblUseCommonPDF;
	
	private JLabel lblDataSetName;
	private JTextField txtDataSetName;
	
	public DataSetPanel(){
		JLabel lblNoData = new JLabel("There is no data set selected.");
		add(lblNoData);
	}
	
	public DataSetPanel (ToFitType toFit){
		fit = toFit;
		
		//for new data set 
		if (fit.getDataSet() == null){
			fit.setDataSet(new DataSetType());
			//by default uses common phase space and common pdf
			fit.setCommonPDF(true);
			fit.setPDFConfigurator(new PDFConfiguratorType());
			fit.getDataSet().setCommonPhaseSpace(new PhaseSpaceBoundaryType());
		}
		
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
		
		//display observables in phase space panel
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
		/*
		//check if common pdf is used
		lblUseCommonPDF = new JLabel("Use Common PDF");
		
		cbCommonPDF = new JCheckBox();
		cbCommonPDF.addActionListener(this);
		cbCommonPDF.setSelected(false);
		
		pdfOptionPanel = new JPanel();
		pdfOptionPanel.add(lblUseCommonPDF);
		pdfOptionPanel.add(cbCommonPDF);
		
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(pdfOptionPanel, BorderLayout.SOUTH);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF</h3></html>"));

		
		//if it is a common pdf
		if (fit.isCommonPDF() != null && fit.isCommonPDF()){
			cbCommonPDF.setSelected(true);
			pdfConfigPanel = new AttributePanel<PDFConfiguratorType>(
					PDFConfiguratorType.class, fit.getPDFConfigurator(),
					"PDF Configurator", null);	
			pdfConfigPanel.setBorder(BorderFactory.createTitledBorder(
					"PDF Configurator"));
			pdfPanel.add(pdfConfigPanel, BorderLayout.CENTER);
			
		} else {
			ObjectFactory of = new ObjectFactory();
			JAXBElement<? extends Serializable> pdfTag;
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
				pdfTag =  of.createPDFOperatorTypePDF(
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
			
			pdfOptionPanel.add(btnEditPDF);
			pdfPanel.add(expressionScrollPane, BorderLayout.CENTER);
		}*/
		
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
		c.weighty = 0.3;
		//add(pdfPanel, c);
		
	}
	
	public void switchToIndividualPhaseSpace(){
		PhaseSpaceBoundaryType space = new PhaseSpaceBoundaryType();
		space.getObservable().addAll(
				dataSet.getCommonPhaseSpace().getObservable());
		dataSet.setCommonPhaseSpace(null);
		dataSet.setPhaseSpaceBoundary(space);
	}
	
	public void switchToCommonPhaseSpace(){
		PhaseSpaceBoundaryType space = new PhaseSpaceBoundaryType();
		space.getObservable().addAll(
				dataSet.getPhaseSpaceBoundary().getObservable());
		dataSet.setCommonPhaseSpace(space);
		dataSet.setPhaseSpaceBoundary(null);
	}
	
	public void switchToCommonPDF(){
		//remove the pdf expression and the edit button
		pdfOptionPanel.remove(btnEditPDF);
		pdfPanel.remove(expressionScrollPane);
		
		//remove the pdf
		if (fit.getPDF() != null){
			fit.setPDF(null);
		} else if (fit.getNormalisedSumPDF() != null){
			fit.setNormalisedSumPDF(null);
		} else if (fit.getProdPDF() != null){
			fit.setProdPDF(null);
		}
		
		PDFConfiguratorType pdfConfig = new PDFConfiguratorType();
		
		fit.setCommonPDF(true);
		fit.setPDFConfigurator(pdfConfig);
		
		//add the pdf config panel
		pdfConfigPanel = new AttributePanel<PDFConfiguratorType>(
				PDFConfiguratorType.class, fit.getPDFConfigurator(),
				"PDF Configurator", null);	
		pdfConfigPanel.setBorder(BorderFactory.createTitledBorder(
				"PDF Configurator"));
		pdfPanel.add(pdfConfigPanel, BorderLayout.CENTER);
		
		//refresh panel display
		pdfPanel.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//clicked on switching phase space checkbox
		if (e.getSource() == cbCommonPhaseSpace){
			int result = JOptionPane.NO_OPTION;
			if (cbCommonPhaseSpace.isSelected()){
				result = JOptionPane.showOptionDialog(this, 
						"Are you sure you want to switch common phase space?\n"
						+ "All observables in this table will be treated\n"
						+ "as additional observables to the common phase space.", 
						"Phase Space Switch Confirmation", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.NO_OPTION || 
					result == JOptionPane.CLOSED_OPTION){
					cbCommonPhaseSpace.setSelected(false);
				} else {
					switchToCommonPhaseSpace();
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
				} else {
					switchToIndividualPhaseSpace();
				}
			}
			
			//clicked on switching pdf checkbox
		} else if (e.getSource() == cbCommonPDF){
			int result = JOptionPane.NO_OPTION;
			if (cbCommonPDF.isSelected()){
				result = JOptionPane.showOptionDialog(this, 
						"Are you sure you want to use the common PDF?\n" + 
						"The PDF data will be lost.", 
								"PDF Switch Confirmation", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.NO_OPTION || 
						result == JOptionPane.CLOSED_OPTION){
					cbCommonPDF.setSelected(false);
				} else {
					switchToCommonPDF();
				}
			} else {
				result = JOptionPane.showOptionDialog(this, 
						"Are you sure you want to use a separate PDF?\n", 
						"PDF Switch Confirmation", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.NO_OPTION || 
						result == JOptionPane.CLOSED_OPTION){
					cbCommonPDF.setSelected(true);
				} else {
					
				}
			}
		} else if (e.getSource() == btnAddObservable){
			phaseSpaceDataPanel.addRow();
			
		} else if (e.getSource() == btnRemoveObservable){
			phaseSpaceDataPanel.removeSelectedRows();
			
		} else if (e.getSource() == btnEditPDF){
			OldPDFBuilder pdfBuilder = new OldPDFBuilder(
					listOfPDFs,(PDFOperatorType) pdfRoot, pdfExpression);
			pdfBuilder.setVisible(true);
			
			
		}
	}
}
