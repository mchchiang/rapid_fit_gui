package rapidFit.view;

import javax.swing.table.AbstractTableModel;

import rapidFit.controller.DataTableController;

@SuppressWarnings("serial")
public class DataTableView extends AbstractTableModel {
	
	private DataTableController controller;
	
	public DataTableView(DataTableController controller){
		this.controller = controller;
	}
	
	@Override
	public int getRowCount() {
		return controller.getRowCount();
	}

	@Override
	public int getColumnCount() {
		return controller.getColumnCount();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return controller.getValueAt(rowIndex, columnIndex);
	}
	
	public void setValueAt(Object value, int row, int col){
		controller.setValueAt(value, row, col);
	}
	
	public String getColumnName(int col){
		return controller.getColumnName(col);
	}

}
