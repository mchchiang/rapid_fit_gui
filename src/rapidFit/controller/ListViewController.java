package rapidFit.controller;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import rapidFit.model.dataModel.ClassModelAdapter;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.EditElementEvent;
import rapidFit.model.dataModel.EditTagNameEvent;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.NullClassModel;
import rapidFit.model.dataModel.RemoveElementEvent;
import rapidFit.view.NullPanel;
import rapidFit.view.bldblocks.ListViewPanel;

public class ListViewController<T> implements Controller, DataListener, ListPanelListener {
	
	private ITagNameDataModel<T> model;
	
	private UIController mainController;
	private IAttributeTableController<T> attributeTableController;
	private IListPanelController<T> listPanelController;
	private ITagNamePanelController tagNamePanelController;
	
	private HashMap<T, IClassModel<T>> modelMap;
	
	private JPanel displayPanel;
	private NullPanel nullPanel;
	private ListViewPanel listViewPanel;
	private T displayElement;

	@SuppressWarnings("unchecked")
	public ListViewController(UIController mainController, 
			ITagNameDataModel<T> model, String listTitle, 
			String attributeTableTitle) {
		
		this.model = model;
		this.model.addDataListener(this);
		
		this.mainController = mainController;
		
		listPanelController = new ListPanelController<T>(
				mainController, this, model);
		listPanelController.addListPanelListener(this);
		tagNamePanelController = new TagNamePanelController(
				mainController, listPanelController, this, model, -1);
		attributeTableController = new AttributeTableController<T>(
				mainController, this, new NullClassModel(), attributeTableTitle);
		
		modelMap = new HashMap<T, IClassModel<T>>();
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new BorderLayout());
		
		//create display panel
		displayPanel.add(tagNamePanelController.getView(), BorderLayout.NORTH);
		displayPanel.add(attributeTableController.getView(), BorderLayout.CENTER);
		
		//create null panel
		nullPanel = new NullPanel();
		
		//create the main panel
		listViewPanel = new ListViewPanel(
				listPanelController.getView(), nullPanel, 
				listTitle, "");
	}
	
	@Override
	public void update(DataEvent e){
		if (e.getDataModel() == model.getActualModel()){
			if (e instanceof RemoveElementEvent){
				//RemoveElementEvent evt = (RemoveElementEvent) e;
				//modelMap.remove(evt.getRemovedElement());
				changeDisplayElement(null);
				listPanelController.clearSelection();
				
			} else if (e instanceof EditElementEvent){
				EditElementEvent evt = (EditElementEvent) e;
				changeDisplayElement(model.get(evt.getIndex()));
				listPanelController.setSelectedIndex(evt.getIndex());
				
			} else if (e instanceof EditTagNameEvent){
				/*
				 * for changing the display title when the tag name 
				 * of the entry that is on display has been modified
				 */
				EditTagNameEvent evt = (EditTagNameEvent) e;
				if (displayElement == model.get(evt.getIndex())){
					listViewPanel.setDisplayTitle(evt.getNewTagName());
				}
			}
		}
	}
	
	@Override
	public void changedSelectedElement(int index){
		changeDisplayElement(listPanelController.get(index));
	}
	
	private void changeDisplayElement(T newDisplayElement){
		if (displayElement != newDisplayElement){
			displayElement = newDisplayElement;
			if (displayElement == null){
				listViewPanel.updateDisplayPanel(nullPanel, null);
			} else {
				int index = model.indexOf(displayElement);
				if (!modelMap.containsKey(displayElement)){
					modelMap.put(displayElement, 
							new ClassModelAdapter<T>(model, index));
				}
				attributeTableController.setModel(modelMap.get(displayElement));
				listViewPanel.updateDisplayPanel(displayPanel, 
						model.getTagName(index));
			}
		}
	}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		ArrayList<Controller> childControllers = new ArrayList<Controller>();
		childControllers.add(attributeTableController);
		childControllers.add(listPanelController);
		childControllers.add(tagNamePanelController);
		return childControllers;
	}
	
	@Override
	public void activateController() {}

	@Override
	public void deactivateController() {}

	@Override
	public JComponent getView(){
		return listViewPanel;
	}
}
