package rapidFit;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import cloning.*;
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
	private DataSetPanel emptyDataSetPanel;
	
	private DataList<ToFitType> dataSetList;
	private DataListModel<ToFitType> listModel;
	
	private List<ToFitType> toFitRoot;
	
	public FitDataSetPanel(List<ToFitType> root, ArrayList<ToFitType> dataSets){
		dataSetPanel = new DataSetPanel();
		
		toFitRoot = root;
		
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
									listModel.getElementAt(index));

							dataSetPanel.setBorder(BorderFactory.createTitledBorder(
									"<html><h3>" + dataSetList.getTagName(
											listModel.getElementAt(index)) + "</h3></html>"));
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
			for (int index : dataSetList.getSelectedIndices()){
				if (index == currentDataSetIndex){
					this.remove(dataSetPanel);
					dataSetPanel = new DataSetPanel();
					this.add(dataSetPanel, BorderLayout.CENTER);
					this.validate();
				}
				toFitRoot.remove(listModel.getElementAt(index));
				listModel.removeRow(index);
			}
			currentDataSetIndex = -1;
		} else if (e.getSource() == btnAddDataSet){
			int index = dataSetList.getSelectedIndex();
			
			if (index == -1){
				index += listModel.getSize();
			} else {
				index++;
			}
			
			listModel.addRow(index);
			toFitRoot.add(listModel.getElementAt(index));
			
			dataSetList.setSelectedIndex(index);
			dataSetList.setTagName(listModel.getElementAt(index));
			
			this.remove(dataSetPanel);
			
			currentDataSetIndex = index;
			
			dataSetPanel = new DataSetPanel(
					listModel.getElementAt(index));
			dataSetPanel.setBorder(BorderFactory.createTitledBorder(
					"<html><h3>" + dataSetList.getTagName(
							listModel.getElementAt(index)) + "</h3></html>"));
			
			this.add(dataSetPanel, BorderLayout.CENTER);
			this.validate();
			
		} else if (e.getSource() == btnDuplicateDataSet){
			//deep copy the data set
			/**
		     * Returns a copy of the object, or null if the object cannot
		     * be serialized.
		     */
			
			int index = dataSetList.getSelectedIndex();
			if (index != -1){
				//remove the current data set from display
				this.remove(dataSetPanel);
				
				//deep copy the data set
				ToFitType original = listModel.getElementAt(index);
				ToFitType copy = copyDataSet(original);
				
				index++;
				
				//add copied data set to the list
				listModel.addRow(index, copy);
				
				//add data to ToFit root
				toFitRoot.add(copy);
				
				//change the data panel to display the new data set
				dataSetPanel = new DataSetPanel(copy);
				
				dataSetList.setSelectedIndex(index);
				currentDataSetIndex = index;
				dataSetList.setTagName(listModel.getElementAt(index), 
						dataSetList.getTagName(listModel.getElementAt(index-1)) + "_copy");

				dataSetPanel.setBorder(BorderFactory.createTitledBorder(
						"<html><h3>" + dataSetList.getTagName(
								listModel.getElementAt(index)) + "</h3></html>"));
				this.add(dataSetPanel, BorderLayout.CENTER);
				this.validate();
			} else {
				Toolkit.getDefaultToolkit().beep();
			}		
		}
		
	}
	
	private ToFitType copyDataSet(ToFitType original){
		ToFitType copy = null;
        try {
            // Write the object out to a byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(original);
            out.flush();
            out.close();

            // Make an input stream from the byte array and read
            // a copy of the object back in.
            ObjectInputStream in = new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray()));
            copy = (ToFitType) in.readObject();
        }
        catch(IOException err) {
            err.printStackTrace();
        }
        catch(ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
		return copy;
	}
}
