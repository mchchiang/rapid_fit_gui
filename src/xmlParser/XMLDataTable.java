package xmlParser;

import javax.swing.*;
import javax.swing.table.*;

public class XMLDataTable extends JPanel{
	
	public XMLDataTable(){
		JTable table = new JTable(new MyTableModel());
	}
	class MyTableModel extends AbstractTableModel {
		

		public int getRowCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return null;
		}
		
		public Class<?> getColumnClass(int n){
			return getValueAt(0, n).getClass();
		}
		
	}
}
