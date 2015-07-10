package rapidFit.controller.command;

import rapidFit.Cloner;
import rapidFit.model.IListModel;

public class ListModelCopyCommand<T> implements UndoableCommand {

	private IListModel<T> model;
	private int index;
	
	public ListModelCopyCommand(IListModel<T> model, int index){
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
