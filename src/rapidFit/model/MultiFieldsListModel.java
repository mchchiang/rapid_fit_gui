package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
//import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class MultiFieldsListModel<T> implements AbstractListModel<T> {
	
	private Class<T> dataClass;
	private List<T> data;
	
	private ArrayList<Method> getMethods;
	private ArrayList<Method> setMethods;
	private ArrayList<String> fieldNames;
	private ArrayList<Class<?>> fieldClasses;
	
	private ArrayList<ListObserver> observers;
	private UpdateType updateType;
	private String updateField;
	private int updateIndex;
	
	public MultiFieldsListModel(Class<T> clazz, List<T> data, ArrayList<String> ignoreAttributes) {
		this.data = data;
		this.dataClass = clazz;
		
		getMethods = new ArrayList<Method>();
		setMethods = new ArrayList<Method>();
		fieldNames = new ArrayList<String>();
		fieldClasses = new ArrayList<Class<?>>();
		
		observers = new ArrayList<ListObserver>();
		
		//get setter and getter methods
		Method [] methods = dataClass.getDeclaredMethods();
		
		for (Method m: methods){
			try {
				if (m.getName().startsWith("get") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(3)))){
					
					//add get method
					getMethods.add(m);
					
					fieldClasses.add(m.getReturnType());

					//add field name
					fieldNames.add(m.getName().substring(3));//remove "get"
					

					//check if the return type is a List object and add set methods
					if (m.getReturnType() == List.class){
						setMethods.add(null);

					} else {
						setMethods.add(dataClass.getMethod(
								"set" + m.getName().substring(3), m.getReturnType()));
					}
					
				//for boolean data types (jaxb by default makes the method name is<AttributeName>)
				} else if (m.getName().startsWith("is") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(3)))){
					//add get method
					getMethods.add(m);	
					fieldClasses.add(m.getReturnType());

					//add field name
					fieldNames.add(m.getName().substring(2));//remove "is"
					
					setMethods.add(dataClass.getMethod(
								"set" + m.getName().substring(2), m.getReturnType()));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void set(int index, T object) {
		if (index >= 0 && index < data.size()){
			data.set(index, object);
			updateType = UpdateType.EDIT;
		}
	}
	
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		int fieldIndex = fieldNames.indexOf(fieldName);
		if (fieldIndex != -1 && setMethods.get(fieldIndex) != null){
			setMethods.get(fieldIndex).invoke(data.get(index), 
					fieldClasses.get(fieldIndex).cast(value));
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
		int fieldIndex = fieldNames.indexOf(fieldName);
		if (fieldIndex != -1){
			return getMethods.get(fieldIndex).invoke(data.get(index), (Object []) null);
		}
		return null;
	}
	
	@Override
	public void add(int index) throws InstantiationException, IllegalAccessException{
		if (index >= 0 && index <= data.size()){
			data.add(index, dataClass.newInstance());
		}
	}
	
	@Override
	public void add(int index, T object) {
		if (index >= 0 && index <= data.size()){
			data.add(index, object);
		}
	}
	
	@Override
	public void remove(int index) {
		if (index >= 0 && index < data.size()){	
			data.remove(index);
		}
	}


	@Override
	public void addObserver(ListObserver lo) {
		observers.add(lo);
	}

	@Override
	public void removeObserver(ListObserver lo) {
		if (observers.contains(lo)){
			observers.remove(lo);
		}
	}

	@Override
	public void notifyObserver() {
		for (ListObserver lo : observers){
			lo.update(updateIndex, updateType, updateField);
		}
	}

	@Override
	public int getNumOfFields() {
		return fieldNames.size();
	}

	@Override
	public List<String> getFieldNames() {
		return fieldNames;
	}
	
	@Override
	public List<Class<?>> getFieldClasses() {
		return fieldClasses;
	}

	@Override
	public int size() {
		return data.size();
	}
}
