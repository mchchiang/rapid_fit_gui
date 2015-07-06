package rapidFit.model;

public interface ListObserver {
	
	public void update(int index, AbstractListModel.UpdateType t, String field);
	
}
