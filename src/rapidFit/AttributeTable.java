package rapidFit;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.ListSelectionModel;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class AttributeTable extends Table {
	
	private Class<?> editingClass;
	
	public AttributeTable(AbstractTableModel dm) {
		super(dm);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	//allow different data types for different rows in the same column
	 @Override
     public TableCellRenderer getCellRenderer(int row, int col) {
		 editingClass = null;
         int modelColumn = convertColumnIndexToModel(col);
         int modelRow = convertRowIndexToModel(row);
         if (modelColumn == 1) {
             Class<?> rowClass = 
            		 ((AttributeTableModel<?>) getModel()).getRowClass(modelRow);
             return getDefaultRenderer(rowClass);
         } else {
             return super.getCellRenderer(row, col);
         }
     }

     @SuppressWarnings("unchecked")
	@Override
     public TableCellEditor getCellEditor(int row, int col) {
    	 editingClass = null;
         int modelColumn = convertColumnIndexToModel(col);
         int modelRow = convertRowIndexToModel(row);
         if (modelColumn == 1) {
        	 editingClass = 
        			 ((AttributeTableModel<?>) getModel()).getRowClass(modelRow);
        	 
        	 //display enum attributes as combo box
        	 if (editingClass.isEnum()){
        		 return new DefaultCellEditor(new JComboBox<Enum<?>>(
							((Class<? extends Enum<?>>) editingClass).getEnumConstants()));
        	 }
             return getDefaultEditor(editingClass);
             
         } else {
             return super.getCellEditor(row, col);
         }
     }
     //  This method is also invoked by the editor when the value in the editor
     //  component is saved in the TableModel. The class was saved when the
     //  editor was invoked so the proper class can be created.

     @Override
     public Class<?> getColumnClass(int column) {
         return editingClass != null ? editingClass : super.getColumnClass(column);
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
	
}
