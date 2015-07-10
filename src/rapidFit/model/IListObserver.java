package rapidFit.model;

public interface IListObserver {
	
	public void update(int index, IListModel.UpdateType t, String field);
	
}
