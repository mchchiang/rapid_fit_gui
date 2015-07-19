package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;

public abstract class IClassModel<T> implements IDataModel<T> {
	
	public abstract T getObject();
	public abstract void set(String field, Object value)
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	public abstract Object get(String field)
		throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public final int size(){return 1;}
	
	/*
	 * Disable all the behaviours for adding and removing elements
	 * from the data model as there is only one (immutable) element
	 * in a class model
	 */
	public final void add(int index){}
	public final void add(T object){}
	public final void add(int index, T object){}
	public final void remove(int index){}
	public final void remove(T object){}
	public final int indexOf(T object){return 0;}
	
	public final void set(int index, String field, Object value)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		set(field, value);
	}
	public final Object get(int index, String field) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return get(field);
	}
	
	public final void set(int index, T object){}
	
	public final T get(int index){
		return getObject();
	}
}
