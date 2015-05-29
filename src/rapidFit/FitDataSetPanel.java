package rapidFit;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class FitDataSetPanel extends JPanel implements ActionListener {
	
	private int currentDataSetIndex = -1;
	
	private JScrollPane dataSetScrollPane;
	
	private JPanel dataSetOptionPanel;
	private JPanel listOfDataSetPanel;
	
	private JButton btnAddDataSet;
	private JButton btnRemoveDataSet;
	private JButton btnDuplicateDataSet;
	
	private DataSetPanel dataSetPanel;
	
	private DataList<ToFitType> dataSetList;
	private DataListModel<ToFitType> listModel;
	
	
	public FitDataSetPanel(ArrayList<ToFitType> dataSets){
		
		dataSetPanel = new DataSetPanel();
		
		final FitDataSetPanel thisPanel = this;
		
		listModel = new DataListModel<ToFitType>(
				ToFitType.class, dataSets);
		dataSetList = new DataList<ToFitType>(listModel, "Data_Set", true);
		
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
									dataSetList.getModel().getElementAt(index));
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
		if (e.getSource() == btnRemoveDataSet){
			listModel.removeRows(dataSetList.getSelectedIndices());
		}
		
	}
}
