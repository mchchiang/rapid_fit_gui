package rapidFit.controller;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

import rapidFit.controller.command.ReplaceTreeNodeCommand;
import rapidFit.model.treeModel.ITreeModel;
import rapidFit.model.treeModel.RenameTreeNodeEvent;
import rapidFit.model.treeModel.SetTreeNodeEvent;
import rapidFit.model.treeModel.TreeEvent;
import rapidFit.model.treeModel.TreeListener;
import rapidFit.view.bldblocks.DataTree;
import rapidFit.view.bldblocks.DataTreeViewModel;

public class TreePanelController implements ITreePanelController, TreeListener {

	private ITreeModel treeModel;
	private UIController mainController;
	private Controller parentController;

	private Object [] selectedPath;
	private ArrayList<TreePanelListener> listeners;

	private DataTreeViewModel dataTreeViewModel;
	private DataTree dataTree;
	private JPanel treePanel;

	public TreePanelController(UIController mainController, 
			Controller parentController, ITreeModel model){

		this.mainController = mainController;
		this.parentController = parentController;
		listeners = new ArrayList<TreePanelListener>();

		treeModel = model;
		treeModel.addTreeListener(this);

		//create view
		dataTreeViewModel = new DataTreeViewModel(this);
		dataTree = new DataTree(dataTreeViewModel, this);
		dataTree.addTreeSelectionListener(new TreeSelectionListener(){
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				setSelectedPath(e.getPath().getPath());
			}
		});
		treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		treePanel.add(dataTree, BorderLayout.CENTER);
	}

	@Override
	public void setModel(ITreeModel newModel){
		if (treeModel != null){
			treeModel.removeTreeListener(this);
		}
		treeModel = newModel;
		treeModel.addTreeListener(this);
		dataTreeViewModel.fireTreeStructureChanged(
				dataTreeViewModel, null, null, null);
		clearSelection();
	}

	@Override
	public ITreeModel getModel(){
		return treeModel;
	}

	@Override
	public void activateController() {
		dataTree.setFocusable(true);
	}

	@Override
	public void deactivateController() {
		dataTree.setFocusable(false);
	}

	@Override
	public Controller getParentController() {
		return parentController;
	}

	@Override
	public List<Controller> getChildControllers() {
		return null;
	}

	@Override
	public JComponent getView() {
		return treePanel;
	}

	@Override
	public Object getRoot() {
		return treeModel.getRoot();
	}

	@Override
	public String getDisplayName(Object node) {
		return treeModel.getTagName(node);
	}

	@Override
	public Object getChild(Object parent, int index) {
		return treeModel.getChild(parent, index);
	}

	@Override
	public int getChildCount(Object parent) {
		return treeModel.getChildCount(parent);
	}

	@Override
	public boolean isLeaf(Object node) {
		return treeModel.isLeaf(node);
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return treeModel.getIndexOfChild(parent, child);
	}

	@Override
	public void replaceNode(Object parent, int index, Object newNode) {
		mainController.setCommand(new ReplaceTreeNodeCommand(
				treeModel, newNode, index, newNode));
	}

	@Override
	public void insertNode(Object parent, Object newNode) {}

	@Override
	public void removeNode(Object parent, Object removeNode) {}

	@Override
	public void setSelectedPath(Object [] path) {
		
		//check if the new path is different from the old path
		boolean isNewPath = false;
		if (selectedPath != null && selectedPath.length == path.length){
			for (int i = 0; i < selectedPath.length; i++){
				if (selectedPath[i] != path[i]){
					isNewPath = true;
					break;
				}
			}
		} else {
			isNewPath = true;
		}

		if (isNewPath){
			selectedPath = path;
			if (selectedPath == null || selectedPath.length == 0){
				dataTree.clearSelection();
			} else {
				dataTree.setSelectionPath(new TreePath(path));
			}
			notifyTreePanelListener();
			mainController.setActiveController(this);
		}
	}

	@Override
	public Object [] getSelectedPath() {
		return selectedPath;
	}

	@Override
	public void clearSelection() {
		setSelectedPath(new Object [0]);
	}

	@Override
	public void addTreePanelListener(TreePanelListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeTreePanelListener(TreePanelListener listener) {
		if (listeners.contains(listener)){
			listeners.remove(listener);
		}
	}

	@Override
	public void notifyTreePanelListener() {
		for (TreePanelListener listener : listeners){
			listener.changeSelectedPath(selectedPath);
		}
	}

	@Override
	public void update(TreeEvent e) {
		if (e.getTreeModel() == treeModel){
			if (e instanceof SetTreeNodeEvent){
				SetTreeNodeEvent evt = (SetTreeNodeEvent) e;
				dataTreeViewModel.fireTreeStructureChanged(
						dataTreeViewModel, evt.getPath(), null, null);
				setSelectedPath(evt.getPath());
				dataTree.expandAllRows();
				mainController.setActiveController(this);
			} else if (e instanceof RenameTreeNodeEvent){
				setSelectedPath(e.getPath());
				dataTree.validate();
				mainController.setActiveController(this);
			}
		}
	}

}
