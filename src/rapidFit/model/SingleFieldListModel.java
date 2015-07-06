package rapidFit.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rapidFit.model.AbstractListModel.UpdateType;

public class SingleFieldListModel<T> implements AbstractListModel<T> {
	
	private List<T> data;
	private Class<T> dataClass;
	private ArrayList<ListObserver> observers;
	private int updateIndex;
	private UpdateType updateType;
	private String updateField;
	private final String FIELD_NAME = "Value";
	
	
	public SingleFieldListModel (Class<T> clazz, List<T> data){
		this.data = data;
		this.dataClass = clazz;
		observers = new ArrayList<ListObserver>();
	}

	@Override
	public void addObserver(ListObserver lo) {
		observers.add(lo);
	}

	@Override
	public void removeObserver(ListObserver lo) {
		observers.remove(lo);
	}

	@Override
	public void notifyObserver() {
		for (ListObserver lo : observers){
			lo.update(updateIndex, updateType, updateField);
		}
	}

	@Override
	public T get(int index) {
		if (index >= 0 && index < data.size()){
			return data.get(index);
		}
		return null;
	}
	
	@Override
	public Object get(int index, String fieldName) {
		if (fieldName.equals(FIELD_NAME)){
			return get(index);
		}
		return null;
	}

	@Override
	public void set(int index, T object) {
		if (index >= 0 && index < data.size()){
			data.set(index, object);
			updateType = UpdateType.EDIT;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void set(int index, String fieldName, Object value) {
		if (fieldName.equals(FIELD_NAME)){
			set(index, (T) value);
		}
	}
	
	@Override
	public void add(int index) {
		data.add(index, null);	
	}
	
	@Override
	public void add(int index, T object){
		data.add(index, object);
	}

	@Override
	public void remove(int index) {
		if (index >= 0 && index < data.size()){	
			data.remove(index);
		}
	}

	@Override
	public int size() {
		return data.size();
	}
	
	@Override
	public int getNumOfFields() {
		return 1;
	}
	
	@Override
	public List<String> getFieldNames() {
		ArrayList<String> fields = new ArrayList<String>();
		fields.add(FIELD_NAME);
		return fields;
	}
	
	@Override
	public List<Class<?>> getFieldClasses() {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(dataClass);
		return classes;
	}
	
	@Override
	public List<Type> getFieldTypes() {
		ArrayList<Type> types = new ArrayList<Type>();
		types.add(dataClass);
		return types;
	}
	
	@Override
	public void setUpdateType(UpdateType t) {
		updateType = t;
	}

	@Override
	public void setUpdateField(String field) {}

	@Override
	public void setUpdateIndex(int index) {
		if (index >= 0 && index < data.size()){
			updateIndex = index;
		} else {
			updateIndex = -1;
		}
	}
}
