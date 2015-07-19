package rapidFit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.CompoundUndoableCommand;
import rapidFit.controller.command.SetTagNameCommand;
import rapidFit.controller.command.TagNameDataModelAddCommand;
import rapidFit.controller.command.TagNameDataModelCopyCommand;
import rapidFit.controller.command.TagNameDataModelRemoveCommand;
import rapidFit.model.dataModel.AddElementEvent;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.EditElementEvent;
import rapidFit.model.dataModel.EditTagNameEvent;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.RemoveElementEvent;
import rapidFit.view.bldblocks.DataList;
import rapidFit.view.bldblocks.DataListPanel;
import rapidFit.view.bldblocks.DataListViewModel;

public class ListPanelController<T> implements IListPanelController<T> {

	private ITagNameDataModel<T> dataModel;
	private Controller parentController;
	private UIController mainController;
	private DataListPanel<T> dataListPanel;
	private DataList<T> dataList;
	private DataListViewModel<T> viewModel;

	private ArrayList<ListPanelListener> listListeners;
	private int selectedIndex;

	public ListPanelController(UIController controller, 
			Controller parentController, ITagNameDataModel<T> dataModel){

		this.dataModel = dataModel;
		this.mainController = controller;
		this.parentController = parentController;

		this.dataModel.addDataListener(this);

		listListeners = new ArrayList<ListPanelListener>();

		//create the view
		selectedIndex = -1;
		viewModel = new DataListViewModel<T>(this);
		dataList = new DataList<T>(this, viewModel);
		dataListPanel = new DataListPanel<T>(this, dataList);
	}

	@Override 
	public void setModel(ITagNameDataModel<T> newModel) {
		if (dataModel != null){
			dataModel.removeDataListener(this);
		}
		dataModel = newModel;
		dataModel.addDataListener(this);
		viewModel.fireContentsChanged(0, getListSize());
		clearSelection();
	}

	@Override
	public ITagNameDataModel<T> getModel() {return dataModel;}

	@Override
	public void setSelectedIndex(int index) {
		if (index >= -1 && index < getListSize() && selectedIndex != index){
			selectedIndex = index;
			if (selectedIndex == -1){
				dataList.clearSelection();
			} else {
				dataList.setSelectedIndex(index);
			}
			mainController.setActiveController(this);
			notifyListPanelListener();
		}
	}

	@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	@Override
	public void clearSelection() {
		setSelectedIndex(-1);
	}

	@Override
	public int getListSize() {
		return dataModel.size();
	}

	@Override
	public T get(int row) {
		return dataModel.get(row);
	}

	@Override
	public String getTagName(int row) {
		return dataModel.getTagName(row);
	}

	@Override
	public void addRow() {
		mainController.setCommand(new TagNameDataModelAddCommand<T>(dataModel, getListSize(),
				"Added a new row"));
	}

	@Override
	public void addRow(int row) {
		mainController.setCommand(new TagNameDataModelAddCommand<T>(dataModel, row+1,
				"Added a new row"));
	}

	@Override
	public void removeRow(int row) {
		mainController.setCommand(new TagNameDataModelRemoveCommand<T>(dataModel, row, row));

	}

	@Override
	public void removeRows(int[] rows) {
		ArrayList<TagNameDataModelRemoveCommand<T>> commands = 
				new ArrayList<TagNameDataModelRemoveCommand<T>>();

		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);

		for (int i = 0; i < rows.length; i++){
			commands.add(new TagNameDataModelRemoveCommand<T>(dataModel, rows[i], rows[i]-i));
		}		
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}

	@Override
	public void copyRow(int row) {
		mainController.setCommand(new TagNameDataModelCopyCommand<T>(dataModel, row));
	}

	@Override
	public void copyRows(int [] rows){
		ArrayList<TagNameDataModelCopyCommand<T>> commands =
				new ArrayList<TagNameDataModelCopyCommand<T>>();

		//need to be sure that the rows are sorted from smallest to largest
		Arrays.sort(rows);

		for (int i = 0; i < rows.length; i++){
			commands.add(new TagNameDataModelCopyCommand<T>(dataModel, rows[i]+i));
		}
		mainController.setCommand(new CompoundUndoableCommand(commands));
	}

	@Override
	public void set(int row) {}

	@Override
	public void setTagName(int row, String tagName) {
		mainController.setCommand(new SetTagNameCommand(
				dataModel, row, getTagName(row), tagName));
	}

	@Override
	public void update(DataEvent e) {
		if (e.getDataModel() == dataModel.getActualModel()){
			if (e instanceof AddElementEvent){
				AddElementEvent evt = (AddElementEvent) e;
				viewModel.fireIntervalAdded(evt.getIndex(), evt.getIndex());
				setSelectedIndex(evt.getIndex());
				viewModel.fireContentsChanged(0, getListSize());
				mainController.setActiveController(this);
				
			} else if (e instanceof RemoveElementEvent){
				RemoveElementEvent evt = (RemoveElementEvent) e;
				viewModel.fireIntervalRemoved(evt.getIndex(), evt.getIndex());
				clearSelection();
				viewModel.fireContentsChanged(0, getListSize());
				mainController.setActiveController(this);
				
			} else if (e instanceof EditElementEvent){
				EditElementEvent evt = (EditElementEvent) e;
				viewModel.fireContentsChanged(evt.getIndex(), evt.getIndex());
				setSelectedIndex(evt.getIndex());
				viewModel.fireContentsChanged(0, getListSize());
				mainController.setActiveController(this);
				
			} else if (e instanceof EditTagNameEvent){
				EditTagNameEvent evt = (EditTagNameEvent) e;
				viewModel.fireContentsChanged(evt.getIndex(), evt.getIndex());
				viewModel.fireContentsChanged(0, getListSize());
				mainController.setActiveController(this);
			}
			
		}
	}

	@Override
	public JComponent getView(){return dataListPanel;}


	@Override
	public void addListPanelListener(ListPanelListener listener) {
		listListeners.add(listener);
	}

	@Override
	public void removeListPanelListener(ListPanelListener listener) {
		if (listListeners.contains(listener)){
			listListeners.remove(listener);
		}
	}

	@Override
	public void notifyListPanelListener() {
		for (ListPanelListener listener : listListeners){
			listener.changedSelectedElement(selectedIndex);
		}
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
	public void activateController() {
		dataList.setFocusable(true);
	}
	
	@Override
	public void deactivateController() {
		dataList.setFocusable(false);
	}

}
