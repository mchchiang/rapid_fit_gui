package rapidFit.model.dataModel.event;

import rapidFit.model.dataModel.IDataModel;

public class AddElementEvent implements DataEvent {
	
	private IDataModel<?> model;
	private int index;
	private Object addedElement;
	
	public AddElementEvent(IDataModel<?> model, int index, Object addedElement){
		this.model = model;
		this.index = index;
		this.addedElement = addedElement;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	public Object getAddedElement(){
		return addedElement;
	}

}
