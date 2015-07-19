package rapidFit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.data.ToFitType;
import rapidFit.model.dataModel.ClassModelAdapter;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.treeModel.ITreeModel;
import rapidFit.model.treeModel.PDFManager;
import rapidFit.view.PDFInspector;
import rapidFit.view.bldblocks.PDFViewPanel;

public class PDFViewController implements Controller, TreePanelListener {

	private UIController mainController;
	private Controller parentController;
	private ITreePanelController pdfTreeController;

	private PDFManager pdfManager;
	private ITreeModel pdfTreeModel;
	private ITagNameDataModel<PDFType> pdfDataModel;
	private HashMap<PDFType, IClassModel<PDFType>> pdfModelMap;

	private PDFViewPanel panel;
	private PDFInspector inspector;

	public PDFViewController(UIController mainController, 
			Controller parentController, PDFExpressionType root){
		this.mainController = mainController;
		this.parentController = parentController;
		this.pdfManager = new PDFManager(root, true);
		init();
	}

	public PDFViewController(UIController mainController,
			Controller parentController, ToFitType root){
		this.mainController = mainController;
		this.parentController = parentController;
		this.pdfManager = new PDFManager(root, true);
		init();
	}

	private void init(){
		//create sub-controllers
		pdfModelMap = new HashMap<PDFType, IClassModel<PDFType>>();
		pdfTreeModel = pdfManager.getTreeModel();
		pdfDataModel = pdfManager.getPDFs();
		pdfTreeController = new TreePanelController(
				mainController, this, pdfTreeModel);
		pdfTreeController.addTreePanelListener(this);

		//create view
		inspector = new PDFInspector();
		panel = new PDFViewPanel(pdfTreeController, inspector);
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
		int lastIndex = path.length-1;
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

}
