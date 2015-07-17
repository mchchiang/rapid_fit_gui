package rapidFit.controller;

public interface ITreeController extends Controller {
	
	public Object getRoot();
	public String getDisplayName(Object node);
	public Object getChild(Object parent, int index);
	public int getChildCount(Object parent);
	public boolean isLeaf(Object node);
	public int getIndexOfChild(Object parent, Object child);
	public void replace(Object [] path, Object newNode);
	
}
