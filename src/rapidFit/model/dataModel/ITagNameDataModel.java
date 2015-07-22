package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;

import rapidFit.controller.exception.TagNameException;;

/**
 * 
 * A decorator abstract class to add tag name or ID
 * to the individual elements in the data model.
 * 
 * @author MichaelChiang
 *
 * @param <E>
 */
public abstract class ITagNameDataModel<E> implements IDataModel<E> {
	
	private IDataModel<E> model;
	
	public ITagNameDataModel (IDataModel<E> model){
		this.model = model;
	}
	
	//abstract methods to be implemented by sub-classes
	protected abstract void addEntry(E entry);
	protected abstract void addEntry(E entry, String tagName);
	protected abstract void removeEntry(E entry);
	public abstract HashMap<E, String> getNameMap();
	public abstract void setTagName(E entry, String tagName) throws TagNameException;
	public abstract String getTagName(E entry);
	
	protected void initTagNames(){
		//assign all existing elements in the model with a tag name 
		for (int i = 0; i < model.size(); i++){
			addEntry(model.get(i));
		}
	}
	
	public void setTagName(int index, String tagName) throws TagNameException {
		E entry = model.get(index);
		if (entry != null){
			setTagName(entry, tagName);
		}
	}
	
	public String getTagName(int index) {
		E entry = model.get(index);
		if (entry != null){
			return getTagName(entry);
		}
		return null;
	}
	
	public void add(int index, String tagName) 
			throws InstantiationException, IllegalAccessException{
		model.add(index);
		addEntry(model.get(index), tagName);
		model.notifyDataListener(
				new EditTagNameEvent(this, index, getTagName(index)));
	}
	
	public void add(int index, E object, String tagName){
		model.add(index, object);
		addEntry(object, tagName);
		model.notifyDataListener(
				new EditTagNameEvent(this, index, getTagName(index)));
	}
	
	@Override
	public void addDataListener(DataListener lo){
		model.addDataListener(lo);
	}
	
	@Override
	public void removeDataListener(DataListener lo){
		model.removeDataListener(lo);
	}
	
	@Override
	public void notifyDataListener(DataEvent e){
		model.notifyDataListener(e);
	}
	
	@Override
	public E get(int index){
		return model.get(index);
	}
	
	@Override
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return model.get(index, fieldName);
	}
	
	@Override
	public void set(int index, E object){
		if (getTagName(object) == null){
			addEntry(object);
		} 
		model.set(index, object);
	}
	
	@Override
	public void set(int index, String fieldName, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		model.set(index, fieldName, value);
	}
	
	@Override
	public int getNumOfFields(){
		return model.getNumOfFields();
	}
	
	@Override
	public AbstractList<String> getFieldNames(){
		return model.getFieldNames();
	}
	
	@Override
	public Class<?> getFieldClass(String fieldName){
		return model.getFieldClass(fieldName);
	}
	
	@Override
	public Type getFieldType(String fieldName){
		return model.getFieldType(fieldName);
	}
	
	@Override
	public int size(){
		return model.size();
	}
	
	@Override
	public void add(int index) throws InstantiationException, IllegalAccessException{
		model.add(index);
		addEntry(model.get(index));
		model.notifyDataListener(
				new EditTagNameEvent(this, index, getTagName(index)));
	}
	
	@Override
	public void add(E object) {
		model.add(object);
		addEntry(object);
		model.notifyDataListener(
				new EditTagNameEvent(this,
						model.indexOf(object), getTagName(object)));
	}
	
	@Override
	public void add(int index, E object){
		model.add(index, object);
		addEntry(object);
		model.notifyDataListener(
				new EditTagNameEvent(this, index, getTagName(object)));
	}

	@Override
	public void remove(E object) {
		int index = model.indexOf(object);
		if (index != -1){
			removeEntry(model.get(index));
		}
		model.remove(index);
	}
	
	@Override
	public void remove(int index){
		if (model.get(index) != null){
			removeEntry(model.get(index));
		}
		model.remove(index);
	}

	@Override
	public int indexOf(E object) {
		return model.indexOf(object);
	}
	
	@Override
	public Class<E> getDataClass() {
		return model.getDataClass();
	}
	
	@Override
	public IDataModel<E> getActualModel() {
		return model.getActualModel();
	}
}
