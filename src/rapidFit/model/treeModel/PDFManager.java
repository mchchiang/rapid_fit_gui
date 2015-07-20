package rapidFit.model.treeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import rapidFit.Cloner;
import rapidFit.controller.exception.TagNameException;
import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.data.ToFitType;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.DataModel;
import rapidFit.model.dataModel.EditTagNameEvent;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.TagNameDataModel;

public class PDFManager implements DataListener {
	
	private Object pdfRoot;
	private ITagNameDataModel<PDFType> pdfDataModel;
	private PDFTreeModel pdfTree;
	private boolean hasDataModel;
	
	public PDFManager(ToFitType root, boolean needDataModel){
		pdfRoot = root;
		pdfTree = new PDFTreeModel(root);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();
		}
	}
	
	public PDFManager(PDFExpressionType root, boolean needDataModel){
		pdfRoot = root;
		pdfTree = new PDFTreeModel(root);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();	
		}
	}
	
	private void initPDFDataModel(){
		ArrayList<PDFType> pdfs = new ArrayList<PDFType>();
		pdfs.addAll(pdfTree.getPDFNodeMap().keySet());
		pdfDataModel = new TagNameDataModel<PDFType>(
				new DataModel<PDFType>(PDFType.class, pdfs, null),"");
		//set the correct (not duplicated) tag names for the pdfs
		for (PDFType pdf : pdfs){
			try {
				/*
				 * there should be a one to one correspondence between
				 * node and pdf when the tree is first initialised
				 */
				pdfDataModel.setTagName(pdf, pdfTree.getPDFNodeMap().
						get(pdf).get(0).getTagName());
			} catch (TagNameException e) {
				e.printStackTrace();
			}
		}
	}
	
	public PDFManager(PDFManager oldManager, boolean needDataModel){
		pdfRoot = Cloner.deepClone(oldManager.pdfRoot);
		if (pdfRoot instanceof ToFitType){
			pdfTree = new PDFTreeModel((ToFitType) pdfRoot);
		} else if (pdfRoot instanceof PDFExpressionType){
			pdfTree = new PDFTreeModel((PDFExpressionType) pdfRoot);
		}
		copyTagNamesFromOldModel(oldManager);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();	
		}
	}
	
	private void copyTagNamesFromOldModel(PDFManager oldManager){
		ITagNameDataModel<PDFType> oldPDFDataModel = oldManager.pdfDataModel;
		LinkedHashMap<PDFType, List<PDFNode>> pdfNodeMap = pdfTree.getPDFNodeMap();
		for (int i = 0; i < oldPDFDataModel.size(); i++){
			PDFType pdf = oldPDFDataModel.get(i);
			if (pdfNodeMap.containsKey(pdf)){
				for (PDFNode node : pdfNodeMap.get(pdf)){
					node.setTagName(oldPDFDataModel.getTagName(pdf));
				}
			}
		}
	}
	
	public PDFTreeModel getTreeModel(){
		return pdfTree;
	}
	
	public ITagNameDataModel<PDFType> getPDFs(){
		if (hasDataModel){
			return pdfDataModel;
		}
		return null;
	}

	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == pdfDataModel){
			if (e instanceof EditTagNameEvent){
				EditTagNameEvent evt = (EditTagNameEvent) e;
				/*
				 * only update the tag name in the PDF tree if 
				 * it contains that node 
				 */
				PDFType pdf = pdfDataModel.get(evt.getIndex());
				if (pdfTree.getPDFNodeMap().containsKey(pdf)){
					for (PDFNode node : pdfTree.getPDFNodeMap().get(pdf)){
						pdfTree.setTagName(node, evt.getNewTagName());
					}
				}
			}
		}
	}
	
	/*
	 * methods allowing controllers to create pdf nodes with 
	 * tag names that are used in the PDF list
	 */
	public PDFNode createPDFNode(PDFType pdf){
		PDFNode node = new PDFNode(null, pdf);
		return node;
	}
	
	public PDFNode createPDFNode(SumPDFType pdf){
		PDFNode node = new PDFNode(null, pdf);
		setTagName(node);
		return node;
	}
	
	public PDFNode createPDFNode(ProdPDFType pdf){
		PDFNode node = new PDFNode(null, pdf);
		setTagName(node);
		return node;
	}
	
	public void setTagName(PDFNode node){
		if (!node.isLeaf()){
			setTagName((PDFNode) node.getChild(0));
			setTagName((PDFNode) node.getChild(1));
		} else {
			node.setTagName(pdfDataModel.getTagName(
					(PDFType) node.getActualObject()));
		}
	}
}
