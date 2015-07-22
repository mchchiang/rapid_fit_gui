package rapidFit.controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import rapidFit.Cloner;
import rapidFit.controller.command.Command;
import rapidFit.controller.exception.TagNameException;
import rapidFit.data.PDFType;
import rapidFit.model.dataModel.ClassModel;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.ListModel;
import rapidFit.model.dataModel.TagNameDataModel;
import rapidFit.view.PDFEditorFrame;

public class PDFEditorController extends UIController implements CommandListener {
	
	private PDFType pdfCopy;
	private CommandHandler commandHandler;
	private PDFBuilderController mainController;
	private IAttributeTableController<PDFType> pdfDetailsTableController;
	private IDataTableController<String> configParamTableController;
	private IDataTableController<String> paramSubTableController;
	private ITagNamePanelController tagNamePanelController;
	private PDFEditorFrame mainFrame;
	
	public PDFEditorController(PDFBuilderController controller, PDFType pdf, String tagName){
		this.mainController = controller;
		commandHandler = new CommandHandler(this);
		
		pdfCopy = (PDFType) Cloner.deepClone(pdf);
		
		ArrayList<String> ignoreAttr = new ArrayList<String>();
		ignoreAttr.add("ConfigurationParameter");
		ignoreAttr.add("ParameterSubstitution");
		
		IClassModel<PDFType> pdfDetails = 
				new ClassModel<PDFType>(PDFType.class, pdfCopy, ignoreAttr);
		
		pdfDetailsTableController = new AttributeTableController<PDFType>(
				this, this, pdfDetails, "PDF Info");
		
		ITagNameDataModel<PDFType> tagNamePDFModel = 
				new TagNameDataModel<PDFType>(pdfDetails,"");
		try {
			tagNamePDFModel.setTagName(0, tagName);
		} catch (TagNameException e) {
			e.printStackTrace();
		}
		tagNamePanelController = new TagNamePanelController(
				this, null, this, tagNamePDFModel, 0);
		
		IDataModel<String> configParamModel = 
				new ListModel<String>(String.class, 
						pdf.getConfigurationParameter(), "Configuration Parameter");
		configParamTableController = new DataTableController<String>(
				this, this, configParamModel, 
				"Add Config Param", "Remove Config Param", "Duplicate Config Param");
		
		IDataModel<String> paramSubModel = 
				new ListModel<String>(String.class, 
						pdf.getParameterSubstitution(), "Parameter Substitution");
		paramSubTableController = new DataTableController<String>(
				this, this, paramSubModel, 
				"Add Param Sub", "Remove Param Sub", "Duplicate Param Sub");
		
		//create view
		mainFrame = new PDFEditorFrame(
				this, pdfDetailsTableController, 
				tagNamePanelController, 
				configParamTableController, 
				paramSubTableController);
		mainFrame.setVisible(true);
	}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		return null;
	}

	@Override
	public void setCommand(Command cmd) {
		commandHandler.setCommand(cmd);
	}

	@Override
	public void undo() {
		commandHandler.undo();
	}

	@Override
	public void redo() {
		commandHandler.redo();
	}

	@Override
	public void setActiveController(Controller controller) {
		commandHandler.setActiveController(controller);
	}

	@Override
	public Controller getActiveController() {
		return commandHandler.getActiveController();
	}

	@Override
	public Window getWindow() {
		return mainFrame;
	}

	@Override
	public void undoOccurred(Command cmd) {
		updateMenuBar();
	}

	@Override
	public void redoOccurred(Command cmd) {
		updateMenuBar();
	}

	@Override
	public void commandExecuted(Command cmd) {
		updateMenuBar();
	}
	
	private void updateMenuBar(){
		if (commandHandler.hasUndoableCommand()){
			mainFrame.enableUndoButton(true);
		} else {
			mainFrame.enableUndoButton(false);
		}
		if (commandHandler.hasRedoableCommand()){
			mainFrame.enableRedoButton(true);
		} else {
			mainFrame.enableRedoButton(false);
		}
	}
	
	public void quitPDFEditor(){
		
	}
}
