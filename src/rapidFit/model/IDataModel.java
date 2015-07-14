package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 
 * A class that models a data set with multiple entries and
 * each entry has multiple fields.
 * 
 * @author MichaelChiang
 *
 * @param <T> The Java class type of each entry
 * 
 */
public interface IDataModel<T> {
	
	/**
	 * Add a listener that listens to any data events related
	 * to this data model.
	 * 
	 * @param listener Any object that implements the DataListener interface
	 * @see rapidFit.model.DataListener
	 * 
	 */
	public void addDataListener(DataListener listener);
	
	/**
	 * Remove a listener that listens to any data events related
	 * to this data model.
	 * 
	 * @param listener Any object that implements the DataListener interface
	 * @see rapidFit.model.DataListener
	 * 
	 */
	public void removeDataListener(DataListener listener);
	public void notifyDataListener(DataEvent e);
	
	public int size();
	
	public void add(int index)
			throws InstantiationException, IllegalAccessException;
	public void add(T object);
	public void add(int index, T object);
	public void remove(int index);
	public void remove(T object);
	public int indexOf(T object);
	
	public void set(int index, T object);
	public void set(int index, String field, Object value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public T get(int index);
	public Object get(int index, String field)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public int getNumOfFields();
	public List<String> getFieldNames();
	public Class<?>	getFieldClass(String field);
	public Type getFieldType(String field);
	public Class<T> getDataClass();
	
	public IDataModel<T> getActualModel();
	
}
