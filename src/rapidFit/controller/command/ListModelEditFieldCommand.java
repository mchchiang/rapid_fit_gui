package rapidFit.controller.command;

import rapidFit.model.IListModel;

public class ListModelEditFieldCommand implements UndoableCommand {
	
	private IListModel<?> model;
	private String field;
	private int index;
	private Object oldValue;
	private Object newValue;
	private String description;
	
	public ListModelEditFieldCommand(IListModel<?> model,
			int index, String field, Object oldValue, Object newValue, String description) {
		this.model = model;
		this.field = field;
		this.index = index;
		this.newValue = newValue;
		this.oldValue = oldValue;
		this.description = description;
	}
	
	@Override
	public boolean execute() {
		try {
			model.set(index, field, newValue);
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean undo() {
		try {
			model.set(index, field, oldValue);
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
