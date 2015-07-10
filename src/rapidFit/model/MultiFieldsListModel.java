package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
//import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MultiFieldsListModel<T> implements IListModel<T> {
	
	private Class<T> dataClass;
	private List<T> data;
	
	private HashMap<String, Field> fields;
	
	private ArrayList<IListObserver> observers;
	private UpdateType updateType;
	private String updateField;
	private int updateIndex;
	
	public MultiFieldsListModel(Class<T> clazz, List<T> data, ArrayList<String> ignoreAttributes) {
		this.data = data;
		this.dataClass = clazz;
		
		fields = new HashMap<String, Field>();
		
		observers = new ArrayList<IListObserver>();
		
		//get setter and getter methods
		Method [] methods = dataClass.getDeclaredMethods();
		
		for (Method m: methods){
			try {
				if (m.getName().startsWith("get") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(3)))){
					
					String fieldName = m.getName().substring(3);
					
					if (m.getReturnType() == List.class){
						fields.put(fieldName, new Field 
								(fieldName, m.getGenericReturnType(), 
								 m.getReturnType(), m, null));
					} else {
						fields.put(fieldName, new Field 
								(fieldName, m.getGenericReturnType(), 
								 m.getReturnType(), m, 
								 dataClass.getMethod(
										 "set" + fieldName, m.getReturnType())));
					}
					
				//for boolean data types (jaxb by default makes the method name is<AttributeName>)
				} else if (m.getName().startsWith("is") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(2)))){
					String fieldName = m.getName().substring(2);
					
					fields.put(fieldName, new Field 
							(fieldName, m.getGenericReturnType(), 
							 m.getReturnType(), m, 
							 dataClass.getMethod(
									 "set" + fieldName, m.getReturnType())));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void setList(List<T> data){
		this.data = data;
	}
	
	@Override
	public void set(int index, T object) {
		if (index >= 0 && index < data.size()){
			data.set(index, object);
			updateType = UpdateType.EDIT;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
		}
	}
	
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (fields.containsKey(fieldName)){
			fields.get(fieldName).getSetter().invoke(
					data.get(index), fields.get(fieldName).getFieldClass().cast(value));
			updateType = UpdateType.EDIT;
			updateField = fieldName;
			updateIndex = index;
			notifyListObserver();
		}
	}
	
	@Override
	public T get(int index) {
		if (index >= 0 && index < data.size()){
			return data.get(index);
		}
		return null;
	}
	
	@Override
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getGetter().invoke(data.get(index), (Object []) null);
		}
		return null;
	}
	
	@Override
	public void add(int index) throws InstantiationException, IllegalAccessException{
		if (index >= 0 && index <= data.size()){
			data.add(index, dataClass.newInstance());
			updateType = UpdateType.ADD;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
		}
	}
	
	@Override
	public void add(int index, T object) {
		if (index >= 0 && index <= data.size()){
			data.add(index, object);
			updateType = UpdateType.ADD;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
		}
	}
	
	@Override
	public void remove(int index) {
		if (index >= 0 && index < data.size()){	
			data.remove(index);
			updateType = UpdateType.REMOVE;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
		}
	}


	@Override
	public void addListObserver(IListObserver lo) {
		observers.add(lo);
	}

	@Override
	public void removeListObserver(IListObserver lo) {
		if (observers.contains(lo)){
			observers.remove(lo);
		}
	}

	@Override
	public void notifyListObserver() {
		for (IListObserver lo : observers){
			lo.update(updateIndex, updateType, updateField);
		}
	}

	@Override
	public int getNumOfFields() {
		return fields.size();
	}

	@Override
	public List<String> getFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<String>();
		fieldNames.addAll(fields.keySet());
		return fieldNames;
	}
	
	@Override
	public Class<?> getFieldClass(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getFieldClass();
		}
		return null;
	}
	
	@Override
	public Type getFieldType(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getType();
		}
		return null;
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public void setUpdateType(UpdateType t) {
		updateType = t;
	}

	@Override
	public void setUpdateField(String field) {
		if (fields.containsKey(field)){
			updateField = field;
		} else {
			updateField = null;
		}
	}

	@Override
	public void setUpdateIndex(int index) {
		if (index >= 0 && index < data.size()){
			updateIndex = index;
		} else {
			updateIndex = -1;
		}
	}
}
