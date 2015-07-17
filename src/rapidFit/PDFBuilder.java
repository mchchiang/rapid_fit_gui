package rapidFit;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import rapidFit.data.*;


@SuppressWarnings("serial")
public class PDFBuilder extends JDialog implements ActionListener {

	private final int width = 1000;
	private final int height = 500;

	private PDFExpressionType pdfRootCopy;
	private PDFExpressionType pdfRoot;

	private JButton btnReplaceWithPDF;
	private JButton btnReplaceWithSum;
	private JButton btnReplaceWithProd;

	private PDFManager rootPDFManager;
	private PDFManager pdfManager;

	private PDFTreePanel pdfTreePanel;
	private JPanel pdfBuilderPanel;
	private JPanel pdfBuilderOptionPanel;

	private JRadioButton rbInspectFromPDFTree;
	private JRadioButton rbInspectFromPDFList;

	private PDFInspectorPanel pdfInspector;
	private JPanel pdfInspectorOptionPanel;
	private JPanel pdfInspectorPanel;

	private JPanel pdfViewerPanel;

	private JButton btnEditPDF;
	private DataListPanel<PDFType> pdfListPanel;

	private JButton btnBuildPDF;

	private List<PhysicsParameterType> parameters;

	public PDFBuilder (List<PhysicsParameterType> params, PDFExpressionType root, PDFManager manager){

		//set window properties
		setTitle("PDF Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));

		pdfRoot = root;
		parameters = params;

		/*
		 * create a deep copy of the PDF root. All edits on the PDFs
		 * and the PDF expression are done on the copy. The actual PDF
		 * expression only gets updated when user presses the save button.
		 */
		pdfRootCopy = (PDFExpressionType) Cloner.deepClone(root);

		//create a list of tag names for the pdfs
		rootPDFManager = manager;
		pdfManager = new PDFManager(pdfRootCopy);

		initListPanel();
		initViewerPanel();
		initMainPanel();

		//for warning before closing the window
		final PDFBuilder thisPanel = this;
		thisPanel.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				int result = JOptionPane.showOptionDialog(thisPanel, 
						"Are you sure to close this window without saving?\n "
								+ "All edits on the PDFs will be lost.", "Really Closing?", 
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
								new String [] {"Yes", "No"}, "No");
				if (result == JOptionPane.YES_OPTION){
					dispose();
				} else {
					setVisible(true);
				}
			}
		});

		pack();
	}

	public void initListPanel(){
		//create the PDF List Panel
		btnEditPDF = new JButton("Edit");
		ArrayList<JButton> buttons = new ArrayList<JButton>();
		buttons.add(btnEditPDF);
		ArrayList<PDFType> pdfList = pdfManager.getListOfPDFs();
		pdfListPanel = new DataListPanel<PDFType>(
				PDFType.class, pdfList, pdfList, pdfManager, buttons){

			protected void mouseClickedOnListEntry(PDFType pdf, int clickCount){
				if (clickCount == 1 && rbInspectFromPDFList.isSelected()){
					updatePDFInspector(pdf);
				} else if (clickCount == 2){
					new PDFEditor(pdf, pdfManager).setVisible(true);
					pdfListPanel.validate();
				}
			}
			protected void addButtonClicked(PDFType pdf){
				new PDFEditor(pdf, pdfManager).setVisible(true);
				pdfListPanel.validate();
			}
			protected void copyButtonClicked(PDFType pdf){
				new PDFEditor(pdf, pdfManager).setVisible(true);
				pdfListPanel.validate();
			}
			protected void otherButtonsClicked(PDFType pdf, Object source){
				if (source == btnEditPDF){
					new PDFEditor(pdf, pdfManager).setVisible(true);
					pdfListPanel.validate();
				}
			}
		};

		pdfListPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Available PDFs</h3></html>"));
	}

	public void initViewerPanel(){
		pdfTreePanel = new PDFTreePanel(pdfRootCopy, pdfManager.getNameMap()){
			public void treeElementSelected(Object obj){
				if (rbInspectFromPDFTree.isSelected()){
					updatePDFInspector(obj);
				}
			}
		};

		btnReplaceWithPDF = new JButton("Replace with Selected PDF");
		btnReplaceWithPDF.addActionListener(this);

		btnReplaceWithSum = new JButton("Replace with Sum");
		btnReplaceWithSum.addActionListener(this);

		btnReplaceWithProd = new JButton("Replace with Product");
		btnReplaceWithProd.addActionListener(this);

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

		pdfInspector = new PDFInspectorPanel();

		rbInspectFromPDFTree = new JRadioButton("Inspect From PDF Expression");
		rbInspectFromPDFTree.setSelected(true);
		rbInspectFromPDFTree.addActionListener(this);

		rbInspectFromPDFList = new JRadioButton("Inspect From PDF List");
		rbInspectFromPDFList.setSelected(false);
		rbInspectFromPDFList.addActionListener(this);

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
	}

	public void initMainPanel(){
		btnBuildPDF = new JButton("Save and Build PDF");
		btnBuildPDF.addActionListener(this);

		Container content = this.getContentPane();
		content.add(pdfListPanel, BorderLayout.WEST);
		content.add(pdfViewerPanel, BorderLayout.CENTER);
		content.add(btnBuildPDF, BorderLayout.SOUTH);
	}

	public void updatePDFInspector(Object pdf){
		pdfInspectorPanel.remove(pdfInspector);
		pdfInspector = new PDFInspectorPanel(pdf, pdfManager);
		pdfInspectorPanel.add(pdfInspector, BorderLayout.CENTER);
		pdfInspectorPanel.validate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PDFTree pdfTree = pdfTreePanel.getPDFTree();
		PDFTreeModel pdfTreeModel = pdfTreePanel.getPDFTreeModel();
		DataList<PDFType> pdfList = pdfListPanel.getDataList();

		if (e.getSource() == btnReplaceWithPDF){
			/*
			 * ensure that a PDF is selected from t
			 */
			if (pdfList.getSelectedIndex() != -1 &&
					pdfTree.getSelectionPath() != null){
				pdfTreeModel.replace(
						pdfTree.getSelectionPath(), 
						pdfList.getSelectedValue());
			}

			/*
			 * for replacing a selected PDF / composite PDF in the PDF tree
			 * with a new PDF sum
			 */
		} else if (e.getSource() == btnReplaceWithSum){
			if (pdfTree.getSelectionPath() != null){
				new PDFSumDialog(parameters, 
						pdfManager.getTagNameAsKeyMap(), pdfTree).setVisible(true);
			}

			/*
			 * for replacing a selected PDF / composite PDF in the PDF tree
			 * with a new PDF product
			 */	
		} else if (e.getSource() == btnReplaceWithProd){
			if (pdfTree.getSelectionPath() != null){
				new PDFProdDialog(
						pdfManager.getTagNameAsKeyMap(), pdfTree).setVisible(true);
			}

		} else if (e.getSource() == rbInspectFromPDFList){
			if (rbInspectFromPDFList.isSelected()){
				rbInspectFromPDFTree.setSelected(false);
			} 

		} else if (e.getSource() == rbInspectFromPDFTree){
			if (rbInspectFromPDFTree.isSelected()){
				rbInspectFromPDFList.setSelected(false);
			}


		} else if (e.getSource() == btnBuildPDF){
			pdfRoot.setNormalisedSumPDF(pdfRootCopy.getNormalisedSumPDF());
			pdfRoot.setProdPDF(pdfRootCopy.getProdPDF());
			pdfRoot.setPDF(pdfRootCopy.getPDF());
			rootPDFManager.setNameMap(pdfManager.getNameMap());
			dispose();
		}
	}
}