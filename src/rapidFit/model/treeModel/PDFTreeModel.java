package rapidFit.model.treeModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.data.ToFitType;

public class PDFTreeModel implements ITreeModel {

	private ArrayList<TreeListener> listeners;
	private PDFNode displayRoot;
	private Object root;
	private LinkedHashMap<PDFType, List<PDFNode>> pdfNodeMap;

	public PDFTreeModel(PDFExpressionType root){
		this.root = root;
		if (root.getNormalisedSumPDF() != null){
			displayRoot = new PDFNode(null, root.getNormalisedSumPDF());
		} else if (root.getProdPDF() != null){
			displayRoot = new PDFNode(null, root.getProdPDF());
		} else if (root.getPDF() != null){
			displayRoot = new PDFNode(null, root.getPDF());
		}

		if (displayRoot != null){
			listeners = new ArrayList<TreeListener>();
			pdfNodeMap = new LinkedHashMap<PDFType, List<PDFNode>>();
			initPDFTagName(new HashMap<String, Integer>(), displayRoot);
		}
	}

	public PDFTreeModel(ToFitType root){
		this.root = root;
		if (root.getNormalisedSumPDF() != null){
			displayRoot = new PDFNode(null, root.getNormalisedSumPDF());
		} else if (root.getProdPDF() != null){
			displayRoot = new PDFNode(null, root.getProdPDF());
		} else if (root.getPDF() != null){
			displayRoot = new PDFNode(null, root.getPDF());
		}

		if (displayRoot != null){
			listeners = new ArrayList<TreeListener>();
			pdfNodeMap = new LinkedHashMap<PDFType, List<PDFNode>>();
			initPDFTagName(new HashMap<String, Integer>(), displayRoot);
		}
	}

	/*
	 * a method to initialise the tag names for all pdfs. It ensures 
	 * that all pdfs (except sums and products) have unique tag names.
	 */
	private void initPDFTagName(HashMap<String, Integer> tagNameCounter, PDFNode node){
		Object pdf = node.getActualObject();
		if (pdf instanceof SumPDFType || pdf instanceof ProdPDFType){
			initPDFTagName(tagNameCounter, (PDFNode) node.getChild(0));
			initPDFTagName(tagNameCounter, (PDFNode) node.getChild(1));

		} else if (pdf instanceof PDFType){
			String name = node.getTagName();
			if (tagNameCounter.containsKey(name)){
				tagNameCounter.put(name, tagNameCounter.get(name)+1);
				node.setTagName(name + "_" + tagNameCounter.get(name));
			} else {
				tagNameCounter.put(name, 1);	
			}
			if (!pdfNodeMap.containsKey(pdf)){
				pdfNodeMap.put((PDFType) pdf, new ArrayList<PDFNode>());
				pdfNodeMap.get(pdf).add(node);
			}
		}
	}

	@Override
	public void addTreeListener(TreeListener listener) {
		listeners.add(listener);		
	}

	@Override
	public void removeTreeListener(TreeListener listener) {
		if (listeners.contains(listener)) {
			listeners.remove(listener);
		}
	}

	@Override
	public void notifyTreeListener(TreeEvent e) {
		List<TreeListener> listenersCopy = new ArrayList<TreeListener>();
		listenersCopy.addAll(listeners);
		for (TreeListener listener : listenersCopy) {
			listener.update(e);
		}
	}

	@Override
	public PDFNode getRoot() {
		return displayRoot;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((PDFNode) parent).getChild(index);
	}

	@Override
	public int getChildCount(Object parent) {
		if (((PDFNode) parent).isLeaf()) return 0;
		return 2;
	}

	@Override
	public boolean isLeaf(Object node){
		return ((PDFNode) node).isLeaf();
	}

	@Override
	public int getIndexOfChild(Object parent, Object child){
		return ((PDFNode) parent).getIndexOfChild((PDFNode) child);
	}

	@Override
	public void insertNode(Object parent, int index, Object newNode) {}

	@Override
	public void removeNode(Object parent, int index) {}

