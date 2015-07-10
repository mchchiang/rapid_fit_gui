package rapidFit.controller;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JComponent;

import rapidFit.controller.command.CompoundUndoableCommand;
import rapidFit.controller.command.ListModelCopyCommand;
import rapidFit.controller.command.ListModelRemoveCommand;
import rapidFit.controller.command.SetTagNameCommand;
import rapidFit.controller.command.TagNameListModelAddCommand;
import rapidFit.controller.command.TagNameListModelRemoveCommand;
import rapidFit.model.IListModel.UpdateType;
import rapidFit.model.ITagNameListModel;
import rapidFit.model.IListObserver;
import rapidFit.view.bldblocks.DataList;
import rapidFit.view.bldblocks.DataListPanel;
import rapidFit.view.bldblocks.DataListViewModel;
import rapidFit.view.bldblocks.ListViewObserver;

public class ListPanelController<T> implements IListPanelController<T>, IListObserver {
	
	private ITagNameListModel<T> listModel;
	private MainController mainController;
	private DataListPanel<T> dataListPanel;
	private DataList<T> dataList;
	private DataListViewModel<T> viewModel;
	
	private ArrayList<ListViewObserver> viewObservers;
	private T selectedElement;
	
	public ListPanelController(MainController controller, ITagNameListModel<T> listModel){
		this.listModel = listModel;
		this.mainController = controller;
		
		this.listModel.addListObserver(this);
		
		viewObservers = new ArrayList<ListViewObserver>();
		
		//create the view
		viewModel = new DataListViewModel<T>(this);
		dataList = new DataList<T>(this, viewModel);
		dataListPanel = new DataListPanel<T>(this, dataList);
	}
	
	@Override 
	public void setModel(ITagNameListModel<T> newModel){
		if (listModel != null){
			listModel.removeListObserver(this);
		}
		listModel = newModel;
		viewModel.fireContentsChanged(0, getListSize());
	}
	
	@Override
	public void setSelectedIndex(int row){
		if (row >= 0 && row < getListSize()){
			selectedElement = listModel.get(row);
			if (dataList.getSelectedIndex() != row){
				dataList.setSelectedIndex(row);
			}
			notifyListViewObserver();
		}
	}
	
	@Override
	public int getListSize() {
		return listModel.size();
	}

	@Override
	public T get(int row) {
		return listModel.get(row);
	}

	@Override
	public String getTagName(int row) {
		return listModel.getTagName(row);
	}
	
	@Override
	public void addRow() {
		addRow(listModel.size());
	}
	
	@Override
	public void addRow(int row) {
		mainController.setCommand(new TagNameListModelAddCommand(listModel, row+1,
				"Added a new row"));
	}

	@Override
	public void removeRow(int row) {
		mainController.setCommand(new TagNameListModelRemoveCommand<T>(listModel, row, row));
		
	}

	@Override
	public void removeRows(int[] rows) {
		ArrayList<ListModelRemoveCommand<T>> commands = 
				new ArrayList<ListModelRemoveCommand<T>>();
		
		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);
		
		for (int i = 0; i < rows.length; i++){
			commands.add(new ListModelRemoveCommand<T>(listModel, rows[i], rows[i]-i));
		}		
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}
	
	@Override
	public void copyRow(int row) {
		mainController.setCommand(new ListModelCopyCommand<T>(listModel, row));
	}
	
	@Override
	public void copyRows(int [] rows){
		ArrayList<ListModelCopyCommand<T>> commands =
				new ArrayList<ListModelCopyCommand<T>>();
		
		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);
		
		for (int i = 0; i < rows.length; i++){
			commands.add(new ListModelCopyCommand<T>(listModel, rows[i]+i));
		}
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}

	@Override
	public void set(int row) {}

	@Override
	public void setTagName(int row, String tagName) {
		mainController.setCommand(new SetTagNameCommand(
				listModel, row, getTagName(row), tagName));
	}
	
	@Override
	public void update(int index, UpdateType t, String field) {
		if (t == UpdateType.ADD){
			viewModel.fireIntervalAdded(index, index);
			setSelectedIndex(index);
		} else if (t == UpdateType.REMOVE){
			selectedElement = null;
			viewModel.fireIntervalRemoved(index, index);
			dataList.clearSelection();
		} else if (t == UpdateType.EDIT){
			viewModel.fireContentsChanged(index, index);
			setSelectedIndex(index);
		}
		viewModel.fireContentsChanged(0, getListSize());
	}
	
	@Override
	public JComponent getViewComponent(){return dataListPanel;}
	
	
	@Override
	public void addListViewObserver(ListViewObserver lvo) {
		viewObservers.add(lvo);
	}

	@Override
	public void removeListViewObserver(ListViewObserver lvo) {
		if (viewObservers.contains(lvo)){
			viewObservers.remove(lvo);
		}
	}

	@Override
	public void notifyListViewObserver() {
		for (ListViewObserver lvo : viewObservers){
			lvo.changedSelectedElement(selectedElement);
		}
	}
	
}
