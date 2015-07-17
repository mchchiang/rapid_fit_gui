package rapidFit.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import rapidFit.controller.UIController;
import rapidFit.controller.command.OpenFileCommand;


@SuppressWarnings("serial")
public class RapidFitMenuBar extends JMenuBar {
	private JMenu mnuFile;
	private JMenu mnuHelp;
	private JMenu mnuEdit;
	
	private JMenuItem mnuAbout;
	
	private JMenuItem mnuSave;
	private JMenuItem mnuSaveAs;
	private JMenuItem mnuOpen;
	private JMenuItem mnuNew;
	private JMenuItem mnuClose;
	private JMenuItem mnuSettings;
	private JMenuItem mnuQuit;
	
	private JMenuItem mnuUndo;
	private JMenuItem mnuRedo;
	
	private JFileChooser fc;
	
	private UIController controller;
	
	public void initMenuBar() {
		mnuOpen = new JMenuItem("Open XML File...");
		mnuOpen.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mnuOpen.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.setCommand(new OpenFileCommand());
			}
			
		});
		
		mnuSave = new JMenuItem("Save");
		mnuSave.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		mnuSaveAs = new JMenuItem("Save As New XML File...");
		mnuSaveAs.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() | 
		        InputEvent.ALT_DOWN_MASK));
		
		mnuNew = new JMenuItem("New XML File");
		mnuNew.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		mnuClose = new JMenuItem("Close File");
		mnuClose.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		mnuQuit = new JMenuItem("Quit");
		mnuQuit.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		mnuUndo = new JMenuItem("Undo");
		mnuUndo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		
		mnuRedo = new JMenuItem("Redo");
		mnuRedo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()| 
		        InputEvent.SHIFT_DOWN_MASK));
		
	}
	
	public JMenuItem getSaveButton() {return mnuSave;}
	public JMenuItem getOpenButton() {return mnuOpen;}
	public JMenuItem getUndoButton() {return mnuUndo;}
	public JMenuItem getRedoButton() {return mnuRedo;}
	
}
