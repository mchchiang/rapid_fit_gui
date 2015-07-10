package rapidFit.controller.command;

import rapidFit.model.IClassModel;

public class ClassModelEditFieldCommand implements UndoableCommand {
	
	private IClassModel model;
	private String field;
	private Object oldValue;
	private Object newValue;
	private String description;
	
	public ClassModelEditFieldCommand (
			IClassModel model, String field, 
			Object oldValue, Object newValue, String description){
		this.model = model;
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.description = description;
	}
	
	@Override
	public boolean execute() {
		try {
			model.set(field, newValue);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean undo() {
		try {
			model.set(field, oldValue);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public String toString(){
		return description;
	}
	
}
