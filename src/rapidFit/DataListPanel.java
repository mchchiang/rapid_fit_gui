package rapidFit;

import java.util.List;
import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class DataListPanel<T> extends AbstractDataListPanel<T> {
	
	private String entryTitle = "Attributes";
	private Class<T> dataClass;

	public DataListPanel(Class<T> clazz, List<T> dataRoot, List<T> data,
			String listTitle, String tagName, String entryTitle) {
		super(clazz, dataRoot, data, listTitle, tagName);
		
		this.dataClass = clazz;
		
		if (entryTitle != null){
			this.entryTitle = entryTitle;
		}
	}

	@Override
	protected JPanel initNullDisplayPanel() {
		JTextArea txtNoData = new JTextArea("There is no entry selected.");
		txtNoData.setLineWrap(true);
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
