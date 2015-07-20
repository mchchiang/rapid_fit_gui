package rapidFit.controller;

import rapidFit.controller.command.Command;

public interface CommandListener {
	void undoOccurred(Command cmd);
	void redoOccurred(Command cmd);
	void commandExecuted(Command cmd);
}
