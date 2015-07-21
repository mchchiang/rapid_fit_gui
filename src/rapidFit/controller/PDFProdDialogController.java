package rapidFit.controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rapidFit.controller.command.Command;
import rapidFit.data.PDFType;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.view.PDFProdDialog;

public class PDFProdDialogController extends UIController {
	
	private PDFBuilderController mainController;
	private Object leftOperand;
	private Object rightOperand;
	private String leftOperandName;
	private String rightOperandName;
	private boolean canEditLeftOperand;
	private boolean canEditRightOperand;
	private PDFProdDialog dialog;
	private HashMap<PDFType, String> nameMap;
	private boolean editProductPDF;
	
	//for building a new product PDF
	public PDFProdDialogController(PDFBuilderController mainController,
			ITagNameDataModel<PDFType> model){
		this(mainController, model, null, null, null, null);
	}
	
	public PDFProdDialogController(PDFBuilderController mainController,
			ITagNameDataModel<PDFType> model, Object leftOperand,
			Object rightOperand, String leftOperandName,
			String rightOperandName){
		this.mainController = mainController;
		
		this.leftOperand = leftOperand;
		this.leftOperandName = leftOperandName;
		this.rightOperand = rightOperand;
		this.rightOperandName = rightOperandName;
		this.nameMap = model.getNameMap();
		
		if (leftOperand == null || leftOperand instanceof PDFType){
			canEditLeftOperand = true;
		}
		if (rightOperand == null || rightOperand instanceof PDFType){
			canEditRightOperand = true;
		}
		
		List<String> pdfNames = new ArrayList<String>();
		pdfNames.addAll(model.getNameMap().values());
		
		//create view
		dialog = new PDFProdDialog(this, pdfNames,
				leftOperandName, rightOperandName, 
				canEditLeftOperand, canEditRightOperand);
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
	
	public void quitPDFProdDialog(){
		if (canEditLeftOperand){
			leftOperand = findPDF(leftOperandName);
		}
		if (canEditRightOperand){
			rightOperand = findPDF(rightOperandName);
		}
		mainController.editPDFProduct(leftOperand, rightOperand);
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
