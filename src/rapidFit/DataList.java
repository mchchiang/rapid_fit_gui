package rapidFit;

import java.awt.*;

import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataList<T> extends JList<T> {
	
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
	
}
