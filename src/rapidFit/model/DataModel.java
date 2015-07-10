package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataModel<T> implements IListModel<T> {
	
	private ClassAgent classAgent;
	private Class<T> dataClass;
	private List<T> data;
	
	private ArrayList<IListObserver> observers;
	private UpdateType updateType;
	private String updateField;
	private int updateIndex;
	
	public DataModel(Class<T> clazz, List<T> data, ArrayList<String> ignoreAttributes) {
		this.data = data;
		this.dataClass = clazz;
		
		classAgent = new ClassAgent(clazz, ignoreAttributes);	
		observers = new ArrayList<IListObserver>();
	}
	
	@Override
	public void setList(List<T> data){
		this.data = data;
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
	
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (classAgent.getSetter(fieldName) != null){
			classAgent.getSetter(fieldName).invoke(
					data.get(index), classAgent.getFieldClass(fieldName).cast(value));
			updateType = UpdateType.EDIT;
			updateField = fieldName;
			updateIndex = index;
			notifyListObserver();
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
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if (classAgent.getGetter(fieldName) != null){
			return classAgent.getGetter(fieldName).invoke(data.get(index), (Object []) null);
		}
		return null;
	}
	
	@Override
	public void add(int index) throws InstantiationException, IllegalAccessException{
		if (index >= 0 && index <= data.size()){
			data.add(index, dataClass.newInstance());
			updateType = UpdateType.ADD;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
		}
	}
	
	@Override
	public void add(int index, T object) {
		if (index >= 0 && index <= data.size()){
			data.add(index, object);
			updateType = UpdateType.ADD;
			updateField = null;
			updateIndex = index;
			notifyListObserver();
		}
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
	public void addListObserver(IListObserver lo) {
		observers.add(lo);
	}

	@Override
	public void removeListObserver(IListObserver lo) {
		if (observers.contains(lo)){
			observers.remove(lo);
		}
	}

	@Override
	public void notifyListObserver() {
		for (IListObserver lo : observers){
			lo.update(updateIndex, updateType, updateField);
		}
	}

	@Override
	public int getNumOfFields() {
		return classAgent.getNumOfFields();
	}

	@Override
	public List<String> getFieldNames() {
		return classAgent.getFieldNames();
	}
	
	@Override
	public Class<?> getFieldClass(String fieldName){
		return classAgent.getFieldClass(fieldName);
	}
	
	@Override
	public Type getFieldType(String fieldName){
		return classAgent.getFieldType(fieldName);
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public void setUpdateType(UpdateType t) {
		updateType = t;
	}

	@Override
	public void setUpdateField(String field) {
		if (classAgent.getFieldNames().contains(field)){
			updateField = field;
		} else {
			updateField = null;
		}
	}

	@Override
	public void setUpdateIndex(int index) {
		if (index >= 0 && index < data.size()){
			updateIndex = index;
		} else {
			updateIndex = -1;
		}
	}
}
