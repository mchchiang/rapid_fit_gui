package rapidFit.view.bldblocks;

public interface ListViewObservable {
	public void addListViewObserver(ListViewObserver lvo);
	public void removeListViewObserver(ListViewObserver lvo);
	public void notifyListViewObserver();
}
