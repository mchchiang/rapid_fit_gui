package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class CommonPropertiesPanel extends JPanel implements ActionListener {
	//variables for common phase space
	private PhaseSpaceBoundaryType phaseSpaceBoundary = null;
	
	private JPanel controlPanel;
	private JPanel phaseSpacePanel;
	
	private JButton btnAddObs;
	private JButton btnRemoveObs;
	
	private DataPanel<ObservableType> obsDataPanel;
	
	//variable for common PDF
	private PDFExpressionType commonPDFTreeRoot;
	private JButton btnEditPDF;
	
	private PDFManager pdfManager;
	private PDFTreeModel pdfTreeModel;
	private PDFTree pdfTree;
	private JScrollPane pdfTreeScrollPane;
	private JPanel pdfTreePanel;
	
	private PDFInspectorPanel pdfInspectorPanel;
	
	private JPanel pdfDisplayPanel;
	private JPanel pdfPanel;
	
	private List<PhysicsParameterType> parameters;
	
	public CommonPropertiesPanel(List<PhysicsParameterType> params, 
			PhaseSpaceBoundaryType phaseBound, PDFExpressionType comPDFTreeRoot){
		
		phaseSpaceBoundary = phaseBound;
		parameters = params;
		
		obsDataPanel = new DataPanel<ObservableType>
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
		phaseSpacePanel.add(obsDataPanel, BorderLayout.CENTER);
		phaseSpacePanel.add(controlPanel, BorderLayout.SOUTH);
		phaseSpacePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common Phase Space</h3></html>"));
		
		//====================================================================================
		//common PDF panel
		
		commonPDFTreeRoot = comPDFTreeRoot;
		
		pdfManager = new PDFManager(commonPDFTreeRoot);
		pdfTreeModel = new PDFTreeModel(commonPDFTreeRoot);
		pdfTree = new PDFTree(pdfTreeModel, pdfManager.getPDFAsKeyMap());
		
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
		pdfTreeScrollPane = new JScrollPane(pdfTree);
		
		pdfTreePanel = new JPanel();
		pdfTreePanel.setLayout(new BorderLayout());
		pdfTreePanel.add(pdfTreeScrollPane, BorderLayout.CENTER);
		
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border title = BorderFactory.createTitledBorder("<html><b>PDF Expression</b></html>");
		pdfTreePanel.setBorder(new CompoundBorder(border, title));
		
		pdfDisplayPanel = new JPanel();
		pdfDisplayPanel.setLayout(new GridLayout(1,2));
		pdfDisplayPanel.add(pdfTreePanel);
		pdfDisplayPanel.add(pdfInspectorPanel);

		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(this);
		
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(pdfDisplayPanel, BorderLayout.CENTER);
		pdfPanel.add(btnEditPDF, BorderLayout.SOUTH);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common PDF</h3></html>"));
		
		this.setLayout(new GridLayout(2,0));
		this.add(phaseSpacePanel);
		this.add(pdfPanel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddObs){
			obsDataPanel.addRow();
			
		} else if (e.getSource() == btnRemoveObs){
			obsDataPanel.removeSelectedRows();
			
		} else if (e.getSource() == btnEditPDF){
			
			PDFBuilder pdfBuilder = new PDFBuilder(parameters, commonPDFTreeRoot);
			pdfBuilder.setVisible(true);
			
			//update the pdf tree and pdf tag names
			pdfManager = new PDFManager(commonPDFTreeRoot);
			pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
			pdfTreeModel.updateEntireTree();
			pdfTree.expandAllRows();
		}
	}
}
