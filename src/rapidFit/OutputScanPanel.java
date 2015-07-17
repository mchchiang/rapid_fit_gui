package rapidFit;

import java.util.List;
import java.awt.*;

import javax.swing.*;

import rapidFit.data.*;

@SuppressWarnings("serial")
public class OutputScanPanel extends JPanel {
	private List<ScanParamType> scanList;
	private List<TwoDScanType> twoDScanList;
	
	private DataListViewer<ScanParamType> scanListPanel;
	private TwoDScanListPanel twoDScanListPanel;
	
	public OutputScanPanel (OutputType output){
		scanList = output.getScan();
		twoDScanList = output.getTwoDScan();
		
		scanListPanel = new DataListViewer<ScanParamType>(
				ScanParamType.class, scanList, scanList,
				"Available Scans", "Scan", "Scan Details");
		
		twoDScanListPanel = new TwoDScanListPanel(twoDScanList);
		
		this.setLayout(new GridLayout(2,1));
		this.add(scanListPanel);
		this.add(twoDScanListPanel);
		
	}
}
