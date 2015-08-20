package rapidFit.model.treeModel;

import java.util.List;

public interface TreeNode {
	public void setParent(TreeNode node);
	public TreeNode getParent();
	public List<TreeNode> children();
	public boolean isLeaf();
	
	public TreeNode getChild(int index);
	public int getIndexOfChild(TreeNode node);
	
	public void setActualObject(Object object);
	public Object getActualObject();
	
	public void setChild(int index, TreeNode newNode);
	public void addChild(int index, TreeNode newNode);
	public void removeChild(int index);
	public void removeChild(TreeNode node);
	
}
