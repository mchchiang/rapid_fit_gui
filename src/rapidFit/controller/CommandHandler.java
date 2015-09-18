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
	
	
	/**
	 * Execute a command. If the command is executed successfully,
	 * it is stored in the command history list for undo/redo purpose.
	 * Notice once a new command is set, the list of redoable commands
	 * is automatically cleared.
	 * 
	 * N.B. This is a thread-safe method.
	 * 
	 * @param cmd
	 */
	public synchronized void setCommand(Command cmd) {
		if (cmd.execute()){
			System.out.println(cmd);
			if (cmd instanceof UndoableCommand){
				commandHistory.push((UndoableCommand) cmd);
			}
			clearRedoableCommand();
			listener.commandExecuted(cmd);
		}
	}
	
	/**
	 * Undo the last (undoable) command that was executed. If 
	 * executed successfully, the command is stored in the list of
	 * redoable commands.
	 * 
	 * N.B. This is a thread-safe method. 
	 */
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
	
	
	/**
	 * Redo the last command that was undone/unexecuted.
	 * 
	 * N.B. This is a thread-safe method.
	 */
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
		if (activeController != controller){
			if (activeController != null){
				activeController.deactivateController();
			}
			controller.activateController();
			activeController = controller;
			System.out.println(activeController.toString());
		}
	}

	public synchronized Controller getActiveController() {
		return activeController;
	}
	
	/**
	 * Clear the list of redoable commands.
	 */
	public void clearRedoableCommand(){
		redoCommands.clear();
	}
	
	/**
	 * Check if there are any undoable commands stored in 
	 * the command history list.
	 */
	public boolean hasUndoableCommand(){
		return !commandHistory.empty();
	}
	
	/**
	 * Check if there are any commands stored in
	 * the redoable commands list.
	 * @return
	 */
	public boolean hasRedoableCommand(){
		return !redoCommands.empty();
	}

}
