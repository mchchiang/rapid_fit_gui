package rapidFit.controller;

import rapidFit.model.DataListener;
import rapidFit.model.IDataModel;

public interface IDataTableController<T> extends ITableController, DataListener {
	
	public void setModel(IDataModel<T> model);
	public IDataModel<?> getModel();
	public Class<?> getColumnClass(int col);
	
	public void addRow();
	public void addRow(int row);
	public void removeRow(int row);
	public void removeRows(int [] rows);
	public void copyRow(int row);
	public void copyRows(int [] rows);
}
