package rapidFit.controller.command;

import rapidFit.data.SumPDFType;

public class EditFractionNameCommand implements UndoableCommand {
	
	private SumPDFType pdf;
	private String oldFractionName;
	private String newFractionName;
	
	public EditFractionNameCommand(SumPDFType pdf, String fractionName){
		this.pdf = pdf;
		this.oldFractionName = pdf.getFractionName();
		this.newFractionName = fractionName;
	}
	
	@Override
	public boolean execute() {
		pdf.setFractionName(newFractionName);
		return true;
	}

	@Override
	public boolean undo() {
		pdf.setFractionName(oldFractionName);
		return true;
	}

}
