package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ClassModel<T> implements AbstractClassModel<T>{
	
	private T data;
	private Class<T> dataClass;
	
	private ArrayList<Method> getMethods;
	private ArrayList<Method> setMethods;
	private ArrayList<Class<?>> fieldClasses;
	private ArrayList<String> fieldNames;
	
	private ArrayList<ClassObserver> observers;
	private String updateField;
	
	public ClassModel (Class<T> clazz, T data, ArrayList<String> ignoreAttributes){
		this.data = data;
		this.dataClass = clazz;
		
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
					
					fieldClasses.add(m.getReturnType());
					
					//set label name
					fieldNames.add(m.getName().substring(3));//remove "get"

					//add set method
					setMethods.add(dataClass.getMethod(
							"set" + m.getName().substring(3), m.getReturnType()));

				} else if (m.getName().startsWith("is") &&
						!(ignoreAttributes != null && 
						ignoreAttributes.contains(m.getName().substring(2)))){ 
					//add get method
					getMethods.add(dataClass.getMethod(m.getName(), (Class<?>[]) null));

					fieldClasses.add(m.getReturnType());
					
					//set label name
					fieldNames.add(m.getName().substring(2));//remove "is"

					//add set method
					setMethods.add(dataClass.getMethod(
							"set" + m.getName().substring(2), m.getReturnType()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
	}
	
	@Override
	public void addObserver(ClassObserver co) {
		observers.add(co);
	}

	@Override
	public void removeObserver(ClassObserver co) {
		observers.remove(co);
	}

	@Override
	public void notifyObserver() {
		for (ClassObserver co : observers){
			co.update(updateField);
		}
	}

	@Override
	public Object get(String fieldName) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		int fieldIndex = fieldNames.indexOf(fieldName);
		if (fieldIndex != -1){
			return getMethods.get(fieldIndex).invoke(data, (Object []) null);
		}
		return null;
	}
	
	@Override
	public void set(String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		
		int fieldIndex = fieldNames.indexOf(fieldName);
		if (fieldIndex != -1 && setMethods.get(fieldIndex) != null){
			setMethods.get(fieldIndex).invoke(
					data, fieldClasses.get(fieldIndex).cast(value));
			updateField = fieldName;
			notifyObserver();
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
}
