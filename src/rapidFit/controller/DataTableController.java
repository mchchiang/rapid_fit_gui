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

import rapidFit.Cloner;
import rapidFit.controller.command.CompoundUndoableCommand;
import rapidFit.controller.command.DataModelAddCommand;
import rapidFit.controller.command.DataModelEditFieldCommand;
import rapidFit.controller.command.DataModelCopyCommand;
import rapidFit.controller.command.DataModelRemoveCommand;
import rapidFit.model.dataModel.AddElementEvent;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.EditElementEvent;
import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.RemoveElementEvent;
import rapidFit.view.bldblocks.DataTable;
import rapidFit.view.bldblocks.DataTablePanel;
import rapidFit.view.bldblocks.DataTableViewModel;

public class DataTableController<T> implements IDataTableController<T> {
	
	private IDataModel<T> dataModel;
	private DataTable table;
	private DataTableViewModel tableViewModel;
	private DataTablePanel tablePanel;
	private Controller parentController;
	private UIController mainController;
	private HashMap<Integer, Class<?>> listMap;
	private List<String> fieldNames;
	
	public DataTableController(
			UIController mainController, Controller parentController, IDataModel<T> model,
			String btnAddName, String btnRemoveName, String btnCopyName){
		this.mainController = mainController;
		this.parentController = parentController;
		this.dataModel = model;
		dataModel.addDataListener(this);
		
		/*
		 * get the list of names of the fields to be
		 * displayed on separate columns
		 */
		fieldNames = dataModel.getFieldNames();

		setListMap();
		
		//create the view
		this.tableViewModel = new DataTableViewModel(this);
		this.table = new DataTable(mainController, this, tableViewModel);
		this.tablePanel = new DataTablePanel(this, table,
				btnAddName, btnRemoveName, btnCopyName);
		
	}
	
