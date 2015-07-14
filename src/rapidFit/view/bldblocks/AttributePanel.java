package rapidFit.view.bldblocks;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class AttributePanel extends JPanel {
	private JScrollPane scrollPane;
	
	public AttributePanel (AttributeTable table, String name){				
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		Border border = BorderFactory.createTitledBorder("<html><h3>" + name + "</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		
		this.setBorder(new CompoundBorder(margin, border));
	}
	
}
