package rapidFit.controller;

import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import rapidFit.controller.command.Command;
import rapidFit.controller.command.ReplaceTreeNodeCommand;
import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.treeModel.ITreeModel;
import rapidFit.model.treeModel.PDFManager;
import rapidFit.model.treeModel.PDFNode;
import rapidFit.view.PDFBuilderFrame;

public class PDFBuilderController extends UIController implements 
CommandListener, ListPanelListener, TreePanelListener {
	
	private CommandHandler commandHandler;
	
	private UIController mainController;
	private IListPanelController<PDFType> pdfListController;
	private ITreePanelController pdfTreeController;
	
	private PDFBuilderFrame mainFrame;
	
	private boolean listenToPDFTree;
	private boolean listenToPDFList;
	
	private PDFManager rootPDFManager;
	private PDFManager pdfManager;
	private ITreeModel pdfTreeModel;
	private ITagNameDataModel<PDFType> pdfDataModel;
	
	public PDFBuilderController(UIController mainController, PDFManager rootPDFManager) {
		this.mainController = mainController;
		
		commandHandler = new CommandHandler(this);
		
		//duplicate the PDF manager
		this.rootPDFManager = rootPDFManager;
		pdfManager = new PDFManager(rootPDFManager, true);
		pdfTreeModel = pdfManager.getTreeModel();
		pdfDataModel = pdfManager.getPDFs();
		
		//create sub-controllers
		pdfTreeController = new TreePanelController(this, this, pdfTreeModel);
		pdfTreeController.addTreePanelListener(this);
		
		pdfListController = new ListPanelController<PDFType>(this, this, pdfDataModel);
		pdfListController.addListPanelListener(this);
		
		listenToPDFTree = true;
		listenToPDFList = false;
		
		//create view
		mainFrame = new PDFBuilderFrame(mainController, 
				this, pdfListController, pdfTreeController);
		mainFrame.setVisible(true);
	}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		ArrayList<Controller> childControllers = new ArrayList<Controller>();
		childControllers.add(pdfListController);
		childControllers.add(pdfTreeController);
		return childControllers;
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
		if (commandHandler.getActiveController() != controller){
			commandHandler.setActiveController(controller);
		}
	}

	@Override
	public Controller getActiveController() {
		return commandHandler.getActiveController();
	}

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
	
	private void updateMenuBar(){
		if (commandHandler.hasUndoableCommand()){
			mainFrame.getMenuBar().getUndoButton().setEnabled(true);
		} else {
			mainFrame.getMenuBar().getUndoButton().setEnabled(false);
		}
		if (commandHandler.hasRedoableCommand()){
			mainFrame.getMenuBar().getRedoButton().setEnabled(true);
		} else {
			mainFrame.getMenuBar().getRedoButton().setEnabled(false);
		}
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
	
	public void quitPDFBuilder(){
		mainFrame.dispose();
		mainController.setCommand(new ReplaceTreeNodeCommand(
				rootPDFManager.getTreeModel(), null, 0, 
				pdfTreeController.getSelectedPath()[0]));
	}
	
	public void replaceWithPDF(){
		int selectedIndex = pdfListController.getSelectedIndex();
		Object [] selectedPath = pdfTreeController.getSelectedPath();
		
		if (selectedIndex != -1 && selectedPath != null && selectedPath.length > 0){
			int pathLastIndex = selectedPath.length - 1;
			PDFNode pdfNode = pdfManager.createPDFNode(
					pdfDataModel.get(selectedIndex));
			pdfNode.setTagName(pdfDataModel.getTagName(selectedIndex));
			if (pathLastIndex == 0){
				setCommand(new ReplaceTreeNodeCommand(
						pdfTreeModel, null, 0, pdfNode));
			} else {
				setCommand(new ReplaceTreeNodeCommand(
						pdfTreeModel, selectedPath[pathLastIndex-1], 
						pdfTreeModel.getIndexOfChild(selectedPath[pathLastIndex-1],
								selectedPath[pathLastIndex]), pdfNode));
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
	
	public void replaceWithPDFSum(){
		
	}
	
	public void replaceWithPDFProduct(){
		
	}
	
	public void editPDF(){
		
	}
	
	public void openPDFProductDialog(boolean buildNewProdPDF){
		Object [] path = pdfTreeController.getSelectedPath();
		if (buildNewProdPDF){
			
		} else if (!buildNewProdPDF && 
				pdfTreeModel.getActualObject(path[path.length-1]) instanceof ProdPDFType){
			
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
	
	public void openPDFSumDialog(){
		
	}
	
	public void openPDFEditor(){
		
	}
	
	public void listenToTreeSelection(){
		listenToPDFTree = true;
		listenToPDFList = false;
	}
	
	public void listenToListSelection(){
		listenToPDFTree = false;
		listenToPDFList = true;
	}

	@Override
	public Window getWindow() {
		return mainFrame;
	}
}
