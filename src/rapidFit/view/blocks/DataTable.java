package rapidFit.view.blocks;

import java.awt.*;
import java.math.BigInteger;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class DataTable extends Table{

	private class ListCellEditor extends DefaultCellEditor {
		private final Border red = new LineBorder(Color.red);
		private final Border black = new LineBorder(Color.black);
		private JTextField textField;
		private final Class<?> dataClass;

		public ListCellEditor(JTextField textField, Class<?> clazz){
			super(textField);
			this.textField = textField;
			this.dataClass = clazz;
		}

		@Override
		public boolean stopCellEditing(){
			try{
				StringTokenizer st = new StringTokenizer(textField.getText(), "[, ]");
				if (dataClass == Double.class){
					while(st.hasMoreElements()){
						Double.parseDouble(st.nextToken());
					}

				} else if (dataClass == BigInteger.class){
					while(st.hasMoreElements()){
						BigInteger.valueOf(Integer.parseInt(st.nextToken()));
					}
				}
			} catch (Exception e){
				textField.setBorder(red);
				return false;
			}
			return super.stopCellEditing();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			textField.setBorder(black);
			return super.getTableCellEditorComponent(
					table, value, isSelected, row, column);
		}
	}
	//
	// Constructors
	@SuppressWarnings("unchecked")
	public DataTable(DataTableModel<?> dm){
		super(dm);

		//display enum attributes as combo box
		for (int i = 0; i < dm.getColumnCount(); i++){
			Class<?> clazz = dm.getColumnClass(i);
			if (clazz.isEnum()){
				getColumnModel().getColumn(i).setCellEditor(
						new DefaultCellEditor(new JComboBox<Enum<?>>(
								((Class<? extends Enum<?>>) clazz).getEnumConstants())));
				continue;
			} 
			
			if (dm.getListMap().containsKey(i)){
				getColumnModel().getColumn(i).setCellEditor(
						new ListCellEditor(new JTextField(), dm.getListMap().get(i)));
			}
		}
	}
}