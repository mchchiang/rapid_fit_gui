package rapidFit.controller;

import java.util.List;
import java.util.Stack;

import javax.swing.JComponent;

import rapidFit.controller.command.Command;
import rapidFit.controller.command.UndoableCommand;
import rapidFit.data.PDFType;
import rapidFit.model.treeModel.PDFManager;
import rapidFit.view.PDFBuilderFrame;

public class PDFBuilder implements UIController, ListPanelListener, TreePanelListener {
	
	private Stack<UndoableCommand> commandHistory;
	private Stack<UndoableCommand> redoCommands;
	
	private UIController mainController;
	private IListPanelController<PDFType> pdfListController;
	private ITreePanelController pdfTreeController;
	private Controller activeController;
	
	private PDFBuilderFrame mainFrame;
	
	private boolean listenToPDFTree;
	private boolean listenToPDFList;
	
	public PDFBuilder(UIController mainController, PDFManager rootPDFManager) {
		this.mainController = mainController;
		pdfListController.addListPanelListener(this);
		pdfTreeController.addTreePanelListener(this);
		listenToPDFTree = true;
		listenToPDFList = false;
	}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JComponent getView() {
		return null;
	}

	@Override
	public void setCommand(Command cmd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setActiveController(Controller c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Controller getActiveController() {
		return activeController;
	}
	
	@Override
	public void activateController() {}
	

	@Override
	public void deactivateController() {}

	@Override
	public void changeSelectedPath(Object[] path) {
		if (listenToPDFTree){
			
		}
	}

	@Override
	public void changedSelectedElement(int index) {
		if (listenToPDFList){
			
		}
	}

}
