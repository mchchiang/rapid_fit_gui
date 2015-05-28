package rapidFit;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import rapidFit.rpfit.*;

public class FitDataSetPanel extends JPanel implements ActionListener {
	
	private HashMap<String, ToFitType> dataSetMap;
	private int dataSetCount = 1;
	private int currentDataSetIndex = -1;
	
	private JList<String> dataSetList; 
	private DefaultListModel<String> listModel;
	
	private JScrollPane dataSetScrollPane;
	
	private JPanel dataSetOptionPanel;
	private JPanel listOfDataSetPanel;
	
	private JButton btnAddDataSet;
	private JButton btnRemoveDataSet;
	private JButton btnDuplicateDataSet;
	
	private DataSetPanel dataSetPanel;
	
	public FitDataSetPanel(ArrayList<ToFitType> dataSets){
		
		//get all the data sets (i.e. filter the constraint functions)
		dataSetMap = new HashMap<String, ToFitType>();
		for (ToFitType dataSet : dataSets){
			dataSetMap.put("Data_Set_" + dataSetCount, dataSet);
			dataSetCount++;
		}
		
		dataSetPanel = new DataSetPanel();
		
		listModel = new DefaultListModel<String>();
		dataSetList = new JList<String>(listModel);
		
		//add data set to list
		for (String dataSet : dataSetMap.keySet()){
			listModel.addElement(dataSet);
		}
		
		final FitDataSetPanel thisPanel = this;
		
		//click on list to switch between data sets
		dataSetList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int index = dataSetList.locationToIndex(e.getPoint());	
				if (e.getClickCount() == 1){
					if (currentDataSetIndex != index){
						try{
							thisPanel.remove(dataSetPanel);
							currentDataSetIndex = index;
							dataSetPanel = new DataSetPanel(
									dataSetMap.get(dataSetList.
											getModel().getElementAt(index)));
							thisPanel.add(dataSetPanel, BorderLayout.CENTER);
							thisPanel.validate();
						} catch (Exception ex){
							ex.printStackTrace();
						}
					}
				} else if (e.getClickCount() == 2){
					
				}
			}
		});
		
		dataSetScrollPane = new JScrollPane(dataSetList);
		
		
		btnAddDataSet = new JButton("Add");
		btnAddDataSet.addActionListener(this);
		btnRemoveDataSet = new JButton("Remove");
		btnRemoveDataSet.addActionListener(this);
		btnDuplicateDataSet = new JButton("Duplicate");
		btnDuplicateDataSet.addActionListener(this);
		
		dataSetOptionPanel = new JPanel();
		dataSetOptionPanel.add(btnAddDataSet);
		dataSetOptionPanel.add(btnRemoveDataSet);
		dataSetOptionPanel.add(btnDuplicateDataSet);
		
		listOfDataSetPanel = new JPanel();
		listOfDataSetPanel.setLayout(new BorderLayout());
		listOfDataSetPanel.add(dataSetScrollPane, BorderLayout.CENTER);
		listOfDataSetPanel.add(dataSetOptionPanel, BorderLayout.SOUTH);
		listOfDataSetPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Available Data Sets</h3></html>"));
		
		setLayout(new BorderLayout());
		add(listOfDataSetPanel, BorderLayout.WEST);
		add(dataSetPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
