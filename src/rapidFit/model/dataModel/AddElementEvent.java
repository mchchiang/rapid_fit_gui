package rapidFit.model.dataModel;

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
	
	public int getIndex() {
		return index;
	}
	
	public Object getAddedElement(){
		return addedElement;
	}

}
