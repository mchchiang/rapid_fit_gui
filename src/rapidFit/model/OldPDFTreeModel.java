package rapidFit.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.data.ToFitType;

public class OldPDFTreeModel implements ITreeModel {

	private ArrayList<TreeListener> listeners;
	private HashMap<Object, OldPDFNode> nodeMap;
	private Object pdfRoot;
	private Object displayRoot;

	
	public OldPDFTreeModel (ToFitType root) {
		this.pdfRoot = root;

		//discover all the pdfs
		if (root.getNormalisedSumPDF() != null){
			displayRoot = root.getNormalisedSumPDF();
		} else if (root.getProdPDF() != null){
			displayRoot = root.getProdPDF();
		} else if (root.getPDF() != null){
			displayRoot = root.getPDF();
		}

		getPDFs(null, displayRoot);
	}

	public OldPDFTreeModel (PDFExpressionType root) {
		this.pdfRoot = root;

		//discover all the pdfs
		if (root.getNormalisedSumPDF() != null){
			displayRoot = root.getNormalisedSumPDF();
		} else if (root.getProdPDF() != null){
			displayRoot = root.getProdPDF();
		} else if (root.getPDF() != null){
			displayRoot = root.getPDF();
		}

		getPDFs(null, displayRoot);
	}

	//a recursive method to find all the PDFs
	private void getPDFs(Object parent, Object object) {
		if (object instanceof SumPDFType){
			SumPDFType pdf = (SumPDFType) object;
			nodeMap.put(pdf, new OldPDFNode(pdf, parent, pdf.getProdPDFOrNormalisedSumPDFOrPDF()));
			getPDFs(pdf, pdf.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(pdf, pdf.getProdPDFOrNormalisedSumPDFOrPDF().get(1));

		} else if (object instanceof ProdPDFType){
			ProdPDFType pdf = (ProdPDFType) object;
			nodeMap.put(pdf, new OldPDFNode(pdf, parent, pdf.getProdPDFOrNormalisedSumPDFOrPDF()));
			getPDFs(pdf, pdf.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(pdf, pdf.getProdPDFOrNormalisedSumPDFOrPDF().get(1));

		} else if (object instanceof PDFType){
			PDFType pdf = (PDFType) object;
			nodeMap.put(pdf, new OldPDFNode(pdf, parent, null));
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
	public Object getRoot() {
		return displayRoot;
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (nodeMap.containsKey(parent)){
			return nodeMap.get(parent).getChild(index);
		}
		return null;
	}
	
	@Override
	public int getChildCount(Object parent) {
		return 0;
	}

	
	public Object getParent(Object node) {
		if (nodeMap.containsKey(node)) {
			return nodeMap.get(node).getParent();
		}
		return null;
	}

	
	public Object[] getParentPath(Object node) {
		if (nodeMap.containsKey(node) &&
			nodeMap.get(node).getParent() != null){
			return createPath(nodeMap.get(nodeMap.get(node).getParent()));
		}
		return null;
	}

	
	public Object[] getPath(Object node) {
		if (nodeMap.containsKey(node)){
			return createPath(nodeMap.get(node));
		}
		return null;
	}

	@Override
	public void set(Object[] path, Object newNode) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Object> getAllLeafNodes() {
		ArrayList<Object> leafNodes = new ArrayList<Object>();
		for (OldPDFNode node : nodeMap.values()){
			if (node.isLeaf()){
				leafNodes.add(node.getActualObject());
			}
		}
		return leafNodes;
	}
	
	private Object [] createPath(OldPDFNode node){
		ArrayList<Object> path = new ArrayList<Object>();
		buildPath(path, node);
		return path.toArray();
	}
	
	private void buildPath (ArrayList<Object> path, OldPDFNode node) {
		if (node.getParent() == null){
			path.add(node.getActualObject());
		} else {
			buildPath(path, nodeMap.get(node.getParent()));
			path.add(node.getActualObject());
		}
	}

}
