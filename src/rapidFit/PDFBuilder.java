package rapidFit;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;

import rapidFit.rpfit.*;

public class PDFBuilder extends JDialog implements ActionListener {
	
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
	
	private JTree pdfTree;
	private PDFTreeModel treeModel;
	
	private DataList<PDFType> pdfList;
	private DataListModel<PDFType> listModel;
	
	private int newPDFCount = 0;
	private ArrayList<PDFType> listOfPDFs;
	
	private List<PhysicsParameterType> parameters;
	
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
		
		//find all pdfs
		listOfPDFs = new ArrayList<PDFType>();
		if (root.getPDF() != null){
			getPDFs(listOfPDFs, root.getPDF());
		} else if (root.getNormalisedSumPDF() != null){
			getPDFs(listOfPDFs, root.getNormalisedSumPDF());
		} else if (root.getProdPDF() != null){
			getPDFs(listOfPDFs, root.getProdPDF());
		}
		
		
		listModel = new DataListModel<PDFType>(PDFType.class, listOfPDFs);
		pdfList = new DataList<PDFType>(listModel, "Name", false);
		
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
		
		btnAddPDF = new JButton("Add PDF");
		btnAddPDF.addActionListener(this);
		btnRemovePDF = new JButton("Remove PDF");
		btnRemovePDF.addActionListener(this);
		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(this);
		
		pdfOptionPanel = new JPanel();
		pdfOptionPanel.setLayout(new GridLayout(0,3));
		pdfOptionPanel.add(btnAddPDF);
		pdfOptionPanel.add(btnRemovePDF);
		pdfOptionPanel.add(btnEditPDF);
		
		listOfPDFPanel = new JPanel();
		listOfPDFPanel.setLayout(new BorderLayout());
		listOfPDFPanel.add(listOfPDFScrollPane, BorderLayout.CENTER);
		listOfPDFPanel.add(pdfOptionPanel, BorderLayout.SOUTH);
		listOfPDFPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Available PDFs</h3></html>"));
		
		
		//create the pdf tree
		treeModel = new PDFTreeModel(pdfRoot);
		pdfTree = new JTree(treeModel);
		pdfTree.setCellRenderer(new PDFTreeCellRenderer());
		pdfTree.setEditable(false);
		
		for (int i = 0; i < pdfTree.getRowCount(); i++) {
			pdfTree.expandRow(i);
		}
		
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
	
	//recursive method for finding all the pdfs
	public void getPDFs(ArrayList<PDFType> list, Object parent){
		if (parent instanceof SumPDFType){
			getPDFs(list, ((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(list, ((SumPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
		} else if (parent instanceof ProdPDFType){
			getPDFs(list, ((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(list, ((ProdPDFType) parent).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
		} else if (parent instanceof PDFType){
			list.add((PDFType) parent);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddPDF){
			newPDFCount++;
			int index = pdfList.getSelectedIndex();
			
			if (index == -1){
				index += listModel.getSize();
			} else {
				index++;
			}
			
			listModel.addRow(index);
			listModel.getElementAt(index).setName("PDF_" + newPDFCount);
			pdfList.setSelectedIndex(index);
			
			new PDFEditor(listModel.getElementAt(index)).setVisible(true);
			
			
		} else if (e.getSource() == btnRemovePDF &&
				pdfList.getSelectedIndex() != -1){


		} else if (e.getSource() == btnEditPDF && 
				pdfList.getSelectedIndex() != -1){
			new PDFEditor(listModel.getElementAt(
					pdfList.getSelectedIndex())).setVisible(true);

		} else if (e.getSource() == btnReplaceWithPDF){
			if (pdfList.getSelectedIndex() != -1 &&
					pdfTree.getSelectionPath() != null){
				((PDFTreeModel) pdfTree.getModel()).replace(
						pdfTree.getSelectionPath(), 
						pdfList.getSelectedValue());
			}
			
		} else if (e.getSource() == btnReplaceWithSum){
			if (pdfTree.getSelectionPath() != null){
				HashMap<String, PDFType> pdfMap = new HashMap<String, PDFType>();
				for (PDFType pdf : listOfPDFs){
					pdfMap.put(pdf.getName(), pdf);
				}
				new PDFSumDialog(parameters, pdfMap, pdfTree).setVisible(true);;
			}
			
		} else if (e.getSource() == btnReplaceWithProd){
			if (pdfTree.getSelectionPath() != null){
				HashMap<String, PDFType> pdfMap = new HashMap<String, PDFType>();
				for (PDFType pdf : listOfPDFs){
					pdfMap.put(pdf.getName(), pdf);
				}
				new PDFProdDialog(pdfMap, pdfTree).setVisible(true);;
			}
		}
	}
	
}
