package rapidFit.controller;

import javax.swing.JComponent;

import rapidFit.model.ITagNameListModel;
import rapidFit.view.bldblocks.ListViewObservable;

public interface IListPanelController<T> extends ListViewObservable {
	
	public void setModel(ITagNameListModel<T> newModel);
		
	public void addRow();
	public void addRow(int row);
	public void removeRow(int row);
	public void removeRows(int [] rows);
	public void copyRow(int row);
	public void copyRows(int [] rows);
	
	public void setSelectedIndex(int row);
	
	public int getListSize();
	
	public T get(int row);
	public String getTagName(int row);
	
	public void set(int row);
	public void setTagName(int row, String tagName);
	
	public JComponent getViewComponent();
}
