package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import rapidFit.rpfit.*;

public class PDFSumDialog extends JDialog implements ActionListener {
	
	private HashMap<String, PDFType> pdfMap;
	private JTree pdfTree; 
	private JComboBox<String> cbOperand1;
	private JComboBox<String> cbOperand2;
	private JLabel lblSumPDF;
	private JLabel lblAddOperator;
	private JLabel lblInstruction;
	private JPanel expressionPanel;
	private JPanel instructionPanel;
	private JButton btnConfirm;
	
	public PDFSumDialog(HashMap<String, PDFType> map, JTree tree){
		setTitle("PDF Sum Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pdfMap = map;
		pdfTree = tree;
		
		cbOperand1 = new JComboBox<String>(
				pdfMap.keySet().toArray(new String [pdfMap.size()]));
		cbOperand2 = new JComboBox<String>(
				pdfMap.keySet().toArray(new String [pdfMap.size()]));
		
		lblSumPDF = new JLabel("<html><b>PDF Sum = </b></html>");
		lblAddOperator = new JLabel("<html><h2>+</h2></html>");
		
		expressionPanel = new JPanel();
		expressionPanel.add(lblSumPDF);
		expressionPanel.add(cbOperand1);
		expressionPanel.add(lblAddOperator);
		expressionPanel.add(cbOperand2);
		
		lblInstruction = new JLabel(
				"<html>"
				+ "<body>"
				+ "<h3>Create a new PDF sum</h3>"
				+ "<p>Select two PDFs to build the PDF sum:</p>"
				+ "</body>"
				+ "</html>");
		
		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BorderLayout());
		instructionPanel.add(lblInstruction, BorderLayout.WEST);
		instructionPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(this);
		
		Container content = getContentPane();
		content.add(instructionPanel, BorderLayout.NORTH);
		content.add(expressionPanel, BorderLayout.CENTER);
		content.add(btnConfirm, BorderLayout.SOUTH);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnConfirm){
			//create a new sum pdf and modify the tree
			SumPDFType sum = new SumPDFType();
			sum.getProdPDFOrNormalisedSumPDFOrPDF().add(pdfMap.get(cbOperand1.getSelectedItem()));
			sum.getProdPDFOrNormalisedSumPDFOrPDF().add(pdfMap.get(cbOperand2.getSelectedItem()));
			((PDFTreeModel) pdfTree.getModel()).replace(pdfTree.getSelectionPath(), sum);
			dispose();
		}
	}
}
