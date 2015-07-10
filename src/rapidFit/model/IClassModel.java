package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

public interface IClassModel {
	
	public void setModelledData(Object data);
	
	public void addObserver(IClassObserver co);
	public void removeObserver(IClassObserver co);
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
