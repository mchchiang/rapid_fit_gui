package rapidFit;

import java.util.List;
import java.awt.*;
import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class TwoDScanListPanel extends DataListPanel<TwoDScanType>{

	public TwoDScanListPanel(List<TwoDScanType> data) {
		super(TwoDScanType.class, data, data, "Available 2D Scans", "2D_Scan", null);
	}

	@Override
	protected JPanel initDataDisplayPanel(TwoDScanType entry) {
		//ensure that x and y param data are not null
		if (entry.getXParam() == null){
			entry.setXParam(new ScanParamType());
		}
		if (entry.getYParam() == null){
			entry.setYParam(new ScanParamType());
			
		}
		AttributePanel<ScanParamType> xParamPanel = new AttributePanel<ScanParamType>(
				ScanParamType.class, entry.getXParam(), "X_Param", null);
		AttributePanel<ScanParamType> yParamPanel = new AttributePanel<ScanParamType>(
				ScanParamType.class, entry.getYParam(), "Y_Param", null);
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(xParamPanel);
		panel.add(yParamPanel);
		
		return panel;
	}

}
