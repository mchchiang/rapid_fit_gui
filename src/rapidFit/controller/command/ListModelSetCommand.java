package rapidFit.controller.command;

import rapidFit.model.IListModel;

public class ListModelSetCommand<T> implements UndoableCommand {
	
	private IListModel<T> model;
	private int index;
	private T oldValue;
	private T newValue;
	
	public ListModelSetCommand(IListModel<T> model, int index, T oldValue, T newValue){
		this.model = model;
		this.index = index;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	@Override
	public boolean execute() {
		model.set(index, newValue);
		return true;
	}

	@Override
	public boolean undo() {
		model.set(index, oldValue);
		return true;
	}
	
}
