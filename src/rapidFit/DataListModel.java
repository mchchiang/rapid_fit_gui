package rapidFit;

import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataListModel<T> extends AbstractListModel<T> {
	
	private List<T> data;
	private Class<T> dataClass;
	
	public DataListModel (Class<T> type, List<T> data){
		this.data = data;
		this.dataClass = type;
	}
	
	public void addRow(int row, T entry){
		data.add(row, entry);
		fireIntervalAdded(this, row, row);
	}
	
	public void addRow(int row){
		try{
			addRow(row, dataClass.newInstance());
		} catch (Exception e){
			e.printStackTrace();
		}
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
	
	//refresh the entire list
	public void update(){
		this.fireContentsChanged(this, 0, getSize());
	}

	@Override
	public int getSize() {
		return data.size();
	}

	@Override
	public T getElementAt(int index) {
		return data.get(index);
	}
	
	public List<T> getData(){return data;}
	public Class<T> getDataClass(){return dataClass;}
}
