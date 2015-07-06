package rapidFit.controller;

public interface DataTableController extends TableController {
	
	public void addRow();
	public void addRow(int row);
	public void removeRow(int row);
	public void removeRows(int [] rows);
	public void copyRow(int row);
	public void copyRows(int [] rows);
	
}
