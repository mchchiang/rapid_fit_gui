package rapidFit;

import java.util.*;
import java.util.List;
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
	
	private List<ToFitType> toFitRoot;
	
	private IdentityHashMap<ToFitType, String> dataSetNameMap;
	private int dataSetCount = 0;
	
	private List<PhysicsParameterType> parameters;

	public FitDataSetPanel(List<PhysicsParameterType> params, List<ToFitType> root, ArrayList<ToFitType> dataSets){
		dataSetPanel = new DataSetPanel();
		
		toFitRoot = root;
		
		parameters = params;
		
		final FitDataSetPanel thisPanel = this;
		
		listModel = new DataListModel<ToFitType>(
				ToFitType.class, dataSets);
		
		//create a list of name tags for the data set
		dataSetNameMap = new IdentityHashMap<ToFitType, String>();
		
		for (ToFitType dataSet : dataSets){
			dataSetCount++;
			dataSetNameMap.put(dataSet, "Data_Set_" + dataSetCount);
		}
		
		dataSetList = new DataList<ToFitType>(listModel, dataSetNameMap);
		
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
									parameters, listModel.getElementAt(index));

							dataSetPanel.setBorder(BorderFactory.createTitledBorder(
									"<html><h3>" + dataSetList.getTagName(
											listModel.getElementAt(index)) + "</h3></html>"));
							//thisPanel.add(dataSetPanel, BorderLayout.CENTER);
							GridBagConstraints c = new GridBagConstraints();
							c.fill = GridBagConstraints.BOTH;
							c.insets = new Insets(0,5,0,5);
							c.gridx = 1;
							c.gridy = 0;
							c.gridwidth = 4;
							c.weightx = 1.0;
							c.weighty = 1.0;
							add(dataSetPanel, c);
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
		dataSetOptionPanel.setLayout(new GridLayout(0,3));
		dataSetOptionPanel.add(btnAddDataSet);
		dataSetOptionPanel.add(btnRemoveDataSet);
		dataSetOptionPanel.add(btnDuplicateDataSet);
		
		listOfDataSetPanel = new JPanel();
		listOfDataSetPanel.setLayout(new BorderLayout());
		listOfDataSetPanel.add(dataSetScrollPane, BorderLayout.CENTER);
		listOfDataSetPanel.add(dataSetOptionPanel, BorderLayout.SOUTH);
		listOfDataSetPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Available Data Sets</h3></html>"));
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		//for details panel
		c.insets = new Insets(0,5,0,5);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(listOfDataSetPanel, c);
		
		//for phase space panel
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,5,0,5);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 4;
		c.weightx = 1.0;
		c.weighty = 1.0;
		add(dataSetPanel, c);
		//add(listOfDataSetPanel, BorderLayout.WEST);
		//add(dataSetPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnRemoveDataSet){
			for (int index : dataSetList.getSelectedIndices()){
				if (index == currentDataSetIndex){
					this.remove(dataSetPanel);
					dataSetPanel = new DataSetPanel();
					//this.add(dataSetPanel, BorderLayout.CENTER);
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.BOTH;
					c.insets = new Insets(0,5,0,5);
					c.gridx = 1;
					c.gridy = 0;
					c.gridwidth = 4;
					c.weightx = 1.0;
					c.weighty = 1.0;
					add(dataSetPanel, c);
					this.validate();
				}
				toFitRoot.remove(listModel.getElementAt(index));
				listModel.removeRow(index);
			}
			currentDataSetIndex = -1;
			
		} else if (e.getSource() == btnAddDataSet){
			
			int index = 0;
			if (listModel.getSize() == 0){
				listModel.addRow();
			} else {
				index = dataSetList.getSelectedIndex();
				if (index == -1){
					index += listModel.getSize();
				} else {
					index++;
				}
				listModel.addRow(index);
			}
			
			toFitRoot.add(listModel.getElementAt(index));
			
			dataSetCount++;
			
			dataSetList.setSelectedIndex(index);
			dataSetList.setTagName(listModel.getElementAt(index), "Data_Set_" + dataSetCount);
			
			this.remove(dataSetPanel);
			
			currentDataSetIndex = index;
			
			dataSetPanel = new DataSetPanel(
					parameters, listModel.getElementAt(index));
			dataSetPanel.setBorder(BorderFactory.createTitledBorder(
					"<html><h3>" + dataSetList.getTagName(
							listModel.getElementAt(index)) + "</h3></html>"));
			
			//this.add(dataSetPanel, BorderLayout.CENTER);
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.BOTH;
			c.insets = new Insets(0,5,0,5);
			c.gridx = 1;
			c.gridy = 0;
			c.gridwidth = 4;
			c.weightx = 1.0;
			c.weighty = 1.0;
			add(dataSetPanel, c);
			this.validate();
			
		} else if (e.getSource() == btnDuplicateDataSet){
			//deep copy the data set			
			int index = dataSetList.getSelectedIndex();
			if (index != -1){
				//remove the current data set from display
				this.remove(dataSetPanel);
				
				//deep copy the data set
				ToFitType original = listModel.getElementAt(index);
				ToFitType copy = (ToFitType) Cloner.deepClone(original);
				
				index++;
				
				//add copied data set to the list
				listModel.addRow(index, copy);
				
				//add data to ToFit root
				toFitRoot.add(copy);
				
				//change the data panel to display the new data set
				dataSetPanel = new DataSetPanel(parameters, copy);
				
				dataSetList.setSelectedIndex(index);
				currentDataSetIndex = index;
				dataSetList.setTagName(listModel.getElementAt(index), 
						dataSetList.getTagName(listModel.getElementAt(index-1)) + "_copy");

				dataSetPanel.setBorder(BorderFactory.createTitledBorder(
						"<html><h3>" + dataSetList.getTagName(
								listModel.getElementAt(index)) + "</h3></html>"));
				//this.add(dataSetPanel, BorderLayout.CENTER);
				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.insets = new Insets(0,5,0,5);
				c.gridx = 1;
				c.gridy = 0;
				c.gridwidth = 4;
				c.weightx = 1.0;
				c.weighty = 1.0;
				add(dataSetPanel, c);
				this.validate();
			} else {
				Toolkit.getDefaultToolkit().beep();
			}		
		}
		
	}
}
