package rapidFit.view.blocks;

import java.util.List;
import java.awt.*;

import javax.swing.*;
import javax.swing.text.*;

import rapidFit.view.AbstractDataListViewer;

@SuppressWarnings("serial")
public class DataListViewer<T> extends AbstractDataListViewer<T> {
	
	private String entryTitle = "Attributes";
	private Class<T> dataClass;

	public DataListViewer(Class<T> clazz, List<T> dataRoot, List<T> data,
			String listTitle, String tagName, String entryTitle) {
		super(clazz, dataRoot, data, listTitle, tagName);
		
		this.dataClass = clazz;
		
		if (entryTitle != null){
			this.entryTitle = entryTitle;
		}
	}

	@Override
	protected JPanel initNullDisplayPanel() {
		JTextPane txtNoData = new JTextPane();
		txtNoData.setText("There is no entry selected.");
		StyledDocument doc = txtNoData.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		txtNoData.setEditable(false);
		txtNoData.setBackground(getBackground());
		
		JPanel nullPanel = new JPanel();
		nullPanel.setLayout(new BorderLayout());
		nullPanel.add(txtNoData, BorderLayout.CENTER);
		
		return nullPanel;
	}

	@Override
	protected JPanel initDataDisplayPanel(T entry) {
		return new AttributePanel<T>(dataClass, entry, entryTitle, null);
	}

}
