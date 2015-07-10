package rapidFit.controller.command;

import rapidFit.TagNameException;
import rapidFit.model.ITagNameListModel;

public class SetTagNameCommand implements UndoableCommand {

	private ITagNameListModel<?> model;
	private String oldTagName;
	private String newTagName;
	private int index;

	public SetTagNameCommand (ITagNameListModel<?> model,
			int index, String oldTagName, String newTagName){
		this.model = model;
		this.index = index;
		this.oldTagName = oldTagName;
		this.newTagName = newTagName;
	}

	@Override
	public boolean execute() {
		try {
			model.setTagName(index, newTagName);
			return true;
		} catch (TagNameException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean undo() {
		try {
			model.setTagName(index, oldTagName);
			return true;
		} catch (TagNameException e) {
			e.printStackTrace();
			return false;
		}
	}

}
