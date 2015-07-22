package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class NullDataModel implements IDataModel {
	
	@Override
	public void addDataListener(DataListener listener) {}

	@Override
	public void removeDataListener(DataListener listener) {}

	@Override
	public void notifyDataListener(DataEvent e) {}

	@Override
	public Object get(int index) {
		return null;
	}

	@Override
	public Object get(int index, String fieldName)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		return null;
	}

	@Override
	public void set(int index, Object object) {}

	@Override
	public void set(int index, String fieldName, Object value)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {}

	@Override
	public int getNumOfFields() {return 0;}

	@Override
	public AbstractList<String> getFieldNames() {return new ArrayList<String>();}

	@Override
	public Class<?> getFieldClass(String fieldName) {return null;}

	@Override
	public Type getFieldType(String fieldName) {return null;}

	@Override
	public int size() {	return 0;}

	@Override
	public void add(int index) {}
	
	@Override
	public void add(Object object) {}
	
	@Override
	public void add(int index, Object object) {}

	@Override
	public void remove(int index) {}

	@Override
	public void remove(Object object) {}

	@Override
	public int indexOf(Object object) {return 0;}
	
	@Override
	public Class<?> getDataClass() {return null;}
	
	@Override
	public IDataModel<?> getActualModel() {return this;}
}
