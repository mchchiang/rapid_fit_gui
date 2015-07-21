package rapidFit.model.treeModel;

public class RenameTreeNodeEvent implements TreeEvent {
	
	private ITreeModel model;
	private Object [] path;
	private String newName;
	private String oldName;
	
	public RenameTreeNodeEvent(ITreeModel model, 
			Object [] path, String oldName, String newName){
		this.model = model;
		this.path = path;
		this.oldName = oldName;
		this.newName = newName;
	}
	
	@Override
	public ITreeModel getTreeModel() {
		return model;
	}

	@Override
	public Object[] getPath() {
		return path;
	}
	
	public String getOldName(){
		return oldName;
	}
	
	public String getNewName(){
		return newName;
	}

}
