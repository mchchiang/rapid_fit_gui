package rapidFit;

import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataListModel_old<T> extends AbstractListModel<Object> {

	private Class<T> dataClass;
	private List<T> data = null;
	
	private Method getMethod = null;
	private Method setMethod = null;
	
	//only used for tag name
	private int attrCount = 1;
	private IdentityHashMap<T, String> nameMap = null;
	private boolean useTagName = false;
	
	
	public DataListModel_old(Class<T> type, List<T> data, Method getter){
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
	
	public DataListModel_old (Class<T> type, List<T> data, String name, boolean useTag){
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
	
	public void addRow(int row){
		try{
			data.add(row+1, dataClass.newInstance());
		} catch (Exception e){
			e.printStackTrace();
		}
		fireIntervalAdded(this, row+1, row+1);
	}
	
	public void addRow(){
		try{
			data.add(dataClass.newInstance());
		} catch (Exception e){
			e.printStackTrace();
		}
		fireIntervalAdded(this, getSize(), getSize());
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
		fireIntervalRemoved(this, row, row);
	}

	@Override
	public int getSize() {
		return data.size();		
	}

	@Override
	public Object getElementAt(int index) {
		T entry = data.get(index);
		Object value = null;
		if (!useTagName){
			try {
				value =  getMethod.invoke(entry, (Object []) null);
			//need to handle the error that the number of parameters passed might not be correct
			} catch (Exception e){
				e.printStackTrace();
			}
		} else {
			value = nameMap.get(entry);
		}
		return value;
	}
	
	
}
