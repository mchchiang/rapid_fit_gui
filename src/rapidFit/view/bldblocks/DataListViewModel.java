package rapidFit.view.bldblocks;


import javax.swing.AbstractListModel;

import rapidFit.controller.IListPanelController;

@SuppressWarnings("serial")
public class DataListViewModel<T> extends AbstractListModel<T> {

	private IListPanelController<T> controller;

	public DataListViewModel (IListPanelController<T> controller){
		this.controller = controller;
	}

	@Override
	public int getSize() {
		return controller.getListSize();
	}

	@Override
	public T getElementAt(int index) {
		return controller.get(index);
	}

	//methods to update the view
	public void fireContentsChanged(int startIndex, int endIndex){
		super.fireContentsChanged(this, startIndex, endIndex);
	}
	
	public void fireIntervalAdded(int startIndex, int endIndex){
		super.fireIntervalAdded(this, startIndex, endIndex);
	}
	
	public void fireIntervalRemoved(int startIndex, int endIndex){
		super.fireIntervalRemoved(this,startIndex, endIndex);
	}
}
