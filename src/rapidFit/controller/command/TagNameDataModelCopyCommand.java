package rapidFit.controller.command;

import rapidFit.Cloner;
import rapidFit.model.dataModel.ITagNameDataModel;

public class TagNameDataModelCopyCommand<T> implements UndoableCommand {
	
	private ITagNameDataModel<T> model;
	private int index;
	private T copiedObject;
	private boolean hasExecutedBefore;
	private String tagName;
	
	public TagNameDataModelCopyCommand(ITagNameDataModel<T> model, T object){
		this(model, model.indexOf(object));
	}
	
	public TagNameDataModelCopyCommand(ITagNameDataModel<T> model, int index){
		this.model = model;
		this.index = index;	
		this.hasExecutedBefore = false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean execute() {
		try {
			
			if (!hasExecutedBefore) {
				copiedObject = (T) Cloner.deepClone(model.get(index));
				tagName = model.getTagName(index) + "_copy";
				hasExecutedBefore = true;
			} 
			model.add(index+1, copiedObject);
			model.setTagName(index+1, tagName);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean undo() {
		model.remove(index+1);
		return true;
	}
	
}
