package rapidFit.view.bldblocks;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import rapidFit.controller.ITreeController;


@SuppressWarnings("serial")
public class DataTree extends JTree {
	
	//used for displaying the tag name of the PDF in the tree
	private ITreeController controller;
	
	private class DataTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		    String name = controller.getDisplayName(value);
		    return super.getTreeCellRendererComponent(
		    		tree, name, sel, expanded, leaf, row, hasFocus);
		}
	}
	
	public DataTree (DataTreeViewModel viewModel, ITreeController controller){
		super(viewModel);
		this.controller = controller;
		
		//default tree settings
		setCellRenderer(new DataTreeCellRenderer());
		setEditable(false);
		getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		expandAllRows();
		
	}
	
	public void expandAllRows(){
		//expand all rows
		for (int i = 0; i < getRowCount(); i++) {
			expandRow(i);
		}
	}

}
