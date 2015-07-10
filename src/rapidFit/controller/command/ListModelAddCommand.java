package rapidFit.controller.command;

import rapidFit.model.IListModel;

public class ListModelAddCommand<T> implements UndoableCommand {
	
	private IListModel<T> model;
	private int index;
	private String description;
	private T addObject;
	
	public ListModelAddCommand(IListModel<T> model, int index, String description){
		this(model, index, null, description);
	}
	
	public ListModelAddCommand(IListModel<T> model, int index, T object, String description){
		this.model = model;
		this.index = index;
		this.description = description;
		this.addObject = object;
	}
	
	@Override
	public boolean execute() {
		try {
			if (addObject == null){
				model.add(index, addObject);
			} else {
				model.add(index);
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
