package rapidFit.view.blocks;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

import javax.swing.*;

import rapidFit.model.*;

@SuppressWarnings("serial")
public class PDFSumDialog extends JDialog implements ActionListener {
	
	private HashMap<String, PDFType> pdfMap;
	private JTree pdfTree; 
	private JComboBox<String> cbOperand1;
	private JComboBox<String> cbOperand2;
	private JLabel lblSumPDF;
	private JLabel lblAddOperator;
	private JLabel lblInstruction;
	
	private JLabel lblFractionName;
	private JComboBox<String> cbFractionName;
	
	private JPanel expressionPanel;
	private JPanel fractionNamePanel;
	private JPanel mainPanel;
	
	private JButton btnConfirm;
	
	public PDFSumDialog(List<PhysicsParameterType> params, HashMap<String, PDFType> map, JTree tree){
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
		
		lblFractionName = new JLabel("<html><b>Fraction Name: </b></html>");
		
		//create a list of parameters name
		String [] paramNames = new String [params.size()];
		for (int i = 0; i < params.size(); i++){
			paramNames[i] = params.get(i).getName();
		}
		cbFractionName = new JComboBox<String>(paramNames);
		
		fractionNamePanel = new JPanel();
		fractionNamePanel.add(lblFractionName);
		fractionNamePanel.add(cbFractionName);
		
		lblInstruction = new JLabel(
				"<html>"
				+ "<body>"
				+ "<p>&nbsp Select two PDFs to build the PDF sum:</p>"
				+ "</body>"
				+ "</html>");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(lblInstruction, BorderLayout.NORTH);
		mainPanel.add(expressionPanel, BorderLayout.CENTER);
		mainPanel.add(fractionNamePanel, BorderLayout.SOUTH);
		mainPanel.setBorder(BorderFactory.createTitledBorder("<html><h3>Create a new PDF sum</h3></html>"));
		
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
			SumPDFType sum = new SumPDFType();
			sum.getProdPDFOrNormalisedSumPDFOrPDF().add(pdfMap.get(cbOperand1.getSelectedItem()));
			sum.getProdPDFOrNormalisedSumPDFOrPDF().add(pdfMap.get(cbOperand2.getSelectedItem()));
			sum.setFractionName((String) cbFractionName.getSelectedItem());
			((PDFTreeModel) pdfTree.getModel()).replace(pdfTree.getSelectionPath(), sum);
			dispose();
		}
	}
}
