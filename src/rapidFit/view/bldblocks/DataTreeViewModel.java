package rapidFit.view.bldblocks;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import rapidFit.controller.ITreePanelController;

public class DataTreeViewModel implements TreeModel {
	
	private ITreePanelController controller;
	private EventListenerList listenerList = new EventListenerList();
	
	public DataTreeViewModel (ITreePanelController controller){
		this.controller = controller;
	}

	@Override
	public Object getRoot() {
		return controller.getRoot();
	}

	@Override
	public Object getChild(Object parent, int index) {
		return controller.getChild(parent, index);
	}

	@Override
	public int getChildCount(Object parent) {
		return controller.getChildCount(parent);
	}

	@Override
	public boolean isLeaf(Object node) {
		return controller.isLeaf(node);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return controller.getIndexOfChild(parent, child);
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
    public void fireTreeStructureChanged(Object source, Object[] path,
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
