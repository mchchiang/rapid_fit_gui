package rapidFit.controller.command;

import rapidFit.model.dataModel.ITagNameDataModel;

public class TagNameDataModelAddCommand<T> implements UndoableCommand {
	
	private ITagNameDataModel<T> model;
	private int index;
	private String description;
	private String tagName;
	private boolean hasExecutedBefore;
	private T addObject;
	
	public TagNameDataModelAddCommand(ITagNameDataModel<T> model,
			int index, String description){
		this(model, index, null, description);
	}
	
	public TagNameDataModelAddCommand(ITagNameDataModel<T> model,
			int index, T object, String description){
		this.model = model;
		this.index = index;
		this.description = description;
		this.hasExecutedBefore = false;
		this.addObject = object;
	}
	
	@Override
	public boolean execute(){
		try {
			if (hasExecutedBefore){
				model.add(index, addObject, tagName);
			} else {
				if (addObject != null) {
					model.add(index, addObject);
				} else {
					model.add(index);
					addObject = model.get(index);
				}
				tagName = model.getTagName(index);
				hasExecutedBefore = true;
			}
			return true;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean undo(){
		model.remove(index);
		return true;
	}
	
	public String toString(){
		return description;
	}
}
