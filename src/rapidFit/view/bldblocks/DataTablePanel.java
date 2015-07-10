package rapidFit.view.bldblocks;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import rapidFit.RowNumberTable;
import rapidFit.controller.IDataTableController;

@SuppressWarnings("serial")
public class DataTablePanel extends JPanel implements ActionListener {
	
	//variables for the table panel
	private DataTable table;
	private DataTableViewModel viewModel;
	private JScrollPane scrollPane;
	private JTable rowTable; //make row numbers next to table

	private JPanel tablePanel;

	//======================================================
	//
	//variables for the option panel

	//buttons to add, remove, and copy entry
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnCopy;

	//default names for the buttons
	private String btnAddName = "Add";
	private String btnRemoveName = "Remove";
	private String btnCopyName = "Copy";

	//container for all the buttons 
	protected JPanel controlPanel;
		
	private IDataTableController controller;
	
	public DataTablePanel (IDataTableController controller, DataTableViewModel viewModel,
			String btnAddName, String btnRemoveName, String btnCopyName){
		
		this.controller = controller;
		this.viewModel = viewModel;
		
		if (btnAddName != null){
			this.btnAddName = btnAddName;
		}
		if (btnRemoveName != null){
			this.btnRemoveName = btnRemoveName;
		}
		if (btnCopyName != null){
			this.btnCopyName = btnCopyName;
		}
		
		initTablePanel();
		initControlPanel();
		initMainPanel();

	}
	
	protected void initTablePanel(){
		table = new DataTable(viewModel);
		rowTable = new RowNumberTable(table);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setRowHeaderView(rowTable);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
		table.setFillsViewportHeight(true);
		
		tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		tablePanel.add(scrollPane);
	}
	
	protected void initControlPanel(){
		//initialise the buttons 
		btnAdd = new JButton(btnAddName);
		btnAdd.addActionListener(this);
		
		btnRemove = new JButton(btnRemoveName);
		btnRemove.addActionListener(this);
		
		btnCopy = new JButton(btnCopyName);
		btnCopy.addActionListener(this);
		
		controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(0,3));
		controlPanel.add(btnAdd);
		controlPanel.add(btnRemove);
		controlPanel.add(btnCopy);
	}
	
	protected void initMainPanel(){
		this.setLayout(new BorderLayout());
		this.add(tablePanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//for clicking the add entry button
		if (e.getSource() == btnAdd){
			/*
			 * if a row is selected, add the new entry directly
			 * below that row; otherwise, add the new entry at
			 * the bottom of the table
			 */
			if (table.getSelectedRow() != -1){
				controller.addRow(table.getSelectedRow()+1);
			} else {
				controller.addRow();
			}
			
		//for clicking the remove entry button
		} else if (e.getSource() == btnRemove){
			if (table.getSelectedRows().length != 0){
				controller.removeRows(table.getSelectedRows());
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
			
		//for clicking the copy button
		} else if (e.getSource() == btnCopy){
			if (table.getSelectedRows().length != 0){
				controller.copyRows(table.getSelectedRows());
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}
