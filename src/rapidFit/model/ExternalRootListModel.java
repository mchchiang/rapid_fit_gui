package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

public class ExternalRootListModel<T> implements AbstractListModel<T> {
	
	private AbstractListModel<T> model;
	
	public ExternalRootListModel (AbstractListModel<T> model){
		this.model = model;
	}
	
	public void addObserver(ListObserver lo){
		model.addObserver(lo);
	}
	public void removeObserver(ListObserver lo){
		model.removeObserver(lo);
	}
	public void notifyObserver(){
		model.notifyObserver();
	}
	public void setUpdateType(UpdateType t){
		model.setUpdateType(t);
	}
	public void setUpdateField(String field){
		model.setUpdateField(field);
	}
	public void setUpdateIndex(int index){
		model.setUpdateIndex(index);
	}
	
	public T get(int index){
		return model.get(index);
	}
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return model.get(index, fieldName);
	}
	
	public void set(int index, T object){
		model.set(index, object);
	}
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		model.set(index, fieldName, value);
	}
	
	public int getNumOfFields(){
		return model.getNumOfFields();
	}
	public List<String> getFieldNames(){
		return model.getFieldNames();
	}
	public Class<?> getFieldClass(String fieldName){
		return model.getFieldClass(fieldName);
	}
	public Type getFieldType(String fieldName){
		return model.getFieldType(fieldName);
	}
	
	public int size(){
		return model.size();
	}
	public void add(int index) throws InstantiationException, IllegalAccessException{
		model.add(index);
	}
	public void add(int index, T object){
		model.add(index, object);
	}
	public void remove(int index){
		model.remove(index);
	}
}
