package rapidFit.controller;

import rapidFit.controller.command.Command;

public interface CommandListener {
	public void undoOccurred(Command cmd);
	public void redoOccurred(Command cmd);
	public void commandExecuted(Command cmd);
}
