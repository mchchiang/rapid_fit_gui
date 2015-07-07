package rapidFit.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class Field {
	private Method getter;
	private Method setter;
	private String name;
	private Type type;
	private Class<?> clazz;
	
	public Field (String name, Type type, Class<?> clazz, Method getter, Method setter){
		this.name = name;
		this.type = type;
		this.clazz = clazz;
		this.getter = getter;
		this.setter = setter;
	}
	
	//accessor methods
	public String getName() {return name;}
	public Type getType() {return type;}
	public Class<?> getFieldClass() {return clazz;}
	public Method getGetter() {return getter;}
	public Method getSetter() {return setter;}
	
}
