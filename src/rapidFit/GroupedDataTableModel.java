package rapidFit;

import java.util.*;

@SuppressWarnings("serial")
public class GroupedDataTableModel<T> extends DataTableModel<T> {
	private List<List<T>> data;
	
	private int [] numOfRows;
	private int numOfGroups;
	
	public GroupedDataTableModel(Class<T> type, 
			List<List<T>> groupedData, ArrayList<String> ignoreAttr) {
		super(type, null, ignoreAttr);
		dataClass = type;
		data = groupedData;
		numOfGroups = groupedData.size();
		numOfRows = new int [numOfGroups];
		
		//get the number of entries per group
		for (int i = 0; i < numOfGroups; i++){
			numOfRows[i] = data.get(i).size();
		}
	}
	
	public Object getValueAt(int row, int col) {
		return null;
	}
	
	public void setValueAt(Object value, int row, int col){
		
	}
	
	public void addRow(int row){
		
		fireTableDataChanged();
	}
	
	public void addRow(){
		
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
