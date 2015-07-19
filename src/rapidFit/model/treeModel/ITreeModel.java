package rapidFit.model.treeModel;


public interface ITreeModel {
	
	public void addTreeListener(TreeListener listener);
	public void removeTreeListener(TreeListener listener);
	public void notifyTreeListener(TreeEvent e);
	
	public Object getRoot();
	public Object getChild(Object parent, int index);
	public Object getActualObject(Object node);
	public int getChildCount(Object parent);
	public boolean isLeaf(Object node);
	public int getIndexOfChild(Object parent, Object child);
	public void insertNode(Object parent, int index, Object newNode);
	public void removeNode(Object parent, int index);
	public void replaceNode(Object parent, int index, Object newNode);	
	
	public void setTagName(Object entry, String tagName);
	public String getTagName(Object entry);
}
