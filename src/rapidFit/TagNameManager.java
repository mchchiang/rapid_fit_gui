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
	private IdentityHashMap<T, String> nameMap;
	
	private int counter = 0;
	
	private String tagName;
	
	public TagNameManager(List<T> data, String tag){
		
		tagName = tag;
		
		nameMap = new IdentityHashMap<T, String>();
		
		for (T entry : data){
			addEntry(entry);
		}
	}
	
	public boolean addEntry(T entry){
		/*
		 * ensure there is no duplicate of the same 
		 * data entry in the map
		 */
		if (!nameMap.containsKey(entry)){
			counter++;
			nameMap.put(entry, tagName + "_" + counter);
			return true;
		}
		return false;
	}
	
	public boolean addEntry(T entry, String tagName){
		return setTagName(entry, tagName);
	}
	
	public boolean removeEntry(T entry){
		if (nameMap.containsKey(entry)){
			nameMap.remove(entry);
			return true;
		}
		return false;
	}
	
	public boolean setTagName(T entry, String tagName){
		/*
		 * check if the tag name is already used.
		 * If the entry is not in the map, it will be added
		 * to the map
		 */
		if (!nameMap.containsValue(tagName)){
			nameMap.put(entry, tagName);
			return true;
		}
		return false;
	}
	
	public String getTagName(T entry){
		return nameMap.get(entry);
	}
	
	public IdentityHashMap<T,String> getNameMap(){return nameMap;}
}
