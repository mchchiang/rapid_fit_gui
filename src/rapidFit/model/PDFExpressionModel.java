package rapidFit.model;

import java.util.ArrayList;
import java.util.List;

import rapidFit.data.PDFExpressionType;
import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;

public class PDFExpressionModel implements ITreeModel {
	
	private ArrayList<TreeListener> listeners;
	private Object pdfRoot;
	private Object displayRoot;
	private DataModel<PDFType> pdfs;
	private TagNameDataModel<PDFType> pdfsWithTagNames;
	
	public PDFExpressionModel(PDFExpressionType root) {
		listeners = new ArrayList<TreeListener>();
		pdfRoot = root;
		
		if (root.getNormalisedSumPDF() != null) {
			displayRoot = root.getNormalisedSumPDF();
		} else if (root.getProdPDF() != null) {
			displayRoot = root.getProdPDF();
		} else if (root.getPDF() != null) {
			displayRoot = root.getPDF();
		}
		
		//find all the pdfs
		
	}
	
	private void getPDFs(){
		
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
		if (parent instanceof SumPDFType){
			return ((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().get(index);
		} else if (parent instanceof ProdPDFType){
			return ((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().get(index);
		}
		return null;
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent instanceof SumPDFType){
			return ((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().size();
		} else if (parent instanceof ProdPDFType){
			return ((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().size();
		}
		return 0;
	}

	@Override
	public void set(Object[] path, Object newNode) {
		
	}

	@Override
	public List<Object> getAllLeafNodes() {
		// TODO Auto-generated method stub
		return null;
	}
	
		

	private Object findNode(Object [] path){
		Object node = null;
		for (int i = 0; i < path.length; i++){
			node = path[i];
			int childCount = getChildCount(node);
			/*
			 * this assumes that any non-leaf nodes (i.e. SumPDF and
			 * ProdPDF) must be unique and there won't be duplicated instances 
			 */
			boolean foundChild = false;
			for (int j = 0; j < childCount; j++){
				if (path[i+1] == getChild(node, j)) {
					foundChild = true;
				}
			}	
			if (!foundChild) {
				return null;
			}
		}
		return node;
	}

}
