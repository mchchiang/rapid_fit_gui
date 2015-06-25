package rapidFit.controller.command;

import rapidFit.model.AbstractModel;

public class TableCellEditCommand<T> implements UndoableCommand {
	
	private AbstractModel entry;
	private String field;
	private T preValue;
	private T value;
	
	public TableCellEditCommand(AbstractModel entry, String field, T value){
		this.entry = entry;
		this.field = field;
		this.value = value;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute() {
		try {
			preValue = (T) entry.get(field);
			entry.set(field, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean undo() {
		try {
			entry.set(field, preValue);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String toString(){
		return "Table Cell Edit";
	}

}
