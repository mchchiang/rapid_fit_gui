package rapidFit.model;

public class SetTreeNodeEvent implements TreeEvent {
	
	private ITreeModel model;
	private Object oldNode;
	private Object newNode;
	
	public SetTreeNodeEvent(ITreeModel model, Object oldNode, Object newNode){
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

}
