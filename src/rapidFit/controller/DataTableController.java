package rapidFit.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JComponent;

import rapidFit.controller.command.CompoundUndoableCommand;
import rapidFit.controller.command.ListModelAddCommand;
import rapidFit.controller.command.ListModelCopyCommand;
import rapidFit.controller.command.ListModelEditFieldCommand;
import rapidFit.controller.command.ListModelEditListCommand;
import rapidFit.controller.command.ListModelRemoveCommand;
import rapidFit.model.IListModel;
import rapidFit.model.IListModel.UpdateType;
import rapidFit.model.IListObserver;
import rapidFit.view.bldblocks.DataTablePanel;
import rapidFit.view.bldblocks.DataTableViewModel;

public class DataTableController<T> implements IDataTableController<T>, IListObserver {
	
	private IListModel<T> listModel;
	private DataTableViewModel tableViewModel;
	private DataTablePanel tablePanel;
	private MainController mainController;
	private HashMap<Integer, Class<?>> listMap;
	private List<String> fieldNames;
	
	public DataTableController(
			MainController controller, IListModel<T> model,
			String btnAddName, String btnRemoveName, String btnCopyName){
		this.mainController = controller;
		this.listModel = model;
		listModel.addListObserver(this);
		
		/*
		 * get the list of names of the fields to be
		 * displayed on separate columns
		 */
		fieldNames = listModel.getFieldNames();

		setListMap();
		
		//create the view
		this.tableViewModel = new DataTableViewModel(this);
		this.tablePanel = new DataTablePanel(this, tableViewModel,
				btnAddName, btnRemoveName, btnCopyName);
		
	}
	
	private void setListMap(){
		//store a list of columns where the data type is a List
		listMap = new HashMap<Integer, Class<?>>();

		for (int col = 0; col < fieldNames.size(); col++){
			if (listModel.getFieldClass(fieldNames.get(col)) == List.class){
				Type type = listModel.getFieldType(fieldNames.get(col));
				if (type instanceof ParameterizedType){
					listMap.put(col, (Class<?>) 
							((ParameterizedType) type).getActualTypeArguments()[0]);
				} else {
					listMap.put(col, null);
				}
			}
		}
	}
	
	@Override
	public void setModel(IListModel<T> newModel){
		if (listModel != null){
			listModel.removeListObserver(this);
		}
		listModel = newModel;
		listModel.addListObserver(this);
		fieldNames = newModel.getFieldNames();
		setListMap();
		tableViewModel.fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return listModel.size();
	}

	@Override
	public int getColumnCount() {
		return fieldNames.size();
	}

	@Override
	public String getColumnName(int col) {
		return fieldNames.get(col);
	}
	
	@Override
	public Class<?> getColumnClass(int col) {
		if (listMap.containsKey(col)) return String.class;
		return listModel.getFieldClass(fieldNames.get(col));
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		
		Object oldValue = getValueAt(row, col);
		
		//for setting a list or array of values
		/*
		 * error checking is handled in a customised cell editor in
		 * the DataTable class
		 */
		if (listMap.containsKey(col)){
			try {
				StringTokenizer st = new StringTokenizer((String) value, "[, ]");
				Class<?> clazz = listMap.get(col);
				if (clazz == Double.class){
					ArrayList<Double> list = new ArrayList<Double>();
					while (st.hasMoreElements()){
						list.add(Double.parseDouble(st.nextToken()));
					}
					if (!list.toString().equals(oldValue)){
						mainController.setCommand(new ListModelEditListCommand<Double>(
								listModel, row, fieldNames.get(col), list));
					}
				} else if (clazz == BigInteger.class){
					ArrayList<BigInteger> list = new ArrayList<BigInteger>();
					while (st.hasMoreElements()){
						list.add(new BigInteger(st.nextToken()));
					}
					if (!list.toString().equals(oldValue)){
						mainController.setCommand(new ListModelEditListCommand<BigInteger>(
								listModel, row, fieldNames.get(col), list));
					}
				} else if (clazz == String.class){
					ArrayList<String> list = new ArrayList<String>();
					while (st.hasMoreElements()){
						list.add(st.nextToken());
					}
					if (!list.toString().equals(oldValue)){
						mainController.setCommand(new ListModelEditListCommand<String>(
								listModel, row, fieldNames.get(col), list));
					}
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			
		} else {
			/*
			 * for empty String input (i.e. ""), set the string to null.
			 * This is needed to ensure there is no empty tag <></> generated
			 */
			if (getColumnClass(col) == String.class && 
					((String) value).equals("")){
				value = null;
			} 
			
			if (value != null && !value.equals(oldValue) ||
					value == null && oldValue != null){
			mainController.setCommand(new ListModelEditFieldCommand
					(listModel, row, fieldNames.get(col), 
							oldValue, value, "Changed field " + fieldNames.get(col) + 
							" from \"" + oldValue + "\" to \"" + value + "\""));
			}
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		try {
			//for the case that the cell contains a List
			if (listMap.containsKey(col)){
				return ((List<?>) listModel.get(row, 
						fieldNames.get(col))).toString();
			}
			return listModel.get(row, fieldNames.get(col));
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
		return true;
	}

	@Override
	public void update(int index, UpdateType t, String fieldName) {
		if (t == UpdateType.EDIT){
			 tableViewModel.fireTableCellUpdated(index, 
					 fieldNames.indexOf(fieldName));
		} else if (t == UpdateType.ADD || t == UpdateType.REMOVE){
			tableViewModel.fireTableDataChanged();
		}
	}

	@Override
	public void addRow() {
		addRow(listModel.size());
	}

	@Override
	public void addRow(int row) {
		mainController.setCommand(new ListModelAddCommand<T>(listModel, row,
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
		
		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);
		
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
		ArrayList<ListModelCopyCommand<T>> commands =
				new ArrayList<ListModelCopyCommand<T>>();
		
		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);
		
		for (int i = 0; i < rows.length; i++){
			commands.add(new ListModelCopyCommand<T>(listModel, rows[i]+i));
		}
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}
	
	public JComponent getViewComponent(){return tablePanel;}

}
