package rapidFit;

import java.util.*;

/*
 * a class to handle tag names for a data list in which 
 * entries do not have a unique identifier (e.g. tag name or ID)
 */

public class TagNameManager<T> {
	/*
	 * a map that associates each object in the data list with
	 * a unique tag name
	 */
	protected IdentityHashMap<T, String> nameMap;
	protected int counter = 0;
	protected String tagName;
	
	public TagNameManager(){
		nameMap = new IdentityHashMap<T, String>();
	}
	
	public TagNameManager(List<T> data, String tag) throws TagNameException{
		tagName = tag;
		nameMap = new IdentityHashMap<T, String>();
		addEntries(data);
	}
	
	public void addEntry(T entry) throws TagNameException {
		/*
		 * ensure there is no duplicate of the same 
		 * data entry in the map
		 */
		if (nameMap.containsKey(entry)){
			throw new TagNameException(
					TagNameException.ErrorType.DUPLICATE_ENTRY);
		}
		counter++;
		nameMap.put(entry, tagName + "_" + counter);
	}
	
	public void addEntry(T entry, String tagName) throws TagNameException {
		setTagName(entry, tagName);
	}
	
	//add multiple entries to the map
	public void addEntries(List<T> data) throws TagNameException{
		for (T entry : data){
			addEntry(entry);
		}
	}
	
	public void removeEntry(T entry) throws TagNameException{
		if (!nameMap.containsKey(entry)){
			throw new TagNameException(
					TagNameException.ErrorType.ENTRY_NOT_EXIST);
		}
		nameMap.remove(entry);
	}
	
	public void setTagName(T entry, String tagName) throws TagNameException{
		/*
		 * check if the tag name is already used.
		 * If the entry is not in the map, it will be added
		 * to the map
		 */
		if (nameMap.containsValue(tagName)){
			throw new TagNameException(
					TagNameException.ErrorType.DUPLICATE_TAG_NAME);
		}
		nameMap.put(entry, tagName);
	}
	
	public String getTagName(T entry) throws TagNameException{
		if (!nameMap.containsKey(entry)){
			throw new TagNameException(
					TagNameException.ErrorType.ENTRY_NOT_EXIST);
		}
		return nameMap.get(entry);
	}
	
	public IdentityHashMap<T,String> getNameMap(){return nameMap;}
}
