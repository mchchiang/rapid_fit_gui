package rapidFit.controller;

import rapidFit.model.IListModel;

public interface IDataTableController<T> extends ITableController {
	
	public void setModel(IListModel<T> model);
	
	public Class<?> getColumnClass(int col);
	
	public void addRow();
	public void addRow(int row);
	public void removeRow(int row);
	public void removeRows(int [] rows);
	public void copyRow(int row);
	public void copyRows(int [] rows);
	
}
