package rapidFit.controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rapidFit.controller.command.Command;
import rapidFit.data.PDFType;
import rapidFit.data.PhysicsParameterType;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.view.PDFSumDialog;

public class PDFSumDialogController extends UIController {
	
	private PDFBuilderController mainController;
	private Object leftOperand;
	private Object rightOperand;
	private String leftOperandName;
	private String rightOperandName;
	private String fractionName;
	private boolean canEditLeftOperand;
	private boolean canEditRightOperand;
	private PDFSumDialog dialog;
	private HashMap<PDFType, String> nameMap;
	
	public PDFSumDialogController(PDFBuilderController mainController,
			ITagNameDataModel<PDFType> pdfModel,
			List<PhysicsParameterType> physicsParams, 
			Object leftOperand, Object rightOperand, 
			String leftOperandName, String rightOperandName,
			String fractionName){
		this.mainController = mainController;
		
		this.leftOperand = leftOperand;
		this.leftOperandName = leftOperandName;
		this.rightOperand = rightOperand;
		this.rightOperandName = rightOperandName;
		this.fractionName = fractionName;
		this.nameMap = pdfModel.getNameMap();
		
		if (leftOperand == null || leftOperand instanceof PDFType){
			canEditLeftOperand = true;
		}
		if (rightOperand == null || rightOperand instanceof PDFType){
			canEditRightOperand = true;
		}
		
		List<String> pdfNames = new ArrayList<String>();
		pdfNames.addAll(pdfModel.getNameMap().values());
		
		List<String> paramNames = new ArrayList<String>();
		for (PhysicsParameterType param : physicsParams){
			paramNames.add(param.getName());
		}
		
		//create view
		dialog = new PDFSumDialog(this, pdfNames, leftOperandName, rightOperandName, 
				canEditLeftOperand, canEditRightOperand, paramNames, fractionName);
		dialog.setVisible(true);
	}
	
	public void setLeftOperand(String pdf){
		if (canEditLeftOperand){
			leftOperandName = pdf;			
		}
	}
	
	public void setRightOperand(String pdf){
		if (canEditRightOperand){
			rightOperandName = pdf;
		}
	}
	
	public void setFractionName(String name){
		fractionName = name;
	}
	
	public void quitPDFSumDialog(){
		if (canEditLeftOperand){
			leftOperand = findPDF(leftOperandName);
		}
		if (canEditRightOperand){
			rightOperand = findPDF(rightOperandName);
		}
		mainController.editPDFSum(fractionName, leftOperand, rightOperand);
		dialog.dispose();
	}
	
	private PDFType findPDF(String pdfName){
		for (PDFType pdf : nameMap.keySet()){
			if (nameMap.get(pdf).equals(pdfName)){
				return pdf;
			}
		}
		return null;
	}
	
	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		return null;
	}

	/*
	 * no undo and redo allowed in this dialog
	 */
	@Override
	public void setCommand(Command cmd) {}
	
	@Override
	public void undo() {}

	@Override
	public void redo() {}

	@Override
	public void setActiveController(Controller c) {}

	@Override
	public Controller getActiveController() {
		return this;
	}
	
	@Override
	public Window getWindow() {
		return dialog;
	}
}
