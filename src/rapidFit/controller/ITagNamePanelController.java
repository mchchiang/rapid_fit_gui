package rapidFit.controller;

import rapidFit.model.dataModel.DataListener;

public interface ITagNamePanelController extends Controller, DataListener {

	public void setTagName(String tagName);
	public String getTagName();
	
}
