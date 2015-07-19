package rapidFit.model.treeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rapidFit.controller.exception.TagNameException;
import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.ToFitType;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.DataModel;
import rapidFit.model.dataModel.EditTagNameEvent;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.TagNameDataModel;

public class PDFManager implements DataListener {
	
	private ITagNameDataModel<PDFType> pdfDataModel;
	private PDFTreeModel pdfTree;
	private boolean hasDataModel;
	
	public PDFManager(ToFitType root, boolean needDataModel){
		pdfTree = new PDFTreeModel(root);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();
		}
	}
	
	public PDFManager(PDFExpressionType root, boolean needDataModel){
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
	
	public PDFManager(PDFManager oldManager,
			ToFitType copiedRoot, boolean needDataModel){
		pdfTree = new PDFTreeModel(copiedRoot);
		copyTagNamesFromOldModel(oldManager);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();	
		}
	}
	
	public PDFManager(PDFManager oldManager, 
			PDFExpressionType copiedRoot, boolean needDataModel){
		pdfTree = new PDFTreeModel(copiedRoot);
		copyTagNamesFromOldModel(oldManager);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();	
		}
	}
	
	private void copyTagNamesFromOldModel(PDFManager oldManager){
		ITagNameDataModel<PDFType> oldPDFDataModel = oldManager.pdfDataModel;
		HashMap<PDFType, List<PDFNode>> pdfNodeMap = pdfTree.getPDFNodeMap();
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
}
