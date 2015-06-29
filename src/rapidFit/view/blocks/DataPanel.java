package rapidFit.view.blocks;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.*;

@SuppressWarnings("serial")
public class DataPanel<T> extends JPanel {
	private DataTable table;
	private DataTableModel<T> tableModel;
	private JScrollPane scrollPane;
	private JTable rowTable; //make row numbers next to table
	
	
	public DataPanel (Class<T> type, List<T> data, ArrayList<String> ignoreAttr){
		tableModel = new DataTableModel<T>(type, data, ignoreAttr);
		table = new DataTable(tableModel);
		rowTable = new RowNumberTable(table);
		
		scrollPane = new JScrollPane(table);
		scrollPane.setRowHeaderView(rowTable);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
		table.setFillsViewportHeight(true);
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane);
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
}
