package rapidFit.controller;

import java.awt.Toolkit;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rapidFit.controller.command.Command;
import rapidFit.controller.command.CompoundUndoableCommand;
import rapidFit.controller.command.EditFractionNameCommand;
import rapidFit.controller.command.EditTreeModelTagNameCommand;
import rapidFit.controller.command.ReplaceTreeNodeCommand;
import rapidFit.controller.command.UndoableCommand;
import rapidFit.data.PDFType;
import rapidFit.data.PhysicsParameterType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.model.dataModel.ClassModelAdapter;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.RemoveElementEvent;
import rapidFit.model.treeModel.ITreeModel;
import rapidFit.model.treeModel.PDFManager;
import rapidFit.model.treeModel.PDFNode;
import rapidFit.view.PDFBuilderFrame;

public class PDFBuilderController extends UIController implements 
CommandListener, ListPanelListener, TreePanelListener, DataListener {

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
	private HashMap<PDFType, IClassModel<PDFType>> pdfModelMap;
	
	private List<PhysicsParameterType> physicsParams;

	public PDFBuilderController(UIController mainController, PDFManager rootPDFManager,
			List<PhysicsParameterType> physicsParams) {
		this.physicsParams = physicsParams;
		this.mainController = mainController;

		commandHandler = new CommandHandler(this);

		//duplicate the PDF manager
		this.rootPDFManager = rootPDFManager;
		pdfManager = new PDFManager(rootPDFManager, true, false);
		pdfTreeModel = pdfManager.getTreeModel();
		pdfDataModel = pdfManager.getPDFs();
		pdfModelMap = new HashMap<PDFType, IClassModel<PDFType>>();

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
		//update buttons
		if (path != null && path.length > 0){
			if (pdfListController.getSelectedIndex() == -1){
				mainFrame.enableReplaceWithPDFButton(false);
			} else {
				mainFrame.enableReplaceWithPDFButton(true);
			}
			mainFrame.enableReplaceWithProdButton(true);
			mainFrame.enableReplaceWithSumButton(true);
			mainFrame.enableEditProdPDFButton(false);
			mainFrame.enableEditSumPDFButton(false);
			
			Object pdf = pdfTreeModel.getActualObject(path[path.length-1]);
			if (pdf instanceof ProdPDFType){
				mainFrame.enableEditProdPDFButton(true);
			} else if (pdf instanceof SumPDFType){
				mainFrame.enableEditSumPDFButton(true);
			}
			
		} else {
			mainFrame.enableReplaceWithPDFButton(false);
			mainFrame.enableReplaceWithProdButton(false);
			mainFrame.enableReplaceWithSumButton(false);
			mainFrame.enableEditProdPDFButton(false);
			mainFrame.enableEditSumPDFButton(false);
		}
		
		if (listenToPDFTree){
			int lastIndex = -1;
			if (path != null){
				lastIndex = path.length-1;
			}
			if (lastIndex != -1){
				Object node = path[lastIndex];
				Object object = pdfTreeModel.getActualObject(node);
				if (object instanceof SumPDFType){
					mainFrame.displaySumPDF(((SumPDFType) object).getFractionName(),
							pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 0)), 
							pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 1)));
				} else if (object instanceof ProdPDFType){
					mainFrame.displayProdPDF(
							pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 0)), 
							pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 1)));
				} else if (object instanceof PDFType){
					PDFType pdf = (PDFType) object;
					if (!pdfModelMap.containsKey(pdf)){
						pdfModelMap.put(pdf, new ClassModelAdapter<PDFType>(pdfDataModel,
								pdfDataModel.indexOf(pdf)));
					}
					mainFrame.displayPDF(pdfModelMap.get(pdf), pdfDataModel.getTagName(pdf));
				}
			} else {
				mainFrame.displayNoPDF();
			}
		}
	}

	@Override
	public void changedSelectedElement(int index) {
		if (index != -1 && pdfTreeController.getSelectedPath() != null &&
				pdfTreeController.getSelectedPath().length > 0){
			mainFrame.enableReplaceWithPDFButton(true);
		} else {
			mainFrame.enableReplaceWithPDFButton(false);
		}
		if (listenToPDFList){
			PDFType pdf = pdfDataModel.get(index);
			if (!pdfModelMap.containsKey(pdf)){
				pdfModelMap.put(pdf, new ClassModelAdapter<PDFType>(pdfDataModel,
						pdfDataModel.indexOf(pdf)));
			}
			mainFrame.displayPDF(pdfModelMap.get(pdf), pdfDataModel.getTagName(pdf));
		}
	}
	
	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == pdfDataModel &&
				e instanceof RemoveElementEvent){
			PDFType pdf = (PDFType) 
					((RemoveElementEvent) e).getRemovedElement();
			if (pdfModelMap.containsKey(pdf)){
				pdfModelMap.remove(pdf);
			}
		}
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
		mainController.setCommand(new ReplaceTreeNodeCommand(
				rootPDFManager.getTreeModel(), null, 0, 
				pdfTreeModel.getRoot()));
		commandHandler.getActiveController().deactivateController();
		mainFrame.dispose();
	}

	public void replaceWithPDF(){
		int selectedIndex = pdfListController.getSelectedIndex();
		Object [] selectedPath = pdfTreeController.getSelectedPath();

		if (selectedIndex != -1 && selectedPath != null && selectedPath.length > 0){
			int pathLastIndex = selectedPath.length - 1;
			PDFNode pdfNode = pdfManager.createPDFNode(
					pdfDataModel.get(selectedIndex));
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

	public void replaceWithPDFSum(String fractionName,
			PDFType leftPDF, PDFType rightPDF){
		SumPDFType sum = new SumPDFType();
		sum.getProdPDFOrNormalisedSumPDFOrPDF().add(leftPDF);
		sum.getProdPDFOrNormalisedSumPDFOrPDF().add(rightPDF);
		sum.setFractionName(fractionName);
		PDFNode node = pdfManager.createPDFNode(sum);
		setReplaceCommand(pdfTreeController.getSelectedPath(), node);
	}

	public void replaceWithPDFProduct(PDFType leftPDF, PDFType rightPDF){
		ProdPDFType product = new ProdPDFType();
		product.getProdPDFOrNormalisedSumPDFOrPDF().add(leftPDF);
		product.getProdPDFOrNormalisedSumPDFOrPDF().add(rightPDF);
		PDFNode node = pdfManager.createPDFNode(product);
		setReplaceCommand(pdfTreeController.getSelectedPath(), node);
	}
	
	private void setReplaceCommand(Object [] path, Object newNode){
		if (path == null || path.length == 0){
			setCommand(new ReplaceTreeNodeCommand(
					pdfTreeModel, null, 0, newNode));
		} else {
			int lastIndex = path.length-1;
			setCommand(new ReplaceTreeNodeCommand(pdfTreeModel,
					path[lastIndex-1], pdfTreeModel.getIndexOfChild(
							path[lastIndex-1], path[lastIndex]), newNode));
		}
	}
	
	public void editPDFProduct(Object leftPDF, Object rightPDF){
		if (leftPDF instanceof PDFType && rightPDF instanceof PDFType){
			replaceWithPDFProduct((PDFType) leftPDF, (PDFType) rightPDF);
		} else {
			int editNodeIndex;
			PDFNode node;
			if (leftPDF instanceof PDFType){
				editNodeIndex = 0;
				node = pdfManager.createPDFNode((PDFType) leftPDF);
			} else {
				editNodeIndex = 1;
				node = pdfManager.createPDFNode((PDFType) rightPDF);
			}
			Object [] path = pdfTreeController.getSelectedPath();
			Object parentNode = path[path.length-1];
			setCommand(new ReplaceTreeNodeCommand(pdfTreeModel,
					parentNode, editNodeIndex, node));
		}
	}

	public void editPDFSum(String fractionName, Object leftPDF, Object rightPDF){
		if (leftPDF instanceof PDFType && rightPDF instanceof PDFType){
			replaceWithPDFSum(fractionName, (PDFType) leftPDF, (PDFType) rightPDF);
			
		} else {
			Object [] path = pdfTreeController.getSelectedPath();
			Object parentNode = path[path.length-1];
			SumPDFType sumPDF = (SumPDFType) pdfTreeModel.getActualObject(parentNode);
			ArrayList<UndoableCommand> commands = new ArrayList<UndoableCommand>();
			
			if (leftPDF instanceof PDFType || rightPDF instanceof PDFType){
				int editNodeIndex;
				PDFNode node;
				if (leftPDF instanceof PDFType){
					editNodeIndex = 0;
					node = pdfManager.createPDFNode((PDFType) leftPDF);
				} else {
					editNodeIndex = 1;
					node = pdfManager.createPDFNode((PDFType) rightPDF);
				}
				commands.add(new ReplaceTreeNodeCommand(pdfTreeModel,
						parentNode, editNodeIndex, node));
			} 
			
			if (!sumPDF.getFractionName().equals(fractionName)){
				commands.add(new EditFractionNameCommand(sumPDF, fractionName));
				commands.add(new EditTreeModelTagNameCommand(pdfTreeModel, 
						parentNode, "Sum PDF (" + fractionName + ")"));
			}
			
			if (commands.size() > 0){
				setCommand(new CompoundUndoableCommand(commands));
			}
		}
	}

	public void editPDF(){

	}

	public void openPDFProductDialog(boolean buildNewProdPDF){
		Object [] path = pdfTreeController.getSelectedPath();
		if (path != null && path.length > 0){
			if (buildNewProdPDF){
				new PDFProdDialogController(this, pdfDataModel);
			} else if (!buildNewProdPDF && 
					pdfTreeModel.getActualObject(path[path.length-1]) 
					instanceof ProdPDFType){
				Object prodPDFNode = path[path.length-1];
				Object leftChildNode = pdfTreeModel.getChild(prodPDFNode, 0);
				Object rightChildNode = pdfTreeModel.getChild(prodPDFNode, 1);
				Object leftPDF = pdfTreeModel.getActualObject(leftChildNode);
				Object rightPDF = pdfTreeModel.getActualObject(rightChildNode);
				/*
				 * can't edit the PDF product if both operands are
				 * composite PDFs
				 */
				if (!(leftPDF instanceof PDFType) && 
						!(rightPDF instanceof PDFType)){
					Toolkit.getDefaultToolkit().beep();
				} else {
					new PDFProdDialogController(
							this, pdfDataModel, leftPDF, rightPDF, 
							pdfTreeModel.getTagName(leftChildNode), 
							pdfTreeModel.getTagName(rightChildNode));
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	public void openPDFSumDialog(boolean buildNewSumPDF){
		Object [] path = pdfTreeController.getSelectedPath();
		if (path != null && path.length > 0){
			if (buildNewSumPDF){
				new PDFSumDialogController(
						this, pdfDataModel, physicsParams, 
						null, null, null, null, null);
			} else if (!buildNewSumPDF &&
					pdfTreeModel.getActualObject(path[path.length-1]) 
					instanceof SumPDFType){
				Object sumPDFNode = path[path.length-1];
				Object leftChildNode = pdfTreeModel.getChild(sumPDFNode, 0);
				Object rightChildNode = pdfTreeModel.getChild(sumPDFNode, 1);
				new PDFSumDialogController(
						this, pdfDataModel, physicsParams,
						pdfTreeModel.getActualObject(leftChildNode), 
						pdfTreeModel.getActualObject(rightChildNode), 
						pdfTreeModel.getTagName(leftChildNode), 
						pdfTreeModel.getTagName(rightChildNode),
						((SumPDFType) pdfTreeModel.getActualObject(
								sumPDFNode)).getFractionName());
			}
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	public void openPDFEditor(){
		int selectedIndex = pdfListController.getSelectedIndex();
		if (selectedIndex != -1){
			new PDFEditorController(this, 
					pdfListController.get(selectedIndex), 
					pdfListController.getTagName(selectedIndex));
		}
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
