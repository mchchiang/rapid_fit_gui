package rapidFit.controller;

import java.util.Stack;

import rapidFit.controller.command.Command;
import rapidFit.controller.command.UndoableCommand;

public class CommandHandler {
	
	private Controller activeController;
	private CommandListener listener;
	private Stack<UndoableCommand> commandHistory;
	private Stack<UndoableCommand> redoCommands;
	
	public CommandHandler(CommandListener listener){
		this.listener = listener;
		commandHistory = new Stack<UndoableCommand>();
		redoCommands = new Stack<UndoableCommand>();
	}
	
	public void setCommand(Command cmd) {
		if (cmd.execute()){
			System.out.println(cmd);
			if (cmd instanceof UndoableCommand){
				commandHistory.push((UndoableCommand) cmd);
			}
			clearRedoableCommand();
			listener.commandExecuted(cmd);
		}
	}
	
	public synchronized void undo() {
		if (activeController != null){
			activeController.deactivateController();
		}
		if (hasUndoableCommand()){
			UndoableCommand uc = commandHistory.pop();
			uc.undo();
			System.out.println(uc);
			redoCommands.push(uc);
			listener.undoOccurred(uc);
		}
	}

	public synchronized void redo() {
		if (activeController != null){
			activeController.deactivateController();
		}
		if (hasRedoableCommand()){
			UndoableCommand uc = redoCommands.pop();
			uc.execute();
			System.out.println(uc);
			commandHistory.push(uc);
			listener.redoOccurred(uc);
		}
	}

	public synchronized void setActiveController(Controller controller) {
		if (activeController != null){
			activeController.deactivateController();
		}
		controller.activateController();
		activeController = controller;
	}

	public Controller getActiveController() {
		return activeController;
	}
	
	public void clearRedoableCommand(){
		redoCommands.clear();
	}
	
	public boolean hasUndoableCommand(){
		return !commandHistory.empty();
	}
	
	public boolean hasRedoableCommand(){
		return !redoCommands.empty();
	}

}
