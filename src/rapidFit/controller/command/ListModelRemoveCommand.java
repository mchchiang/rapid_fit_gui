package rapidFit.controller.command;

import rapidFit.model.IListModel;

public class ListModelRemoveCommand<T> implements UndoableCommand {

	private IListModel<T> model;
	private T oldObject;
	private int index;
	
	public ListModelRemoveCommand(IListModel<T> model, int currentIndex, int removeIndex){
		this.model = model;
		this.index = removeIndex;
		oldObject = model.get(currentIndex);
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
