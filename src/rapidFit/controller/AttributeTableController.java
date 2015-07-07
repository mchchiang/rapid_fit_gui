package rapidFit.controller;

import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.ClassModelEditFieldCommand;
import rapidFit.model.AbstractClassModel;
import rapidFit.model.ClassObserver;
import rapidFit.view.bldblocks.AttributePanel;
import rapidFit.view.bldblocks.AttributeTableViewModel;

public class AttributeTableController<T> implements AbstractAttributeTableController, ClassObserver {

	private AbstractClassModel<T> model;
	private AttributePanel tablePanel;
	private AttributeTableViewModel tableViewModel;
	private MainController mainController;

	private List<String> fieldNames;

	public AttributeTableController (MainController controller, AbstractClassModel<T> model, String className) {
		this.model = model;
		this.mainController = controller;
		
		model.addObserver(this);

		fieldNames = model.getFieldNames();

		//create view
		tableViewModel = new AttributeTableViewModel(this);
		tablePanel = new AttributePanel(tableViewModel, className);
	}

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
				/*
				 * for empty String input (i.e. ""), set the string to null.
				 * This is needed to ensure there is no empty tag <></> generated
				 */
				if (getRowClass(row) == String.class && 
						((String) value).equals("")){
					value = null;
				}
				
				//only set a new value if it is different from the old one
				if (value != null && !value.equals(getValueAt(row, col)) ||
						value == null && getValueAt(row, col) != null){
					Object oldValue = model.get(fieldNames.get(row));
					mainController.setCommand(new ClassModelEditFieldCommand
							(model, fieldNames.get(row), 
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
	public JComponent getViewComponent() {return tablePanel;}

	@Override
	public void update(String field) {
		tableViewModel.fireTableCellUpdated(fieldNames.indexOf(field), 1);		
	}

}
