package rapidFit;

import java.util.List;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public abstract class AbstractDataListPanel<T> extends JPanel implements ActionListener {
	
	//variables for the data list panel
	
	//container for the entire data list panel
	private JPanel dataListPanel;
	
	//store the display title for the list
	private String listTitle = "Available Data Sets";
	
	//store reference of the actual data to be displayed
	private List<T> data;
	private List<T> root;
	/*
	 * for identifying if the root is the same as the data origin.
	 * This occurs when the root tag contains different types of data 
	 * sets and the data passed in is a only one type of the data set.
	 * The root is needed to link any changes in this data set back to
	 * the main xml tree.
	 */
	private boolean isExternalRoot = false;
	private Class<T> dataClass;
	private TagNameManager<T> tagNameManager;
	
	//for display the entries in the data as a list
	private int currentDataSetIndex = -1;
	private DataListModel<T> listModel;
	private DataList<T> dataList;
	
	//scroll pane for the data list
	private JScrollPane dataListScrollPane;
	
	//buttons to add, remove, and copy data set
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnCopy;

	//panel that holds the add, remove and copy buttons
	private JPanel dataListOptionPanel;
	
	//===================================================================
	//
	//variables for the main panel
	
	//container for the entire main panel
	private JPanel mainDisplayPanel;
	
	//labels and textfield for setting tag name
	private JLabel lblTagName;
	private JTextField txtTagName;
	
	//store the tag name suffix
	private String tagName = "Data_Set";
	
	//panel for displaying the tag name
	private JPanel tagNamePanel;
	
	//panel for display an entry of the data
	private JPanel dataDisplayPanel;
	
	
	/*
	 * requires the sub-class to determine how to display the data
	 */
	protected abstract JPanel initNullDisplayPanel();
	protected abstract JPanel initDataDisplayPanel(T entry);
	
	//constructor
	public AbstractDataListPanel(Class<T> clazz, List<T> dataRoot, List<T> data,
			String listTitle, String tagName){
		this.dataClass = clazz;
		this.root = dataRoot;
		this.data = data;
		
		//check if root and data are the same reference
		if (root != data){
			isExternalRoot = true;
		} else {
			isExternalRoot = false;
		}
		
		if (listTitle != null){
			this.listTitle = listTitle;
		}
		if (tagName != null){
			this.tagName = tagName;
		}
		
		//set up tag names for the list of data
		try {
			tagNameManager = new TagNameManager<T>(this.data, this.tagName);
		} catch (TagNameException e) {
			RapidFitExceptionHandler.handles(e);
		}
		
		
		initDataListPanel();
		initMainDisplayPanel();
		initMainPanel();
	}
	
	public void initDataListPanel(){
		//initialise the list panel
		listModel = new DataListModel<T>(dataClass, data);
		dataList = new DataList<T>(listModel, tagNameManager.getNameMap());
		
		//allow clicking on list to switch between data sets		
		dataList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int index = dataList.locationToIndex(e.getPoint());	
				if (e.getClickCount() == 1){
					if (currentDataSetIndex != index){
						currentDataSetIndex = index;
						changeDataDisplayPanel(listModel.getElementAt(index));
					}
				} 
			}
		});
		
		dataListScrollPane = new JScrollPane(dataList);
		
		//initialise the option panel containing the buttons
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(this);
		
		btnCopy = new JButton("Copy");
		btnCopy.addActionListener(this);
		
		dataListOptionPanel = new JPanel();
		dataListOptionPanel.setLayout(new GridLayout(2,2));
		dataListOptionPanel.add(btnAdd);
		dataListOptionPanel.add(btnRemove);
		dataListOptionPanel.add(btnCopy);
		
		dataListPanel = new JPanel();
		dataListPanel.setLayout(new BorderLayout());
		dataListPanel.add(dataListScrollPane, BorderLayout.CENTER);
		dataListPanel.add(dataListOptionPanel, BorderLayout.SOUTH);	
		dataListPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>" + listTitle + "</h3></html>"));		
	}
	
	public void initMainDisplayPanel(){
		//initialise the tag name panel
		lblTagName = new JLabel("<html><b>Tag Name: </b></html>");
		txtTagName = new JTextField();
		
		tagNamePanel = new JPanel();
		tagNamePanel.setLayout(new BorderLayout());
		tagNamePanel.add(lblTagName, BorderLayout.WEST);
		tagNamePanel.add(txtTagName, BorderLayout.CENTER);
		
		dataDisplayPanel = initNullDisplayPanel();

		//add everything to container
		mainDisplayPanel = new JPanel();
		mainDisplayPanel.setLayout(new BorderLayout());
		mainDisplayPanel.add(dataDisplayPanel, BorderLayout.CENTER);
		mainDisplayPanel.setBorder(BorderFactory.createTitledBorder("<html><h3>&nbsp;</h3></html>"));
	}
	
	public void initMainPanel(){
		this.setLayout(new BorderLayout());
		this.add(dataListPanel, BorderLayout.WEST);
		this.add(mainDisplayPanel, BorderLayout.CENTER);
	}
	
	public void changeDataDisplayPanel(T entry){
		mainDisplayPanel.remove(dataDisplayPanel);
		
		if (entry == null){
			mainDisplayPanel.remove(tagNamePanel);
			dataDisplayPanel = initNullDisplayPanel();
			mainDisplayPanel.setBorder(BorderFactory.createTitledBorder("<html><h3>&nbsp;</h3></html>"));	
			
		} else {
			dataDisplayPanel = initDataDisplayPanel(entry);
			
			//set tag name
			String name = "null";
			try {
				name = tagNameManager.getTagName(entry);
			} catch (TagNameException e){
				RapidFitExceptionHandler.handles(e);
			}
			
			txtTagName.setText(name);
			txtTagName.setEditable(false);
			
			mainDisplayPanel.add(tagNamePanel, BorderLayout.NORTH);
			mainDisplayPanel.setBorder(BorderFactory.createTitledBorder(
					"<html><h3>" + name + "</h3></html>"));	
		}
		
		mainDisplayPanel.add(dataDisplayPanel, BorderLayout.CENTER);
		
		//refresh the display panel
		mainDisplayPanel.validate();
	}
	
	//handling the events for click the add, remove and copy button
	public void actionPerformed(ActionEvent e){
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
			changeDataDisplayPanel(entry);
		
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
				changeDataDisplayPanel(null);
				
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
				changeDataDisplayPanel(copy);
				
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}
