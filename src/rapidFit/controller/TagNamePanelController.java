package rapidFit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.SetTagNameCommand;
import rapidFit.model.DataEvent;
import rapidFit.model.DataListener;
import rapidFit.model.EditTagNameEvent;
import rapidFit.model.ITagNameDataModel;
import rapidFit.model.ListListener;
import rapidFit.view.bldblocks.TagNamePanel;

public class TagNamePanelController implements ITagNamePanelController, ListListener, DataListener {
	
	private ITagNameDataModel<?> model;
	private UIController mainController;
	private IListPanelController<?> listController;
	private Controller parentController;
	private TagNamePanel panel;
	
	public TagNamePanelController(UIController mainController, 
			IListPanelController<?> listController, 
			Controller parentController, ITagNameDataModel<?> model){
		this.model = model;
		this.mainController = mainController;
		this.listController = listController;
		this.parentController = parentController;
		listController.addListListener(this);
		model.addDataListener(this);
		
		//create view
		panel = new TagNamePanel(this);
	}
	
	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == model.getActualModel() && 
			e instanceof EditTagNameEvent){
			EditTagNameEvent evt = (EditTagNameEvent) e;
			if (evt.getIndex() == listController.getSelectedIndex()){
				panel.setTagName(evt.getNewTagName());
			}
		}
	}

	@Override
	public void setTagName(String tagName) {
		if (listController.getSelectedIndex() != -1){
			mainController.setCommand(
					new SetTagNameCommand(model, listController.getSelectedIndex(),
							model.getTagName(listController.getSelectedIndex()), tagName));
		}
	}

	@Override
	public String getTagName() {
		return model.getTagName(listController.getSelectedIndex());
	}

	@Override
	public void changedSelectedElement(int index) {
		if (listController.getSelectedIndex() != -1){
			panel.setTagName(model.getTagName(
					listController.getSelectedIndex()));
		} else {
			panel.setTagName("");
		}
	}

	@Override
	public Controller getParentController() {
		return parentController;
	}

	@Override
	public List<Controller> getChildControllers() {
		return new ArrayList<Controller>();
	}

	@Override
	public JComponent getView() {
		return panel;
	}
	
	@Override
	public void activateController() {
		mainController.setActiveController(this);
	}
	
	@Override
	public void makeViewFocusable(boolean focusable) {
		panel.getTagNameTextField().setFocusable(focusable);
	}
}
