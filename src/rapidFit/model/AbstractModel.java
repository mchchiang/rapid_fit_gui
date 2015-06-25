package rapidFit.model;

public interface AbstractModel {
	
	public void addObserver(Observer o);
	public void removeObserver(Observer o);
	public void notifyObserver();
	public void set(String field, Object value) throws Exception;
	public Object get(String field) throws Exception;
	
}
