package rapidFit.model;

public interface ITreeModel {
	
	public void addTreeListener(TreeListener listener);
	public void removeTreeListener(TreeListener listener);
	public void notifyTreeListener(TreeEvent e);
	
	public void set(Object [] path, Object newNode);
	
}
