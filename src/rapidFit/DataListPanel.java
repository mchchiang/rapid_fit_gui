package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
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
	private Class<T> dataClass;
	private TagNameManager<T> tagNameManager;
	
	//for display the entries in the data as a list
	private int currentSelectedIndex = -1;
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
	//for extra buttons to be added to the option panel
	private ArrayList<JButton> extraButtons = null;
	
	//container for all the control buttons
	private JPanel optionPanel;
	
	
	public DataListPanel (Class<T> clazz, List<T> dataRoot, 
			List<T> data, TagNameManager<T> manager,
			ArrayList<JButton> buttons){
		this.dataClass = clazz;
		this.root = dataRoot;
		this.data = data;
		
		//check if root and data are the same reference
		if (root != data){
			isExternalRoot = true;
		} else {
			isExternalRoot = false;
		}
		
		//for extra components to be added to option panel
		extraButtons = buttons;
		
		tagNameManager = manager;
		
		initDataListPanel();
		initOptionPanel();
		initMainPanel();
	}
	
	public void initDataListPanel(){
		//initialise the list panel
		listModel = new DataListModel<T>(dataClass, data);
		dataList = new DataList<T>(listModel, tagNameManager.getNameMap());
		
		dataList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int index = dataList.locationToIndex(e.getPoint());
				if (index != -1 && 
						((currentSelectedIndex != index && e.getClickCount() == 1) ||
						(e.getClickCount() == 2))){
					currentSelectedIndex = index;
					mouseClickedOnListEntry(
							listModel.getElementAt(index), e.getClickCount());
				}
			}
		});
		
		scrollPane = new JScrollPane(dataList);
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
		optionPanel.setLayout(new GridLayout(0,2));
		optionPanel.add(btnAdd);
		optionPanel.add(btnRemove);
		optionPanel.add(btnCopy);
		
		/*
		 * add action listener to extra buttons and add the
		 * buttons to the option panel
		 */
		if (extraButtons != null){
			for (JButton btn : extraButtons){
				btn.addActionListener(this);
				optionPanel.add(btn);
			}
		}
	}
	
	public void initMainPanel(){
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(optionPanel, BorderLayout.SOUTH);
	}
	
	public DataList<T> getDataList(){return dataList;}
	
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
			currentSelectedIndex = index;
			
			tagNameManager.addEntry(entry);

			//update the tag name of the data list
			dataList.validate();
			
			addButtonClicked(entry);

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
				currentSelectedIndex = -1;

				tagNameManager.removeEntry(entry);

				//update the tag name of the data list
				dataList.validate();
				
				removeButtonClicked();

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

				//add data to root

				//update the main XML tree if root is external
				if (isExternalRoot){
					root.add(copy);
				}

				dataList.setSelectedIndex(index);
				currentSelectedIndex = index;

				tagNameManager.addEntry(copy, 
						tagNameManager.getTagName(original) + "_copy");
				
				//update the tag name of the data list
				dataList.validate();
				
				copyButtonClicked(copy);

			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		
		//for clicking on the extra buttons added to the option panel
		} else if (extraButtons != null &&
				extraButtons.contains(e.getSource())){
			
			int index = dataList.getSelectedIndex();
			if (index != -1){
				otherButtonsClicked(listModel.getElementAt(index), e.getSource());
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
	
	public void updateListPanel(){
		listModel.update();
	}
	
	/*
	 * methods to be overridden by subclasses to handle events 
	 * (e.g. when a list entry is clicked)
	 */
	protected void mouseClickedOnListEntry(T entry, int clickCount){}
	protected void addButtonClicked(T entry){}
	protected void removeButtonClicked(){}
	protected void copyButtonClicked(T entry){}
	protected void otherButtonsClicked(T entry, Object source){}
}
