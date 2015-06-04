package rapidFit;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

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
	//private JTree pdfTree;
	private PDFTreeModel treeModel;
	
	private DataList<PDFType> pdfList;
	private DataListModel<PDFType> listModel;
	
	private ArrayList<PDFType> listOfPDFs;
	
	private List<PhysicsParameterType> parameters;
	
	private IdentityHashMap<PDFType, String> pdfNameMap;
	private HashMap<String, Integer> nameCount;
	
	private class PDFTreeCellRenderer extends DefaultTreeCellRenderer{
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, 
				boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			String name = "";
			
		    if (value instanceof SumPDFType){
		    	name = "<html><b>Sum PDF (" + ((SumPDFType) value).getFractionName()  + ") </b></html>";
		    } else if (value instanceof ProdPDFType){
		    	name = "<html><b>Product PDF</b></html>";
		    } else if (value instanceof PDFType){
		    	name = ((PDFType) value).getName();
		    }
		    
		    return super.getTreeCellRendererComponent(
		    		tree, name, sel, expanded, leaf, row, hasFocus);
		}
	}
	
	//pass in a deep copy of the pdf root
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
		 * and the PDF expression is done on the copy. The actual PDF
		 * expression only gets updated when user press the save button.
		 */
		copyOfPDFRoot = (PDFExpressionType) Cloner.deepClone(root);
		
		//create a list of tag names for the pdfs
		pdfNameMap = new IdentityHashMap<PDFType, String>();
		nameCount = new HashMap<String, Integer>();
		
		if (copyOfPDFRoot.getPDF() != null){
			getPDFs(copyOfPDFRoot.getPDF());
		} else if (copyOfPDFRoot.getNormalisedSumPDF() != null){
			getPDFs(copyOfPDFRoot.getNormalisedSumPDF());
		} else if (copyOfPDFRoot.getProdPDF() != null){
			getPDFs(copyOfPDFRoot.getProdPDF());
		}
		
		//create a data list for displaying the available PDFs
		List<PDFType> listOfPDFs = new ArrayList<PDFType>();
		listOfPDFs.addAll(pdfNameMap.keySet());
		listModel = new DataListModel<PDFType>(PDFType.class, listOfPDFs);
		
		pdfList = new DataList<PDFType>(listModel, pdfNameMap);
		
		pdfList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					int index = pdfList.locationToIndex(e.getPoint());
					try{
						
						//do something
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
		//pdfOptionPanel.add(btnRemovePDF);
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
		
		//create the pdf tree
		treeModel = new PDFTreeModel(copyOfPDFRoot);
		pdfTree = new PDFTree(treeModel, pdfNameMap);
		
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
	
	//recursive method for finding all the PDFs
	public void getPDFs(Object node){
		if (node instanceof SumPDFType){
			getPDFs(((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof ProdPDFType){
			getPDFs(((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof PDFType){
			updatePDFName((PDFType) node);
		}
	}
	
	public void updatePDFName(PDFType pdf){
		String name = pdf.getName();
		/*
		 * if the PDF has a name that is the same as another PDF,
		 * put a index count after the name
		 */
		if (nameCount.containsKey(name)){
			nameCount.put(name, nameCount.get(name)+1);
			pdfNameMap.put(pdf, name + "_" + nameCount.get(name));
			
		} else {
			nameCount.put(name, 0);
			pdfNameMap.put(pdf, name);
		}
		
		
	}
	
	//get the name map with the tag name being the key
	public HashMap<String, PDFType> getInverseNameMap(){
		HashMap<String, PDFType> map = new HashMap<String, PDFType>();
		for (PDFType pdf : pdfNameMap.keySet()){
			map.put(pdfNameMap.get(pdf), pdf);
		}
		return map;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//for adding a new PDF
		if (e.getSource() == btnAddPDF){
			
			/*
			 *  add the PDF at the position below the currently 
			 *  selected PDF in the list. If no PDF is selected,
			 *  the new PDF is added at the end of the list.
			 */
			int index = pdfList.getSelectedIndex();
			
			if (index == -1){
				index += listModel.getSize();
			} else {
				index++;
			}
			listModel.addRow(index);
			
			//set the name of the new PDF
			PDFType pdf = listModel.getElementAt(index);
			pdf.setName("PDF");
			updatePDFName(pdf);
			pdfTree.updateMap(pdfNameMap);
			
			//set the selected PDF in the list to be the new PDF
			pdfList.setSelectedIndex(index);
			
			//open the PDF editor to allow user to edit the new PDF
			new PDFEditor(listModel.getElementAt(index)).setVisible(true);
			
			//only update pdf tag name if the pdf name has changed
			if (!pdf.getName().equals("PDF")){
				updatePDFName(pdf);
				pdfTree.updateMap(pdfNameMap);
			}
		
		//for removing a PDF
		} else if (e.getSource() == btnRemovePDF &&
				pdfList.getSelectedIndex() != -1){
			
			// remove the selected PDF from the list
			listModel.removeRow(pdfList.getSelectedIndex());

		//for editing a PDF
		} else if (e.getSource() == btnEditPDF && 
				pdfList.getSelectedIndex() != -1){
			PDFType pdf = listModel.getElementAt(pdfList.getSelectedIndex());
			String name = pdf.getName();
			new PDFEditor(pdf).setVisible(true);
			
			//only update PDF tag name if the PDF name has changed
			if (!pdf.getName().equals(name)){
				updatePDFName(pdf);
				pdfTree.updateMap(pdfNameMap);
			}
		
		/*
		 * for replacing a selected PDF / composite PDF in the PDF tree 
		 * with the selected PDF in the data list
		 */
		} else if (e.getSource() == btnReplaceWithPDF){
			if (pdfList.getSelectedIndex() != -1 &&
					pdfTree.getSelectionPath() != null){
				((PDFTreeModel) pdfTree.getModel()).replace(
						pdfTree.getSelectionPath(), 
						pdfList.getSelectedValue());
			}
		
		/*
		 *  for replacing a selected PDF / composite PDF in the PDF tree
		 *  with a new PDF sum
		 */
		} else if (e.getSource() == btnReplaceWithSum){
			if (pdfTree.getSelectionPath() != null){
				new PDFSumDialog(parameters, getInverseNameMap(), pdfTree).setVisible(true);
			}
			
		/*
		 *  for replacing a selected PDF / composite PDF in the PDF tree
		 *  with a new PDF product
		 */	
		} else if (e.getSource() == btnReplaceWithProd){
			if (pdfTree.getSelectionPath() != null){
				new PDFProdDialog(getInverseNameMap(), pdfTree).setVisible(true);
			}
		}
	}
}
