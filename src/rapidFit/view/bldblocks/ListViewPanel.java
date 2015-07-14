package rapidFit.view.bldblocks;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListViewPanel extends JPanel {
	
	private JComponent displayPanel;
	private JComponent listPanel;
	
	public ListViewPanel(JComponent listPanel, JComponent displayPanel, 
			String listTitle, String displayTitle){
		this.listPanel = listPanel;
		this.displayPanel = displayPanel;
		
		this.listPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>" + listTitle + "</h3></html>"));
		
		if (displayTitle == null || displayTitle.equals("")) {
			displayTitle = "&nbsp;";
		}
		this.displayPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>" + displayTitle + "</h3></html>"));
		
		this.setLayout(new BorderLayout());
		this.add(listPanel, BorderLayout.WEST);
		this.add(this.displayPanel, BorderLayout.CENTER);
		
	}
	
	public void updateDisplayPanel(JComponent displayPanel, String displayTitle){
		this.remove(this.displayPanel);
		this.displayPanel = displayPanel;
		if (displayTitle == null || displayTitle.equals("")) {
			displayTitle = "&nbsp;";
		}
		this.displayPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>" + displayTitle + "</h3></html>"));
		
		this.add(this.displayPanel, BorderLayout.CENTER);
		this.repaint();
		this.validate();
	}
	
	public void updateDisplayTitle(String displayTitle) {
		if (displayTitle == null || displayTitle.equals("")) {
			displayTitle = "&nbsp;";
		}
		this.displayPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>" + displayTitle + "</h3></html>"));
		this.repaint();
		this.validate();
	}
	
}
