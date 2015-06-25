package rapidFit.view;

import rapidFit.controller.DataTableController;
import rapidFit.model.Observer;

@SuppressWarnings("serial")
public class DataTable extends Table implements Observer {
	
	private DataTableModel model;
	
	public DataTable(DataTableController controller) {
		super(new DataTableModel(controller));
		model = (DataTableModel) getModel();
	}

	@Override
	public void update() {
		model.fireTableStructureChanged();
	}

}
