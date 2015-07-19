package rapidFit.model.treeModel;

public class SetTreeNodeEvent implements TreeEvent {
	
	private ITreeModel model;
	private Object oldNode;
	private Object newNode;
	private Object [] path;
	
	public SetTreeNodeEvent(ITreeModel model, Object [] path, Object oldNode, Object newNode){
		this.model = model;
		this.oldNode = oldNode;
		this.newNode = newNode;
	}
	
	@Override
	public ITreeModel getTreeModel() {
		return model;
	}
	
	public Object getNewNode() {
		return newNode;
	}
	
	public Object getOldNode() {
		return oldNode;
	}
	
	public Object [] getPath() {
		return path;
	}

}
