package rapidFit.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog implements ActionListener {
	
	private JLabel lblAbout;
	private JButton btnClose;
	
	public AboutDialog(){
		setTitle("About Rapid Fit Editor");
		setModal(true);
		setSize(new Dimension(300, 180));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		lblAbout = new JLabel(
			"<html>"
			+"<body>" 
			+"<h2> Rapid Fit Editor </h2>"
			+"<p> Version 1.0.2 (Beta) </p>"
			+"<p> Created by Michael Chiang </p>"
			+"</body>"
			+"</html>");
		
		btnClose = new JButton("Close");
		btnClose.addActionListener(this);
		
		setLayout(new BorderLayout());
		getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		
		Container content = getContentPane();
		content.add(lblAbout, BorderLayout.CENTER);
		content.add(btnClose, BorderLayout.SOUTH);
	 }
	
	public void actionPerformed (ActionEvent e){
		if (e.getSource() == btnClose){
			dispose();
		}
	}
}
