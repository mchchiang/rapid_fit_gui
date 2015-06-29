package rapidFit.view.blocks;

import javax.swing.*;

import rapidFit.main.Cloner;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class DataTablePanel<T> extends JPanel implements ActionListener {
	//variables for the table panel
	private DataTable table;
	private DataTableModel<T> tableModel;
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
	
	//constructor
	public DataTablePanel (Class<T> type, List<T> data, ArrayList<String> ignoreAttr){
		this (type, data, ignoreAttr, null, null, null);
	}
	public DataTablePanel (Class<T> type, List<T> data, ArrayList<String> ignoreAttr,
			String addBtnName, String removeBtnName, String copyBtnName){
		
		tableModel = new DataTableModel<T>(type, data, ignoreAttr);
		
		if (addBtnName != null)	this.btnAddName = addBtnName;
		if (removeBtnName != null) this.btnRemoveName = removeBtnName;
		if (copyBtnName != null) this.btnCopyName = copyBtnName;
		
		initTablePanel();
		initControlPanel();
		initMainPanel();
	}
	
	protected void initTablePanel(){
		table = new DataTable(tableModel);
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
	
	public DataTableModel<T> getTableModel(){return tableModel;}
	public void addRow(){
		if (table.getSelectedRow() != -1){
			tableModel.addRow(table.getSelectedRow());
		} else {
			tableModel.addRow();
		}
	}
	public void removeSelectedRows(){
		tableModel.removeRows(table.getSelectedRows());
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
				tableModel.addRow(table.getSelectedRow()+1);
			} else {
				tableModel.addRow();
			}
			
		//for clicking the remove entry button
		} else if (e.getSource() == btnRemove){
			if (table.getSelectedRows().length != 0){
				tableModel.removeRows(table.getSelectedRows());
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
			
		//for clicking the copy button
		} else if (e.getSource() == btnCopy){
			if (table.getSelectedRows().length != 0){
				int rowShift = 0;
				for (int row : table.getSelectedRows()){
					tableModel.addRow(row+rowShift+1);
					//copy the data entry (column by column)
					for (int i = 0; i < tableModel.getColumnCount(); i++){
						tableModel.setValueAt(Cloner.deepClone(
								tableModel.getValueAt(row+rowShift, i)),
								row+rowShift+1, i);
					}
					rowShift++;
				}
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
		
	}
}
