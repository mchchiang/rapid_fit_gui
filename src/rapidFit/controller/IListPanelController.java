package rapidFit.controller;

import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.ITagNameDataModel;

public interface IListPanelController<T> extends Controller, DataListener {
	
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
	
	public int indexOf(T object);
	
	public void addListPanelListener(ListPanelListener listener);
	public void removeListPanelListener(ListPanelListener listener);
	public void notifyListPanelListener();
	
}
