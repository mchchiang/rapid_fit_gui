package rapidFit.view;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import rapidFit.controller.UIController;

@SuppressWarnings("serial")
public class EditMenu extends JMenu {
	
	private UIController mainController;
	
	private JMenuItem mnuUndo;
	private JMenuItem mnuRedo;
	
	public EditMenu(UIController controller){
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

		this.add(mnuUndo);
		this.add(mnuRedo);
		this.setText("Edit");
	}

	public void enableUndoButton(boolean b){
		mnuUndo.setEnabled(b);
	}
	
	public void enableRedoButton(boolean b){
		mnuRedo.setEnabled(b);
	}
}
