package rapidFit.view;

import java.util.List;

import javax.swing.JPanel;

import rapidFit.model.*;

@SuppressWarnings("serial")
public class FitDataSetPanel extends AbstractDataListViewer<ToFitType> {
	
	private List<PhysicsParameterType> parameters;

	public FitDataSetPanel(List<PhysicsParameterType> params,
			List<ToFitType> dataRoot, List<ToFitType> actualFits,
			String listTitle, String tagName) {
		super(ToFitType.class, dataRoot, actualFits, listTitle, tagName);
		
		parameters = params;
	}

	@Override
	protected JPanel initNullDisplayPanel() {
		return new DataSetPanel();
	}

	@Override
	protected JPanel initDataDisplayPanel(ToFitType entry) {
		return new DataSetPanel(parameters, entry);
	}

}
