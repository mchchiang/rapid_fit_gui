package rapidFit.model;

public interface ListObservable {
	
	public void addListListener(ListListener listener);
	public void removeListListener(ListListener listener);
	public void notifyListListener();
	
}
