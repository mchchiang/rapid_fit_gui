package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NullClassModel implements IClassModel {

	@Override
	public void setModelledData(Object data) {}
	
	@Override
	public void addObserver(IClassObserver co) {}

	@Override
	public void removeObserver(IClassObserver co) {}

	@Override
	public void notifyObserver() {}

	@Override
	public void setUpdateField(String field) {}

	@Override
	public void set(String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {}

	@Override
	public Object get(String fieldName) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		return null;
	}

	@Override
	public int getNumOfFields() {
		return 0;
	}

	@Override
	public List<String> getFieldNames() {
		return new ArrayList<String>();
	}

	@Override
	public Class<?> getFieldClass(String fieldName) {
		return null;
	}

	@Override
	public Type getFieldType(String fieldName) {
		return null;
	}

}
