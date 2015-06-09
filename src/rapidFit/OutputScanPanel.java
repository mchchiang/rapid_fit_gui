package rapidFit;

import java.util.List;
import java.awt.*;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class OutputScanPanel extends JPanel {
	private List<ScanParamType> scanList;
	private List<TwoDScanType> twoDScanList;
	
	private DataListPanel<ScanParamType> scanListPanel;
	private TwoDScanListPanel twoDScanListPanel;
	
	public OutputScanPanel (OutputType output){
		scanList = output.getScan();
		twoDScanList = output.getTwoDScan();
		
		scanListPanel = new DataListPanel<ScanParamType>(
				ScanParamType.class, scanList, scanList,
				"Available Scans", "Scan", "Scan Details");
		
		twoDScanListPanel = new TwoDScanListPanel(twoDScanList);
		
		this.setLayout(new GridLayout(2,1));
		this.add(scanListPanel);
		this.add(twoDScanListPanel);
		
	}
}
