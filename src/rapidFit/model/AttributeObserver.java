package rapidFit.model;

public interface AttributeObserver {
	public void update(String field, Object oldValue, Object newValue);
}
