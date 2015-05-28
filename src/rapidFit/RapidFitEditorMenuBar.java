package rapidFit;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

@SuppressWarnings("serial")
public class RapidFitEditorMenuBar extends JMenuBar implements ActionListener {
	private JMenu mnuFile;
	private JMenu mnuHelp;
	private JMenuItem mnuAbout;
	private JMenuItem mnuImport;
	private JMenuItem mnuExport;
	private JFileChooser fc = new JFileChooser();
	private RapidFitEditor editor;
	
	public RapidFitEditorMenuBar(RapidFitEditor e){
		editor = e;
		mnuImport = new JMenuItem("Import XML");
		mnuImport.addActionListener(this);
		mnuExport = new JMenuItem("Export XML");
		mnuExport.addActionListener(this);
		
		mnuFile = new JMenu("File");
		mnuFile.add(mnuImport);
		mnuFile.add(mnuExport);
		
		mnuAbout = new JMenuItem("About");
		mnuAbout.addActionListener(this);
		mnuHelp = new JMenu("Help");
		mnuHelp.add(mnuAbout);

		this.add(mnuFile);
		this.add(mnuHelp);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mnuImport) {
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				//File file = fc.getSelectedFile();
			}
		} else if (e.getSource() == mnuExport){
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				XMLWriter writer = new XMLWriter();
				writer.writeFile(editor.root, file.getAbsolutePath(), "/Users/MichaelChiang/Dropbox/Edinburgh"
						+ "/Courses/Year 2/Summer_Project/"
						+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd");
			}
		} else if (e.getSource() == mnuAbout){
			
			JFrame helpFrame = new JFrame("About Rapid Fit Editor");
			helpFrame.setSize(new Dimension(300, 180));
			helpFrame.setLayout(new BorderLayout());
			helpFrame.getRootPane().setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
			helpFrame.add(new JLabel(
	"<html>"
	+"<body>" 
	+"<h2> Rapid Fit Editor </h2>"
	+"<p> Version 1.0.0 </p>"
	+"<p> Created by Michael Chiang </p>"
	+"</body>"
	+"</html>"),
	BorderLayout.CENTER);
			helpFrame.setVisible(true);
			
			helpFrame.setLocationRelativeTo(null);
		}
		
	}

}
