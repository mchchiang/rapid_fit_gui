package rapidFit.model.dataModel;

public class SetElementEvent implements DataEvent {
	
	private IDataModel<?> model;
	private int index;
	private Object oldElement;
	private Object newElement;
	
	public SetElementEvent (IDataModel<?> model, int index, 
			Object oldElement, Object newElement){
		this.model = model;
		this.index = index;
		this.oldElement = oldElement;
		this.newElement = newElement;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}
	
	public int getIndex(){
		return index;
	}
	
	public Object getOldElement(){
		return oldElement;
	}
	
	public Object getNewElement(){
		return newElement;
	}

}
