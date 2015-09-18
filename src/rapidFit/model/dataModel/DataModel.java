package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import rapidFit.model.dataModel.event.AddElementEvent;
import rapidFit.model.dataModel.event.DataEvent;
import rapidFit.model.dataModel.event.EditElementEvent;
import rapidFit.model.dataModel.event.RemoveElementEvent;
import rapidFit.model.dataModel.event.SetElementEvent;

public class DataModel<T> implements IDataModel<T> {
	
	private ClassAgent classAgent;
	private List<T> data;
	private Class<T> dataClass;
	
	private ArrayList<DataListener> listeners;
	
	public DataModel(Class<T> clazz, List<T> data, ArrayList<String> ignoreAttributes) {
		this.data = data;	
		this.dataClass = clazz;
		classAgent = new ClassAgent(dataClass, ignoreAttributes);	
		listeners = new ArrayList<DataListener>();
	}
	
	@Override
	public void set(int index, T object) {
		if (index >= 0 && index < data.size()){
			T oldObject = data.get(index);
			data.set(index, object);
			notifyDataListener(new SetElementEvent(this, index, oldObject, object));
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		/*
		 * for handling fields whose data types are a List. This
		 * is needed as there is no setter method for these fields
		 */
		if (classAgent.getFieldClass(fieldName) == List.class) {
			boolean listEdited = false;
			List list = (List) get(index, fieldName);
			
			if (value == null){
				list.clear();
				listEdited = true;
				
			} else if (value != null && value instanceof List) {
				List<?> newList = (List<?>) value;
				
				if (newList.size() == 0){
					list.clear();
					listEdited = true;
				
				/*
				 * check to make sure that the generic type of the 
				 * old and that of the new list match.
				 */
				} else if (classAgent.getFieldType(fieldName) instanceof ParameterizedType &&
						((ParameterizedType) classAgent.getFieldType(fieldName)).
						getActualTypeArguments()[0] == newList.get(0).getClass()) {
					
					list.clear();
					for (int i = 0; i < newList.size(); i++){
						list.add(newList.get(i));
					}
					
					listEdited = true;
				}
			}
			
			if (listEdited){
				notifyDataListener(new EditElementEvent(this, index, fieldName));
			}
			
		} else if (classAgent.getSetter(fieldName) != null){
			classAgent.getSetter(fieldName).invoke(
					data.get(index), classAgent.getFieldClass(fieldName).cast(value));
			notifyDataListener(new EditElementEvent(this, index, fieldName));
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
			notifyDataListener(new AddElementEvent(this, index, data.get(index)));
		}
	}
	
	@Override
	public void add(T object) {
		data.add(object);
		notifyDataListener(new AddElementEvent(this, data.size()-1, object));
	}
	
	@Override
	public void add(int index, T object) {
		if (index >= 0 && index <= data.size()){
			data.add(index, object);
			notifyDataListener(new AddElementEvent(this, index, object));
		}
	}
	
	@Override
	public void remove(int index) {
		if (index >= 0 && index < data.size()){	
			T removedObject = data.remove(index);
			notifyDataListener(new RemoveElementEvent(this, index, removedObject));
		}
	}
	
	@Override
	public void remove(T object) {
		int index = data.indexOf(object);
		if (index != -1){
			remove(index);
		}
		notifyDataListener(new RemoveElementEvent(this, index, object));
	}

	@Override
	public void addDataListener(DataListener lo) {
		listeners.add(lo);
	}

	@Override
	public void removeDataListener(DataListener lo) {
		if (listeners.contains(lo)){
			listeners.remove(lo);
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
	public int getNumOfFields() {
		return classAgent.getNumOfFields();
	}

	@Override
	public AbstractList<String> getFieldNames() {
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
	public int indexOf(T object) {
		return data.indexOf(object);
	}

	@Override
	public Class<T> getDataClass() {
		return dataClass;
	}
	
	@Override
	public IDataModel<T> getActualModel() {
		return this;
	}
}
