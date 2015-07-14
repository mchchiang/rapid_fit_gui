package rapidFit.controller;

import rapidFit.model.DataListener;
import rapidFit.model.ITagNameDataModel;
import rapidFit.model.ListObservable;

public interface IListPanelController<T> extends Controller, DataListener, ListObservable {
	
	public void setModel(ITagNameDataModel<T> model);
	public ITagNameDataModel<T> getModel();
	
	public void addRow();
	public void addRow(int row);
	public void removeRow(int row);
	public void removeRows(int [] rows);
	public void copyRow(int row);
	public void copyRows(int [] rows);
	
	public void setSelectedIndex(int row);
	public int getSelectedIndex();
	public void clearSelection();
	
	public int getListSize();
	
	public T get(int row);
	public String getTagName(int row);
	
	public void set(int row);
	public void setTagName(int row, String tagName);
	
}
