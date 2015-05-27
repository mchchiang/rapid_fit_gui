package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFBuilder extends JDialog implements ActionListener {
	
	private JList<String> pdfList;
	private DefaultListModel<String> listModel;
	
	private JButton btnAddPDF;
	private JButton btnRemovePDF;
	private JButton btnEditPDF;
	private JButton btnBuildPDF;
	
	private JButton btnLeftBracket;
	private JButton btnRightBracket;
	private JButton btnAdd;
	private JButton btnMultiply;
	private Dimension opBtnDimension = new Dimension(55,35);
	
	private JTextPane txtPDFExpression;
	
	private JScrollPane listOfPDFScrollPane;
	private JScrollPane pdfExpressionScrollPane;
	
	private JPanel listOfPDFPanel;
	private JPanel pdfOptionPanel;
	
	private JPanel pdfBuilderPanel;
	private JPanel pdfBuilderOptionPanel;
	
	private String pdfExpression;
	
	private ExpressionStyledDocument exprStyle;
	
	private int newPDFCount = 0;
	
	private HashMap<String, PDFType> pdfs;
	private PDFOperatorType pdfTreeRoot;

	
	public PDFBuilder (ArrayList<PDFType> listOfPDFs, PDFOperatorType root, String expr){
		setTitle("PDF Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pdfTreeRoot = root;
		pdfs = new HashMap<String, PDFType>();
		//create a pdf mapping between actual object and its name
		for (PDFType pdf : listOfPDFs){
			pdfs.put(pdf.getName(), pdf);
		}
		
		pdfExpression = expr;
		
		listModel = new DefaultListModel<String>();
		pdfList = new JList<String>(listModel);
		pdfList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//add pdf to list
		for (String pdfName : pdfs.keySet()){
			listModel.addElement(pdfName);
		}
		
		pdfList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					int index = pdfList.locationToIndex(e.getPoint());
					
					try{
						txtPDFExpression.getDocument().insertString(
								txtPDFExpression.getCaretPosition(), 
								" \"" + pdfList.getModel().getElementAt(index) + "\" ", null);
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
		pdfOptionPanel.add(btnAddPDF);
		pdfOptionPanel.add(btnRemovePDF);
		pdfOptionPanel.add(btnEditPDF);
		
		listOfPDFPanel = new JPanel();
		listOfPDFPanel.setLayout(new BorderLayout());
		listOfPDFPanel.add(listOfPDFScrollPane, BorderLayout.CENTER);
		listOfPDFPanel.add(pdfOptionPanel, BorderLayout.SOUTH);
		listOfPDFPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Available PDFs</h3></html>"));
		
		//get a list of pdf names
		exprStyle = new ExpressionStyledDocument(getPDFNames());
		
		txtPDFExpression = new JTextPane(exprStyle);
		txtPDFExpression.setText(pdfExpression);
		txtPDFExpression.setEditable(true);
		txtPDFExpression.setFocusable(true);
		txtPDFExpression.setPreferredSize(new Dimension(300,200));
		
		pdfExpressionScrollPane = new JScrollPane(txtPDFExpression);
		
		btnAdd = new JButton("<html><div style=\"text-align: center;\"><b>+</b></html>");
		btnAdd.addActionListener(this);
		btnAdd.setPreferredSize(opBtnDimension);
		
		btnMultiply = new JButton("<html><div style=\"text-align: center;\"><b>x</b></html>");
		btnMultiply.addActionListener(this);
		btnMultiply.setPreferredSize(opBtnDimension);
		
		btnLeftBracket = new JButton("<html><div style=\"text-align: center;\"><b>(</b></html>");
		btnLeftBracket.addActionListener(this);
		btnLeftBracket.setPreferredSize(opBtnDimension);
		
		btnRightBracket = new JButton("<html><div style=\"text-align: center;\"><b>)</b></html>");
		btnRightBracket.addActionListener(this);
		btnRightBracket.setPreferredSize(opBtnDimension);
		
		btnBuildPDF = new JButton("Update PDF");
		btnBuildPDF.setMaximumSize(new Dimension(50,100));
		btnBuildPDF.addActionListener(this);
		
		pdfBuilderOptionPanel = new JPanel();
		pdfBuilderOptionPanel.add(btnLeftBracket);
		pdfBuilderOptionPanel.add(btnRightBracket);
		pdfBuilderOptionPanel.add(btnAdd);
		pdfBuilderOptionPanel.add(btnMultiply);
		
		pdfBuilderPanel = new JPanel();
		pdfBuilderPanel.setLayout(new BorderLayout());
		pdfBuilderPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Expression</h3></html>"));
		pdfBuilderPanel.add(pdfExpressionScrollPane, BorderLayout.CENTER);
		pdfBuilderPanel.add(pdfBuilderOptionPanel, BorderLayout.SOUTH);
		
		Container content = this.getContentPane();
		content.add(listOfPDFPanel, BorderLayout.WEST);
		content.add(pdfBuilderPanel, BorderLayout.CENTER);
		content.add(btnBuildPDF, BorderLayout.SOUTH);
		pack();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAdd){
			try{
				txtPDFExpression.getDocument().insertString(
						txtPDFExpression.getCaretPosition(), " + ", null);
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		} else if (e.getSource() == btnMultiply){
			try{
				txtPDFExpression.getDocument().insertString(
						txtPDFExpression.getCaretPosition(), " * ", null);
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		} else if (e.getSource() == btnLeftBracket){
			try{
				txtPDFExpression.getDocument().insertString(
						txtPDFExpression.getCaretPosition(), " ( ", null);
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		} else if (e.getSource() == btnRightBracket){
			try{
				txtPDFExpression.getDocument().insertString(
						txtPDFExpression.getCaretPosition(), " ) ", null);
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
		} else if (e.getSource() == btnAddPDF){
			newPDFCount++;
			
			PDFType pdf = new PDFType();
			pdf.setName("PDF_" + newPDFCount);
			pdfs.put("PDF_" + newPDFCount, pdf);
			listModel.addElement("PDF_" + newPDFCount);
			exprStyle.updatePDFNames(getPDFNames());
			
			new PDFEditor(pdf).setVisible(true);
			
			listModel.setElementAt(pdf.getName(), listModel.size()-1);
			
		} else if (e.getSource() == btnRemovePDF &&
				pdfs.get(pdfList.getSelectedValue()) != null){
			//remove the pdf from pdf expression
			String pdfName = pdfList.getSelectedValue();
			txtPDFExpression.setText(txtPDFExpression.getText().replaceAll(
					"\"" + pdfName + "\"", ""));
			pdfs.remove(pdfName);
			listModel.removeElementAt(pdfList.getSelectedIndex());
			exprStyle.updatePDFNames(getPDFNames());
			
		} else if (e.getSource() == btnEditPDF && 
				pdfs.get(pdfList.getSelectedValue()) != null){
				new PDFEditor(pdfs.get(pdfList.getSelectedValue())).setVisible(true);
			
		} else if (e.getSource() == btnBuildPDF){
			//update the pdf tree root with the new pdf
			pdfTreeRoot.getProdPDFOrNormalisedSumPDFOrPDF().add(
					PDFParser.convertToXML(txtPDFExpression.getText(), pdfs));
			pdfTreeRoot.getProdPDFOrNormalisedSumPDFOrPDF().remove(0);
			
			dispose();
		}
	}	
	
	public ArrayList<String> getPDFNames(){
		ArrayList<String> pdfNames = new ArrayList<String>();
		pdfNames.addAll(pdfs.keySet());
		return pdfNames;
	}
}
