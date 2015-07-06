package rapidFit.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rapidFit.controller.command.CompoundUndoableCommand;
import rapidFit.controller.command.ListModelAddCommand;
import rapidFit.controller.command.ListModelCopyCommand;
import rapidFit.controller.command.ListModelEditFieldCommand;
import rapidFit.controller.command.ListModelEditListCommand;
import rapidFit.controller.command.ListModelRemoveCommand;
import rapidFit.controller.command.UndoableCommand;
import rapidFit.model.AbstractListModel;
import rapidFit.model.AbstractListModel.UpdateType;
import rapidFit.model.ListObserver;
import rapidFit.view.DataTablePanel;
import rapidFit.view.DataTableView;

public class DefaultDataTableController<T> implements DataTableController, ListObserver {
	
	private AbstractListModel<T> listModel;
	private DataTableView tableView;
	private DataTablePanel tablePanel;
	private MainController mainController;
	private HashMap<Integer, Class<?>> listMap;
	
	public DefaultDataTableController(
			MainController controller, AbstractListModel<T> model){
		this.mainController = controller;
		this.listModel = model;
		listModel.addObserver(this);
		
		//store a list of columns where the data type is a List
		listMap = new HashMap<Integer, Class<?>>();
		for (int col = 0; col < listModel.getFieldTypes().size(); col++){
			Type type = listModel.getFieldTypes().get(col);
			if (type instanceof Class<?> && (Class<?>) type == List.class &&
					type instanceof ParameterizedType){
				listMap.put(col, (Class<?>) 
						((ParameterizedType) type).getActualTypeArguments()[0]);
			}
		}
		
		//create the view
		this.tableView = new DataTableView(this);
		this.tablePanel = new DataTablePanel(this, tableView);
		
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
	public Class<?> getColumnClass(int col) {
		if (listMap.containsKey(col)) return String.class;
		return listModel.getFieldClasses().get(col);
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		try {
			
			//for setting a list or array of values
			/*
			 * error checking is handled in a customised cell editor in
			 * the DataTable class
			 */
			if (listMap.containsKey(col)){
				if (listMap.get(col) == Double.class){
					mainController.setCommand(
							new ListModelEditListCommand<Double>(
									listModel, row, listModel.getFieldNames().get(col),
									Double.class, (String) value));
				} else if (listMap.get(col) == BigInteger.class){
					mainController.setCommand(
							new ListModelEditListCommand<BigInteger>(
									listModel, row, listModel.getFieldNames().get(col),
									BigInteger.class, (String) value));
				} else if (listMap.get(col) == String.class){
					mainController.setCommand(
							new ListModelEditListCommand<String>(
									listModel, row, listModel.getFieldNames().get(col),
									String.class, (String) value));
				}
			}
			/*
			 * for empty String input (i.e. ""), set the string to null.
			 * This is needed to ensure there is no empty tag <></> generated
			 */
			if (getColumnClass(col) == String.class && 
					((String) value).equals("")){
				value = null;
			}
			
			Object oldValue = listModel.get(row, listModel.getFieldNames().get(col));
			mainController.setCommand(new ListModelEditFieldCommand
					(listModel, row, listModel.getFieldNames().get(col), 
					oldValue, value, "Changed field " + listModel.getFieldNames().get(col) + 
					" from \"" + oldValue + "\" to \"" + value + "\""));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		try {
			//for the case that the cell contains a List
			if (listMap.containsKey(col)){
				return ((List<?>) listModel.get(row, 
						listModel.getFieldNames().get(col))).toString();
			}
			return listModel.get(row, listModel.getFieldNames().get(col));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		/*
		 * if the cell is a list, make it only editable if
		 * the parameter type of the list is a String, Double, or 
		 * BigInteger
		 */
		if (listMap.containsKey(col) &&
				listMap.get(col) != String.class &&
				listMap.get(col) != Double.class &&
				listMap.get(col) != BigInteger.class){
			
			return false;
		} 
		System.out.println("OK");
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
		mainController.setCommand(new ListModelAddCommand(listModel, row,
				"Added a new row"));
	}

	@Override
	public void removeRow(int row) {
		mainController.setCommand(new ListModelRemoveCommand<T>(listModel, row, row));
	}
	
	@Override
	public void removeRows(int [] rows){
		ArrayList<ListModelRemoveCommand<T>> commands = 
				new ArrayList<ListModelRemoveCommand<T>>();
		for (int i = 0; i < rows.length; i++){
			commands.add(new ListModelRemoveCommand<T>(listModel, rows[i], rows[i]-i));
		}		
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}
	
	@Override
	public void copyRow(int row) {
		mainController.setCommand(new ListModelCopyCommand<T>(listModel, row));
	}
	
	@Override
	public void copyRows(int [] rows){
		
	}
	
	public DataTablePanel getTablePanel(){return tablePanel;}

}
