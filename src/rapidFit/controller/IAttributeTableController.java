package rapidFit.controller;

import rapidFit.model.DataListener;
import rapidFit.model.IClassModel;

public interface IAttributeTableController<T> extends ITableController, DataListener {
	
	public void setModel(IClassModel<T> model);
	public IClassModel<T> getModel();
	public Class<?> getRowClass(int row);
	
}
