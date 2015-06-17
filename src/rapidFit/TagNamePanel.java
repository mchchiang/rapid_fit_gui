package rapidFit;

import java.awt.*;
import javax.swing.*;

public class TagNamePanel extends JPanel{
	private JLabel lblTagName;
	private JTextField txtTagName;
	
	public TagNamePanel(){
		//initialise the tag name panel
		lblTagName = new JLabel("<html><b>Tag Name: </b></html>");
		txtTagName = new JTextField();

		setLayout(new BorderLayout());
		add(lblTagName, BorderLayout.WEST);
		add(txtTagName, BorderLayout.CENTER);
	}
}
