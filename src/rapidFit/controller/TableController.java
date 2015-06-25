package rapidFit.controller;

public interface TableController {
	
	public int getRowCount();
	public int getColumnCount();
	public String getColumnName(int column);
	public void setValueAt(Object value, int row, int column);
	public Object getValueAt(int row, int column);
	public boolean isCellEditable(int row, int column);
	
}
