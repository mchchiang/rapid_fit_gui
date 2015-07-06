package rapidFit.controller.command;

import rapidFit.Cloner;
import rapidFit.model.AbstractListModel;

public class ListModelCopyCommand<T> implements UndoableCommand {

	private AbstractListModel<T> model;
	private int index;
	
	public ListModelCopyCommand(AbstractListModel<T> model, int index){
		this.model = model;
		this.index = index;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute() {
		model.add(index+1, (T) Cloner.deepClone(model.get(index)));
		return true;
	}

	@Override
	public boolean undo() {
		model.remove(index+1);
		return true;
	}

	
}
