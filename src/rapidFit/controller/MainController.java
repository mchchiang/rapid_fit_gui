package rapidFit.controller;

import java.util.Stack;

import rapidFit.controller.command.UndoableCommand;

public class MainController {
	
	private Stack<UndoableCommand> commandHistory;
	private Stack<UndoableCommand> redoCommands;
	
	public MainController(){
		commandHistory = new Stack<UndoableCommand>();
		redoCommands = new Stack<UndoableCommand>();
	}
	
	public void setCommand(UndoableCommand uc){
		if (uc.execute()){
			commandHistory.push(uc);
			clearRedoableCommand();
		}
	}
	
	public void undo(){
		if (hasUndoableCommand()){
			UndoableCommand uc = commandHistory.pop();
			uc.undo();
			redoCommands.push(uc);
		}
	}
	
	public void redo(){
		if (hasRedoableCommand()){
			UndoableCommand uc = redoCommands.pop();
			uc.execute();
			commandHistory.push(uc);
		}
	}
	
	public boolean hasUndoableCommand(){
		return commandHistory.empty();
	}
	
	public boolean hasRedoableCommand(){
		return redoCommands.empty();
	}
	
	public void clearRedoableCommand(){
		redoCommands.clear();
	}
}
