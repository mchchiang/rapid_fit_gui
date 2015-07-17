package rapidFit.model;

import java.util.List;

public class OldPDFNode {
	
	private Object parent;
	private List<? extends Object> children;
	private Object node;
	
	
	public OldPDFNode(Object itself, Object parent, List<? extends Object> children) {
		this.node = itself;
		this.parent = parent;
		this.children = children;
	}
	
	public Object getParent(){
		return parent;
	}
	
	public Object getChild(int index){
		if (children != null && index >= 0 && index < children.size()) {
			return children.get(index);
		}
		return null;
	}
	
	public int getChildCount(){
		if (children != null){
			return children.size();
		}
		return 0;
	}
	
	public boolean isLeaf(){
		if (children == null || children.size() == 0){
			return true;
		}
		return false;
	}
	
	public Object getActualObject(){
		return node;
	}
	
}
