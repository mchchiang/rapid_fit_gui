package rapidFit.controller;

import rapidFit.model.IClassModel;
import rapidFit.view.bldblocks.ListViewObserver;

public interface IAttributeTableController extends ITableController, ListViewObserver {
	
	public void setModel(IClassModel model);
	public IClassModel getModel();
	
	public Class<?> getRowClass(int row);
	
}
