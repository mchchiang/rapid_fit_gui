package rapidFit.model;

import java.util.List;

public interface ITreeModel {
	
	public void addTreeListener(TreeListener listener);
	public void removeTreeListener(TreeListener listener);
	public void notifyTreeListener(TreeEvent e);
	
	public Object getRoot();
	public Object getChild(Object parent, int index);
	public int getChildCount(Object parent);
	public void set(Object [] path, Object newNode);
	//public void getPathToRoot(Object node);
	public List<Object> getAllLeafNodes();
	
	
}
