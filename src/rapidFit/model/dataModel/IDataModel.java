package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 
 * An interface that models a data set with multiple entries and
 * each entry has multiple editable fields.
 * 
 * @author MichaelChiang
 *
 * @param <T> The Java class type of each entry
 * 
 */
public interface IDataModel<T> {
	
	/*
	 * Methods needed to notify the listeners for any events occurred
	 * in the data set 
	 */
	
	/**
	 * Add a listener that listens to any data events related
	 * to this data model.
	 * 
	 * @param listener Any object that implements the DataListener interface
	 * @see rapidFit.model.dataModel.DataListener
	 * 
	 */
	public void addDataListener(DataListener listener);
	
	/**
	 * 
	 * Remove a listener that listens to any data events related
	 * to this data model.
	 * 
	 * @param listener Any object that implements the DataListener interface
	 * @see rapidFit.model.dataModel.DataListener
	 * 
	 */
	public void removeDataListener(DataListener listener);
	
	/**
	 * 
	 * 
	 * 
	 * @param e The data event that has occurred in the data set
	 * 
	 */
	public void notifyDataListener(DataEvent e);
	
	
	/**
	 * 
	 * Return the number of entries in the data set
	 * 
	 */
	public int size();
	
	/**
	 * 
	 * Add a new empty entry at a particular index to the data set. 
	 * 
	 * @param index Index where the new entry should be inserted
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * 
	 */
	public void add(int index)
			throws InstantiationException, IllegalAccessException;
	
	/**
	 * 
	 * Add an entry to the data set
	 * 
	 * @param object The entry to be added to the data set
	 * 
	 */
	public void add(T object);
	
	/**
	 * 
	 * Insert an entry at a particular index to the data set
	 * 
	 * @param index Index where the entry should be inserted
	 * @param object The entry to be inserted
	 * 
	 */
	public void add(int index, T object);
	
	/**
	 * 
	 * Remove the entry at a particular index from the data set
	 * 
	 * @param index Index of the entry
	 * 
	 */
	public void remove(int index);
	
	/**
	 * 
	 * Remove the first occurrence of the object from the data set
	 * 
	 * @param object Object to be removed
	 * 
	 */
	public void remove(T object);
	
	/**
	 * 
	 * Return the index of the first occurring
	 * 
	 * @param object
	 * @return
	 */
	public int indexOf(T object);
	
	/**
	 * 
	 * 
	 * 
	 * @param index
	 * @param object
	 * 
	 */
	public void set(int index, T object);
	
	/**
	 * 
	 * 
	 * 
	 * @param index
	 * @param field
	 * @param value
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * 
	 */
	public void set(int index, String field, Object value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	public T get(int index);
	
	/**
	 * 
	 * @param index
	 * @param field
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * 
	 */
	public Object get(int index, String field)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	/**
	 * 
	 * Return the total number of editable fields of an 
	 * entry in the data set
	 * 
	 */
	public int getNumOfFields();
	
	/**
	 * 
	 * Return the field names of all the editable fields of 
	 * an entry in the data set
	 * 
	 */
	public List<String> getFieldNames();
	
	/**
	 * 
	 * Return the Java class (<code>Class<?></code>) of the
	 * specified field
	 * 
	 * @param field The name of the field
	 * 
	 */
	public Class<?>	getFieldClass(String field);
	
	/**
	 * 
	 * Return the Java <code>Type</code> of the specified field
	 * 
	 * @param field The name of the field
	 * 
	 */
	public Type getFieldType(String field);
	
	/**
	 * 
	 * Return the Java class of the entries in the data set
	 * 
	 */
	public Class<T> getDataClass();
	
	/**
	 * 
	 * Return the actual/root model of this data model. The root model is 
	 * the data model that performs direct manipulations on the
	 * data set (e.g. <code>DataModel</code> and <code>ClassModel</code>).
	 * This method is needed as there are cases when the root model is 
	 * wrapped in a decorating model to add additional behaviours to the
	 * root model.
	 * 
	 */
	public IDataModel<T> getActualModel();
	
}
