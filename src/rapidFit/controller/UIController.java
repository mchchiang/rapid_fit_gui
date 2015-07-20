package rapidFit.controller;

import java.awt.Window;

import javax.swing.JComponent;

import rapidFit.controller.command.Command;

public abstract class UIController implements Controller {
	
	public abstract void setCommand(Command cmd);
	public abstract void undo();
	public abstract void redo();
	
	public abstract void setActiveController(Controller c);
	public abstract Controller getActiveController();
	
	public void activateController() {}
	public void deactivateController() {}
	
	public JComponent getView(){
		return null;
	}
	
	public abstract Window getWindow();
}
