package rapidFit.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import rapidFit.controller.PDFBuilderController;

@SuppressWarnings("serial")
public class PDFBuilderMenuBar extends JMenuBar {
	private JMenu mnuEdit;
	private JMenuItem mnuUndo;
	private JMenuItem mnuRedo;
	private PDFBuilderController mainController;
	
	public PDFBuilderMenuBar(PDFBuilderController controller){
		mainController = controller;
		
		mnuUndo = new JMenuItem("Undo");
		mnuUndo.setEnabled(false);
		mnuUndo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		mnuUndo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.undo();
			}
		});
		
		
		mnuRedo = new JMenuItem("Redo");
		mnuRedo.setEnabled(false);
		mnuRedo.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()| 
		        InputEvent.SHIFT_DOWN_MASK));
		mnuRedo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.redo();
			}
		});
		
		mnuEdit = new JMenu("Edit");
		mnuEdit.add(mnuUndo);
		mnuEdit.add(mnuRedo);
		this.add(mnuEdit);
	}
	
	public JMenuItem getUndoButton(){return mnuUndo;}
	public JMenuItem getRedoButton(){return mnuRedo;}
}
