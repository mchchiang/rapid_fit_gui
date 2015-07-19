package rapidFit.model.dataModel;

public class RemoveElementEvent implements DataEvent {
	
	private IDataModel<?> model;
	private int index;
	private Object removedElement;
	
	public RemoveElementEvent(IDataModel<?> model, int index, Object removedElement){
		this.model = model;
		this.index = index;
		this.removedElement = removedElement;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}
	
	public int getIndex() {
		return index;
	}
	
	public Object getRemovedElement(){
		return removedElement;
	}
}
