package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class DataListPanel<T> extends JPanel implements ActionListener {
	
	//variables for displaying the list of data
	
	//store reference of the actual data to be displayed
	private List<T> data;
	private List<T> root;
	/*
	 * for identifying if the root is the same as the data origin.
	 * This occurs when the root tag contains different types of data 
	 * sets and the data passed in is a only one type of the data set.
	 * The root is needed to link any changes in this data set back to
	 * the main XML tree.
	 */
	private boolean isExternalRoot = false;
	private TagNameManager<T> tagNameManager;
	
	//for display the entries in the data as a list
	private int currentDataSetIndex = -1;
	private DataListModel<T> listModel;
	private DataList<T> dataList;
	
	//container for the list
	private JScrollPane scrollPane;
	
	//==========================================================
	//
	//variables for controlling/manipulating the data
	//
	
	//buttons for adding, removing and duplicating a data entry
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnCopy;
	
	//container for all the control buttons
	private JPanel optionPanel;
	
	
	public DataListPanel (Class<T> clazz, List<T> root, 
			List<T> data, TagNameManager<T> manager){
		
	}
	
	public void initDataListPanel(){
		dataList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int index = dataList.locationToIndex(e.getPoint());
				if (index != -1){
					mouseClickedOnListEntry(listModel.getElementAt(index));
				}
			}
		});
	}
	
	public void initOptionPanel(){
		//initialise the option panel containing the buttons
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(this);
		
		btnCopy = new JButton("Copy");
		btnCopy.addActionListener(this);
		
		optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(2,2));
		optionPanel.add(btnAdd);
		optionPanel.add(btnRemove);
		optionPanel.add(btnCopy);
	}
	
	public void mouseClickedOnListEntry(T entry){
		//do nothing by default (to be overridden by subclasses)
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//for clicking the add button
		if (e.getSource() == btnAdd){
			int index = 0;

			//if the list is empty
			if (listModel.getSize() == 0){
				listModel.addRow();

			} else {
				index = dataList.getSelectedIndex();
				/*
				 * add a row at the bottom of the list
				 * if no element of the list is selected; 
				 * otherwise, insert a row directly below the
				 * current selected element
				 */
				if (index == -1){
					index += listModel.getSize();
				} else {
					index++;
				}

				listModel.addRow(index);
			}

			T entry = listModel.getElementAt(index);

			//update the main XML tree if root is external
			if (isExternalRoot){
				root.add(entry);
			}

			dataList.setSelectedIndex(index);
			currentDataSetIndex = index;
			try {
				tagNameManager.addEntry(entry);
			} catch (TagNameException err){
				RapidFitExceptionHandler.handles(err);
			}

			//update the tag name of the data list
			dataList.validate();

			//display the newly added data entry
			//changeDataDisplayPanel(entry);

			//for clicking the remove button
		} else if (e.getSource() == btnRemove){
			int index = dataList.getSelectedIndex();

			if (index != -1){
				T entry = listModel.getElementAt(index);

				//update the main XML tree if root is external
				if (isExternalRoot){
					root.remove(entry);
				}

				listModel.removeRow(index);
				currentDataSetIndex = -1;

				try {
					tagNameManager.removeEntry(entry);
				} catch (TagNameException err){

				}


				//update the tag name of the data list
				dataList.validate();

				//display the newly added data entry
				//changeDataDisplayPanel(null);

			} else {
				Toolkit.getDefaultToolkit().beep();
			}

			//for clicking the copy button
		} else if (e.getSource() == btnCopy){

			int index = dataList.getSelectedIndex();

			if (index != -1){
				//deep copy the data set
				T original = listModel.getElementAt(index);

				@SuppressWarnings("unchecked")
				T copy = (T) Cloner.deepClone(original);

				index++;

				//add copied data set to the list
				listModel.addRow(index, copy);

				//add data to ToFit root

				//update the main XML tree if root is external
				if (isExternalRoot){
					root.add(copy);
				}

				dataList.setSelectedIndex(index);
				currentDataSetIndex = index;

				try {
					tagNameManager.addEntry(copy, 
							tagNameManager.getTagName(original) + "_copy");
				} catch (TagNameException err){
					RapidFitExceptionHandler.handles(err);
				}

				//update the tag name of the data list
				dataList.validate();

				//display the newly added data entry
				//changeDataDisplayPanel(copy);

			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}
