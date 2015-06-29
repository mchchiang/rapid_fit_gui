package rapidFit.view;

import java.util.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.event.*;

import rapidFit.model.*;

@SuppressWarnings("serial")
public class PDFTreePanel extends JPanel {
	
	//variables for displaying the PDF tree
	private PDFTreeModel pdfTreeModel;
	private PDFTree pdfTree;
	private JScrollPane scrollPane;
	
	//constructor
	public PDFTreePanel (PDFExpressionType root, HashMap<PDFType, String> nameMap){
		
		pdfTreeModel = new PDFTreeModel(root);
		pdfTree = new PDFTree(pdfTreeModel, nameMap);
		pdfTree.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (e.getNewLeadSelectionPath() != null){
					treeElementSelected(
							e.getNewLeadSelectionPath().getLastPathComponent());
				}
			}
		});
		
		scrollPane = new JScrollPane(pdfTree);
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}
	
	/*
	 * no action by default when the tree element is selected.
	 */
	public void treeElementSelected(Object obj){}
	
	//get methods
	public PDFTree getPDFTree(){return pdfTree;}
	public PDFTreeModel getPDFTreeModel(){return pdfTreeModel;}
	
	public void updatePDFTree(HashMap<PDFType, String> nameMap){
		pdfTree.updateMap(nameMap);
		pdfTreeModel.updateEntireTree();
		pdfTree.expandAllRows();
	}
	
	public void deselectTreeElement(){
		pdfTree.clearSelection();
	}
}
