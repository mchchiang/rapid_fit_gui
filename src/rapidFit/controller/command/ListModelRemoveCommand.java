package rapidFit.controller.command;

import rapidFit.model.AbstractListModel;

public class ListModelRemoveCommand<T> implements UndoableCommand {

	private AbstractListModel<T> model;
	private T oldObject;
	private int index;
	
	public ListModelRemoveCommand(AbstractListModel<T> model, int index){
		this.model = model;
		this.index = index;
		oldObject = model.get(index);
	}
	
	@Override
	public boolean execute() {
		model.remove(index);
		return true;
	}

	@Override
	public boolean undo() {
		model.add(index, oldObject);
		return true;
	}
	
}
