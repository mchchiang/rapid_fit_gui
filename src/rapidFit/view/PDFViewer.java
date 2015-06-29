package rapidFit.view;

import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import rapidFit.main.RapidFitMainControl;
import rapidFit.model.*;

@SuppressWarnings("serial")
public class PDFViewer extends JPanel implements ActionListener {
	//variables for the display panel
	//variables for the PDF expression tree
	private PDFExpressionType pdfRoot;
	private PDFManager pdfManager;
	private PDFTreePanel pdfTreePanel;
	
	//variables for the PDF inspector
	private PDFInspectorPanel pdfInspectorPanel;
	
	private JPanel displayPanel;
	
	//===========================================================
	//
	//variables for the control panel
	//
	
	private JButton btnEditPDF;
	
	private JPanel controlPanel;
	
	private List<PhysicsParameterType> parameters;
	
	public PDFViewer(PDFExpressionType root, List<PhysicsParameterType> params){
		pdfRoot = root;
		parameters = params;

		pdfManager = new PDFManager(pdfRoot);
		
		pdfTreePanel = new PDFTreePanel(pdfRoot, pdfManager.getNameMap()){
			public void treeElementSelected(Object pdf){
				if (pdf != null){
					displayPanel.remove(pdfInspectorPanel);
					pdfInspectorPanel = new PDFInspectorPanel(pdf, pdfManager);
					Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
					Border title = BorderFactory.createTitledBorder(
							"<html><b>PDF Inspector</b></html>");
					pdfInspectorPanel.setBorder(new CompoundBorder(border, title));
					
					displayPanel.add(pdfInspectorPanel);
					displayPanel.validate();
				}
			}
		};
		
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border title = BorderFactory.createTitledBorder(
				"<html><b>PDF Expression</b></html>");
		pdfTreePanel.setBorder(new CompoundBorder(border, title));
		
		title = BorderFactory.createTitledBorder(
				"<html><b>PDF Inspector</b></html>");
		pdfInspectorPanel = new PDFInspectorPanel();	
		pdfInspectorPanel.setBorder(new CompoundBorder(border, title));
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new GridLayout(1,2));
		displayPanel.add(pdfTreePanel);
		displayPanel.add(pdfInspectorPanel);
		
		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(this); 
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(btnEditPDF, BorderLayout.CENTER);
		
		setLayout(new BorderLayout());
		add(displayPanel, BorderLayout.CENTER);
		add(btnEditPDF, BorderLayout.SOUTH);
	}

	//update the pdf tree and pdf tag names
	public void updatePDFTree(){
		pdfTreePanel.updatePDFTree(pdfManager.getNameMap());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnEditPDF){
			new PDFBuilder(parameters, pdfRoot, pdfManager).setVisible(true);
			updatePDFTree();
			RapidFitMainControl.getInstance().setUnsavedEdits(true);
		}			
	}	
}
