package rapidFit.controller.command;

import rapidFit.Cloner;
import rapidFit.model.dataModel.IDataModel;

public class DataModelCopyCommand<T> implements UndoableCommand {

	private IDataModel<T> model;
	private int index;
	private T copiedObject;
	private boolean hasExecutedBefore;
	
	public DataModelCopyCommand(IDataModel<T> model, T object){
		this(model, model.indexOf(object));
	}
	
	public DataModelCopyCommand(IDataModel<T> model, int index){
		this.model = model;
		this.index = index;
		this.hasExecutedBefore = false;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute() {
		if (!hasExecutedBefore) {
			copiedObject = (T) Cloner.deepClone(model.get(index));
			hasExecutedBefore = true;
		} 		
		model.add(index+1, copiedObject);
		return true;
	}

	@Override
	public boolean undo() {
		model.remove(index+1);
		return true;
	}

	
}
