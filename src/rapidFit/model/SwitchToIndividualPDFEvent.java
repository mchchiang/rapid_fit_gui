package rapidFit.model;

import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.event.DataEvent;

public class SwitchToIndividualPDFEvent implements DataEvent {
	
	private DataSetModel model;
	private int index;
	private PDFManager manager;
	
	public SwitchToIndividualPDFEvent(DataSetModel model, int index, PDFManager manager){
		this.model = model;
		this.index = index;
		this.manager = manager;
	}

	@Override
	public IDataModel<?> getDataModel() {
		return model;
	}

	@Override
	public int getIndex() {
		return index;
	}
	
	public PDFManager getPDFManager(){
		return manager;
	}

}
