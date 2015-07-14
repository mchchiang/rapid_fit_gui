package rapidFit.controller;

import java.util.List;

import javax.swing.JComponent;

public interface Controller {
	
	public Controller getParentController();
	public List<Controller> getChildControllers();
	public JComponent getView();
	
}
