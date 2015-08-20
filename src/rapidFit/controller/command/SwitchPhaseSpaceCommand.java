package rapidFit.controller.command;

import rapidFit.model.DataSetModel;

public class SwitchPhaseSpaceCommand implements UndoableCommand {
	
	private DataSetModel model;
	private int index;
	private boolean useCommonPhaseSpace;
	
	public SwitchPhaseSpaceCommand(DataSetModel model, int index, boolean useCommonPhaseSpace){
		this.model = model;
		this.index = index;
		this.useCommonPhaseSpace = useCommonPhaseSpace;
	}

	@Override
	public boolean execute() {
		if (useCommonPhaseSpace){
			model.switchToCommonPhaseSpace(index);
		} else {
			model.switchToIndividualPhaseSpace(index);
		}
		return true;
	}

	@Override
	public boolean undo() {
		if (useCommonPhaseSpace){
			model.switchToIndividualPhaseSpace(index);
		} else {
			model.switchToCommonPhaseSpace(index);
		}
		return true;
	}

}
