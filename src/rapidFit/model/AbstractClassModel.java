package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

public interface AbstractClassModel<T> {
	
	public void addObserver(ClassObserver co);
	public void removeObserver(ClassObserver co);
	public void notifyObserver();
	public void setUpdateField(String field);

	public void set(String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public Object get(String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public int getNumOfFields();
	public List<String> getFieldNames();
	public Class<?> getFieldClass(String fieldName);
	public Type getFieldType(String fieldName);
	
	
}
