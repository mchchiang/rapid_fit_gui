package rapidFit.controller.command;

import rapidFit.controller.exception.TagNameException;
import rapidFit.model.dataModel.ITagNameDataModel;

public class EditDataModelTagNameCommand implements UndoableCommand {

	private ITagNameDataModel<?> model;
	private String oldTagName;
	private String newTagName;
	private int index;

	public EditDataModelTagNameCommand (ITagNameDataModel<?> model,
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
