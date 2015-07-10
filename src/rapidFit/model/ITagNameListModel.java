package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import rapidFit.TagNameException;

public abstract class ITagNameListModel<T> implements IListModel<T> {
	
	private IListModel<T> model;
	
	public ITagNameListModel (IListModel<T> model){
		this.model = model;
	}
	
	//abstract methods to be implemented by sub-classes
	protected abstract void addEntry(T entry);
	protected abstract void addEntry(T entry, String tagName);
	protected abstract void removeEntry(T entry);
	public abstract void setTagName(T entry, String tagName) throws TagNameException;
	public abstract String getTagName(T entry);
	public abstract HashMap<T, String> getNameMap();
	
	protected void initTagNames(){
		//assign all existing elements in the model with a tag name 
		for (int i = 0; i < model.size(); i++){
			addEntry(model.get(i));
		}
	}
	
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
	
	public void add(int index, String tagName) throws InstantiationException, IllegalAccessException{
		model.add(index);
		addEntry(model.get(index), tagName);
	}
	
	public void add(int index, T object, String tagName){
		model.add(index, object);
		addEntry(object, tagName);
	}
	
	public void setList(List<T> data){
		//remove all tag names from elements of existing model
		for (int i = 0; i < model.size(); i++){
			removeEntry(model.get(i));
		}
		model.setList(data);
		initTagNames();
	}
	
	public void addListObserver(IListObserver lo){
		model.addListObserver(lo);
	}
	public void removeListObserver(IListObserver lo){
		model.removeListObserver(lo);
	}
	public void notifyListObserver(){
		model.notifyListObserver();
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
		if (getTagName(object) == null){
			addEntry(object);
		} 
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
