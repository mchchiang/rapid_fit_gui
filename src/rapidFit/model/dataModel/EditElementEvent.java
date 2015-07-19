package rapidFit.model.dataModel;

public class EditElementEvent implements DataEvent {
	
	private IDataModel<?> model;
	private int index;
	private String field;
	
	public EditElementEvent(IDataModel<?> model, int index, String field){
		this.model = model;
		this.index = index;
		this.field = field;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getField() {
		return field;
	}

}
