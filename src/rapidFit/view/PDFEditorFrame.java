package rapidFit.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import rapidFit.controller.IAttributeTableController;
import rapidFit.controller.IDataTableController;
import rapidFit.controller.ITagNamePanelController;
import rapidFit.controller.PDFEditorController;
import rapidFit.data.PDFType;

@SuppressWarnings("serial")
public class PDFEditorFrame extends JDialog {
	
	private final int width = 700;
	private final int height = 550;
	
	private PDFEditorController mainController;
	
	private JPanel pdfPanel;
	
	private JPanel configAndParamSubPanel;
	private JPanel mainPanel;
	
	private JButton btnSave;
	
	private EditMenu editMenu;
	
	public PDFEditorFrame(PDFEditorController controller, 
			IAttributeTableController<PDFType> pdfAttrController,
			ITagNamePanelController tagNameController,
			IDataTableController<String> configParamController,
			IDataTableController<String> paramSubController){
		super((Dialog) controller.getWindow(), true);
		
		this.mainController = controller;
		
		//set window properties
		setTitle("PDF Editor");
		setResizable(true);
		setModal(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));
		
		//create the menu bar
		editMenu = new EditMenu(controller);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(editMenu);
		setJMenuBar(menuBar);
		
		JComponent pdfDetailsPanel = pdfAttrController.getView();
		JComponent pdfTagNamePanel = tagNameController.getView();
		
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(pdfTagNamePanel, BorderLayout.NORTH);
		pdfPanel.add(pdfDetailsPanel, BorderLayout.CENTER);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Details</html></h3>"));
		
		JComponent configTablePanel = configParamController.getView();
		configTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Configuration Parameters</html></h3>"));
		
		JComponent paramSubTablePanel = paramSubController.getView();
		paramSubTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Parameter Substitutions</html></h3>"));
		
		configAndParamSubPanel = new JPanel();
		configAndParamSubPanel.setLayout(new GridLayout(2,1));		
		configAndParamSubPanel.add(configTablePanel);
		configAndParamSubPanel.add(paramSubTablePanel);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		mainPanel.add(pdfPanel);
		mainPanel.add(configAndParamSubPanel);
		
		btnSave = new JButton("Save Edit and Close");
		btnSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.quitPDFEditor();
			}
		});
		
		Container content = getContentPane();
		content.add(mainPanel, BorderLayout.CENTER);
		content.add(btnSave, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				
				dispose();
			}
		});
		pack();
	}
	
	public void enableUndoButton(boolean b){
		editMenu.enableUndoButton(b);
	}
	
	public void enableRedoButton(boolean b){
		editMenu.enableRedoButton(b);
	}
}
