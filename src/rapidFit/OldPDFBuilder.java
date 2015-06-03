package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.*;

import javax.swing.*;
import javax.xml.bind.JAXBElement;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class OldPDFBuilder extends JDialog implements ActionListener {
	
	private JList<String> pdfOldList;
	private DefaultListModel<String> oldListModel;
	
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

	private DataList<PDFType> pdfList;
	private DataListModel<PDFType> listModel;
	
	public OldPDFBuilder (ArrayList<PDFType> listOfPDFs, PDFOperatorType root, String expr){
		setTitle("PDF Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pdfTreeRoot = root;
		
		listModel = new DataListModel<PDFType>(PDFType.class, listOfPDFs);
		pdfList = new DataList<PDFType>(listModel, "Name", false);
		
		//create a pdf mapping between actual object and its name
		pdfs = new HashMap<String, PDFType>();
		for (PDFType pdf : listOfPDFs){
			pdfs.put(pdf.getName(), pdf);
		}
		
		pdfExpression = expr;
		
		oldListModel = new DefaultListModel<String>();
		pdfOldList = new JList<String>(oldListModel);
		pdfOldList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//add pdf to list
		for (String pdfName : pdfs.keySet()){
			oldListModel.addElement(pdfName);
		}
		
		pdfList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					int index = pdfList.locationToIndex(e.getPoint());
					
					try{
						txtPDFExpression.getDocument().insertString(
								txtPDFExpression.getCaretPosition(), 
								" \"" + listModel.getElementAt(index).getName() + "\" ", null);
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
		
		//create the add button
		btnAdd = new JButton(
				"<html><div style=\"text-align: center;\"><b>+</b></html>");
		btnAdd.addActionListener(this);
		btnAdd.setPreferredSize(opBtnDimension);
		
		//create the multiply button
		btnMultiply = new JButton(
				"<html><div style=\"text-align: center;\"><b>x</b></html>");
		btnMultiply.addActionListener(this);
		btnMultiply.setPreferredSize(opBtnDimension);
		
		//create the left bracket button
		btnLeftBracket = new JButton(
				"<html><div style=\"text-align: center;\"><b>(</b></html>");
		btnLeftBracket.addActionListener(this);
		btnLeftBracket.setPreferredSize(opBtnDimension);
		
		//create the right bracket button
		btnRightBracket = new JButton(
				"<html><div style=\"text-align: center;\"><b>)</b></html>");
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
			
			exprStyle.updatePDFNames(getPDFNames());
			
		} else if (e.getSource() == btnRemovePDF &&
				pdfs.get(pdfOldList.getSelectedValue()) != null){
			//remove the pdf from pdf expression
			String pdfName = pdfOldList.getSelectedValue();
			txtPDFExpression.setText(txtPDFExpression.getText().replaceAll(
					"\"" + pdfName + "\"", ""));
			pdfs.remove(pdfName);
			oldListModel.removeElementAt(pdfOldList.getSelectedIndex());
			exprStyle.updatePDFNames(getPDFNames());
			
		} else if (e.getSource() == btnEditPDF && 
				pdfList.getSelectedIndex() != -1){
				new PDFEditor(listModel.getElementAt(
						pdfList.getSelectedIndex())).setVisible(true);
			
		} else if (e.getSource() == btnBuildPDF){
			//update the pdf tree root with the new pdf
			pdfTreeRoot.getProdPDFOrNormalisedSumPDFOrPDF().add(
					(JAXBElement<? extends Serializable>) PDFParser.convertToXML(txtPDFExpression.getText(), pdfs));
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
