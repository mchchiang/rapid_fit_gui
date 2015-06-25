package rapidFit.model;

import java.util.ArrayList;

public class Model implements AbstractModel {
	
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private Object model;
	private Class<?> modelClass;
	
	public Model (Object model){
		this.model = model;
		this.modelClass = model.getClass();
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		if (observers.contains(o)){
			observers.remove(o);
		}
	}

	@Override
	public void notifyObserver() {
		for (Observer o : observers){
			o.update();
		}
	}
	
	public void set(String field, Object value) throws Exception {
		Class<?> type = value.getClass();
		modelClass.getDeclaredMethod("set" + field, type).invoke(model, value);
		notifyObserver();
	}
	
	public Object get(String field) throws Exception {
		return modelClass.getDeclaredMethod(
					"get" + field, (Class<?>) null).invoke(model, (Class<?>) null);
	}
	
	public Class<?> getFieldClass(String field) throws Exception {
		return null;
	}
}
