package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClassModel implements IClassModel{
	
	private Object data;
	private Class<?> dataClass;
	
	private HashMap<String, Field> fields;
	
	private ArrayList<IClassObserver> observers;
	private String updateField;
	
	public ClassModel (Class<?> clazz, Object data, ArrayList<String> ignoreAttributes){
		this.data = data;
		this.dataClass = clazz;
		
		observers = new ArrayList<IClassObserver>();
		
		fields = new HashMap<String, Field>();
		
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
	public void setModelledData(Object data){
		this.data = data;
		for (String fieldName : fields.keySet()){
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
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getGetter().invoke(data, (Object []) null);
		}
		return null;
	}
	
	@Override
	public void set(String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (fields.containsKey(fieldName)){
			fields.get(fieldName).getSetter().invoke(
					data, fields.get(fieldName).getFieldClass().cast(value));
			updateField = fieldName;
			notifyObserver();
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
	public void setUpdateField(String field) {
		if (fields.containsKey(field)){
			updateField = field;
		} else {
			updateField = null;
		}
	}

	@Override
	public Class<?> getFieldClass(String fieldName) {
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getFieldClass();
		}
		return null;
	}

	@Override
	public Type getFieldType(String fieldName) {
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getType();
		}
		return null;
	}

}
