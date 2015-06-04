package rapidFit;

import java.awt.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFTree extends JTree {
	
	//used for displaying the tag name of the PDF in the tree
	private IdentityHashMap<PDFType, String> nameMap;
	
	private class PDFTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
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
	
	public PDFTree (PDFTreeModel model, IdentityHashMap<PDFType, String> map){
		super(model);
		
		nameMap = map;
		
		//default tree settings
		setCellRenderer(new PDFTreeCellRenderer());
		setEditable(false);
		//expand all rows
		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}
	
	public void updateMap(IdentityHashMap<PDFType, String> map){
		nameMap = map;
		setCellRenderer(new PDFTreeCellRenderer());
		validate();
	}

}
