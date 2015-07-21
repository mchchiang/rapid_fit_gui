package rapidFit.view.bldblocks;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import rapidFit.controller.ITreePanelController;


@SuppressWarnings("serial")
public class DataTree extends JTree {
	
	//used for displaying the tag name of the PDF in the tree
	private ITreePanelController panelController;
	
	private class DataTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			String name =  panelController.getDisplayName(value);
			if (!panelController.isLeaf(value)){
				name = "<html><b>" + name + "</b></html>";
			}
		    return super.getTreeCellRendererComponent(
		    		tree, name, sel, expanded, leaf, row, hasFocus);
		}
	}
	
	public DataTree (DataTreeViewModel viewModel, ITreePanelController controller){
		super(viewModel);
		this.panelController = controller;
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
