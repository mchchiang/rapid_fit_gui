package rapidFit.controller;

import javax.swing.JComponent;
import javax.swing.JPanel;

import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.event.DataEvent;
import rapidFit.model.dataModel.event.EditTagNameEvent;
import rapidFit.view.bldblocks.ListViewPanel;

public abstract class IListViewController<T> implements Controller, ListPanelListener, DataListener {
	
	protected IListPanelController<T> listPanelController = null;
	private ITagNameDataModel<T> model;
	private ListViewPanel mainPanel;
	private JComponent listPanel;
	private JComponent displayPanel;
	
	/*
	 * requires the sub-class to determine how to display the data
	 */
	protected abstract JPanel getView(T object);
	
	public void setModel(ITagNameDataModel<T> model) {
		if (model != null){
			if (this.model != null){
				this.model.removeDataListener(this);
			}
			this.model = model;
			this.model.addDataListener(this);
		}
	}
	
	public void setListPanelController(IListPanelController<T> listPanelController) {
		if (listPanelController != null) {
			if (this.listPanelController != null){
				this.listPanelController.removeListPanelListener(this);
			}
			this.listPanelController = listPanelController;
			this.listPanelController.addListPanelListener(this);
		}
	}
	
	public void setListTitle(String title) {
		mainPanel.setListTitle(title);
	}
	
	public void setDisplayTitle(String title) {
		mainPanel.setDisplayTitle(title);
	}
	
	public void initView(String listTitle, String displayTitle) {
		//create the view
		if (listPanelController != null){
			listPanel = listPanelController.getView();
			displayPanel = getView(null);
			mainPanel = new ListViewPanel(listPanel, displayPanel, listTitle, displayTitle);
		}		
	}
	
	@Override
	public void changedSelectedElement(int index) {
		displayPanel = getView(listPanelController.get(index));
		mainPanel.updateDisplayPanel(displayPanel, 
				listPanelController.getTagName(index));
	}
	
	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == listPanelController.getModel().getActualModel()){
			if (e instanceof EditTagNameEvent){
				EditTagNameEvent evt = (EditTagNameEvent) e;
				if (evt.getIndex() == listPanelController.getSelectedIndex()) {
					setDisplayTitle(evt.getNewTagName());
				}
			} 
		}
	}
	
	@Override
	public JComponent getView() {
		return mainPanel;
	}
	
}
