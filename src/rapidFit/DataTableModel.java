package rapidFit;

import javax.swing.table.*;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("serial")
public class DataTableModel<T> extends AbstractTableModel {
	private Class<T> dataClass;
	private List<T> data = null;
	
	private ArrayList<String> columnNames = new ArrayList<String>();
	private ArrayList<Method> getMethods = new ArrayList<Method>();
	private ArrayList<Method> setMethods = new ArrayList<Method>();
	
	//need Class<T> to determine the actual class of the generic type (irritating problem!)
	public DataTableModel (Class<T> type, List<T> data, ArrayList<String> ignoreAttributes){
		this.dataClass = type;
		this.data = data;
		
		//get setter and getter methods
		Method [] methods = dataClass.getDeclaredMethods();

		for (Method m: methods){
			try {
				if (m.getName().startsWith("get") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(3)))){
					//add get method
					getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));	
					
					//add column name
					columnNames.add(m.getName().substring(3));//remove "get"
					
					//add set method
					setMethods.add(dataClass.getMethod(
							"set" + m.getName().substring(3), m.getReturnType()));
					
				//for boolean data types (jaxb by default makes the method name is<AttributeName>)
				} else if (m.getName().startsWith("is") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(3)))){
					//add get method
					getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));	
					
					//add column name
					columnNames.add(m.getName().substring(2));//remove "is"
					
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
		return data.size();
	}

	public int getColumnCount() {
		return columnNames.size();
	}

	public String getColumnName(int columnIndex){
		return columnNames.get(columnIndex);
	}
	
	public Class<?> getColumnClass(int col) {
		return getMethods.get(col).getReturnType();
    }

	public boolean isCellEditable(int row, int column){
		return true;
	}

	public Object getValueAt(int row, int col) {
		T param = data.get(row);
		Object value = null;
		try {
			value =  getMethods.get(col).invoke(param, (Object []) null);
		//need to handle the error that the number of parameters passed might not be correct
		} catch (Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	public void setValueAt(Object value, int row, int col){
		T param = data.get(row);
		try {
			setMethods.get(col).invoke(param, (getColumnClass(col).cast(value)));
		} catch (Exception e){
			e.printStackTrace();
		}
		fireTableCellUpdated(row, col);
	}
	
	public void addRow(int row, T entry){
		data.add(row, entry);
		fireTableDataChanged();
	}
	
	public void addRow(int row){
		try{
			data.add(row, dataClass.newInstance());
		} catch (Exception e){
			e.printStackTrace();
		}
		fireTableDataChanged();
	}
	
	public void addRow(){
		try{
			data.add(dataClass.newInstance());
		} catch (Exception e){
			e.printStackTrace();
		}
		fireTableDataChanged();
	}
	
	public void removeRows(int [] rows){
		//first row is always the smallest index
		for (int i = 0; i < rows.length; i++){
			//need to take into account that removing an element changes the index
			removeRow(rows[i]-i);
		}
	}
	
	public void removeRow(int row){
		data.remove(row);
		fireTableDataChanged();
	}
	
	public ArrayList<Method> getGetterMethods(){return getMethods;}

}
