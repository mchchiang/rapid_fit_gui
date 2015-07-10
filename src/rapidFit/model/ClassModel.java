package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ClassModel implements IClassModel{
	
	private Object data;
	private ClassAgent classAgent;
	
	private ArrayList<IClassObserver> observers;
	private String updateField;
	
	public ClassModel (Class<?> clazz, Object data, ArrayList<String> ignoreAttributes){
		this.data = data;
		
		classAgent = new ClassAgent(clazz, ignoreAttributes);		
		observers = new ArrayList<IClassObserver>();
		
	}
	
	@Override
	public void setModelledData(Object data){
		this.data = data;
		for (String fieldName : classAgent.getFieldNames()){
			System.out.println(fieldName);
			updateField = fieldName;
			notifyObserver();
		}
	}
	
	@Override
	public void addObserver(IClassObserver co) {
		observers.add(co);
	}

	@Override
	public void removeObserver(IClassObserver co) {
		observers.remove(co);
	}

	@Override
	public void notifyObserver() {
		for (IClassObserver co : observers){
			co.update(updateField);
		}
	}

	@Override
	public Object get(String fieldName) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (classAgent.getGetter(fieldName) != null){
			return classAgent.getGetter(fieldName).invoke(data, (Object []) null);
		}
		return null;
	}
	
	@Override
	public void set(String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (classAgent.getSetter(fieldName) != null){
			classAgent.getSetter(fieldName).invoke(
					data, classAgent.getFieldClass(fieldName).cast(value));
			updateField = fieldName;
			notifyObserver();
		}
	}

	@Override
	public int getNumOfFields() {
		return classAgent.getNumOfFields();
	}

	@Override
	public List<String> getFieldNames() {
		return classAgent.getFieldNames();
	}

	@Override
	public void setUpdateField(String field) {
		if (classAgent.getFieldNames().contains(field)){
			updateField = field;
		} else {
			updateField = null;
		}
	}

	@Override
	public Class<?> getFieldClass(String fieldName) {
		return classAgent.getFieldClass(fieldName);
	}

	@Override
	public Type getFieldType(String fieldName) {
		return classAgent.getFieldType(fieldName);
	}

}
