package rapidFit.controller.command;

import rapidFit.model.IDataModel;

public class DataModelAddCommand<T> implements UndoableCommand {
	
	private IDataModel<T> model;
	private int index;
	private String description;
	private T addObject;
	
	public DataModelAddCommand(IDataModel<T> model, int index, String description){
		this(model, index, null, description);
	}
	
	public DataModelAddCommand(IDataModel<T> model, int index, T object, String description){
		this.model = model;
		this.index = index;
		this.description = description;
		this.addObject = object;
	}
	
	@Override
	public boolean execute() {
		try {
			if (addObject != null){
				model.add(index, addObject);
			} else {
				model.add(index);
				addObject = model.get(index);
			}
			return true;
		} catch (Exception e){
			return false;
		}
	}

	@Override
	public boolean undo() {
		model.remove(index);
		return true;
	}
	
	public String toString(){
		return description;
	}

}