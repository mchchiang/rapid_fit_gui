package rapidFit;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import rapidFit.rpfit.*;

public class PDFTreePanel extends JPanel {
	private JTree pdfTree;
	private PDFTreeModel treeModel;
	private JScrollPane scrollPane;
	
	private class PDFTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			String name = "";
		    if (value instanceof SumPDFType){
		    	name = "<html><b>Normalised Sum PDF</b></html>";
		    } else if (value instanceof ProdPDFType){
		    	name = "<html><b>Product PDF</b></html>";
		    } else if (value instanceof PDFType){
		    	name = ((PDFType) value).getName();
		    }
		    
		    return super.getTreeCellRendererComponent(
		    		tree, name, sel, expanded, leaf, row, hasFocus);
		}
	}
	
	public PDFTreePanel (PDFExpressionType pdfRoot){
		treeModel = new PDFTreeModel(pdfRoot);
		
		pdfTree = new JTree(treeModel);
		pdfTree.setCellRenderer(new PDFTreeCellRenderer());
		pdfTree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				//Object node = pdfTree.getLastSelectedPathComponent();
				int length = pdfTree.getSelectionPath().getPath().length;
				if (length == 1) length = 2;
				System.out.println(pdfTree.getSelectionPath().getPath()[length-2]);
			}
			
		});
		scrollPane = new JScrollPane(pdfTree);
		
		add(scrollPane);
		
	}
}
