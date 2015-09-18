package rapidFit.model.dataModel.event;

import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.ITagNameDataModel;


public class EditTagNameEvent implements DataEvent {
	
	private ITagNameDataModel<?> model;
	private int index;
	private String newTagName;
	
	public EditTagNameEvent(ITagNameDataModel<?> model, int index, String newTagName){
		this.model = model;
		this.index = index;
		this.newTagName = newTagName;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}
	
	@Override
	public int getIndex() {
		return index;
	}
	
	public String getNewTagName() {
		return newTagName;
	}
}
