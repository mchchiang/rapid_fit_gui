package rapidFit.controller;

public interface ITableController extends Controller {
	
	public void startCellEditing(int row, int col);
	public void stopCellEditing();
	public void cancelCellEditing();
	public void setSelectedCell(int row, int col);
	public int getSelectedRow();
	public int getSelectedColumn();
	public void clearSelection();
	
	public boolean isCellEditable(int row, int col);
	
	public int getRowCount();
	public int getColumnCount();
	public String getColumnName(int col);
	
	public void setValueAt(Object value, int row, int col);
	public Object getValueAt(int row, int col);
	
	
}
