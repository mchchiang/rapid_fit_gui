package rapidFit.controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.Command;
import rapidFit.controller.command.NullCommand;
import rapidFit.data.PDFType;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.view.PDFProdDialog;

public class PDFProdDialogController extends UIController {
	
	private PDFBuilderController mainController;
	private String leftOperand;
	private String rightOperand;
	private boolean canEditLeftOperand;
	private boolean canEditRightOperand;
	private PDFProdDialog dialog;
	private HashMap<PDFType, String> nameMap;
	
	public PDFProdDialogController(PDFBuilderController mainController,
			ITagNameDataModel<PDFType> model, String leftOperand,
			String rightOperand){
		this.mainController = mainController;
		
		this.leftOperand = leftOperand;
		this.rightOperand = rightOperand;
		
		nameMap = model.getNameMap();
		canEditLeftOperand = nameMap.containsValue(leftOperand);
		canEditRightOperand = nameMap.containsValue(rightOperand);
		
		List<String> tagNames = new ArrayList<String>();
		tagNames.addAll(nameMap.values());
		
		//create view
		dialog = new PDFProdDialog(this, tagNames,
				leftOperand, rightOperand, canEditLeftOperand, canEditRightOperand);
	}
	
	public void setLeftOperand(String pdf){
		leftOperand = pdf;
	}
	
	public void setRightOperand(String pdf){
		rightOperand = pdf;
	}
	
	public void quitPDFProdDialog(){
		mainController.setCommand(new NullCommand());
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
