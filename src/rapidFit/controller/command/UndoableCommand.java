package rapidFit.controller.command;

public interface UndoableCommand extends Command {
	public boolean execute();
	public boolean undo();
}
