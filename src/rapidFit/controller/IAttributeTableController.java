package rapidFit.controller;

import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.IClassModel;

public interface IAttributeTableController<T> extends ITableController, DataListener {
	
	public void setModel(IClassModel<T> model);
	public IClassModel<T> getModel();
	public Class<?> getRowClass(int row);
	
}
