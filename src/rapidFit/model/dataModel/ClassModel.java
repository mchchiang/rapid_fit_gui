package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ClassModel<T> extends IClassModel<T>{
	
	private T data;
	private Class<T> dataClass;
	private ClassAgent classAgent;
	
	private ArrayList<DataListener> listeners;
	
	public ClassModel (Class<T> clazz, T data, ArrayList<String> ignoreAttributes){
		this.data = data;
		this.dataClass = clazz;
		classAgent = new ClassAgent(dataClass, ignoreAttributes);		
		listeners = new ArrayList<DataListener>();
	}
	
	
	@Override
	public void addDataListener(DataListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeDataListener(DataListener listener) {
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
	public Object get(String fieldName) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (classAgent.getGetter(fieldName) != null){
			return classAgent.getGetter(fieldName).invoke(data, (Object []) null);
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void set(String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		/*
		 * for handling fields whose data types are a List. This
		 * is needed as there is no setter method for these fields
		 */
		if (classAgent.getFieldClass(fieldName) == List.class) {
			boolean listEdited = false;
			List list = (List) get(fieldName);
			
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
				notifyDataListener(new EditElementEvent(this, 0, fieldName));
			}
			
		} else if (classAgent.getSetter(fieldName) != null){
			classAgent.getSetter(fieldName).invoke(
					data, classAgent.getFieldClass(fieldName).cast(value));
			notifyDataListener(new EditElementEvent(this, 0, fieldName));
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
	public Class<?> getFieldClass(String fieldName) {
		return classAgent.getFieldClass(fieldName);
	}

	@Override
	public Type getFieldType(String fieldName) {
		return classAgent.getFieldType(fieldName);
	}

	@Override
	public T getObject() {
		return data;
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
