package rapidFit.view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

@SuppressWarnings("serial")
public class Table extends JTable{
	private boolean isSelectAllForMouseEvent = false;
	private boolean isSelectAllForActionEvent = false;
	private boolean isSelectAllForKeyEvent = false;

	//Constructor
	public Table(TableModel dm){
        super(dm, null, null);
        
        //set default properties
        setShowGrid(true);
		setGridColor(Color.GRAY);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		setSelectAllForEdit(true);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		setFocusable(true);
		
		/*
		 * make sure the edits to a cell is saved when user click on
		 * another gui object
		 */
		putClientProperty("terminateEditOnFocusLost", true);
    }
      
//
//  Overridden methods
//
	/*
	 *  Override to provide Select All editing functionality
	 */
	public boolean editCellAt(int row, int column, EventObject e){
		boolean result = super.editCellAt(row, column, e);

		if (isSelectAllForMouseEvent
		||  isSelectAllForActionEvent
		||  isSelectAllForKeyEvent) {
			selectAll(e);
		}

		return result;
	}

	/*
	 * Select the text when editing on a text related cell is started
	 */
	private void selectAll(EventObject e){
		final Component editor = getEditorComponent();

		if (editor == null || ! (editor instanceof JTextComponent))
			return;

		if (e == null){
			((JTextComponent)editor).selectAll();
			return;
		}

		//  Typing in the cell was used to activate the editor

		if (e instanceof KeyEvent && isSelectAllForKeyEvent){
			((JTextComponent)editor).selectAll();
			return;
		}

		//  F2 was used to activate the editor

		if (e instanceof ActionEvent && isSelectAllForActionEvent){
			((JTextComponent)editor).selectAll();
			return;
		}

		//  A mouse click was used to activate the editor.
		//  Generally this is a double click and the second mouse click is
		//  passed to the editor which would remove the text selection unless
		//  we use the invokeLater()

		if (e instanceof MouseEvent && isSelectAllForMouseEvent){
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					((JTextComponent)editor).selectAll();
				}
			});
		}
	}

//
//  Newly added methods
//
	/*
	 *  Sets the Select All property for for all event types
	 */
	public void setSelectAllForEdit(boolean isSelectAllForEdit){
		setSelectAllForMouseEvent( isSelectAllForEdit );
		setSelectAllForActionEvent( isSelectAllForEdit );
		setSelectAllForKeyEvent( isSelectAllForEdit );
	}

	/*
	 *  Set the Select All property when editing is invoked by the mouse
	 */
	public void setSelectAllForMouseEvent(boolean isSelectAllForMouseEvent){
		this.isSelectAllForMouseEvent = isSelectAllForMouseEvent;
	}

	/*
	 *  Set the Select All property when editing is invoked by the "F2" key
	 */
	public void setSelectAllForActionEvent(boolean isSelectAllForActionEvent){
		this.isSelectAllForActionEvent = isSelectAllForActionEvent;
	}

	/*
	 *  Set the Select All property when editing is invoked by
	 *  typing directly into the cell
	 */
	public void setSelectAllForKeyEvent(boolean isSelectAllForKeyEvent){
		this.isSelectAllForKeyEvent = isSelectAllForKeyEvent;
	}
	
	//for initialising cells so that the full name is shown
 	public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
 		int extraspacing = 10;
 		Component component = super.prepareRenderer(renderer, row, column);
 		int rendererWidth = component.getPreferredSize().width;
 		TableColumn tableColumn = getColumnModel().getColumn(column);
 		tableColumn.setPreferredWidth(Math.max(rendererWidth +
 				getIntercellSpacing().width + extraspacing, 
 				tableColumn.getPreferredWidth()));
 		return component;
 	} 
 	
 	public Component prepareEditor(TableCellEditor editor, int row, int column){
 		//RapidFitMainControl.getInstance().setCurrentEditingTable(this);
 		return super.prepareEditor(editor,row,column);
 	}
//
//  Static, convenience methods
//
	/**
	 *  Convenience method to order the table columns of a table. The columns
	 *  are ordered based on the column names specified in the array. If the
	 *  column name is not found then no column is moved. This means you can
	 *  specify a null value to preserve the current order of a given column.
	 *
     *  @param table        the table containing the columns to be sorted
     *  @param columnNames  an array containing the column names in the
     *                      order they should be displayed
	 */
	public static void reorderColumns(JTable table, Object... columnNames){
		TableColumnModel model = table.getColumnModel();

		for (int newIndex = 0; newIndex < columnNames.length; newIndex++){
			try	{
				Object columnName = columnNames[newIndex];
				int index = model.getColumnIndex(columnName);
				model.moveColumn(index, newIndex);
			}
			catch(IllegalArgumentException e) {}
		}
	}
}