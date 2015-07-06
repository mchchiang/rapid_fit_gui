package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface AbstractClassModel<T> {
	
	public void addObserver(ClassObserver co);
	public void removeObserver(ClassObserver co);
	public void notifyObserver();

	public Object get(String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public int getNumOfFields();
	public List<String> getFieldNames();
	public List<Class<?>> getFieldClasses();
	
	public void set(String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
}
