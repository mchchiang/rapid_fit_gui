package rapidFit;

import javax.swing.*;
import javax.swing.filechooser.*;

import rapidFit.controller.MainController;

import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;

@SuppressWarnings("serial")
public class RapidFitEditorMenuBar extends JMenuBar implements ActionListener {
	private JMenu mnuFile;
	private JMenu mnuHelp;
	private JMenu mnuEdit;
	
	private JMenuItem mnuAbout;
	
	private JMenuItem mnuImport;
	private JMenuItem mnuSave;
	private JMenuItem mnuExport;
	private JMenuItem mnuNew;
	
	private JMenuItem mnuUndo;
	private JMenuItem mnuRedo;
	
	private JFileChooser fc;
	
	private static RapidFitEditorMenuBar menuBar = null;
	
	private MainController controller;
	
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
	
	public void setMainController(MainController controller){
		this.controller = controller;
	}
	
	private void initMenuBar(){
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
		
		mnuFile = new JMenu("File");
		mnuFile.add(mnuImport);
		mnuFile.add(mnuSave);
		mnuFile.add(mnuExport);
		mnuFile.add(mnuNew);
		
		mnuUndo = new JMenuItem("Undo");
		mnuUndo.addActionListener(this);
		mnuUndo.setEnabled(false);
		mnuUndo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, 
		        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		mnuRedo = new JMenuItem("Redo");
		mnuRedo.addActionListener(this);
		mnuRedo.setEnabled(false);
		mnuRedo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, 
		        Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()| InputEvent.SHIFT_DOWN_MASK));
		
		mnuEdit = new JMenu("Edit");
		mnuEdit.add(mnuUndo);
		mnuEdit.add(mnuRedo);
		
		mnuAbout = new JMenuItem("About");
		mnuAbout.addActionListener(this);
		mnuHelp = new JMenu("Help");
		mnuHelp.add(mnuAbout);

		this.add(mnuFile);
		this.add(mnuEdit);
		this.add(mnuHelp);
	}
	
	public JMenuItem getSaveButton(){return mnuSave;}
	public JMenuItem getSaveAsButton(){return mnuExport;}
	public JMenuItem getOpenButton(){return mnuImport;}
	public JMenuItem getUndoButton(){return mnuUndo;}
	public JMenuItem getRedoButton(){return mnuRedo;}
	
	public void actionPerformed(ActionEvent e) {
		//stop the active table editing before any menu item action is invoked
		if (RapidFitMainControl.getInstance().getCurrentEditingTable() != null){
			RapidFitMainControl.getInstance().
			getCurrentEditingTable().getCellEditor().stopCellEditing();
		}
		
		if (e.getSource() == mnuImport) {
			//check if there is unsaved edits
			if (unsaveEditCheck() == JOptionPane.YES_OPTION){
				int returnVal = fc.showOpenDialog(this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					
					//read xml
					try {
						RapidFitMainControl.getInstance().setRoot(
								RapidFitFactory.createFitFromFile(file.getAbsolutePath(), 
								"/Users/MichaelChiang/Dropbox/Edinburgh/Courses/Year 2/Summer_Project/"
								+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd"));
						String fileName = file.getName();
						RapidFitMainControl.getInstance().setFile(file);
						mnuSave.setEnabled(true);
						mnuExport.setEnabled(true);
						RapidFitEditor.getInstance().showFit(
								RapidFitMainControl.getInstance().getRoot(), fileName);
					} catch (XMLIOException xe){
						RapidFitExceptionHandler.handles(xe);
					}
				}
			}
						
		} else if (e.getSource() == mnuExport){
			int returnVal = fc.showSaveDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				
				XMLIO.writeFile(RapidFitMainControl.getInstance().getRoot(), 
						file.getAbsolutePath(), "/Users/MichaelChiang/Dropbox/Edinburgh"
						+ "/Courses/Year 2/Summer_Project/"
						+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd");
				
				RapidFitMainControl.getInstance().setFile(file);
				RapidFitMainControl.getInstance().setUnsavedEdits(false);
			}
		
		} else if (e.getSource() == mnuSave){			
			//write the file
			XMLIO.writeFile(RapidFitMainControl.getInstance().getRoot(), 
					RapidFitMainControl.getInstance().getFile().getAbsolutePath(), 
					"/Users/MichaelChiang/Dropbox/Edinburgh"
						+ "/Courses/Year 2/Summer_Project/"
						+ "rapid_fit_gui/src/rapidFit/RapidFit.xsd");
			
			RapidFitMainControl.getInstance().setUnsavedEdits(false);
			
		} else if (e.getSource() == mnuNew) {
			//check for unsaved edits
			if (unsaveEditCheck() == JOptionPane.YES_OPTION){
				RapidFitEditor.getInstance().showFit(RapidFitFactory.createEmptyFit(), "New Fit");
				mnuExport.setEnabled(true);
			}

		} else if (e.getSource() == mnuAbout){
			new AboutDialog().setVisible(true);
		
		} else if (e.getSource() == mnuUndo){
			controller.undo();
			
		} else if (e.getSource() == mnuRedo){
			controller.redo();
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
