package rapidFit.controller;

import java.util.List;

import javax.swing.JComponent;

import rapidFit.data.PDFType;
import rapidFit.data.PhysicsParameterType;
import rapidFit.data.SumPDFType;
import rapidFit.model.dataModel.IDataModel;
import rapidFit.view.PDFSumDialog;

public class PDFSumDialogController implements Controller {
	
	private IDataModel<PhysicsParameterType> physicsParams;	
	private UIController mainController;
	private PDFType leftOperand;
	private PDFType rightOperand;
	private String fractionName;
	private PDFSumDialog panel;
	
	public PDFSumDialogController(UIController mainController){
		this.mainController = mainController;
		
		//create view
		//panel = new PDFSumDialog();
	}
	
	public void setLeftOperandValue(PDFType pdf){
		
	}
	
	public void setRightOperandValue(PDFType pdf){
		
	}
	
	public void setFractionName(String name){
		
	}

	@Override
	public void activateController() {}

	@Override
	public void deactivateController() {}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		return null;
	}

	@Override
	public JComponent getView() {
		return null;
	}
	
	public void quitPDFSumDialog(){
		
	}

}
