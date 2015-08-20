package rapidFit.controller.command;

import rapidFit.model.dataModel.ITagNameDataModel;

public class TagNameDataModelSetCommand<T> implements UndoableCommand {

	private ITagNameDataModel<T> model;
	private int index;
	private T oldValue;
	private T newValue;
	private String oldTagName;
	private String newTagName;
	private boolean hasExecutedBefore;
	
	public TagNameDataModelSetCommand(ITagNameDataModel<T> model, int index,
			T oldValue, T newValue){
		this(model, index, oldValue, newValue, null);
	}
	
	public TagNameDataModelSetCommand(
			ITagNameDataModel<T> model, int index, 
			T oldValue, T newValue, String tagName){
		this.model = model;
		this.index = index;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.oldTagName = model.getTagName(index);
		this.newTagName = tagName;
		this.hasExecutedBefore = false;
	}
	
	@Override
	public boolean execute() {
		if (!hasExecutedBefore){
			if (newTagName == null){
				model.set(index, newValue);
				newTagName = model.getTagName(index);
			} else {
				model.set(index, newValue, newTagName);
			}
			hasExecutedBefore = true;
		} else {
			model.set(index, newValue, newTagName);
		}
		return true;
	}

	@Override
	public boolean undo() {
		model.set(index, oldValue, oldTagName);
		return true;
	}
	
}
