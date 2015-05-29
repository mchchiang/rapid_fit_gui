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
	
	private class DataListRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {
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
			nameMap = new IdentityHashMap<T, String>();
			for (T entry : model.getData()){
				nameMap.put(entry, name + "_" + attrCount);
				attrCount++;
			}
		}
		setCellRenderer(new DataListRenderer());
	}
	
	public void setDefaultOptions(){
		setCellRenderer(new DataListRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
}