	@Override
	public void replaceNode(Object parent, int index, Object node) {
		PDFNode newNode = (PDFNode) node;

		if (parent == null){
			PDFNode oldNode = displayRoot;
			newNode.setParent(null);
			displayRoot = newNode;

			if (root instanceof PDFExpressionType){
				PDFExpressionType expType = (PDFExpressionType) root;
				Object pdf = newNode.getActualObject();
				expType.setProdPDF(null);
				expType.setNormalisedSumPDF(null);
				expType.setPDF(null);

				if (pdf instanceof ProdPDFType){
					expType.setProdPDF((ProdPDFType) pdf); 
				} else if (pdf instanceof SumPDFType){
					expType.setNormalisedSumPDF((SumPDFType) pdf);
				} else if (pdf instanceof PDFType){
					expType.setPDF((PDFType) pdf);
				}

			} else if (root instanceof ToFitType){
				ToFitType tofit = (ToFitType) root;
				Object pdf = newNode.getActualObject();
				tofit.setProdPDF(null);
				tofit.setNormalisedSumPDF(null);
				tofit.setPDF(null);

				if (pdf instanceof ProdPDFType){
					tofit.setProdPDF((ProdPDFType) pdf); 
				} else if (pdf instanceof SumPDFType){
					tofit.setNormalisedSumPDF((SumPDFType) pdf);
				} else if (pdf instanceof PDFType){
					tofit.setPDF((PDFType) pdf);
				}
			}
			updateLeafNodes(oldNode, newNode);
			notifyTreeListener(new SetTreeNodeEvent(
					this, new PDFNode [] {newNode}, oldNode, newNode));
		} else {
			PDFNode parentNode = (PDFNode) parent;
			PDFNode oldNode = (PDFNode) parentNode.getChild(index);
			oldNode.setParent(null);
			newNode.setParent(parentNode); 
			parentNode.setChild(index, newNode);
			updateLeafNodes(oldNode, newNode);
			notifyTreeListener(new SetTreeNodeEvent(
					this, getPathToRoot(newNode), oldNode, newNode));
		}
	}

	/*
	 * used for updating the tag names of the nodes
	 * when the tree structure has changed
	 */
	private void updateLeafNodes(PDFNode oldNode, PDFNode newNode){
		removeLeafNodes(oldNode);
		addLeafNodes(newNode);
	}

	/*
	 * start removing tag names by going from the root
	 */
	private void removeLeafNodes(PDFNode node){
		if (!node.isLeaf()){
			removeLeafNodes((PDFNode) node.getChild(0));
			removeLeafNodes((PDFNode) node.getChild(1));
		} else {
			PDFType pdf = (PDFType) node.getActualObject();
			pdfNodeMap.get(pdf).remove(node);
			if (pdfNodeMap.get(pdf).size() == 0){
				pdfNodeMap.remove(pdf);
			}
		}
	}

	private void addLeafNodes(PDFNode node){
		if (!node.isLeaf()){
			addLeafNodes((PDFNode) node.getChild(0));
			addLeafNodes((PDFNode) node.getChild(1));
		} else {
			PDFType pdf = (PDFType) node.getActualObject();
			if (!pdfNodeMap.containsKey(pdf)){
				pdfNodeMap.put(pdf, new ArrayList<PDFNode>());
			}
			pdfNodeMap.get(pdf).add(node);
		}
	}

	public PDFNode [] getPathToRoot(PDFNode node){
		ArrayList<PDFNode> path = new ArrayList<PDFNode>();
		buildPath(path, node);
		PDFNode [] returnpath = path.toArray(new PDFNode [path.size()]);
		return returnpath;
	}

	private void buildPath(ArrayList<PDFNode> path, PDFNode node){
		if (node != displayRoot){
			buildPath(path, (PDFNode) node.getParent());
			path.add(node);
		} else {
			path.add(node);
		}
	}

	@Override
	public void setTagName(Object entry, String tagName) {
		PDFNode node = (PDFNode) entry;
		String oldName = node.getTagName();
		node.setTagName(tagName);
		notifyTreeListener(new RenameTreeNodeEvent(
				this, getPathToRoot(node), oldName, tagName));
	}

	@Override
	public String getTagName(Object entry) {
		return ((PDFNode) entry).getTagName();
	}

	@Override
	public void setActualObject(Object node, Object object){
		if (object instanceof PDFType ||
				object instanceof ProdPDFType ||
				object instanceof SumPDFType){
			PDFNode pdfNode = (PDFNode) node;
			if (pdfNode.getParent() != null){
				Object parent = pdfNode.getParent().getActualObject();
				int childIndex = pdfNode.getParent().getIndexOfChild(pdfNode);
				if (parent instanceof ProdPDFType){
					((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().
					set(childIndex, (Serializable) object);
				} else if (parent instanceof SumPDFType){
					((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().
					set(childIndex, (Serializable) object);
				}
			}
			pdfNode.setActualObject(object);
			notifyTreeListener(new SetTreeNodeEvent(
					this, getPathToRoot(pdfNode), pdfNode, pdfNode));
		}
	}

	@Override
	public Object getActualObject(Object node){
		return ((PDFNode) node).getActualObject();
	}

	public LinkedHashMap<PDFType, List<PDFNode>> getPDFNodeMap() {
		return pdfNodeMap;
	}
}
