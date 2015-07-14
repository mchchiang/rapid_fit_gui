package rapidFit.controller;

import java.util.List;
import java.util.Stack;

import javax.swing.JComponent;

import rapidFit.controller.command.Command;
import rapidFit.controller.command.UndoableCommand;

public class PDFBuilder implements UIController {
	
	private Stack<UndoableCommand> commandHistory;
	private Stack<UndoableCommand> redoCommands;
	
	private UIController mainController;
	
	public PDFBuilder(UIController mainController) {
		
	}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JComponent getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCommand(Command cmd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActiveController(Controller c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Controller getActiveController() {
		// TODO Auto-generated method stub
		return null;
	}

}
