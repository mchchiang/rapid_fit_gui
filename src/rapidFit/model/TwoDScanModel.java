package rapidFit.model;

import java.util.ArrayList;
import java.util.List;

import rapidFit.data.ScanParamType;
import rapidFit.data.TwoDScanType;
import rapidFit.model.dataModel.DataModel;

public class TwoDScanModel extends DataModel<TwoDScanType>{

	public TwoDScanModel(List<TwoDScanType> data,
			ArrayList<String> ignoreAttributes) {
		super(TwoDScanType.class, data, ignoreAttributes);
	}
	
	/*
	 * configure the inserted 2D scans to ensure that
	 * their x param and y param objects are non-null
	 */
	
	@Override
	public void add(int index){
		TwoDScanType twoDScan = new TwoDScanType();
		twoDScan.setXParam(new ScanParamType());
		twoDScan.setYParam(new ScanParamType());
		super.add(index, twoDScan);
	}
	
	@Override
	public void add(int index, TwoDScanType twoDScan){
		if (twoDScan.getXParam() == null){
			twoDScan.setXParam(new ScanParamType());
		}
		if (twoDScan.getYParam() == null){
			twoDScan.setYParam(new ScanParamType());
		}
		super.add(index, twoDScan);
	}
	
	@Override
	public void set(int index, TwoDScanType twoDScan){
		if (twoDScan.getXParam() == null){
			twoDScan.setXParam(new ScanParamType());
		}
		if (twoDScan.getYParam() == null){
			twoDScan.setYParam(new ScanParamType());
		}
	}
	
}
