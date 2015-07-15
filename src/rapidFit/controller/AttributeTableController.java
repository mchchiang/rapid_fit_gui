package rapidFit.controller;

import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.DataModelEditFieldCommand;
import rapidFit.model.DataEvent;
import rapidFit.model.EditElementEvent;
import rapidFit.model.IClassModel;
import rapidFit.view.bldblocks.AttributePanel;
import rapidFit.view.bldblocks.AttributeTable;
import rapidFit.view.bldblocks.AttributeTableViewModel;

public class AttributeTableController<T> implements IAttributeTableController<T> {

	private IClassModel<T> model;
	private AttributePanel tablePanel;
	private AttributeTable table;
	private AttributeTableViewModel tableViewModel;
	private UIController mainController;
	private Controller parentController;

	private List<String> fieldNames;

	public AttributeTableController (UIController controller, 
			Controller parentController, IClassModel<T> model, String className) {
		this.model = model;
		this.mainController = controller;
		this.parentController = parentController;

		model.addDataListener(this);

		fieldNames = model.getFieldNames();

		//create view
		tableViewModel = new AttributeTableViewModel(this);
		table = new AttributeTable(this, tableViewModel);
		tablePanel = new AttributePanel(table, className);
	}

	@Override
	public void setModel(IClassModel<T> newModel){
		if (model != null){
			model.removeDataListener(this);
		}
		model = newModel;
		model.addDataListener(this);
		fieldNames = newModel.getFieldNames();
		tableViewModel.fireTableDataChanged();
	}

	@Override
	public IClassModel<T> getModel(){return model;}

	@Override
	public int getRowCount() {
		return fieldNames.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int col) {
		if (col == 0){
			return "Attribute";
		} else {
			return "Value";
		}
	}

	@Override
	public Class<?> getRowClass(int row) {
		return model.getFieldClass(fieldNames.get(row));
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		if (col == 1){
			try {

				Object oldValue = model.get(fieldNames.get(row));

				/*
				 * for empty String input (i.e. ""), set the string to null.
				 * This is needed to ensure there is no empty tag <></> generated
				 */
				if (getRowClass(row) == String.class && 
						((String) value).equals("")){
					value = null;
				}
				
				if (getRowClass(row) == Boolean.class && 
						(Boolean) value == false) {
					value = null;
				}
				
				//only set a new value if it is different from the old one
				if ((value != null && !value.equals(oldValue)) ||
						(value == null && oldValue != null)){
					mainController.setCommand(new DataModelEditFieldCommand
							(model, 0, fieldNames.get(row), 
									oldValue, value, "Changed field " + fieldNames.get(row) + 
									" from \"" + oldValue + "\" to \"" + value + "\""));
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public Object getValueAt(int row, int col) {
		if (col == 0){
			return fieldNames.get(row);
		} else {
			try {
				return model.get(fieldNames.get(row));
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		if (col == 0){
			return false;
		} else {
			return true;
		}
	}

	@Override
	public JComponent getView() {return tablePanel;}

	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == model.getActualModel()
				&& e instanceof EditElementEvent){
			EditElementEvent evt = (EditElementEvent) e;
			tableViewModel.fireTableCellUpdated(
					fieldNames.indexOf(evt.getField()), 1);	
			setSelectedCell(fieldNames.indexOf(evt.getField()), 1);
			mainController.setActiveController(this);
		}			
	}

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
	public void makeViewFocusable(boolean focusable){
		table.setFocusable(focusable);
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
		mainController.setActiveController(this);
	}
}
