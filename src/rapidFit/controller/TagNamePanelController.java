package rapidFit.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.EditDataModelTagNameCommand;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.EditTagNameEvent;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.view.bldblocks.TagNamePanel;

public class TagNamePanelController implements ITagNamePanelController, ListPanelListener, DataListener {
	
	private ITagNameDataModel<?> model;
	private UIController mainController;
	private Controller parentController;
	private TagNamePanel panel;
	private int selectedIndex;
	
	public TagNamePanelController(UIController mainController, 
			IListPanelController<?> listController, 
			Controller parentController, ITagNameDataModel<?> model,
			int index){
		this.model = model;
		this.mainController = mainController;
		this.parentController = parentController;
		this.selectedIndex = index;
		if (listController != null){
			listController.addListPanelListener(this);
		}
		model.addDataListener(this);
		
		//create view
		panel = new TagNamePanel(this);
	}
	
	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == model.getActualModel() && 
			e instanceof EditTagNameEvent){
			EditTagNameEvent evt = (EditTagNameEvent) e;
			if (evt.getIndex() == selectedIndex){
				panel.setTagName(evt.getNewTagName());
				mainController.setActiveController(this);
			}
		}
	}

	@Override
	public void setTagName(String tagName) {
		if (selectedIndex != -1){
			mainController.setCommand(
					new EditDataModelTagNameCommand(model, selectedIndex,
							model.getTagName(selectedIndex), tagName));
		}
	}

	@Override
	public String getTagName() {
		if (selectedIndex != -1){
			return model.getTagName(selectedIndex);
		} else {
			return "";
		}
	}

	@Override
	public void changedSelectedElement(int index) {
		selectedIndex = index;
		if (selectedIndex != -1){
			panel.setTagName(model.getTagName(selectedIndex));
			mainController.setActiveController(this);
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
		panel.getTagNameTextField().requestFocusInWindow();
	}

	@Override
	public void deactivateController() {}
}
