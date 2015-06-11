package rapidFit;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

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
	private PDFExpressionType pdfRoot;
	
	private PDFManager pdfManager;
	private PDFTreeModel pdfTreeModel;
	private PDFTree pdfTree;
	private JScrollPane pdfTreeScrollPane;
	private PDFInspectorPanel pdfInspectorPanel;
	private JPanel pdfTreePanel;
	private JPanel pdfDisplayPanel;
	
	private JButton btnEditPDF;
	
	private JPanel pdfPanel;
	private JPanel pdfOptionPanel;
	private AttributePanel<PDFConfiguratorType> pdfConfigPanel;

	
	private JLabel lblUseCommonPhaseSpace;
	private JLabel lblUseCommonPDF;
	
	private List<PhysicsParameterType> parameters;
	
	public DataSetPanel(){
		JLabel lblNoData = new JLabel("There is no data set selected.");
		add(lblNoData);
	}
	
	public DataSetPanel (List<PhysicsParameterType> params, ToFitType toFit){
		fit = toFit;
		parameters = params;
		
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
		
		//==============================================================================
		// Phase space panel
		
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
		
		//==============================================================================
		// PDF panel
		
		lblUseCommonPDF = new JLabel("Use Common PDF");
		cbCommonPDF = new JCheckBox();
		cbCommonPDF.addActionListener(this);
		
		pdfOptionPanel = new JPanel();
		pdfOptionPanel.add(lblUseCommonPDF);
		pdfOptionPanel.add(cbCommonPDF);
		
		pdfPanel = new JPanel();
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF</h3></html>"));
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(pdfOptionPanel, BorderLayout.SOUTH);
		
		//for using common PDF
		if (fit.isCommonPDF() != null && fit.isCommonPDF()){
			initCommonPDF();
		} else {
			initIndividualPDF();
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
		c.weighty = 0.5;
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
		c.weighty = 0.8;
		add(pdfPanel, c);
		
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
	
	public void initIndividualPDF(){
		/*
		 * need to do this because there is no wrapper tag around
		 * the PDF expression in the To Fit section
		 */
		pdfRoot = new PDFExpressionType();
		pdfRoot.setNormalisedSumPDF(fit.getNormalisedSumPDF());
		pdfRoot.setProdPDF(fit.getProdPDF());
		pdfRoot.setPDF(fit.getPDF());
		
		pdfManager = new PDFManager(pdfRoot);
		pdfTreeModel = new PDFTreeModel(pdfRoot);
		pdfTree = new PDFTree(pdfTreeModel, pdfManager.getPDFAsKeyMap());
		
		pdfTreeScrollPane = new JScrollPane(pdfTree);
		
		pdfTreePanel = new JPanel();
		pdfTreePanel.setLayout(new BorderLayout());
		pdfTreePanel.add(pdfTreeScrollPane, BorderLayout.CENTER);
		
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border title = BorderFactory.createTitledBorder("<html><b>PDF Expression</b></html>");
		pdfTreePanel.setBorder(new CompoundBorder(border, title));
		
		pdfInspectorPanel = new PDFInspectorPanel();
		/*
		 * trigger the PDF inspector panel to display info of the selected
		 * node in the PDF tree
		 */
		pdfTree.addTreeSelectionListener(new TreeSelectionListener() {
		  
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (e.getNewLeadSelectionPath() != null){
					pdfDisplayPanel.remove(pdfInspectorPanel);
					
					pdfInspectorPanel = new PDFInspectorPanel(
							e.getNewLeadSelectionPath().getLastPathComponent());
					pdfDisplayPanel.add(pdfInspectorPanel);
					pdfDisplayPanel.validate();
				}
			}
		});
		
		pdfDisplayPanel = new JPanel();
		pdfDisplayPanel.setLayout(new GridLayout(1,2));
		pdfDisplayPanel.add(pdfTreePanel);
		pdfDisplayPanel.add(pdfInspectorPanel);

		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(this);
		
		pdfPanel.add(pdfDisplayPanel, BorderLayout.CENTER);
		pdfOptionPanel.add(btnEditPDF);
	}
	
	public void initCommonPDF(){
		fit.setCommonPDF(true);
		cbCommonPDF.setSelected(true);
		//add the PDF config panel
		
		//ensure that there is a pdf configurator
		if (fit.getPDFConfigurator() == null){
			fit.setPDFConfigurator(new PDFConfiguratorType());
		}
		
		pdfConfigPanel = new AttributePanel<PDFConfiguratorType>(
				PDFConfiguratorType.class, fit.getPDFConfigurator(),
				"PDF Configurator", null);	
		pdfConfigPanel.setBorder(BorderFactory.createTitledBorder(
				"PDF Configurator"));
		pdfPanel.add(pdfConfigPanel, BorderLayout.CENTER);
	}
	
	public void removeCommonPDF(){
		fit.setCommonPDF(false);
		cbCommonPDF.setSelected(false);
		//remove the PDF config panel
		pdfPanel.remove(pdfConfigPanel);
	}
	
	public void removeIndividualPDF(){
		//remove the PDF Tree
		pdfPanel.remove(pdfDisplayPanel);
		pdfOptionPanel.remove(btnEditPDF);
		
		//remove the pdf
		fit.setPDF(null);
		fit.setNormalisedSumPDF(null);
		fit.setProdPDF(null);
	}
	
	public void switchToCommonPDF(){
		removeIndividualPDF();
		fit.setPDFConfigurator(new PDFConfiguratorType());
		initCommonPDF();
		
		//refresh panel display
		pdfPanel.validate();
	}
	
	public void switchToIndividualPDF(){
		removeCommonPDF();
		PDFType pdf = new PDFType();
		pdf.setName("PDF");
		fit.setPDF(pdf);
		initIndividualPDF();
		
		//refresh panel display
		pdfPanel.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//clicked on switching phase space checkbox
		if (e.getSource() == cbCommonPhaseSpace){
			int result = JOptionPane.NO_OPTION;
			
			//selected to switch to common phase space
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
			
			//selected to switch to individual phase space
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
			
			//selected to switch to common PDF
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
			
			//selected to switch to individual PDF
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
					switchToIndividualPDF();
				}
			}
			
		} else if (e.getSource() == btnAddObservable){
			phaseSpaceDataPanel.addRow();
			
		} else if (e.getSource() == btnRemoveObservable){
			phaseSpaceDataPanel.removeSelectedRows();
			
		} else if (e.getSource() == btnEditPDF){
					
			PDFBuilder pdfBuilder = new PDFBuilder(parameters, pdfRoot);
			pdfBuilder.setVisible(true);
			
			//update the PDF of the data set
			fit.setNormalisedSumPDF(pdfRoot.getNormalisedSumPDF());
			fit.setProdPDF(pdfRoot.getProdPDF());
			fit.setPDF(pdfRoot.getPDF());
			
			//update the pdf tree and pdf tag names
			pdfManager = new PDFManager(pdfRoot);
			pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
			pdfTreeModel.updateEntireTree();
			pdfTree.expandAllRows();
		}
	}
}
