package rapidFit;

@SuppressWarnings("serial")
public class DataList<T> extends Table {

	public DataList(DataListModel<T> listModel) {
		super(listModel);
		setShowGrid(false);
		setTableHeader(null);
	}
	
}
