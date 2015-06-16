package rapidFit;

import java.util.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;
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
		
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border title = BorderFactory.createTitledBorder(
				"<html><b>PDF Expression</b></html>");
		setBorder(new CompoundBorder(border, title));
		
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
	}
	
	public void treeElementSelectedAction(Object obj){
		/*
		 * no action by default when the tree element is selected.
		 */
	}
	
	public void updatePDFTree(IdentityHashMap<PDFType, String> nameMap){
		pdfTree.updateMap(nameMap);
		pdfTreeModel.updateEntireTree();
		pdfTree.expandAllRows();
	}
}
