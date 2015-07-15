package rapidFit.controller;

public interface ITreeController {
	
	public Object getRoot();
	public Object getChild(Object parent, int index);
	public int getChildCount(Object parent);
	public boolean isLeaf(Object node);
	public int getIndexOfChild(Object parent, Object child);
	
}
