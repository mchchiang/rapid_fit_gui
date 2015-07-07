package rapidFit.controller.command;

public class NullCommand implements UndoableCommand {

	@Override
	public boolean execute() {return false;}

	@Override
	public boolean undo() {return false;}
	
}
