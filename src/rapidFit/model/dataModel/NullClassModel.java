package rapidFit.model.dataModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class NullClassModel extends IClassModel {
	
	@Override
	public void addDataListener(DataListener listener) {}

	@Override
	public void removeDataListener(DataListener listener) {}

	@Override
	public void notifyDataListener(DataEvent e) {}

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
	public int getNumOfFields() {return 0;}

	@Override
	public AbstractList<String> getFieldNames() {return new ArrayList<String>();}

	@Override
	public Class<?> getFieldClass(String fieldName) {return null;}

	@Override
	public Type getFieldType(String fieldName) {return null;}

	@Override
	public Object getObject() {return null;}
	
	@Override 
	public Class<?> getDataClass() {return null;}
	
	@Override
	public IDataModel<?> getActualModel() {return this;}
}
