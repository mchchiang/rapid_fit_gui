package rapidFit.view.bldblocks;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rapidFit.controller.ITagNamePanelController;

@SuppressWarnings("serial")
public class TagNamePanel extends JPanel implements PropertyChangeListener {
	private ITagNamePanelController controller;
	private JLabel lblTagName;
	private JFormattedTextField txtTagName;
	private String tagName;
	
	public TagNamePanel(ITagNamePanelController controller){
		this.controller = controller;
		
		this.tagName = "";
		
		//initialise the tag name panel
		lblTagName = new JLabel("<html><b>Tag Name: </b></html>");
		
		txtTagName = new JFormattedTextField();
		txtTagName.setEditable(true);
		txtTagName.setText(tagName);
		txtTagName.addPropertyChangeListener(this);
		
		setLayout(new BorderLayout());
		add(lblTagName, BorderLayout.WEST);
		add(txtTagName, BorderLayout.CENTER);
	}
	
	public void setTagName(String tagName) {
		if (tagName == null && this.tagName != ""){
			this.tagName = "";
		} else if (tagName != null && !this.tagName.equals(tagName)){
			this.tagName = tagName;
		}
		txtTagName.setText(this.tagName);
	}
	
	public String getTagName() {
		return tagName;
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == txtTagName && 
				!tagName.equals(txtTagName.getText())){
			tagName = txtTagName.getText();
			controller.setTagName(tagName);
		}
	}
	
	public JFormattedTextField getTagNameTextField() {return txtTagName;}
}
