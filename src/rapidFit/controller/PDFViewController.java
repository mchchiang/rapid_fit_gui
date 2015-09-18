package rapidFit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.PhysicsParameterType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.data.ToFitType;
import rapidFit.model.PDFManager;
import rapidFit.model.dataModel.ClassModelAdapter;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.event.DataEvent;
import rapidFit.model.dataModel.event.RemoveElementEvent;
import rapidFit.model.treeModel.ITreeModel;
import rapidFit.view.PDFInspector;
import rapidFit.view.bldblocks.PDFViewPanel;

public class PDFViewController implements Controller, TreePanelListener, DataListener {

	private UIController mainController;
	private Controller parentController;
	private ITreePanelController pdfTreeController;

	private PDFManager pdfManager;
	private ITreeModel pdfTreeModel;
	private ITagNameDataModel<PDFType> pdfDataModel;
	private HashMap<PDFType, IClassModel<PDFType>> pdfModelMap;

	private PDFViewPanel panel;
	private PDFInspector inspector;

	private List<PhysicsParameterType> physicsParams;

	public PDFViewController(UIController mainController, 
			Controller parentController, PDFExpressionType root,
			List<PhysicsParameterType> physicsParams){
		this.mainController = mainController;
		this.parentController = parentController;
		this.physicsParams = physicsParams;
		this.pdfManager = new PDFManager(root, true, true);
		init();
	}

	public PDFViewController(UIController mainController,
			Controller parentController, ToFitType root,
			List<PhysicsParameterType> physicsParams){
		this.mainController = mainController;
		this.parentController = parentController;
		this.physicsParams = physicsParams;
		this.pdfManager = new PDFManager(root, true, true);
		init();
	}

	public void setPDFManager(PDFManager manager){
		pdfManager = manager;
		pdfTreeModel = pdfManager.getTreeModel();
		if (pdfDataModel != null){
			pdfDataModel.removeDataListener(this);
		}
		pdfDataModel = pdfManager.getPDFs();
		if (pdfDataModel != null){
			pdfDataModel.addDataListener(this);
		}
		pdfTreeController.setModel(pdfTreeModel);
		pdfModelMap = new HashMap<PDFType, IClassModel<PDFType>>();
	}

	private void init(){
		pdfTreeModel = pdfManager.getTreeModel();
		pdfDataModel = pdfManager.getPDFs();
		pdfDataModel.addDataListener(this);	
		pdfTreeController = new TreePanelController(
				mainController, parentController, pdfTreeModel);
		pdfTreeController.addTreePanelListener(this);
		pdfModelMap = new HashMap<PDFType, IClassModel<PDFType>>();
		
		//create view
		inspector = new PDFInspector();
		panel = new PDFViewPanel(this, pdfTreeController, inspector);
	}

	@Override
	public Controller getParentController() {
		return parentController;
	}

	@Override
	public List<Controller> getChildControllers() {
		ArrayList<Controller> childControllers = new ArrayList<Controller>();
		childControllers.add(pdfTreeController);
		return childControllers;
	}

	@Override
	public JComponent getView() {
		return panel;
	}

	@Override
	public void changeSelectedPath(Object[] path) {
		System.out.println("Set selected path");
		int lastIndex = -1;
		if (path != null){
			lastIndex = path.length-1;
		}
		if (lastIndex != -1){
			
			Object node = path[lastIndex];
			Object object = pdfTreeModel.getActualObject(node);
			if (object instanceof SumPDFType){
				inspector.displaySumPDF(((SumPDFType) object).getFractionName(),
						pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 0)), 
						pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 1)));
			} else if (object instanceof ProdPDFType){
				inspector.displayProdPDF(
						pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 0)), 
						pdfTreeModel.getTagName(pdfTreeModel.getChild(node, 1)));
			} else if (object instanceof PDFType){
				PDFType pdf = (PDFType) object;
				if (!pdfModelMap.containsKey(pdf)){
					pdfModelMap.put(pdf, new ClassModelAdapter<PDFType>(pdfDataModel,
							pdfDataModel.indexOf(pdf)));
				}
				inspector.displayPDF(pdfModelMap.get(pdf), pdfDataModel.getTagName(pdf));
			}
		} else {
			inspector.displayNoPDF();
		}
	}

	@Override
	public void activateController() {}

	@Override
	public void deactivateController() {}

	public void editPDF(){
		new PDFBuilderController(mainController, pdfManager, physicsParams);
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
}
