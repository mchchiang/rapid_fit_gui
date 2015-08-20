package rapidFit.model;

import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.IDataModel;

public class SwitchPhaseSpaceEvent implements DataEvent {
	
	private DataSetModel model;
	private int index;
	private boolean useCommonPhaseSpace;

	public SwitchPhaseSpaceEvent(DataSetModel model, int index, boolean useCommonPhaseSpace){
		this.model = model;
		this.index = index;
		this.useCommonPhaseSpace = useCommonPhaseSpace;
	}
	
	@Override
	public IDataModel<?> getDataModel() {
		return model.getActualModel();
	}

	@Override
	public int getIndex() {
		return index;
	}

	
	public boolean isCommonPhaseSpace() {
		return useCommonPhaseSpace;
	}
	
	public boolean isIndividualPhaseSpace() {
		return !useCommonPhaseSpace;
	}
}
