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
	
	private DataTablePanel<ObservableType> obsDataTablePanel;
	
	//variable for common PDF
	private PDFExpressionType commonPDFTreeRoot;
	private JButton btnEditPDF;
	
	private OldPDFManager pdfManager;
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
		
		obsDataTablePanel = new DataTablePanel<ObservableType>(
				ObservableType.class,
				phaseSpaceBoundary.getObservable(), null,
				"Add Observable", "Remove Observable(s)",
				"Duplicate Observable(s)");
		
		obsDataTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common Phase Space</h3></html>"));
		
		//====================================================================================
		//common PDF panel
		
		commonPDFTreeRoot = comPDFTreeRoot;
		
		pdfManager = new OldPDFManager(commonPDFTreeRoot);
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
		this.add(obsDataTablePanel);
		this.add(pdfPanel);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEditPDF){
			
			PDFBuilder pdfBuilder = new PDFBuilder(parameters, commonPDFTreeRoot);
			pdfBuilder.setVisible(true);
			
			//update the pdf tree and pdf tag names
			pdfManager = new OldPDFManager(commonPDFTreeRoot);
			pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
			pdfTreeModel.updateEntireTree();
			pdfTree.expandAllRows();
		}
	}
}
