package rapidFit.controller.command;

import rapidFit.model.IDataModel;

public class DataModelRemoveCommand<T> implements UndoableCommand {

	private IDataModel<T> model;
	private T oldObject;
	private int index;
	
	public DataModelRemoveCommand(IDataModel<T> model, int currentIndex, int removeIndex){
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

