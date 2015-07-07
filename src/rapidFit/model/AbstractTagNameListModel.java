package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import rapidFit.TagNameException;

public abstract class AbstractTagNameListModel<T> implements AbstractListModel<T> {
	
	private AbstractListModel<T> model;
	protected String tagName;
	
	public AbstractTagNameListModel (AbstractListModel<T> model, String tag){
		this.model = model;
		this.tagName = tag;
		
		//assign all existing elements in the model with a tag name 
		for (int i = 0; i < model.size(); i++){
			addEntry(model.get(i));
		}
	}
	
	//abstract methods to be implemented by sub-classes
	protected abstract void addEntry(T entry);
	protected abstract void removeEntry(T entry);
	public abstract void setTagName(T entry, String tagName) throws TagNameException;
	public abstract String getTagName(T entry);
	public abstract HashMap<T, String> getNameMap();
	
	public void setTagName(int index, String tagName) throws TagNameException {
		T entry = model.get(index);
		if (entry != null){
			setTagName(entry, tagName);
		}
	}
	
	public String getTagName(int index) {
		T entry = model.get(index);
		if (entry != null){
			return getTagName(entry);
		}
		return null;
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
		addEntry(model.get(index));
	}
	public void add(int index, T object){
		model.add(index, object);
		addEntry(object);
	}
	public void remove(int index){
		if (model.get(index) != null){
			removeEntry(model.get(index));
		}
		model.remove(index);
	}
}
