package rapidFit.model.treeModel;

import java.util.List;

public interface TreeNode {
	public TreeNode getParent();
	public List<TreeNode> children();
	public boolean isLeaf();
	
	public TreeNode getChild(int index);
	public int getIndexOfChild(TreeNode node);
	
	public Object getActualObject();
	
	public void setChild(int index, TreeNode newNode);
	public void addChild(int index, TreeNode newNode);
	public void removeChild(int index);
	public void removeChild(TreeNode node);
	
}
