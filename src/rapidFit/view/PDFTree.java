package rapidFit.view;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import rapidFit.model.*;

@SuppressWarnings("serial")
public class PDFTree extends JTree {
	
	//used for displaying the tag name of the PDF in the tree
	private HashMap<PDFType, String> nameMap;
	
	private class PDFTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			
			/*
			 * set the display name of the PDF in the tree depending on its
			 * type (e.g. Sum PDF, Product PDF, PDF)
			 */
			String name = "";
			
		    if (value instanceof SumPDFType){
		    	name = "<html><b>Sum PDF (" + ((SumPDFType) value).getFractionName()  + ") </b></html>";
		    } else if (value instanceof ProdPDFType){
		    	name = "<html><b>Product PDF</b></html>";
		    } else if (value instanceof PDFType){
		    	name = nameMap.get((PDFType) value);
		    }
		    
		    return super.getTreeCellRendererComponent(
		    		tree, name, sel, expanded, leaf, row, hasFocus);
		}
	}
	
	public PDFTree (PDFTreeModel model, HashMap<PDFType, String> map){
		super(model);
		
		nameMap = map;
		
		//default tree settings
		setCellRenderer(new PDFTreeCellRenderer());
		setEditable(false);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		expandAllRows();
		
	}
	
	public void expandAllRows(){
		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}
	
	public void updateMap(HashMap<PDFType, String> map){
		nameMap = map;
		setCellRenderer(new PDFTreeCellRenderer());
		validate();
	}

}
