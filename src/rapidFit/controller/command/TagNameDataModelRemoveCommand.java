package rapidFit.controller.command;

import rapidFit.model.ITagNameDataModel;

public class TagNameDataModelRemoveCommand<T> implements UndoableCommand {
	
	private ITagNameDataModel<T> model;
	private T oldObject;
	private int index;
	private String tagName;
	
	public TagNameDataModelRemoveCommand(
			ITagNameDataModel<T> model, int currentIndex, int removeIndex){
		this.model = model;
		this.index = removeIndex;
		oldObject = model.get(currentIndex);
		tagName = model.getTagName(currentIndex);
	}
	
	@Override
	public boolean execute() {
		model.remove(index);
		return true;
	}

	@Override
	public boolean undo() {
		model.add(index, oldObject, tagName);
		return true;
	}
	
}
