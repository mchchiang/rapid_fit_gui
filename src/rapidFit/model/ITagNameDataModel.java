package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import rapidFit.TagNameException;

/**
 * 
 * A decorator abstract class to add tag name or ID
 * to the individual elements in the data model.
 * 
 * @author MichaelChiang
 *
 * @param <T>
 */
public abstract class ITagNameDataModel<T> implements IDataModel<T> {
	
	private IDataModel<T> model;
	
	public ITagNameDataModel (IDataModel<T> model){
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
	
	public void add(int index, String tagName) 
			throws InstantiationException, IllegalAccessException{
		model.add(index);
		addEntry(model.get(index), tagName);
		model.notifyDataListener(
				new EditTagNameEvent(this, index, getTagName(index)));
	}
	
	public void add(int index, T object, String tagName){
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
	public T get(int index){
		return model.get(index);
	}
	
	@Override
	public Object get(int index, String fieldName) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		return model.get(index, fieldName);
	}
	
	@Override
	public void set(int index, T object){
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
	public List<String> getFieldNames(){
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
	public void add(T object) {
		model.add(object);
		addEntry(object);
		model.notifyDataListener(
				new EditTagNameEvent(this,
						model.indexOf(object), getTagName(object)));
	}
	
	@Override
	public void add(int index, T object){
		model.add(index, object);
		addEntry(object);
		model.notifyDataListener(
				new EditTagNameEvent(this, index, getTagName(object)));
	}

	@Override
	public void remove(T object) {
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
	public int indexOf(T object) {
		return model.indexOf(object);
	}
	
	@Override
	public Class<T> getDataClass() {
		return model.getDataClass();
	}
	
	@Override
	public IDataModel<T> getActualModel() {
		return model.getActualModel();
	}
}
