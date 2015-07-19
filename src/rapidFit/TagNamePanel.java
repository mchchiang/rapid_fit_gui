package rapidFit;

import java.awt.*;
import java.beans.*;

import javax.swing.*;

import rapidFit.controller.exception.TagNameException;

@SuppressWarnings("serial")
public class TagNamePanel<T> extends JPanel implements PropertyChangeListener{
	private JLabel lblTagName;
	private JFormattedTextField txtTagName;
	private TagNameManager<T> tagNameManager;
	private T entry;
	private String tagName;
	
	public TagNamePanel(){
		lblTagName = new JLabel("<html><b>Tag Name: </b></html>");
		txtTagName = new JFormattedTextField();
		txtTagName.setEditable(false);
		setLayout(new BorderLayout());
		add(lblTagName, BorderLayout.WEST);
		add(txtTagName, BorderLayout.CENTER);
	}
	
	public TagNamePanel(TagNameManager<T> manager, T entry){
		this.tagNameManager = manager;
		this.entry = entry;
		
		//initialise the tag name panel
		tagName = tagNameManager.getTagName(this.entry);
		
		lblTagName = new JLabel("<html><b>Tag Name: </b></html>");
		
		txtTagName = new JFormattedTextField();
		txtTagName.setEditable(true);
		txtTagName.setText(tagName);
		txtTagName.addPropertyChangeListener(this);
		
		setLayout(new BorderLayout());
		add(lblTagName, BorderLayout.WEST);
		add(txtTagName, BorderLayout.CENTER);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == txtTagName && 
				!tagName.equals(txtTagName.getText())){
			try {
				tagName = txtTagName.getText();
				tagNameManager.setTagName(entry, tagName);
			} catch (TagNameException tne1){
				RapidFitExceptionHandler.handles(tne1);
				tagName = tagNameManager.getTagName(entry);
				txtTagName.setText(tagName);
			}
			tagNameChanged(tagName);
		}
	}
	
	public void tagNameChanged(String newTagName){}
	public String getTagName(){return tagName;}
}
