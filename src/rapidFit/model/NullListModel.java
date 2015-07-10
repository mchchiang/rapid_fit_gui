package rapidFit.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;

@SuppressWarnings("rawtypes")
public class NullListModel implements IListModel {
	
	@Override
	public void setList(List data) {}
	
	@Override
	public void addListObserver(IListObserver lo) {}

	@Override
	public void removeListObserver(IListObserver lo) {}

	@Override
	public void notifyListObserver() {}

	@Override
	public void setUpdateType(UpdateType t) {}

	@Override
	public void setUpdateField(String field) {}

	@Override
	public void setUpdateIndex(int index) {}

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
	public int getNumOfFields() {
		return 0;
	}

	@Override
	public List<?> getFieldNames() {
		return null;
	}

	@Override
	public Class<?> getFieldClass(String fieldName) {
		return null;
	}

	@Override
	public Type getFieldType(String fieldName) {
		return null;
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public void add(int index) throws InstantiationException,
			IllegalAccessException {}

	@Override
	public void add(int index, Object object) {}

	@Override
	public void remove(int index) {}

}
