package rapidFit.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import rapidFit.controller.PDFSumDialogController;
import rapidFit.data.PDFType;
import rapidFit.data.PhysicsParameterType;

@SuppressWarnings("serial")
public class PDFSumDialog extends JDialog implements ActionListener {

	private PDFSumDialogController mainController;

	private JComboBox<PDFType> cbLeftOperand;
	private JComboBox<PDFType> cbRightOperand;
	private JLabel lblSumPDF;
	private JLabel lblAddOperator;
	private JLabel lblInstruction;

	private JLabel lblFractionName;
	private JComboBox<PhysicsParameterType> cbFractionName;

	private JPanel expressionPanel;
	private JPanel fractionNamePanel;
	private JPanel mainPanel;

	private JButton btnConfirm;
	
	/*
	 * customise the combo box cell renderer such that it displays
	 * the name of the physics parameter
	 */
	private class PDFComboBoxRenderer extends JLabel implements 
	ListCellRenderer<PDFType> {

		@Override
		public Component getListCellRendererComponent(
				JList<? extends PDFType> list,
				PDFType value, int index, boolean isSelected,
				boolean cellHasFocus) {
			this.setName(value.getName());			
			return this;
		}
	}

	public PDFSumDialog(PDFSumDialogController controller){
		//set windows properties
		setTitle("PDF Sum Builder");
		setModal(true);
		setResizable(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.mainController = controller;

		cbLeftOperand.setRenderer(new PDFComboBoxRenderer());
		cbLeftOperand.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setLeftOperandValue(
						(PDFType) cbLeftOperand.getSelectedItem());
			}

		});
		
		cbRightOperand.setRenderer(new PDFComboBoxRenderer());
		cbRightOperand.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				mainController.setRightOperandValue(
						(PDFType) cbRightOperand.getSelectedItem());
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

			dispose();
		}
	}
}
