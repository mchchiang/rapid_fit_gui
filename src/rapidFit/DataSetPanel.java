package rapidFit;

import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;

import rapidFit.rpfit.*;

public class DataSetPanel extends JPanel {
	
	private DataSetType dataSet;
	
	private AttributePanel<DataSetType> details;
	
	private JCheckBox cbCommonPhaseSpace;
	private JCheckBox cbCommonPDF;
	
	private DataPanel<PhaseSpaceBoundaryType> phaseSpace;
	
	public DataSetPanel(){
		JLabel lblNoData = new JLabel("There is no data set selected.");
		add(lblNoData);
	}
	
	public DataSetPanel (ToFitType fit){
		dataSet = fit.getDataSet();
		
		ArrayList<String> ignoreAttr = new ArrayList<String>();
		ignoreAttr.add("PhaseSpaceBoundary");
		ignoreAttr.add("CommonPhaseSpace");
		
		details = new AttributePanel<DataSetType>(
				DataSetType.class, dataSet, "Details", ignoreAttr);
		setLayout(new BorderLayout());
		add(details, BorderLayout.NORTH);
		
		cbCommonPDF = new JCheckBox();
		cbCommonPDF.setSelected(false);
		//if it is a common pdf
		if (fit.isCommonPDF()){
			cbCommonPDF.setSelected(true);
		}
		
		cbCommonPhaseSpace = new JCheckBox();
		cbCommonPhaseSpace.setSelected(false);
		//using common phase space
		if (dataSet.getCommonPhaseSpace() != null){
			cbCommonPhaseSpace.setSelected(true);
			
		} else {
			
		}
	}
}
