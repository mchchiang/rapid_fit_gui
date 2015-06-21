package rapidFit.view.blocks;

import java.lang.reflect.*;
import java.util.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataTable extends Table{
	
	
	/*private class ListCellRenderer extends DefaultTableCellRenderer{
		public ListCellRenderer(){
			super();
		}
		
		
	}*/
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
}