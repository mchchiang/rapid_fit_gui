package rapidFit.main;

import javax.swing.*;
import javax.swing.filechooser.*;

import rapidFit.model.*;
import rapidFit.view.AboutDialog;
import rapidFit.view.SettingsDialog;

import java.awt.event.*;
import java.io.File;

@SuppressWarnings("serial")
public class RapidFitEditorMenuBar extends JMenuBar implements ActionListener {
	private JMenu mnuFile;
	private JMenu mnuHelp;
	
	private JMenuItem mnuAbout;
	
	private JMenuItem mnuImport;
	private JMenuItem mnuSave;
	private JMenuItem mnuExport;
	private JMenuItem mnuNew;
	private JMenuItem mnuSettings;
	private JMenuItem mnuQuit;
	
	private JFileChooser fc;
	
	private static RapidFitEditorMenuBar menuBar = null;
	
	//prevent outer classes to create a new menu bar (singleton pattern)
	private RapidFitEditorMenuBar(){
		initMenuBar();
	}
	
	public static RapidFitEditorMenuBar getInstance(){
		if (menuBar == null){
			menuBar = new RapidFitEditorMenuBar();
		}
		return menuBar;
	}
	
	public void initMenuBar(){
		FileNameExtensionFilter filter = new FileNameExtensionFilter("XML file", "xml");
		fc = new JFileChooser();
		fc.setFileFilter(filter);
		
		mnuImport = new JMenuItem("Open XML");
		mnuImport.addActionListener(this);
		
		mnuExport = new JMenuItem("Save As New XML");
		mnuExport.setEnabled(false);
		mnuExport.addActionListener(this);
		
		mnuSave = new JMenuItem("Save XML");
		mnuSave.setEnabled(false);
		mnuSave.addActionListener(this);
		
		mnuNew = new JMenuItem("New XML");
		mnuNew.addActionListener(this);
		
		mnuSettings = new JMenuItem("Settings and File Info");
		mnuSettings.addActionListener(this);
		
		mnuQuit = new JMenuItem("Quit");
		mnuQuit.addActionListener(this);
		
		mnuFile = new JMenu("File");
		mnuFile.add(mnuImport);
		mnuFile.add(mnuSave);
		mnuFile.add(mnuExport);
		mnuFile.add(mnuNew);
		mnuFile.add(new JSeparator());
		mnuFile.add(mnuSettings);
		mnuFile.add(new JSeparator());
		mnuFile.add(mnuQuit);
		
		mnuAbout = new JMenuItem("About");
		mnuAbout.addActionListener(this);
		mnuHelp = new JMenu("Help");
		mnuHelp.add(mnuAbout);

		this.add(mnuFile);
		this.add(mnuHelp);
	}
	
	public JMenuItem getSaveButton(){return mnuSave;}
	public JMenuItem getSaveAsButton(){return mnuExport;}
	public JMenuItem getOpenButton(){return mnuImport;}
	
	public void actionPerformed(ActionEvent e) {
		//stop the active table editing before any menu item action is invoked
		RapidFitMainControl.getInstance().stopTableEditing();
		
		if (e.getSource() == mnuImport) {
			//check if there is unsaved edits
			if (unsaveEditCheck() == JOptionPane.YES_OPTION){
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					//read xml
					try {
						RapidFitType root = RapidFitFactory.createFitFromFile(file.getAbsolutePath());
						RapidFitMainControl.getInstance().setRoot(root);
								
						String fileName = file.getName();
						RapidFitMainControl.getInstance().setFile(file);
						RapidFitMainControl.getInstance().setUnsavedEdits(false);
						mnuSave.setEnabled(true);
						mnuExport.setEnabled(true);
						RapidFitEditor.getInstance().showFit(root, fileName);
					} catch (XMLIOException xe){
						RapidFitExceptionHandler.handles(xe);
					}
				}
			}
						
		} else if (e.getSource() == mnuExport){
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				try {
					XMLIO.getInstance().writeFile(RapidFitMainControl.getInstance().getRoot(), 
							file.getAbsolutePath());
					RapidFitMainControl.getInstance().setFile(file);
					RapidFitMainControl.getInstance().setUnsavedEdits(false);
					RapidFitEditor.getInstance().setDisplayFileName(file.getName());
					RapidFitEditorMenuBar.getInstance().getSaveButton().setEnabled(true);
					
				} catch (XMLIOException err){
					RapidFitExceptionHandler.handles(err);
				}				
			}
		
		} else if (e.getSource() == mnuSave){			
			//write the file
			try {
				XMLIO.getInstance().writeFile(RapidFitMainControl.getInstance().getRoot(), 
						RapidFitMainControl.getInstance().getFile().getAbsolutePath());
				RapidFitMainControl.getInstance().setUnsavedEdits(false);
			} catch (XMLIOException err){
				RapidFitExceptionHandler.handles(err);
			}
			
		} else if (e.getSource() == mnuNew) {
			//check for unsaved edits
			if (unsaveEditCheck() == JOptionPane.YES_OPTION){
				RapidFitType root = RapidFitFactory.createEmptyFit();
				RapidFitMainControl.getInstance().setRoot(root);
				RapidFitMainControl.getInstance().setFile(new File("New Fit"));
				RapidFitMainControl.getInstance().setUnsavedEdits(false);
				RapidFitEditor.getInstance().showFit(root, "New Fit");
				RapidFitEditorMenuBar.getInstance().getSaveButton().setEnabled(false);
				mnuExport.setEnabled(true);
			}
		} else if (e.getSource() == mnuSettings){
			new SettingsDialog().setVisible(true);
			
		} else if (e.getSource() == mnuAbout){
			new AboutDialog().setVisible(true);
			
		} else if (e.getSource() == mnuQuit){
			System.exit(0);
		}
	}
	
	private int unsaveEditCheck(){
		int result = JOptionPane.YES_OPTION;
		if (RapidFitMainControl.getInstance().hasUnsavedEdits()){
			result = JOptionPane.showOptionDialog(this, 
					"There are unsaved edits in the current file.\n"
					+ "Are you sure you want to open a new file ?\n "
							+ "All unsaved edits will be lost.", "Unsaved Edits", 
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
							new String [] {"Yes", "No"}, "No");
		}
		return result;
	}
}
