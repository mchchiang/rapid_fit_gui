package rapidFit.view.blocks;

import javax.swing.table.*;

import rapidFit.main.RapidFitMainControl;

import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.*;

@SuppressWarnings("serial")
public class DataTableModel<T> extends AbstractTableModel {
	private Class<T> dataClass;
	private List<T> data = null;
	private boolean singleColumnData = false;

	private ArrayList<String> columnNames = new ArrayList<String>();
	private ArrayList<Method> getMethods;
	private ArrayList<Method> setMethods;

	private HashMap<Integer, Class<?>> listMap = new HashMap<Integer, Class<?>>();

	//need Class<T> to determine the actual class of the generic type (irritating problem!)
	public DataTableModel (Class<T> type, List<T> data, ArrayList<String> ignoreAttributes){
		this.dataClass = type;
		this.data = data;


		/*
		 * check if the type of data is String, Double, or BigInteger
		 * instead of classes in the model. If it is, display the data on a 
		 * single column.
		 */
		if (dataClass == String.class || 
				dataClass == BigInteger.class || 
				dataClass == Double.class){
			singleColumnData = true;
			columnNames.add(null);		
			
		} else {
			getMethods = new ArrayList<Method>();
			setMethods = new ArrayList<Method>();

			//get setter and getter methods
			Method [] methods = dataClass.getDeclaredMethods();
			int col = 0;

			for (Method m: methods){
				try {
					if (m.getName().startsWith("get") && !(ignoreAttributes != null &&
							ignoreAttributes.contains(m.getName().substring(3)))){

						//add get method
						getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));

						//add column name
						columnNames.add(m.getName().substring(3));//remove "get"

						//check if the return type is a List object and add set methods
						if (m.getReturnType() == List.class){
							Type returnType = m.getGenericReturnType();
							if (returnType instanceof ParameterizedType){
								listMap.put(col, (Class<?>) 
										((ParameterizedType) returnType).getActualTypeArguments()[0]);
							} else {
								listMap.put(col, null);
							}
							setMethods.add(null);
						} else {
							setMethods.add(dataClass.getMethod(
									"set" + m.getName().substring(3), m.getReturnType()));
						}
										
						//for boolean data types (jaxb by default makes the method name is<AttributeName>)
					} else if (m.getName().startsWith("is") && !(ignoreAttributes != null &&
							ignoreAttributes.contains(m.getName().substring(3)))){
						//add get method
						getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));	

						//add column name
						columnNames.add(m.getName().substring(2));//remove "is"
						setMethods.add(dataClass.getMethod(
								"set" + m.getName().substring(2), m.getReturnType()));
					}
					col++;	
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		//check if it is a list
		if (singleColumnData) return dataClass;
		if (listMap.containsKey(col)) return String.class;
		return getMethods.get(col).getReturnType();
	}

	public boolean isCellEditable(int row, int col){
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

	public Object getValueAt(int row, int col) {
		if (singleColumnData){
			return data.get(row);
		} else {
			T param = data.get(row);
			Object value = null;
			try {
				if (listMap.containsKey(col)){
					value = ((List<?>) getMethods.get(col).invoke(
							param, (Object []) null)).toString();
				} else {
					value =  getMethods.get(col).invoke(param, (Object []) null);
				}

				//need to handle the error that the number of parameters passed might not be correct
			} catch (Exception e){
				e.printStackTrace();
			}
			return value;
		}
	}

	@SuppressWarnings("unchecked")
	public void setValueAt(Object value, int row, int col){
		if (singleColumnData){
			data.set(row, (T) value);
		} else {
			T param = data.get(row);
			try {
				//for setting a list or array of values
				/*
				 * error checking is handled in a customised cell editor in
				 * the DataTable class
				 */
				if (listMap.containsKey(col)){
					StringTokenizer st = new StringTokenizer((String) value, "[, ]");
					Class<?> clazz = listMap.get(col);
					if (clazz == Double.class){
						List<Double> list = (List<Double>) 
								getMethods.get(col).invoke(param, (Object []) null);
						list.clear();
						while(st.hasMoreElements()){
							list.add(Double.parseDouble(st.nextToken()));
						}

					} else if (clazz == BigInteger.class){
						List<BigInteger> list = (List<BigInteger>) 
								getMethods.get(col).invoke(param, (Object []) null);
						list.clear();
						while(st.hasMoreElements()){
							list.add(BigInteger.valueOf(Integer.parseInt(st.nextToken())));
						}

					} else if (clazz == String.class){
						List<String> list = (List<String>)
								getMethods.get(col).invoke(param, (Object []) null);
						list.clear();
						while(st.hasMoreElements()){
							list.add(st.nextToken());
						}
					}

				} else {
					/*
					 * for empty String input (i.e. ""), set the string to null.
					 * This is needed to ensure there is no empty tag <></> generated
					 */
					if (getColumnClass(col) == String.class && 
							((String) value).equals("")){
						setMethods.get(col).invoke(param, (getColumnClass(col)).cast(null));
					} else {
						setMethods.get(col).invoke(param, (getColumnClass(col).cast(value)));
					}
				}

				RapidFitMainControl.getInstance().setUnsavedEdits(true);

			} catch (Exception e){
				e.printStackTrace();
			}
		}

		fireTableCellUpdated(row, col);
	}

	public void addRow(int row, T entry){
		data.add(row, entry);
		fireTableDataChanged();
		RapidFitMainControl.getInstance().setUnsavedEdits(true);
	}

	public void addRow(int row){
		if (singleColumnData){
			data.add(row, null);
		} else {
			try{
				data.add(row, dataClass.newInstance());
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		fireTableDataChanged();
		RapidFitMainControl.getInstance().setUnsavedEdits(true);
	}

	public void addRow(){
		if (singleColumnData){
			data.add(null);
		} else {
			try{
				data.add(dataClass.newInstance());
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		fireTableDataChanged();
		RapidFitMainControl.getInstance().setUnsavedEdits(true);
	}

	public void removeRows(int [] rows){
		//first row is always the smallest index
		for (int i = 0; i < rows.length; i++){
			//need to take into account that removing an element changes the index
			removeRow(rows[i]-i);
		}
		RapidFitMainControl.getInstance().setUnsavedEdits(true);
	}

	public void removeRow(int row){
		data.remove(row);
		fireTableDataChanged();
		RapidFitMainControl.getInstance().setUnsavedEdits(true);
	}
	
	public HashMap<Integer, Class<?>> getListMap(){return listMap;}
}
