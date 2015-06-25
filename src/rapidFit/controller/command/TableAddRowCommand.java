package rapidFit.controller.command;

import java.util.List;

import rapidFit.model.AbstractModel;

public class TableAddRowCommand implements UndoableCommand {
	
	
	
	public TableAddRowCommand (List<AbstractModel> data){
		
	}
	
	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean undo() {
		// TODO Auto-generated method stub
		return false;
	}

}
