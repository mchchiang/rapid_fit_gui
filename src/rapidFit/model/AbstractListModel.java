package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface AbstractListModel<T> {	
	public static enum UpdateType {
		ADD, REMOVE, EDIT;
	}
	
	public void addObserver(ListObserver lo);
	public void removeObserver(ListObserver lo);
	public void notifyObserver();
	
	public T get(int index);
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public int getNumOfFields();
	public List<String> getFieldNames();
	public List<Class<?>> getFieldClasses();
	
	public void set(int index, T object);
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public int size();
	public void add(int index) throws InstantiationException, IllegalAccessException;
	public void add(int index, T object);
	public void remove(int index);
	
}
