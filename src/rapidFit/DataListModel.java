package rapidFit;

import java.lang.reflect.*;
import java.util.*;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class DataListModel<T> extends AbstractTableModel {

	private Class<T> dataClass;
	private List<T> data = null;
	
	private Method getMethod = null;
	private Method setMethod = null;
	
	//only used for tag name
	private int attrCount = 1;
	private IdentityHashMap<T, String> nameMap = null;
	private boolean useTagName = false;
	
	
	public DataListModel(Class<T> type, List<T> data, Method getter){
		this.dataClass  = type;
		this.data = data;
		this.getMethod = getter;
		
		//replace "get" at the beginning with "set"
		try {
			this.setMethod = dataClass.getMethod(
					"set" + getMethod.getName().substring(3), 
					getMethod.getReturnType());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DataListModel (Class<T> type, List<T> data, String name, boolean useTag){
		this.dataClass = type;
		this.data = data;
		
		if (!useTag){
			useTagName = false;
			try {
				//find the get method 
				this.getMethod = dataClass.getMethod("get" + name, (Class<?>[]) null);
			
				//replace "get" at the beginning with "set"
				this.setMethod = dataClass.getMethod(
						"set" + getMethod.getName().substring(3), 
						getMethod.getReturnType());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			useTagName = true;
			nameMap = new IdentityHashMap<T, String>();
			for (T entry : data){
				nameMap.put(entry, name + "_" + attrCount);
				attrCount++;
			}
		}
	}

	public int getRowCount() {
		return data.size();
	}

	public int getColumnCount() {
		return 1;
	}
	
	public Class<?> getColumnClass(int col) {
		if (!useTagName){
			return getMethod.getReturnType();
		} else {
			return String.class;
		}
    }

	public boolean isCellEditable(int row, int column){
		return false;
	}

	public Object getValueAt(int row, int col) {
		T entry = data.get(row);
		Object value = null;
		try {
			value =  getMethod.invoke(entry, (Object []) null);
		//need to handle the error that the number of parameters passed might not be correct
		} catch (Exception e){
			e.printStackTrace();
		}
		return value;
	}
	
	public void setValueAt(Object value, int row, int col){
		T entry = data.get(row);
		if (!useTagName){
			try {
				setMethod.invoke(entry, (getColumnClass(col).cast(value)));
			} catch (Exception e){
				e.printStackTrace();
			}
		} else {
			nameMap.put(entry, (String) value);
		}
		fireTableCellUpdated(row, col);
	}
	
	public void addRow(int row){
		try{
			data.add(row+1, dataClass.newInstance());
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

}
