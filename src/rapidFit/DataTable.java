package rapidFit;

import java.awt.Component;
import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

@SuppressWarnings("serial")
public class DataTable extends Table{

//
// Constructors
	@SuppressWarnings("unchecked")
	public DataTable(DataTableModel<?> dm){
        super(dm);
        
		//display enum attributes as combo box
		ArrayList<Method> getMethods = dm.getGetterMethods();
		for (int i = 0; i < getMethods.size(); i++){
			Class<?> clazz = getMethods.get(i).getReturnType();
			if (clazz.isEnum()){
				getColumnModel().getColumn(i).setCellEditor(
						new DefaultCellEditor(new JComboBox<Enum<?>>(
								((Class<? extends Enum<?>>) clazz).getEnumConstants())));
			}
		}
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