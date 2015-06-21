package rapidFit.view.blocks;

import java.util.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class AttributePanel<T> extends JPanel {
	private AttributeTableModel<T> tableModel;
	private AttributeTable table;
	private JScrollPane scrollPane;
	
	public AttributePanel (Class<T> type, T data, String name, ArrayList<String> ignoreAttr){		
		tableModel = new AttributeTableModel<T>(type, data, ignoreAttr);
		table = new AttributeTable(tableModel);
		
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		//this.setLayout(new GridLayout(1,1));
		//this.add(scrollPane);
		Border border = BorderFactory.createTitledBorder("<html><h3>" + name + "</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		
		this.setBorder(new CompoundBorder(margin, border));
	}
	
}
