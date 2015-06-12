package rapidFit;

import java.lang.reflect.*;
import java.util.*;

import javax.swing.table.*;

@SuppressWarnings("serial")
public class AttributeTableModel<T> extends AbstractTableModel {

	private Class<T> dataClass; 
	private T data;
	private ArrayList<Method> getMethods = new ArrayList<Method>();
	private ArrayList<Method> setMethods = new ArrayList<Method>();
	private ArrayList<String> rowNames = new ArrayList<String>();

	public AttributeTableModel(Class<T> type, T data, ArrayList<String> ignoreAttributes){
		this.dataClass = type;
		this.data = data;

		//get setter and getter methods
		Method [] methods = dataClass.getDeclaredMethods();

		for (Method m: methods){
			try {
				if ((m.getName().startsWith("get") && 
						!m.getReturnType().equals(List.class)) &&
						!(ignoreAttributes != null && 
						ignoreAttributes.contains(m.getName().substring(3)))){
				
					//add get method
					getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));
					
					//set label name
					rowNames.add(m.getName().substring(3));//remove "get"
					
					//add set method
					setMethods.add(dataClass.getMethod(
							"set" + m.getName().substring(3), m.getReturnType()));
										
				} else if (m.getName().startsWith("is") &&
						!(ignoreAttributes != null && 
						ignoreAttributes.contains(m.getName().substring(2)))){ 
					//add get method
					getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));
					
					//set label name
					rowNames.add(m.getName().substring(2));//remove "is"
					
					//add set method
					setMethods.add(dataClass.getMethod(
							"set" + m.getName().substring(2), m.getReturnType()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	public int getRowCount() {
		return rowNames.size();
	}

	public int getColumnCount() {
		return 2;
	}

	public String getColumnName(int col){
		if (col == 0){
			return "Type";
		} else {
			return "Value";
		}
	}
	
	public Class<?> getRowClass(int row){
		return getMethods.get(row).getReturnType();
	}
	
	public boolean isCellEditable(int row, int col){
		if (col == 1){
			return true;
		} else {
			return false;
		}
	}

	public Object getValueAt(int row, int col) {
		if (col == 0){
			return rowNames.get(row);
		} else {
			Object value = null;
			try {
				value =  getMethods.get(row).invoke(data, (Object []) null);
			//need to handle the error that the number of parameters passed might not be correct
			} catch (Exception e){
				e.printStackTrace();
			}
			return value;
		}
	}
	
	public void setValueAt(Object value, int row, int col){
		if (col == 1){
			try {
				/*
				 * for empty String input (i.e. ""), set the string to null.
				 * This is needed to ensure there is no empty tag <></> generated
				 */
				if (getRowClass(row) == String.class &&
						((String) value).equals("")){
					setMethods.get(row).invoke(data, 
							(getRowClass(row).cast(null)));
				} else {
					setMethods.get(row).invoke(data, 
							(getRowClass(row).cast(value)));
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			fireTableCellUpdated(row, col);
		}
	}
}
