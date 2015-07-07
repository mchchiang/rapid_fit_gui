package rapidFit.controller;

import javax.swing.JComponent;

public interface TableController {
	
	public int getRowCount();
	public int getColumnCount();
	
	public String getColumnName(int col);
	
	public void setValueAt(Object value, int row, int col);
	public Object getValueAt(int row, int col);
	
	public boolean isCellEditable(int row, int col);
	
	public JComponent getViewComponent();
}
