package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.AbstractList;

import rapidFit.model.dataModel.event.DataEvent;

/**
 * 
 * An adapter for converting a particular element in a data model
 * (<code>IDataModel<T></code> into a class model)
 * 
 * @author MichaelChiang
 *
 * @param <T>
 */
public class ClassModelAdapter<T> extends IClassModel<T> {
	
	private IDataModel<T> model;
	private T element;
	
	public ClassModelAdapter(IDataModel<T> model, int index){
		this.model = model;
		this.element = model.get(index);
	}
	
	public ClassModelAdapter(IDataModel<T> model, T object){
		this.model = model;
		this.element = object;
	}

	@Override
	public void addDataListener(DataListener listener) {
		model.addDataListener(listener);
	}

	@Override
	public void removeDataListener(DataListener listener) {
		model.removeDataListener(listener);
	}

	@Override
	public void notifyDataListener(DataEvent e) {
		model.notifyDataListener(e);
	}

	@Override
	public int getNumOfFields() {
		return model.getNumOfFields();
	}

	@Override
	public AbstractList<String> getFieldNames() {
		return model.getFieldNames();
	}

	@Override
	public Class<?> getFieldClass(String field) {
		return model.getFieldClass(field);
	}

	@Override
	public Type getFieldType(String field) {
		return model.getFieldType(field);
	}

	@Override
	public T getObject() {
		return element;
	}

	@Override
	public void set(String field, Object value) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		model.set(model.indexOf(element), field, value);
	}

	@Override
	public Object get(String field) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		return model.get(model.indexOf(element), field);
	}
	
	@Override 
	public Class<T> getDataClass(){
		return model.getDataClass();
	}
	
	@Override
	public IDataModel<T> getActualModel() {
		return model.getActualModel();
	}
}
