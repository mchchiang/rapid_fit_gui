package rapidFit.view;

import java.io.Serializable;

import javax.swing.event.*;
import javax.swing.tree.*;

import rapidFit.model.*;

public class PDFTreeModel implements TreeModel {
	private EventListenerList listenerList = new EventListenerList();
	
	private PDFExpressionType pdfRoot;
	private Object displayRoot;
	
	public PDFTreeModel (PDFExpressionType root){
		pdfRoot = root;
		setDisplayRoot();
	}
	
	private void setDisplayRoot(){
		if (pdfRoot.getNormalisedSumPDF() != null){
			displayRoot = pdfRoot.getNormalisedSumPDF();
		} else if (pdfRoot.getProdPDF() != null){
			displayRoot = pdfRoot.getProdPDF();
		} else if (pdfRoot.getPDF() != null){
			displayRoot = pdfRoot.getPDF();
		}
	}
	
	public void updateEntireTree(){
		setDisplayRoot();
		fireTreeStructureChanged(this, new Object [] {displayRoot}, null, null);
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
	
	public void replace(TreePath path, Object newNode){
		Object oldNode = path.getLastPathComponent();
		
		//if it is the root element (node has no parent)
		if (path.getParentPath() == null){
			displayRoot = newNode;
			if (newNode instanceof SumPDFType){
				pdfRoot.setNormalisedSumPDF((SumPDFType) newNode);
				pdfRoot.setProdPDF(null);
				pdfRoot.setPDF(null);
			} else if (newNode instanceof ProdPDFType){
				pdfRoot.setNormalisedSumPDF(null);
				pdfRoot.setProdPDF((ProdPDFType) newNode);
				pdfRoot.setPDF(null);
			} else if (newNode instanceof PDFType){
				pdfRoot.setNormalisedSumPDF(null);
				pdfRoot.setProdPDF(null);
				pdfRoot.setPDF((PDFType) newNode);
			}
			
			fireTreeStructureChanged(this, path.getPath(), null, null);
			
		//if the node has a parent
		} else {
			Object parent = path.getParentPath().getLastPathComponent();
			int index = 0;
			if (parent instanceof SumPDFType){
				index = ((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().indexOf(oldNode);
				((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().set(index, (Serializable) newNode);
			} else if (parent instanceof ProdPDFType){
				index = ((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().indexOf(oldNode);
				((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().set(index, (Serializable) newNode);
			}
			fireTreeStructureChanged(this, path.getParentPath().getPath(), null, null);
		}		
	}
	
	@Override
	public boolean isLeaf(Object node) {
		if (node instanceof PDFType){
			return true;
		}
		return false;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		//not needed
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		if (parent instanceof PDFOperatorType){
			return ((PDFOperatorType) parent).
					getProdPDFOrNormalisedSumPDFOrPDF().indexOf(child);
		}
		return -1;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		listenerList.add(TreeModelListener.class, l);
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		listenerList.remove(TreeModelListener.class, l);	
	}
	
	/**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the source of the {@code TreeModelEvent};
     *               typically {@code this}
     * @param path the path to the parent of the structure that has changed;
     *             use {@code null} to identify the root has changed
     * @param childIndices the indices of the affected elements
     * @param children the affected elements
     */
    protected void fireTreeStructureChanged(Object source, Object[] path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
            }
        }
    }
}
