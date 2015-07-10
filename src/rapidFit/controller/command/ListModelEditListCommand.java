package rapidFit.controller.command;

//import java.math.BigInteger;
//import java.util.ArrayList;
import java.util.List;
//import java.util.StringTokenizer;

import rapidFit.Cloner;
import rapidFit.model.IListModel;
import rapidFit.model.IListModel.UpdateType;

public class ListModelEditListCommand<T> implements UndoableCommand {
	
	private IListModel<?> model;
	private int index;
	private String field;
	private List<T> oldList;
	private List<T> newList;
	private List<T> modelList;
	private boolean canExecute = false;
	
	@SuppressWarnings("unchecked")
	public ListModelEditListCommand (
			IListModel<?> model, int index, 
			String field, List<T> newList){
		
		this.model = model;
		this.index = index;
		this.field = field;
		this.newList = newList;
		
		try {
			this.modelList = (List<T>) model.get(index, field);
			this.oldList = (List<T>) Cloner.deepClone(modelList);
			canExecute = true;
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean execute() {
		if (canExecute){
			setList(newList);
			return true;
		}
		return false;
	}

	@Override
	public boolean undo() {
		if (canExecute){
			setList(oldList);
			return true;
		}
		return false;
	}
	
	private void setList(List<T> data){
		modelList.clear();
		for (T entry : data){
			modelList.add(entry);
		}
		model.setUpdateType(UpdateType.EDIT);
		model.setUpdateIndex(index);
		model.setUpdateField(field);
		model.notifyListObserver();
	}

}
