package rapidFit.model.dataModel;

public class SetElementEvent implements DataEvent {
	
	private IDataModel<?> model;
	private int index;
	
	public SetElementEvent (IDataModel<?> model, int index){
		this.model = model;
		this.index = index;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}
	
	public int getIndex(){
		return index;
	}

}
