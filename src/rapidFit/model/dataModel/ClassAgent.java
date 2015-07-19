package rapidFit.model.dataModel;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * A class to discover the fields with getter and setter methods in a class
 * 
 * @author MichaelChiang
 *
 */

public class ClassAgent {

	private Class<?> clazz;
	private HashMap<String, Field> fields;
	
	/**
	 * Create an agent for finding the fields and their respective setter
	 * and getter methods of a class
	 * 
	 * @param clazz The class of interest
	 * @param ignoreAttributes Attributes or fields to be ignored in the search
	 */
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
	/**
	 * 
	 * Return the total number of declared fields in the class (excluding the
	 * ones that are stated to be ignored in the constructor)
	 * 
	 */
	public int getNumOfFields() {
		return fields.size();
	}
	
	/**
	 * 
	 * Return the names of the declared fields (excluding the
	 * ones that are stated to be ignored in the constructor)
	 * 
	 */
	public List<String> getFieldNames(){
		ArrayList<String> fieldNames = new ArrayList<String>();
		fieldNames.addAll(fields.keySet());
		return fieldNames;
	}
	
	/**
	 * 
	 * Return the Java class type (<code>Class<?></code>) of the 
	 * specified declared field
	 * @param fieldName Name of the field
	 * 
	 */
	public Class<?> getFieldClass(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getFieldClass();
		}
		return null;
	}
	
	/**
	 * 
	 * Return the Java <code>Type</code> of the specified declared field
	 * @param fieldName Name of the field
	 * @see <code>Type</code>
	 * 
	 */
	public Type getFieldType(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getType();
		}
		return null;
	}
	
	/**
	 * 
	 * Return the getter method of the specified declared field
	 * @param fieldName Name of the field
	 *
	 */
	public Method getGetter(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getGetter();
		}
		return null;
	}
	
	
	/**
	 * 
	 * Return the setter method of the specified declared field
	 * @param fieldName Name of the field
	 * 
	 */
	public Method getSetter(String fieldName){
		if (fields.containsKey(fieldName)){
			return fields.get(fieldName).getSetter();
		}
		return null;
	}
	
	/**
	 * 
	 * Return the Java class type that the agent is analysing
	 * 
	 */
	public Class<?> getAgentClass(){
		return clazz;
	}
}
