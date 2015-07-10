package rapidFit.view.bldblocks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rapidFit.controller.IListPanelController;


@SuppressWarnings("serial")
public class DataListPanel<T> extends JPanel{
	
	//variables for displaying the list of data
	
	//store reference of the actual data to be displayed
	private IListPanelController<T> controller;
	private DataList<T> dataList;
	
	//for display the entries in the data as a list
	private int currentSelectedIndex = -1;
	
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
	
	public DataListPanel (IListPanelController<T> controller, DataList<T> dataList){
		this.controller = controller;
		this.dataList = dataList;
		
		initDataListPanel();
		initOptionPanel();
		initMainPanel();
	}
	
	public void initDataListPanel(){
		//initialise the list panel
		scrollPane = new JScrollPane(dataList);
		
		dataList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int index = dataList.locationToIndex(e.getPoint());
				if (index != -1 && 
						((currentSelectedIndex != index && e.getClickCount() == 1) ||
						(e.getClickCount() == 2))){
					currentSelectedIndex = index;
					controller.setSelectedIndex(index);
				}
			}
		});
		
		scrollPane = new JScrollPane(dataList);
	}
	
	public void initOptionPanel(){
		//initialise the option panel containing the buttons
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = dataList.getSelectedIndex();
				if (index != -1){
					controller.addRow(index);
				} else {
					controller.addRow();
				}
			}
		});
		
		btnRemove = new JButton("Remove");
		btnRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = dataList.getSelectedIndex();
				if (index != -1){
					controller.removeRow(index);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
		
		btnCopy = new JButton("Copy");
		btnCopy.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = dataList.getSelectedIndex();
				if (index != -1){
					controller.copyRow(index);
				} else {
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});

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
				optionPanel.add(btn);
			}
		}
	}
	
	public void initMainPanel(){
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		add(optionPanel, BorderLayout.SOUTH);
	}
}
