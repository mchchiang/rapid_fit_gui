package rapidFit.model;

import java.lang.reflect.Method;

public class Model<T> extends AbstractModel {
	private T type;
	
	private Method [] setters;
	private Method [] getters;
	
	public Model(T obj){
		type = obj;
		
		//find all getter and setter methods
		setters = type.getClass().getDeclaredMethods();
	}
	
	
	
}
