package rapidFit.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rapidFit.controller.PDFSumDialogController;

@SuppressWarnings("serial")
public class PDFSumDialog extends JDialog {

	private PDFSumDialogController mainController;

	private JComboBox<String> cbLeftOperand;
	private JComboBox<String> cbRightOperand;
	private JComboBox<String> cbFractionName;
	private JLabel lblSumPDF;
	private JLabel lblAddOperator;
	private JLabel lblInstruction;

	private JLabel lblFractionName;

	private JPanel expressionPanel;
	private JPanel fractionNamePanel;
	private JPanel mainPanel;

	private JButton btnConfirm;

	public PDFSumDialog(PDFSumDialogController controller,
			List<String> pdfTagNames, String leftOperand, String rightOperand,
			boolean enableLeftOperand, boolean enableRightOperand,
			List<String> fractionNames, String fractionName){
		super((Dialog) controller.getWindow(), true);
		
		//set windows properties
		setTitle("PDF Sum Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.mainController = controller;

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

		lblSumPDF = new JLabel("<html><b>PDF Sum = </b></html>");
		lblAddOperator = new JLabel("<html><h2>+</h2></html>");

		expressionPanel = new JPanel();
		expressionPanel.add(lblSumPDF);
		expressionPanel.add(cbLeftOperand);
		expressionPanel.add(lblAddOperator);
		expressionPanel.add(cbRightOperand);

		lblFractionName = new JLabel("<html><b>Fraction Name: </b></html>");
		
		cbFractionName = new JComboBox<String>(fractionNames.toArray(
				new String [fractionNames.size()]));
		
		if (fractionName != null && fractionNames.contains(fractionName)){
			cbFractionName.setSelectedItem(fractionName);
		} else {
			cbFractionName.setSelectedIndex(0);
			mainController.setFractionName(cbFractionName.getItemAt(0));
		}
		
		cbFractionName.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setFractionName(
						(String) cbFractionName.getSelectedItem());
			}
		});

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
		mainPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Create a new PDF sum</h3></html>"));

		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.quitPDFSumDialog();				
			}
		});
		Container content = getContentPane();
		content.add(mainPanel, BorderLayout.CENTER);
		content.add(btnConfirm, BorderLayout.SOUTH);
		pack();
	}
}
