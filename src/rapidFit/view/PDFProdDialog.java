package rapidFit.view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import rapidFit.model.*;

@SuppressWarnings("serial")
public class PDFProdDialog extends JDialog implements ActionListener {
	
	private HashMap<String, PDFType> pdfMap;
	private JTree pdfTree; 
	private JComboBox<String> cbOperand1;
	private JComboBox<String> cbOperand2;
	private JLabel lblProdPDF;
	private JLabel lblMultiplyOperator;
	private JLabel lblInstruction;
	
	private JPanel expressionPanel;
	private JPanel mainPanel;
	
	private JButton btnConfirm;
	
	public PDFProdDialog(HashMap<String, PDFType> map, JTree tree){
		setTitle("PDF Product Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pdfMap = map;
		pdfTree = tree;
		
		cbOperand1 = new JComboBox<String>(
				pdfMap.keySet().toArray(new String [pdfMap.size()]));
		cbOperand2 = new JComboBox<String>(
				pdfMap.keySet().toArray(new String [pdfMap.size()]));
		
		lblProdPDF = new JLabel("<html><b>PDF Product = </b></html>");
		lblMultiplyOperator = new JLabel("<html><h2>x</h2></html>");
		
		expressionPanel = new JPanel();
		expressionPanel.add(lblProdPDF);
		expressionPanel.add(cbOperand1);
		expressionPanel.add(lblMultiplyOperator);
		expressionPanel.add(cbOperand2);
		
		lblInstruction = new JLabel(
				"<html>"
				+ "<body>"
				+ "<p>&nbsp Select two PDFs to build the PDF product:</p>"
				+ "</body>"
				+ "</html>");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(lblInstruction, BorderLayout.NORTH);
		mainPanel.add(expressionPanel, BorderLayout.CENTER);
		mainPanel.setBorder(BorderFactory.createTitledBorder("<html><h3>Create a new PDF product</h3></html>"));
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(this);
		
		Container content = getContentPane();
		content.add(mainPanel, BorderLayout.CENTER);
		content.add(btnConfirm, BorderLayout.SOUTH);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirm){
			//create a new sum pdf and modify the tree
			ProdPDFType product = new ProdPDFType();
			product.getProdPDFOrNormalisedSumPDFOrPDF().add(pdfMap.get(cbOperand1.getSelectedItem()));
			product.getProdPDFOrNormalisedSumPDFOrPDF().add(pdfMap.get(cbOperand2.getSelectedItem()));
			((PDFTreeModel) pdfTree.getModel()).replace(pdfTree.getSelectionPath(), product);
			dispose();
		}
	}
}
