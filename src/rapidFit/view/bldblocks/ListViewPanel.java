package rapidFit.view.bldblocks;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ListViewPanel extends JPanel {
	
	private JComponent displayPanel;
	private JComponent listPanel;
	private String listTitle;
	private String displayTitle;
	
	public ListViewPanel(JComponent listPanel, JComponent displayPanel, 
			String listTitle, String displayTitle){
		this.listPanel = listPanel;
		this.displayPanel = displayPanel;
		
		setListTitle(listTitle);
		setDisplayTitle(displayTitle);
		
		this.setLayout(new BorderLayout());
		this.add(listPanel, BorderLayout.WEST);
		this.add(this.displayPanel, BorderLayout.CENTER);
		
	}
	
	public void setDisplayTitle(String title) {
		if (title == null || title == "") {
			title = "&nbsp;";
		}
		if ((displayTitle == null && title != null) ||
				!displayTitle.equals(title)) {
			displayTitle = title;
			displayPanel.setBorder(BorderFactory.createTitledBorder(
					"<html><h3>" + displayTitle + "</h3></html>"));
			displayPanel.repaint();
			displayPanel.validate();
		}
	}
	
	public void setListTitle(String title) {
		if (title == null || title == "") {
			title = "&nbsp;";
		}
		if ((listTitle == null && title != null) ||
				!listTitle.equals(title)){
			listTitle = title;
			listPanel.setBorder(BorderFactory.createTitledBorder(
					"<html><h3>" + listTitle + "</h3></html>"));
			listPanel.repaint();
			listPanel.validate();
		}
	}
	
	public void updateDisplayPanel(JComponent newDisplayPanel, String newDisplayTitle){
		if (displayPanel != newDisplayPanel){
			remove(displayPanel);
			displayPanel = newDisplayPanel;
			add(displayPanel, BorderLayout.CENTER);
		}
		setDisplayTitle(newDisplayTitle);
		repaint();
		validate();
	}	
}
