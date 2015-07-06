package rapidFit.controller.command;

import rapidFit.model.AbstractListModel;

public class ListModelAddCommand implements UndoableCommand {
	
	private AbstractListModel<?> model;
	private int index;
	
	public ListModelAddCommand(AbstractListModel<?> model, int index){
		this.model = model;
		this.index = index;
	}
	
	@Override
	public boolean execute() {
		try {
			model.add(index);
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

}
