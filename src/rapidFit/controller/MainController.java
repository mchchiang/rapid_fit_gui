package rapidFit.controller;

import java.util.Stack;

import rapidFit.RapidFitEditorMenuBar;
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
			System.out.println(uc);
			commandHistory.push(uc);
			clearRedoableCommand();
			RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(true);
		}
	}
	
	public void undo(){
		if (hasUndoableCommand()){
			UndoableCommand uc = commandHistory.pop();
			uc.undo();
			redoCommands.push(uc);
			RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(true);
			if (hasUndoableCommand()){
				RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(true);
			} else {
				RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(false);
			}
		}
	}
	
	public void redo(){
		if (hasRedoableCommand()){
			UndoableCommand uc = redoCommands.pop();
			uc.execute();
			commandHistory.push(uc);
			
			RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(true);
			if (hasRedoableCommand()){
				RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(true);
			} else {
				RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(false);
			}
		}
	}
	
	public boolean hasUndoableCommand(){
		return !commandHistory.empty();
	}
	
	public boolean hasRedoableCommand(){
		return !redoCommands.empty();
	}
	
	public void clearRedoableCommand(){
		redoCommands.clear();
		RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(false);
	}
}
