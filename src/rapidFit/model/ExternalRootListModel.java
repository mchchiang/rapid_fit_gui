package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

public class ExternalRootListModel<T> implements IListModel<T> {
	
	private IListModel<T> model;
	private IListModel<T> externalModel;
	
	public ExternalRootListModel (IListModel<T> model, IListModel<T> externalModel){
		this.model = model;
		this.externalModel = externalModel;
	}
	
	public void setList(List<T> data){
		model.setList(data);
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
		externalModel.add(index);
	}
	public void add(int index, T object){
		model.add(index, object);
		externalModel.add(index, object);
	}
	public void remove(int index){
		model.remove(index);
		externalModel.remove(index);
	}
}
