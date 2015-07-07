package rapidFit.controller.command;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import rapidFit.Cloner;
import rapidFit.model.AbstractListModel;
import rapidFit.model.AbstractListModel.UpdateType;

public class ListModelEditListCommand<T> implements UndoableCommand {
	
	private AbstractListModel<?> model;
	private int index;
	private String field;
	private List<T> oldList;
	private List<T> newList;
	private List<T> modelList;
	private boolean canExecute = false;
	
	@SuppressWarnings("unchecked")
	public ListModelEditListCommand (
			AbstractListModel<?> model, int index, 
			String field, Class<T> listType, String newList){
		
		this.model = model;
		this.index = index;
		this.field = field;
		
		try {
			this.modelList = (List<T>) model.get(index, field);
			
			this.oldList = (List<T>) Cloner.deepClone(modelList);
			
			this.newList = new ArrayList<T>();
			
			StringTokenizer st = new StringTokenizer(newList, "[, ]");
			
			if (listType == Double.class){
				while(st.hasMoreElements()){
					this.newList.add(listType.cast(Double.parseDouble(st.nextToken())));
				}
			} else if (listType == BigInteger.class){
				while(st.hasMoreElements()){
					this.newList.add(listType.cast(Integer.parseInt(st.nextToken())));
				}
			} else if (listType == String.class){
				while(st.hasMoreElements()){
					this.newList.add(listType.cast(st.nextToken()));
				}
			}
			
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
		model.notifyObserver();
	}

}
