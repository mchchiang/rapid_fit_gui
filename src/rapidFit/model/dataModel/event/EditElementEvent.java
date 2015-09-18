package rapidFit.model.dataModel.event;

import rapidFit.model.dataModel.IDataModel;

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
	
	@Override
	public int getIndex() {
		return index;
	}
	
	public String getField() {
		return field;
	}

}
