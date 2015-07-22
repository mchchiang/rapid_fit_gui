package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.List;

public class ExternalRootDataModel<T> implements IDataModel<T> {
	
	private IDataModel<T> model;
	private IDataModel<T> externalModel;
	
	public ExternalRootDataModel (IDataModel<T> model, IDataModel<T> externalModel){
		this.model = model;
		this.externalModel = externalModel;
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
		externalModel.add(index);
	}
	
	@Override
	public void add(T object) {
		model.add(object);
		externalModel.add(object);
	}
	
	@Override
	public void add(int index, T object){
		model.add(index, object);
		externalModel.add(index, object);
	}
	
	@Override
	public void remove(int index){
		externalModel.remove(model.get(index));
		model.remove(index);
	}

	@Override
	public void remove(T object) {
		model.remove(object);
		externalModel.remove(object);
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
