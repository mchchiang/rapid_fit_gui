package rapidFit.controller.command;

import rapidFit.model.treeModel.ITreeModel;

public class EditTreeModelTagNameCommand implements UndoableCommand {
	
	private ITreeModel model;
	private Object node;
	private String oldTagName;
	private String newTagName;
	private boolean canExecute;
	
	public EditTreeModelTagNameCommand(ITreeModel model, Object node, String tagName){
		this.model = model;
		this.node = node;
		this.newTagName = tagName;
		try {
			this.oldTagName = model.getTagName(node);
			canExecute = true;
		} catch (Exception e){
			e.printStackTrace();
			canExecute = false;
		}
	}
	
	@Override
	public boolean execute() {
		if (canExecute){
			model.setTagName(node, newTagName);
			return true;
		}
		return false;
	}

	@Override
	public boolean undo() {
		if (canExecute){
			model.setTagName(node, oldTagName);
		}
		return false;
	}
	
}
