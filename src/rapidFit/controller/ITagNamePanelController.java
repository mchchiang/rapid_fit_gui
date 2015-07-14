package rapidFit.controller;

import rapidFit.model.DataListener;

public interface ITagNamePanelController extends Controller, DataListener {

	public void setTagName(String tagName);
	public String getTagName();
	
}
