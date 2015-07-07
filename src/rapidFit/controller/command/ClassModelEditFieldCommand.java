package rapidFit.controller.command;

import rapidFit.model.AbstractClassModel;

public class ClassModelEditFieldCommand<T> implements UndoableCommand {
	
	private AbstractClassModel<?> model;
	private String field;
	private T oldValue;
	private T newValue;
	
	public ClassModelEditFieldCommand (AbstractClassModel<?> model, String field, T oldValue, T newValue){
		this.model = model;
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;
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
	
}
