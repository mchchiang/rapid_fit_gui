package rapidFit.view;

import java.awt.Dialog;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rapidFit.controller.PDFProdDialogController;

@SuppressWarnings("serial")
public class PDFProdDialog extends JDialog {
	
	private PDFProdDialogController mainController;
	
	private boolean createNewProductPDF;
	
	private JComboBox<String> cbLeftOperand;
	private JComboBox<String> cbRightOperand;
	private JLabel lblProdPDF;
	private JLabel lblMultiplyOperator;
	private JLabel lblInstruction;
	
	private JPanel expressionPanel;
	private JPanel mainPanel;
	
	private JButton btnConfirm;
	
	public PDFProdDialog(PDFProdDialogController controller,
			List<String> pdfTagNames, String leftOperand, String rightOperand,
			boolean enableLeftOperand, boolean enableRightOperand){
		super((Dialog) controller.getWindow(), true);
		
		mainController = controller;
		
		//set window properties
		setTitle("Product PDF Editor");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		if (enableLeftOperand){
			cbLeftOperand = new JComboBox<String>(pdfTagNames.toArray(
					new String [pdfTagNames.size()]));
			if (leftOperand != null){
				cbLeftOperand.setSelectedItem(leftOperand);
			} else {
				cbLeftOperand.setSelectedIndex(0);
				mainController.setLeftOperand(cbLeftOperand.getItemAt(0));
			}
		} else {
			cbLeftOperand = new JComboBox<String>(new String [] {leftOperand});
		}
		
		cbLeftOperand.setEnabled(enableLeftOperand);
		cbLeftOperand.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setLeftOperand(
						(String) cbLeftOperand.getSelectedItem()); 
			}
		});
		
		if (enableRightOperand){
			cbRightOperand = new JComboBox<String>(pdfTagNames.toArray(
					new String [pdfTagNames.size()]));
			if (rightOperand != null){
				cbRightOperand.setSelectedItem(rightOperand);
			} else {
				cbRightOperand.setSelectedIndex(0);
				mainController.setRightOperand(cbRightOperand.getItemAt(0));
			}
		} else {
			cbRightOperand = new JComboBox<String>(new String [] {rightOperand});
		}
		
		cbRightOperand.setEnabled(enableRightOperand);
		cbRightOperand.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setRightOperand(
						(String) cbRightOperand.getSelectedItem()); 
			}
		});
		
		lblProdPDF = new JLabel("<html><b>PDF Product = </b></html>");
		lblMultiplyOperator = new JLabel("<html><h2>x</h2></html>");
		
		expressionPanel = new JPanel();
		expressionPanel.add(lblProdPDF);
		expressionPanel.add(cbLeftOperand);
		expressionPanel.add(lblMultiplyOperator);
		expressionPanel.add(cbRightOperand);
		
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
		mainPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Create a new PDF product</h3></html>"));
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.quitPDFProdDialog();	
				dispose();
			}
		});
		
		Container content = getContentPane();
		content.add(mainPanel, BorderLayout.CENTER);
		content.add(btnConfirm, BorderLayout.SOUTH);
		pack();
	}
}