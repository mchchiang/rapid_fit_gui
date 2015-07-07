package rapidFit.view.bldblocks;

import javax.swing.table.AbstractTableModel;

import rapidFit.controller.AbstractDataTableController;

@SuppressWarnings("serial")
public class DataTableViewModel extends AbstractTableModel {
	
	private AbstractDataTableController controller;
	
	public DataTableViewModel(AbstractDataTableController controller){
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
	
	public Class<?>	getColumnClass(int col){
		return controller.getColumnClass(col);
	}
	
	public boolean isCellEditable(int row, int col){
		return controller.isCellEditable(row, col);
	}
	
	public void setValueAt(Object value, int row, int col){
		//only set a new value if it is different from the old one
		if (value != null && !value.equals(controller.getValueAt(row, col)) ||
			value == null && getValueAt(row, col) != null){
			controller.setValueAt(value, row, col);
		} 
	}
	
	public String getColumnName(int col){
		return controller.getColumnName(col);
	}

}
