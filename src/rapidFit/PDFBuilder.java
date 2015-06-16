package rapidFit;

import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFBuilder extends JDialog implements ActionListener {
	
	private PDFExpressionType copyOfPDFRoot;
	private PDFExpressionType pdfRoot;
	
	private JButton btnAddPDF;
	private JButton btnRemovePDF;
	private JButton btnEditPDF;
	private JButton btnBuildPDF;
	
	private JButton btnReplaceWithPDF;
	private JButton btnReplaceWithSum;
	private JButton btnReplaceWithProd;
	
	private JScrollPane listOfPDFScrollPane;
	private JScrollPane pdfTreeScrollPane;
	
	private JPanel listOfPDFPanel;
	private JPanel pdfOptionPanel;
	
	private JPanel pdfBuilderPanel;
	private JPanel pdfBuilderOptionPanel;
	
	private PDFTree pdfTree;

	private PDFTreeModel pdfTreeModel;
	
	private DataList<PDFType> pdfList;
	private DataListModel<PDFType> listModel;

	private List<PhysicsParameterType> parameters;
	
	private OldPDFManager pdfManager;
	
	public PDFBuilder (List<PhysicsParameterType> params, PDFExpressionType root){
		
		//set window properties
		setTitle("PDF Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		pdfRoot = root;
		parameters = params;
		
		/*
		 * create a deep copy of the PDF root. All edits on the PDFs
		 * and the PDF expression are done on the copy. The actual PDF
		 * expression only gets updated when user presses the save button.
		 */
		copyOfPDFRoot = (PDFExpressionType) Cloner.deepClone(root);
		
		//create a list of tag names for the pdfs
		pdfManager = new OldPDFManager(copyOfPDFRoot);
		
		//create a data list for displaying the available PDFs
		listModel = new DataListModel<PDFType>(PDFType.class, pdfManager.getListOfPDFs());
		
		pdfList = new DataList<PDFType>(listModel, pdfManager.getPDFAsKeyMap());
		
		pdfList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				//allow user to edit the PDF by double-clicking it
				if (e.getClickCount() == 2){
					int index = pdfList.locationToIndex(e.getPoint());
					try{
						if (index != -1){
							new PDFEditor(listModel.getElementAt(
									pdfList.getSelectedIndex())).setVisible(true);
							
							//update tag name for PDFs
							pdfManager.updateTagName();
							pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
						}
					} catch (Exception ex){
						ex.printStackTrace();
					}
				}
			}
		});
		
		listOfPDFScrollPane = new JScrollPane(pdfList);
		
		//add the buttons for adding, removing, and editing the PDFs
		btnAddPDF = new JButton("Add PDF");
		btnAddPDF.addActionListener(this);
		btnRemovePDF = new JButton("Remove PDF");
		btnRemovePDF.addActionListener(this);
		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(this);
		
		pdfOptionPanel = new JPanel();
		pdfOptionPanel.setLayout(new GridLayout(0,3));
		pdfOptionPanel.add(btnAddPDF);
		pdfOptionPanel.add(btnEditPDF);
		
		listOfPDFPanel = new JPanel();
		listOfPDFPanel.setLayout(new BorderLayout());
		listOfPDFPanel.add(listOfPDFScrollPane, BorderLayout.CENTER);
		listOfPDFPanel.add(pdfOptionPanel, BorderLayout.SOUTH);
		listOfPDFPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Available PDFs</h3></html>"));
		
		//============================================================================
		//
		//for the PDF expression
		//
		
		//create the PDF tree
		pdfTreeModel = new PDFTreeModel(copyOfPDFRoot);
		pdfTree = new PDFTree(pdfTreeModel, pdfManager.getPDFAsKeyMap());
		
		pdfTreeScrollPane = new JScrollPane(pdfTree);
		
		btnReplaceWithPDF = new JButton("Replace with Selected PDF");
		btnReplaceWithPDF.addActionListener(this);
		btnReplaceWithSum = new JButton("Replace with Sum");
		btnReplaceWithSum.addActionListener(this);
		btnReplaceWithProd = new JButton("Replace with Product");
		btnReplaceWithProd.addActionListener(this);
		
		pdfBuilderOptionPanel = new JPanel();
		pdfBuilderOptionPanel.setLayout(new GridLayout(0,3));
		pdfBuilderOptionPanel.add(btnReplaceWithPDF);
		pdfBuilderOptionPanel.add(btnReplaceWithSum);
		pdfBuilderOptionPanel.add(btnReplaceWithProd);
		
		pdfBuilderPanel = new JPanel();
		pdfBuilderPanel.setLayout(new BorderLayout());
		pdfBuilderPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Expression</h3></html>"));
		pdfBuilderPanel.add(pdfTreeScrollPane, BorderLayout.CENTER);
		pdfBuilderPanel.add(pdfBuilderOptionPanel, BorderLayout.SOUTH);
		
		btnBuildPDF = new JButton("Save and Build PDF");
		btnBuildPDF.addActionListener(this);
		
		Container content = this.getContentPane();
		content.add(listOfPDFPanel, BorderLayout.WEST);
		content.add(pdfBuilderPanel, BorderLayout.CENTER);
		content.add(btnBuildPDF, BorderLayout.SOUTH);
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//for adding a new PDF
		if (e.getSource() == btnAddPDF){
			
			/*
			 * add the PDF at the position below the currently 
			 * selected PDF in the list. If no PDF is selected,
			 * the new PDF is added at the end of the list.
			 */
			
			int index = 0;
			
			if (listModel.getSize() == 0){
				listModel.addRow();
			} else {
				index = pdfList.getSelectedIndex();
				if (index == -1){
					index += listModel.getSize();
				} else {
					index++;
				}
				listModel.addRow(index);
			}
			
			//set the name of the new PDF
			PDFType pdf = listModel.getElementAt(index);
			pdf.setName("PDF");
			pdfManager.addPDF(pdf);
			pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
			
			//set the selected PDF in the list to be the new PDF
			pdfList.setSelectedIndex(index);
			
			//open the PDF editor to allow user to edit the new PDF
			new PDFEditor(listModel.getElementAt(index)).setVisible(true);
			
			//update tag name for PDFs
			pdfManager.updateTagName();
			pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
		
		//for removing a PDF
		} else if (e.getSource() == btnRemovePDF &&
				pdfList.getSelectedIndex() != -1){
			
			//remove the selected PDF from the list
			pdfManager.removePDF(pdfList.getSelectedValue());
			listModel.removeRow(pdfList.getSelectedIndex());

		//for editing a PDF
		} else if (e.getSource() == btnEditPDF && 
				pdfList.getSelectedIndex() != -1){
			
			new PDFEditor(listModel.getElementAt(
					pdfList.getSelectedIndex())).setVisible(true);
			
			//update tag name for PDFs
			pdfManager.updateTagName();
			pdfTree.updateMap(pdfManager.getPDFAsKeyMap());
		
		/*
		 * for replacing a selected PDF / composite PDF in the PDF tree 
		 * with the selected PDF in the data list
		 */
		} else if (e.getSource() == btnReplaceWithPDF){
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
			
		} else if (e.getSource() == btnBuildPDF){
			pdfRoot.setNormalisedSumPDF(copyOfPDFRoot.getNormalisedSumPDF());
			pdfRoot.setProdPDF(copyOfPDFRoot.getProdPDF());
			pdfRoot.setPDF(copyOfPDFRoot.getPDF());
	
			dispose();
		}
	}
}
