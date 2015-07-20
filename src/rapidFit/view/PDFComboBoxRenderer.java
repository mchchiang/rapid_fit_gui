package rapidFit.view;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import rapidFit.data.PDFType;
import rapidFit.model.dataModel.ITagNameDataModel;

@SuppressWarnings("serial")
public class PDFComboBoxRenderer extends JLabel implements 
ListCellRenderer<PDFType> {
	/*
	 * customise the combo box cell renderer such that it displays
	 * the tag name of the PDF
	 */
	private ITagNameDataModel<PDFType> model;
	
	public PDFComboBoxRenderer(ITagNameDataModel<PDFType> model){
		this.model = model;
	}
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends PDFType> list,
			PDFType value, int index, boolean isSelected,
			boolean cellHasFocus) {
		this.setName(model.getTagName(value));			
		return this;
	}
}

