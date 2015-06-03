package rapidFit;

import java.awt.*;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataList<T> extends JList<T> {
	
	private Method getMethod = null;
	
	//only used for tag name
	private int attrCount = 1;
	private IdentityHashMap<T, String> nameMap = null;
	private boolean useTagName = false;
	private String tagName;
	
	/*
	 * overriding the cell renderer to display a meaningful name of the object
	 * (i.e. a particular attribute of the object or a tag name) instead of the
	 * object memory reference (from toString() method) 
	 */
	private class DataListRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, 
				Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			if (!useTagName){
				try {
					setText((String) getMethod.invoke(value, (Object []) null));
				} catch (Exception e){
					e.printStackTrace();
				}
			} else {
				setText(nameMap.get(value));
			}
			return this;
		}
	}
	
	public DataList(DataListModel<T> model, Method getter){
		super(model);
		this.getMethod = getter;
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setCellRenderer(new DataListRenderer());
	}
	
	public DataList(DataListModel<T> model, String name, boolean useTag){
		super(model);
		
		if (!useTag){
			useTagName = false;
			try {
				//find the get method 
				this.getMethod = model.getDataClass().getMethod("get" + name, (Class<?>[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			useTagName = true;
			tagName = name;
			nameMap = new IdentityHashMap<T, String>();
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
	
	public String getTagName(T entry){
		if (useTagName){
			return nameMap.get(entry);
		} else {
			return "NO TAG NAME";
		}
	}
	
	public void updateRenderer(){
		setCellRenderer(new DataListRenderer());
	}
}
