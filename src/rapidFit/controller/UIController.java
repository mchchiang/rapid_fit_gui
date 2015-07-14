package rapidFit.controller;

import rapidFit.controller.command.Command;

public interface UIController extends Controller {
	
	public void setCommand(Command cmd);
	public void undo();
	public void redo();
	
	public void setActiveController(Controller c);
	public Controller getActiveController();
	
}
