package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ListModel<T> implements IDataModel<T> {
	
	private List<T> list;
	private Class<T> listClass;
	private List<DataListener> listeners;
	private String listName;
	
	public ListModel(Class<T> clazz, List<T> list, String name){
		this.listClass = clazz;
		this.list = list;
		this.listName = name;
		listeners = new ArrayList<DataListener>();
	}
	
	@Override
	public void addDataListener(DataListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeDataListener(DataListener listener) {
		if (listeners.contains(listener)){
			listeners.remove(listener);
		}
	}

	@Override
	public void notifyDataListener(DataEvent e) {
		List<DataListener> listenersCopy = new ArrayList<DataListener>();
		listenersCopy.addAll(listeners);
		for (DataListener listener : listenersCopy){
			listener.update(e);
		}
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void add(int index) throws InstantiationException,
			IllegalAccessException {
		list.add(index, null);
		notifyDataListener(new AddElementEvent(this, index));		
	}

	@Override
	public void add(T object) {
		list.add(object);
		notifyDataListener(new AddElementEvent(this, list.size()-1));
	}

	@Override
	public void add(int index, T object) {
		list.add(index, object);
		notifyDataListener(new AddElementEvent(this, index));
	}

	@Override
	public void remove(int index) {
		T object = list.remove(index);
		notifyDataListener(new RemoveElementEvent(this, index, object));
	}

	@Override
	public void remove(T object) {
		int index = list.indexOf(object);
		if (index != -1){
			remove(index);
		}
		notifyDataListener(new RemoveElementEvent(this, index, object));
	}

	@Override
	public int indexOf(T object) {
		return list.indexOf(object);
	}

	@Override
	public void set(int index, T object) {
		list.set(index, object);
		notifyDataListener(new SetElementEvent(this, index));
	}

	@Override
	public void set(int index, String field, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		if (value.getClass() == listClass){
			set(index, listClass.cast(value));
		}
	}

	@Override
	public T get(int index) {
		return list.get(index);
	}

	@Override
	public Object get(int index, String field) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return get(index);
	}

	@Override
	public int getNumOfFields() {
		return 1;
	}

	@Override
	public AbstractList<String> getFieldNames() {
		AbstractList<String> fieldNames = new ArrayList<String>();
		fieldNames.add(listName);
		return fieldNames;
	}

	@Override
	public Class<?> getFieldClass(String field) {
		return listClass;
	}

	@Override
	public Type getFieldType(String field) {
		return listClass;
	}

	@Override
	public Class<T> getDataClass() {
		return listClass;
	}

	@Override
	public IDataModel<T> getActualModel() {
		return this;
	}

}
