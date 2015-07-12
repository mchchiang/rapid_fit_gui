package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


public abstract class ImmutableDataModel<T> implements IDataModel<T> {
	
	public void setList(List<T> data){};
	
	public abstract T get(int index);
	public abstract Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public final void set(int index, T object){}
	public abstract void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	public final void add(int index){};
	public final void add(int index, T object){};
	public final void remove(int index){};
}
