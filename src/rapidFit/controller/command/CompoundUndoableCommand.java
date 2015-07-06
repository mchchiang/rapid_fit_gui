package rapidFit.controller.command;

import java.util.List;

public class CompoundUndoableCommand implements UndoableCommand {
	
	private List<UndoableCommand> commands;
	
	public CompoundUndoableCommand(List<UndoableCommand> commands){
		this.commands = commands;
	}
	
	@Override
	public boolean execute() {
		for (int i = 0; i < commands.size(); i++){
			commands.get(i).execute();
		}
		return true;
	}

	@Override
	public boolean undo() {
		for (int i = commands.size()-1; i >= 0; i--){
			commands.get(i).undo();
		}
		return true;
	}
	
}
