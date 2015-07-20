package rapidFit.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import rapidFit.controller.IListPanelController;
import rapidFit.controller.ITreePanelController;
import rapidFit.controller.PDFBuilderController;
import rapidFit.data.PDFType;

@SuppressWarnings("serial")
public class PDFBuilderFrame extends JDialog {

	private final int width = 1000;
	private final int height = 500;
	
	private	PDFBuilderController mainController;
	private ITreePanelController pdfTreeController;
	private IListPanelController<PDFType> pdfListController;
	
	private JButton btnReplaceWithPDF;
	private JButton btnReplaceWithSum;
	private JButton btnReplaceWithProd;

	private JRadioButton rbInspectFromPDFTree;
	private JRadioButton rbInspectFromPDFList;

	private JComponent pdfTreePanel;
	private JPanel pdfBuilderPanel;
	private JPanel pdfBuilderOptionPanel;

	private PDFInspector pdfInspector;
	private JPanel pdfInspectorOptionPanel;
	private JPanel pdfInspectorPanel;
	
	private JPanel pdfViewerPanel;

	private JComponent pdfListPanel;
	
	private JButton btnBuildPDF;

	public PDFBuilderFrame(PDFBuilderController controller, 
			IListPanelController<PDFType> listPanelController,
			ITreePanelController treePanelController){

		//set window properties
		setTitle("PDF Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));
		
		this.mainController = controller;
		this.pdfListController = listPanelController;
		this.pdfTreeController = treePanelController;
		
		initView();
		
		//for warning before closing the window
		final PDFBuilderFrame thisFrame = this;
		thisFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int result = JOptionPane.showOptionDialog(thisFrame, 
						"Are you sure to close this window without saving?\n "
								+ "All edits on the PDFs will be lost.", "Really Closing?", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.YES_OPTION){
					
				} else {
					setVisible(true);
				}
			}
		});
		
		pack();
	}
	
	public void initView(){
		pdfListPanel = pdfListController.getView();
		pdfTreePanel = pdfTreeController.getView();
		
		btnReplaceWithPDF = new JButton("Replace with Selected PDF");
		btnReplaceWithPDF.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.replaceWithPDF();
			}
			
		});

		btnReplaceWithSum = new JButton("Replace with Sum");
		btnReplaceWithSum.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.replaceWithPDFSum();
			}
			
		});

		btnReplaceWithProd = new JButton("Replace with Product");
		btnReplaceWithProd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.replaceWithPDFProduct();
			}
			
		});

		pdfBuilderOptionPanel = new JPanel();
		pdfBuilderOptionPanel.setLayout(new GridLayout(3,1));
		pdfBuilderOptionPanel.add(btnReplaceWithPDF);
		pdfBuilderOptionPanel.add(btnReplaceWithSum);
		pdfBuilderOptionPanel.add(btnReplaceWithProd);

		pdfBuilderPanel = new JPanel();
		pdfBuilderPanel.setLayout(new BorderLayout());
		pdfBuilderPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Expression</h3></html>"));
		pdfBuilderPanel.add(pdfTreePanel, BorderLayout.CENTER);
		pdfBuilderPanel.add(pdfBuilderOptionPanel, BorderLayout.SOUTH);

		pdfInspector = new PDFInspector();

		rbInspectFromPDFTree = new JRadioButton("Inspect From PDF Expression");
		rbInspectFromPDFTree.setSelected(true);
		rbInspectFromPDFTree.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rbInspectFromPDFTree.isSelected()){
					rbInspectFromPDFList.setSelected(false);
					mainController.listenToTreeSelection();
				}
			}
			
		});

		rbInspectFromPDFList = new JRadioButton("Inspect From PDF List");
		rbInspectFromPDFList.setSelected(false);
		rbInspectFromPDFList.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if (rbInspectFromPDFList.isSelected()){
					rbInspectFromPDFTree.setSelected(false);
					mainController.listenToListSelection();
				}
			}
			
		});

		pdfInspectorOptionPanel = new JPanel();
		pdfInspectorOptionPanel.setLayout(new GridLayout(2,1));
		pdfInspectorOptionPanel.add(rbInspectFromPDFList);
		pdfInspectorOptionPanel.add(rbInspectFromPDFTree);

		pdfInspectorPanel = new JPanel();
		pdfInspectorPanel.setLayout(new BorderLayout());
		pdfInspectorPanel.add(pdfInspector, BorderLayout.CENTER);
		pdfInspectorPanel.add(pdfInspectorOptionPanel, BorderLayout.SOUTH);
		pdfInspectorPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Inspector</h3></html>"));

		pdfViewerPanel = new JPanel();
		pdfViewerPanel.setLayout(new GridLayout(1,2));
		pdfViewerPanel.add(pdfBuilderPanel);
		pdfViewerPanel.add(pdfInspectorPanel);
		
		//initialise main panel
		btnBuildPDF = new JButton("Save and Build PDF");

		Container content = this.getContentPane();
		content.add(pdfListPanel, BorderLayout.WEST);
		content.add(pdfViewerPanel, BorderLayout.CENTER);
		content.add(btnBuildPDF, BorderLayout.SOUTH);
	}
}