	private void setListMap(){
		//store a list of columns where the data type is a List
		listMap = new HashMap<Integer, Class<?>>();

		for (int col = 0; col < fieldNames.size(); col++){
			if (dataModel.getFieldClass(fieldNames.get(col)) == List.class){
				Type type = dataModel.getFieldType(fieldNames.get(col));
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
	public void setModel(IDataModel<T> newModel){
		if (dataModel != null){
			dataModel.removeDataListener(this);
		}
		dataModel = newModel;
		dataModel.addDataListener(this);
		fieldNames = newModel.getFieldNames();
		setListMap();
		tableViewModel.fireTableStructureChanged();
	}
	
	@Override
	public IDataModel<T> getModel() {return dataModel;}

	@Override
	public int getRowCount() {
		return dataModel.size();
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
		return dataModel.getFieldClass(fieldNames.get(col));
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		
		Object oldValue = null;
		try {
			oldValue = dataModel.get(row, fieldNames.get(col));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//for setting a list or array of values
		/*
		 * error checking is handled in a customised cell editor in
		 * the DataTable class
		 */
		if (listMap.containsKey(col)){
			try {
				//get the old list
				List<?> oldList = (List<?>) Cloner.deepClone(oldValue);
				
				StringTokenizer st = new StringTokenizer((String) value, "[, ]");
				Class<?> clazz = listMap.get(col);
				if (clazz == Double.class){
					ArrayList<Double> list = new ArrayList<Double>();
					while (st.hasMoreElements()){
						list.add(Double.parseDouble(st.nextToken()));
					}
					if (!list.toString().equals(oldValue)){
						mainController.setCommand(new DataModelEditFieldCommand(
								dataModel, row, fieldNames.get(col), oldList, list, "Edited list"));
					}
				} else if (clazz == BigInteger.class){
					ArrayList<BigInteger> list = new ArrayList<BigInteger>();
					while (st.hasMoreElements()){
						list.add(new BigInteger(st.nextToken()));
					}
					if (!list.toString().equals(oldValue)){
						mainController.setCommand(new DataModelEditFieldCommand(
								dataModel, row, fieldNames.get(col), oldList, list, "Edited list"));
					}
				} else if (clazz == String.class){
					ArrayList<String> list = new ArrayList<String>();
					while (st.hasMoreElements()){
						list.add(st.nextToken());
					}
					if (!list.toString().equals(oldValue)){
						mainController.setCommand(new DataModelEditFieldCommand(
								dataModel, row, fieldNames.get(col), oldList, list, "Edited list"));
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
			
			if (getColumnClass(col) == Boolean.class && 
					(Boolean) value == false) {
				value = null;
			}
			
			if ((value != null && !value.equals(oldValue)) ||
					(value == null && oldValue != null)){
			mainController.setCommand(new DataModelEditFieldCommand
					(dataModel, row, fieldNames.get(col), 
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
				return ((List<?>) dataModel.get(row, 
						fieldNames.get(col))).toString();
			}
			return dataModel.get(row, fieldNames.get(col));
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
	public void update(DataEvent e) {
		if (e.getDataModel() == dataModel.getActualModel()){
			if (e instanceof EditElementEvent){
				EditElementEvent evt = (EditElementEvent) e;
				tableViewModel.fireTableCellUpdated(
						evt.getIndex(), fieldNames.indexOf(evt.getField()));
				setSelectedCell(
						evt.getIndex(), fieldNames.indexOf(evt.getField()));
				mainController.setActiveController(this);
				
			} else if (e instanceof AddElementEvent){
				AddElementEvent evt = (AddElementEvent) e;
				tableViewModel.fireTableDataChanged();
				setSelectedCell(evt.getIndex(), 0);
				mainController.setActiveController(this);
				
			} else if (e instanceof RemoveElementEvent){
				tableViewModel.fireTableDataChanged();
				clearSelection();
				mainController.setActiveController(this);
			}
		}
	}

	@Override
	public void addRow() {
		addRow(dataModel.size());
	}

	@Override
	public void addRow(int row) {
		mainController.setCommand(new DataModelAddCommand<T>(dataModel, row,
				"Added a new row"));
	}

	@Override
	public void removeRow(int row) {
		mainController.setCommand(new DataModelRemoveCommand<T>(dataModel, row, row));
	}
	
	@Override
	public void removeRows(int [] rows){
		ArrayList<DataModelRemoveCommand<T>> commands = 
				new ArrayList<DataModelRemoveCommand<T>>();
		
		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);
		
		for (int i = 0; i < rows.length; i++){
			commands.add(new DataModelRemoveCommand<T>(dataModel, rows[i], rows[i]-i));
		}		
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}
	
	@Override
	public void copyRow(int row) {
		mainController.setCommand(new DataModelCopyCommand<T>(dataModel, row));
	}
	
	@Override
	public void copyRows(int [] rows){
		ArrayList<DataModelCopyCommand<T>> commands =
				new ArrayList<DataModelCopyCommand<T>>();
		
		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);
		
		for (int i = 0; i < rows.length; i++){
			commands.add(new DataModelCopyCommand<T>(dataModel, rows[i]+i));
		}
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}
	
	@Override
	public JComponent getView(){return tablePanel;}

	@Override
	public void startCellEditing(int row, int col) {
		table.editCellAt(row, col);	
	}

	@Override
	public void stopCellEditing() {
		if (table.getCellEditor() != null){
			if (!table.getCellEditor().stopCellEditing()) {
				table.getCellEditor().cancelCellEditing();
			}
		}
	}
	
	@Override 
	public void cancelCellEditing() {
		if (table.getCellEditor() != null){
			table.getCellEditor().cancelCellEditing();
		}
	}
	
	@Override
	public void setSelectedCell(int row, int col){
		table.changeSelection(row, col, false, false);
	}
	
	@Override
	public int getSelectedRow(){
		return table.getSelectedRow();
	}
	
	@Override
	public int getSelectedColumn(){
		return table.getSelectedColumn();
	}
	
	@Override
	public void clearSelection(){
		table.clearSelection();
	}

	@Override
	public Controller getParentController() {
		return parentController;
	}

	@Override
	public List<Controller> getChildControllers() {
		return null;
	}
	
	@Override
	public void activateController() {
		table.setFocusable(true);
	}

	@Override
	public void deactivateController() {
		stopCellEditing();
		clearSelection();
		table.setFocusable(false);
	}
}
