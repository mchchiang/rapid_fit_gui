package rapidFit.controller.command;

import rapidFit.model.treeModel.ITreeModel;

public class ReplaceTreeNodeCommand implements UndoableCommand {
	
	private ITreeModel model;
	private Object parentNode;
	private int index;
	private Object oldNode;
	private Object newNode;
	private boolean canExecute;
	
	public ReplaceTreeNodeCommand(ITreeModel model, Object parent, int index, Object newNode){
		this.model = model;
		this.parentNode = parent;
		this.index = index;
		
		//for the case if the node is the root node
		if (parent == null){
			this.oldNode = model.getRoot();
		} else {
			this.oldNode = model.getChild(parent, index);
		}
		this.newNode = newNode;
		if (oldNode != null){
			canExecute = true;
		} else {
			canExecute = false;
		}
	}

	@Override
	public boolean execute() {
		if (canExecute) {
			model.replaceNode(parentNode, index, newNode);
			return true;
		}
		return false;
	}

	@Override
	public boolean undo() {
		if (canExecute) {
			model.replaceNode(parentNode, index, oldNode);
			return true;
		}
		return false;
	}

}
