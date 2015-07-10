package rapidFit.view.bldblocks;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import rapidFit.controller.IListPanelController;

@SuppressWarnings("serial")
public class DataList<T> extends JList<T> {
	
	private IListPanelController<T> controller;
	
	/*
	 * overriding the cell renderer to display a meaningful name of the object
	 * (i.e. a particular attribute of the object or a tag name) instead of the
	 * object memory reference (from toString() method) 
	 */
	private class DataListRenderer extends DefaultListCellRenderer{
		public Component getListCellRendererComponent(JList<?> list, 
				Object value, int index, boolean isSelected, boolean cellHasFocus) {
			super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			setText(controller.getTagName(index));
			return this;
		}
	}
	
	public DataList(IListPanelController<T> controller, DataListViewModel<T> viewModel){
		super(viewModel);
		this.controller = controller;
		setCellRenderer(new DataListRenderer());
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}	
}