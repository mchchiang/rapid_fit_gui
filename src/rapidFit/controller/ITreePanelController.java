package rapidFit.controller;

import rapidFit.model.treeModel.ITreeModel;

public interface ITreePanelController extends Controller {
	
	public void setModel(ITreeModel model);
	public ITreeModel getModel();
	
	public void addTreePanelListener(TreePanelListener listener);
	public void removeTreePanelListener(TreePanelListener listener);
	public void notifyTreePanelListener();
	
	public void setSelectedPath(Object [] path);
	public Object [] getSelectedPath();
	public void clearSelection();
	
	public Object getRoot();
	public String getDisplayName(Object node);
	public Object getChild(Object parent, int index);
	public int getChildCount(Object parent);
	public boolean isLeaf(Object node);
	public int getIndexOfChild(Object parent, Object child);
	
	public void replaceNode(Object parent, int index, Object newNode);
	public void insertNode(Object parent, Object newNode);
	public void removeNode(Object parent, Object removeNode);
	
}
