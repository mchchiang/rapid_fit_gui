package rapidFit.controller;

import rapidFit.controller.command.ListModelAddCommand;
import rapidFit.controller.command.ListModelEditCommand;
import rapidFit.controller.command.ListModelRemoveCommand;
import rapidFit.model.AbstractListModel;
import rapidFit.model.AbstractListModel.UpdateType;
import rapidFit.model.ListObserver;
import rapidFit.view.DataTableView;

public class DefaultDataTableController<T> implements DataTableController, ListObserver {
	
	private AbstractListModel<T> listModel;
	private DataTableView tableView;
	private MainController mainController;
	
	public DefaultDataTableController(
			MainController controller, AbstractListModel<T> model, DataTableView view){
		this.mainController = controller;
		this.listModel = model;
		this.tableView = view;
	}

	@Override
	public int getRowCount() {
		return listModel.size();
	}

	@Override
	public int getColumnCount() {
		return listModel.getNumOfFields();
	}

	@Override
	public String getColumnName(int col) {
		return listModel.getFieldNames().get(col);
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		try {
			Object oldValue = listModel.get(row, listModel.getFieldNames().get(col));
			mainController.setCommand(new ListModelEditCommand
					(listModel, row, listModel.getFieldNames().get(col), 
					oldValue, value, "Changed field " + listModel.getFieldNames().get(col) + 
					" from \"" + oldValue.toString() + "\" to \"" + value.toString() + "\""));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		try {
			return listModel.get(row, listModel.getFieldNames().get(col));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public void update(int index, UpdateType t, String fieldName) {
		if (t == UpdateType.EDIT){
			 tableView.fireTableCellUpdated(index, 
					 listModel.getFieldNames().indexOf(fieldName));
		} else if (t == UpdateType.ADD || t == UpdateType.REMOVE){
			tableView.fireTableDataChanged();
		}
	}

	@Override
	public void addRow() {
		addRow(listModel.size());
	}

	@Override
	public void addRow(int row) {
		mainController.setCommand(new ListModelAddCommand(listModel, row));
	}

	@Override
	public void removeRow(int row) {
		mainController.setCommand(new ListModelRemoveCommand<T>(listModel, row));
	}

}
