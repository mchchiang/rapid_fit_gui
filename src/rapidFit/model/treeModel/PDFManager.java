package rapidFit.model.treeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

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

public class PDFManager implements DataListener, TreeListener {
	
	private Object pdfRoot;
	private ITagNameDataModel<PDFType> pdfDataModel;
	private PDFTreeModel pdfTree;
	private boolean hasDataModel;
	
	public PDFManager(ToFitType root, boolean needDataModel, boolean syncModels){
		pdfRoot = root;
		pdfTree = new PDFTreeModel(root);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();
			if (syncModels){
				pdfTree.addTreeListener(this);
			}
		}
	}
	
	public PDFManager(PDFExpressionType root, boolean needDataModel, boolean syncModels){
		pdfRoot = root;
		pdfTree = new PDFTreeModel(root);
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();
			if (syncModels){
				pdfTree.addTreeListener(this);
			}
		}
	}
	
	private void initPDFDataModel(){
		if (pdfDataModel != null){
			pdfDataModel.removeDataListener(this);
		}
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
		pdfDataModel.addDataListener(this);
	}
	
	public PDFManager(PDFManager oldManager, boolean needDataModel, boolean syncModels){
		pdfRoot = Cloner.deepClone(oldManager.pdfRoot);
		if (pdfRoot instanceof ToFitType){
			pdfTree = new PDFTreeModel((ToFitType) pdfRoot);
		} else if (pdfRoot instanceof PDFExpressionType){
			pdfTree = new PDFTreeModel((PDFExpressionType) pdfRoot);
		}
		//copy tag names of the leaf nodes from the old manager
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
		hasDataModel = needDataModel;
		if (needDataModel){
			initPDFDataModel();
			if (syncModels){
				pdfTree.addTreeListener(this);
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
	
	@Override
	public void update(TreeEvent e) {
		if (e.getTreeModel() == pdfTree){
			if (e instanceof SetTreeNodeEvent){
				pdfDataModel.removeDataListener(this);
				int i = 0;
				/*
				 * update the PDF data model such that it has the
				 * same set of PDFs (leaf nodes) as the tree model
				 */
				Set<PDFType> pdfs = pdfTree.getPDFNodeMap().keySet();
				//remove any PDF in the data model that is not in the tree model
				while (i < pdfDataModel.size()){
					if (!pdfs.contains(pdfDataModel.get(i))){
						pdfDataModel.remove(i);
						i--;
					}
					i++;
				}
				//add any PDF in the tree model that is not in the data model
				for (PDFType pdf : pdfs){
					if (pdfDataModel.indexOf(pdf) == -1){
						pdfDataModel.add(pdf);
						try {
							pdfDataModel.setTagName(pdf, 
									pdfTree.getPDFNodeMap().get(pdf).
									get(0).getTagName());
						} catch (TagNameException ex) {
							ex.printStackTrace();
						}
					}
				}
				pdfDataModel.addDataListener(this);
			}
		}
	}
	
	/*
	 * methods allowing controllers to create pdf nodes with 
	 * tag names that are used in the PDF list
	 */
	public PDFNode createPDFNode(PDFType pdf){
		PDFNode node = new PDFNode(null, pdf);
		setTagName(node);
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
	
	private void setTagName(PDFNode node){
		if (!node.isLeaf()){
			setTagName((PDFNode) node.getChild(0));
			setTagName((PDFNode) node.getChild(1));
		} else {
			node.setTagName(pdfDataModel.getTagName(
					(PDFType) node.getActualObject()));
		}
	}
}
