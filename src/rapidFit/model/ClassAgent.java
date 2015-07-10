package rapidFit.model;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//a class to discover the fields with getter and setter methods in a class

public class ClassAgent {

	private Class<?> clazz;
	private HashMap<String, Field> fields;

	public ClassAgent (Class<?> clazz, ArrayList<String> ignoreAttributes){
		this.clazz = clazz;
		fields = new HashMap<String, Field>();

		//get setter and getter methods
		Method [] methods = clazz.getDeclaredMethods();

		for (Method m: methods){
			try {
				if (m.getName().startsWith("get") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(3)))){

					String fieldName = m.getName().substring(3);

					if (m.getReturnType() == List.class){
						fields.put(fieldName, new Field 
								(fieldName, m.getGenericReturnType(), 
										m.getReturnType(), m, null));
					} else {
						fields.put(fieldName, new Field 
								(fieldName, m.getGenericReturnType(), 
										m.getReturnType(), m, 
										clazz.getMethod(
												"set" + fieldName, m.getReturnType())));
					}

					//for boolean data types (jaxb by default makes the method name is<AttributeName>)
				} else if (m.getName().startsWith("is") && !(ignoreAttributes != null &&
						ignoreAttributes.contains(m.getName().substring(2)))){
					String fieldName = m.getName().substring(2);

					fields.put(fieldName, new Field 
							(fieldName, m.getGenericReturnType(), 
									m.getReturnType(), m, 
									clazz.getMethod(
											"set" + fieldName, m.getReturnType())));
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//accessor methods
	public int getNumOfFields() {
		return fields.size();
	}
	
	public List<String> getFieldNames(){
		ArrayList<String> fieldNames = new ArrayList<String>();
		fieldNames.addAll(fields.keySet());
		return fieldNames;
	}
	
	public Class<?> getFieldClass(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getFieldClass();
		}
		return null;
	}
	
	public Type getFieldType(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getType();
		}
		return null;
	}
	
	public Method getGetter(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getGetter();
		}
		return null;
	}
	
	public Method getSetter(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getSetter();
		}
		return null;
	}
	
	public Class<?> getAgentClass(){
		return clazz;
	}
}
