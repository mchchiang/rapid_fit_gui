package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

public interface IListModel<T> {	
	public static enum UpdateType {
		ADD, REMOVE, EDIT;
	}
	
	public void setList(List<T> data);
	
	public void addListObserver(IListObserver lo);
	public void removeListObserver(IListObserver lo);
	public void notifyListObserver();
	public void setUpdateType(UpdateType t);
	public void setUpdateField(String field);
	public void setUpdateIndex(int index);
	
	public T get(int index);
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public void set(int index, T object);
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public int getNumOfFields();
	public List<String> getFieldNames();
	public Class<?> getFieldClass(String fieldName);
	public Type getFieldType(String fieldName);
	
	public int size();
	public void add(int index) throws InstantiationException, IllegalAccessException;
	public void add(int index, T object);
	public void remove(int index);
	
}