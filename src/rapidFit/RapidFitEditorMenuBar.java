package rapidFit;

import javax.swing.*;
import javax.swing.filechooser.*;

import rapidFit.rpfit.RapidFitType;
import java.awt.event.*;
import java.io.File;

@SuppressWarnings("serial")
public class RapidFitEditorMenuBar extends JMenuBar implements ActionListener {
	private JMenu mnuFile;
	private JMenu mnuHelp;
	
	private JMenuItem mnuAbout;
	
	private JMenuItem mnuImport;
	private JMenuItem mnuExport;
	private JMenuItem mnuNew;
	
	private JFileChooser fc = new JFileChooser();
	private RapidFitEditor editor;
	
	public RapidFitEditorMenuBar(RapidFitEditor e){
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file", "xml");
		fc.setFileFilter(filter);
		editor = e;
		mnuImport = new JMenuItem("Import XML");
		mnuImport.addActionListener(this);
		mnuExport = new JMenuItem("Export XML");
		mnuExport.addActionListener(this);
		mnuNew = new JMenuItem("New XML");
		mnuNew.addActionListener(this);
		
		mnuFile = new JMenu("File");
		mnuFile.add(mnuImport);
		mnuFile.add(mnuExport);
		mnuFile.add(mnuNew);
		
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
				File file = fc.getSelectedFile();
				
				//read xml
				try {
					RapidFitType root = RapidFitFactory.createFitFromFile(file.getAbsolutePath(), 
							"/Users/MichaelChiang/Dropbox/Edinburgh/Courses/Year 2/Summer_Project/"
							+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd");
					String fileName = file.getName();
					editor.showFit(root, fileName);
				} catch (XMLIOException xe){
					RapidFitExceptionHandler.handles(xe);
				}
			}
						
		} else if (e.getSource() == mnuExport){
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				XMLIO.writeFile(editor.root, file.getAbsolutePath(), "/Users/MichaelChiang/Dropbox/Edinburgh"
						+ "/Courses/Year 2/Summer_Project/"
						+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd");
			}
		
		} else if (e.getSource() == mnuNew) {
			editor.showFit(RapidFitFactory.createEmptyFit(), "New Fit");
			
		} else if (e.getSource() == mnuAbout){
			new AboutDialog().setVisible(true);
		}
		
	}

}
