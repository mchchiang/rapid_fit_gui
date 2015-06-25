package rapidFit.controller;

import java.util.List;

import rapidFit.controller.command.TableCellEditCommand;
import rapidFit.model.AbstractModel;
import rapidFit.view.DataTable;

public class DefaultDataTableController implements DataTableController {
	
	private List<? extends AbstractModel> data;
	private DataTable dataTable;
	private TableCellEditCommand cellEditCommand;
	private MainController mainController;
	
	public DefaultDataTableController(MainController mainController,
			List<? extends AbstractModel> data, DataTable dataTable){
		this.data = data;
		this.dataTable = dataTable;
		this.mainController = mainController;
	}
	
	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValueAt(Object value, int row, int column) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValueAt(int row, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addRow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRow(int row) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeRow(int row) {
		// TODO Auto-generated method stub
		
	}

}
