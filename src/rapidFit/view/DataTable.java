package rapidFit.view;

import rapidFit.controller.DataTableController;

@SuppressWarnings("serial")
public class DataTable extends Table {
	public DataTable(DataTableController controller) {
		super(new DataTableView(controller));
	}
}
