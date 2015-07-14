package rapidFit.controller;

import javax.swing.JComponent;
import javax.swing.JPanel;

import rapidFit.model.DataEvent;
import rapidFit.model.DataListener;
import rapidFit.model.EditTagNameEvent;
import rapidFit.model.ITagNameDataModel;
import rapidFit.model.ListListener;
import rapidFit.view.bldblocks.ListViewPanel;

public abstract class IListViewController<T> implements Controller, ListListener, DataListener {
	
	protected IListPanelController<T> listPanelController = null;
	private ITagNameDataModel<T> model;
	private ListViewPanel mainPanel;
	private JComponent listPanel;
	private JComponent displayPanel;
	private String listTitle = null;
	private String displayTitle = null;
	
	/*
	 * requires the sub-class to determine how to display the data
	 */
	protected abstract JPanel getView(T object);
	
	public IListViewController (ITagNameDataModel<T> model) {
		this.model = model;
		this.model.addDataListener(this);
	}
	
	public void setListPanelController(IListPanelController<T> listPanelController) {
		if (listPanelController != null) {
			this.listPanelController = listPanelController;
			this.listPanelController.addListListener(this);
		}
	}
	
	public void setListTitle(String title) {
		this.listTitle = title;
	}
	
	public void setDisplayTitle(String title) {
		this.displayTitle = title;
	}
	
	public void initView() {
		//create the view
		listPanel = this.listPanelController.getView();
		displayPanel = getView(null);
		mainPanel = new ListViewPanel(listPanel, displayPanel, listTitle, displayTitle);		
	}
	
	@Override
	public void changedSelectedElement(int index) {
		displayTitle = listPanelController.getTagName(index);
		displayPanel = getView(listPanelController.get(index));
		mainPanel.updateDisplayPanel(displayPanel, displayTitle);
	}
	
	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == listPanelController.getModel().getActualModel() &&
				e instanceof EditTagNameEvent){
			EditTagNameEvent evt = (EditTagNameEvent) e;
			if (evt.getIndex() == listPanelController.getSelectedIndex()) {
				displayTitle = evt.getNewTagName();
				mainPanel.updateDisplayTitle(displayTitle);
			}
		}
	}
	
	@Override
	public JComponent getView() {
		return mainPanel;
	}
	
}
