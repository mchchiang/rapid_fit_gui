package rapidFit.controller.command;

import rapidFit.model.ITagNameListModel;

public class TagNameListModelAddCommand implements UndoableCommand {
	
	private ITagNameListModel<?> model;
	private int index;
	private String description;
	private String tagName;
	private boolean hasExecutedBefore;
	
	public TagNameListModelAddCommand(ITagNameListModel<?> model, 
			int index, String description) {
		this.model = model;
		this.index = index;
		this.description = description;
		this.hasExecutedBefore = false;
		this.tagName = null;
	}
	
	@Override
	public boolean execute(){
		try {
			if (hasExecutedBefore){
				model.add(index, tagName);
			} else {
				model.add(index);
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
