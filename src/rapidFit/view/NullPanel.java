package rapidFit.view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

@SuppressWarnings("serial")
public class NullPanel extends JPanel {
	public NullPanel() {
		
		setLayout(new BorderLayout());
		
		JTextPane txtNoData = new JTextPane();
		txtNoData.setText("There is no entry selected.");
		StyledDocument doc = txtNoData.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		txtNoData.setEditable(false);
		txtNoData.setBackground(getBackground());
		
		add(txtNoData, BorderLayout.CENTER);	
	}
}
