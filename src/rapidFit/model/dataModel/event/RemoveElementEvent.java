package rapidFit.model.dataModel.event;

import rapidFit.model.dataModel.IDataModel;

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
	
	@Override
	public int getIndex() {
		return index;
	}
	
	public Object getRemovedElement(){
		return removedElement;
	}
}
