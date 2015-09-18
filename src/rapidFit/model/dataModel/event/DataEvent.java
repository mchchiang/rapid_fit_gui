package rapidFit.model.dataModel.event;

import rapidFit.model.dataModel.IDataModel;

public interface DataEvent {
	
	public IDataModel<?> getDataModel();
	public int getIndex();
	
}
