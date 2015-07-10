package rapidFit.model;

import java.util.HashMap;

import rapidFit.TagNameException;

public class TagNameListModel<T> extends ITagNameListModel<T> {
	
	/*
	 * a map that associates each object in the data list with
	 * a unique tag name
	 */
	private HashMap<T, String> nameMap;
	private String tagName;
	private int counter = 0;
	
	public TagNameListModel(IListModel<T> model, String tagName) {
		super(model);
		this.tagName = tagName;
		nameMap = new HashMap<T, String>();
		super.initTagNames();
	}
	
	@Override
	public void addEntry(T entry) {
		if (!nameMap.containsKey(entry)){
			counter++;
			nameMap.put(entry, tagName + "_" + counter);
		}
	}
	
	@Override
	public void addEntry(T entry, String tagName){
		if (!nameMap.containsKey(entry) && !nameMap.containsValue(tagName)){
			nameMap.put(entry, tagName);
		}
	}
	
	@Override
	public void removeEntry(T entry) {
		if (nameMap.containsKey(entry)){
			nameMap.remove(entry);
		}
	}

	@Override
	public void setTagName(T entry, String tagName)
			throws TagNameException {
		/*
		 * check if the tag name is already used.
		 * If the entry is not in the map, it will be added
		 * to the map
		 */
		if (nameMap.containsValue(tagName)){
			throw new TagNameException(
					TagNameException.ErrorType.DUPLICATE_TAG_NAME);
		}
		if (!nameMap.containsKey(entry)){
			throw new TagNameException(
					TagNameException.ErrorType.ENTRY_NOT_EXIST);
		}
		nameMap.put(entry, tagName);
		
	}

	@Override
	public String getTagName(T entry) {
		return nameMap.get(entry);
	}

	@Override
	public HashMap<T, String> getNameMap() {
		return nameMap;
	}

}
