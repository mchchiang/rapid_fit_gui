package rapidFit.view.bldblocks;

import javax.swing.table.*;

import rapidFit.controller.AbstractAttributeTableController;

@SuppressWarnings("serial")
public class AttributeTableViewModel extends AbstractTableModel {
	
	private AbstractAttributeTableController controller;
	
	public AttributeTableViewModel(AbstractAttributeTableController controller){
		this.controller = controller;
	}
	
	public int getRowCount() {
		return controller.getRowCount();
	}

	public int getColumnCount() {
		return controller.getColumnCount();
	}

	public String getColumnName(int col){
		return controller.getColumnName(col);
	}
	
	public Class<?> getRowClass(int row){
		return controller.getRowClass(row);
	}
	
	public boolean isCellEditable(int row, int col){
		return controller.isCellEditable(row, col);
	}

	public Object getValueAt(int row, int col) {
		return controller.getValueAt(row, col);
	}
	
	public void setValueAt(Object value, int row, int col){
		controller.setValueAt(value, row, col);
	}
}
