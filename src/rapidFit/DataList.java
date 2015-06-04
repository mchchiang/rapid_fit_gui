package rapidFit;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataList<T> extends JList<T> {
	
	/*private Method getMethod = null;
	
	//only used for tag name
	private int attrCount = 1;
	private boolean useTagName = false;
	private String tagName;*/
	
	private IdentityHashMap<T, String> nameMap = null;
	
	/*
	 * overriding the cell renderer to display a meaningful name of the object
	 * (i.e. a particular attribute of the object or a tag name) instead of the
	 * object memory reference (from toString() method) 
	 */
	private class DataListRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, 
				Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (nameMap.containsKey(value)){
				setText(nameMap.get(value));
			} else {
				setText(value.toString());
			}
			/*if (!useTagName){
				try {
					setText((String) getMethod.invoke(value, (Object []) null));
				} catch (Exception e){
					e.printStackTrace();
				}
			} else {
				setText(nameMap.get(value));
			}*/
			return this;
		}
	}
	
	public DataList(DataListModel<T> model, IdentityHashMap<T, String> map){
		super(model);
		nameMap = map;
		setCellRenderer(new DataListRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void setTagName(T entry, String name){
		nameMap.put(entry, name);
		setCellRenderer(new DataListRenderer());
	}
	
	public String getTagName(T entry){
		return nameMap.get(entry);
	}
	
	public void updateNameMap(IdentityHashMap<T, String> map){
		nameMap = map;
	}
	
	/*
	//=====================================================================================
	public DataList(DataListModel<T> model, Method getter){
		super(model);
		this.getMethod = getter;
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setCellRenderer(new DataListRenderer());
	}
	
	public DataList(DataListModel<T> model, String name, boolean useTag){
		super(model);
		
		nameMap = new IdentityHashMap<T, String>();
		nameCount = new HashMap<String, Integer>();
		
		if (!useTag){
			useTagName = false;
			
			try {
				//find the get method 
				this.getMethod = model.getDataClass().getMethod("get" + name, (Class<?>[]) null);
				
				//create distinct name for each entry
				for (T entry : model.getData()){
					String attrName = getMethod.invoke(entry, (Object []) null).toString();
					if (nameCount.containsKey(attrName)){
						nameCount.put(attrName, nameCount.get(attrName)+1);
					} else {
						nameCount.put(attrName, 1);
					}
					nameMap.put(entry, attrName + "_" + nameCount.get(attrName));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			useTagName = true;
			tagName = name;
			
			for (T entry : model.getData()){
				nameMap.put(entry, name + "_" + attrCount);
				attrCount++;
			}
		}
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setCellRenderer(new DataListRenderer());
	}
	
	public void setDefaultOptions(){
		setCellRenderer(new DataListRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void setTagName(T entry, String name){
		if (useTagName){
			nameMap.put(entry, name);
		}
		setCellRenderer(new DataListRenderer());
	}
	
	public void setTagName(T entry){
		if (useTagName){
			nameMap.put(entry, tagName + "_" + attrCount);
			attrCount++;
		}
		setCellRenderer(new DataListRenderer());
	}
	
	/*public String getTagName(T entry){
		if (useTagName){
			return nameMap.get(entry);
		} else {
			return "NO TAG NAME";
		}
	}*/
	
}
