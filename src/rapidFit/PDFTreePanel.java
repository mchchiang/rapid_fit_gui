package rapidFit;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFTreePanel extends JPanel {
	
	//variables for displaying the PDF tree
	private PDFTreeModel pdfTreeModel;
	private PDFTree pdfTree;
	private JScrollPane scrollPane;
	
	//constructor
	public PDFTreePanel (PDFExpressionType root, IdentityHashMap<PDFType, String> nameMap){
		
		pdfTreeModel = new PDFTreeModel(root);
		pdfTree = new PDFTree(pdfTreeModel, nameMap);
		pdfTree.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				if (e.getNewLeadSelectionPath() != null){
					treeElementSelectedAction(
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
	public void treeElementSelectedAction(Object obj){}
	
	//get methods
	public PDFTree getPDFTree(){return pdfTree;}
	public PDFTreeModel getPDFTreeModel(){return pdfTreeModel;}
	
	public void updatePDFTree(IdentityHashMap<PDFType, String> nameMap){
		pdfTree.updateMap(nameMap);
		pdfTreeModel.updateEntireTree();
		pdfTree.expandAllRows();
	}
}
