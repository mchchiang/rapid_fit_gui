package rapidFit.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListModel<T> implements IListModel<T> {
	
	private List<T> data;
	private Class<T> dataClass;
	private ArrayList<IListObserver> observers;
	private int updateIndex;
	private UpdateType updateType;
	private String updateField;
	private final String FIELD_NAME = "Value";
	private boolean hasDefaultConstructor = false;
	
	
	public ListModel (Class<T> clazz, List<T> data){
		this.data = data;
		this.dataClass = clazz;
		observers = new ArrayList<IListObserver>();
		
		//check if class has default constructor
		for (Constructor<?> constructor : dataClass.getConstructors()){
			if (constructor.getParameterTypes().length == 0){
				hasDefaultConstructor = true;
				break;
			}
		}
	}
	
	@Override
	public void setList(List<T> data){
		this.data = data;
	}
	

	@Override
	public void addListObserver(IListObserver lo) {
		observers.add(lo);
	}

	@Override
	public void removeListObserver(IListObserver lo) {
		observers.remove(lo);
	}

	@Override
	public void notifyListObserver() {
		for (IListObserver lo : observers){
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
			updateField = null;
			updateIndex = index;
			notifyListObserver();
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
	public void add(int index) throws InstantiationException, IllegalAccessException{
		if (hasDefaultConstructor){
			data.add(index, dataClass.newInstance());
		} else {
			data.add(index, null);
		}
		
		updateType = UpdateType.ADD;
		updateField = null;
		updateIndex = index;
		notifyListObserver();
	}
	
	@Override
	public void add(int index, T object){
		data.add(index, object);
		updateType = UpdateType.ADD;
		updateField = null;
		updateIndex = index;
		notifyListObserver();
	}

	@Override
	public void remove(int index) {
		if (index >= 0 && index < data.size()){	
			data.remove(index);
			updateType = UpdateType.REMOVE;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
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

	@Override
	public Class<?> getFieldClass(String fieldName) {
		if (fieldName.equals(FIELD_NAME)){
			return dataClass;
		}
		return null;
	}

	@Override
	public Type getFieldType(String fieldName) {
		return getFieldClass(fieldName);
	}
}
