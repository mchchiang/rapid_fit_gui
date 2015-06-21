package rapidFit.view;

import java.util.List;
import java.awt.*;

import javax.swing.*;

import rapidFit.view.blocks.DataListPanel;
import rapidFit.view.blocks.TagNameManager;
import rapidFit.view.blocks.TagNamePanel;

@SuppressWarnings("serial")
public abstract class AbstractDataListViewer<T> extends JPanel {
	
	//variables for the data list panel
	
	//store the display title for the list
	private String listTitle = "Available Data Sets";
	
	//store reference of the actual data to be displayed
	private List<T> data;
	private List<T> root;
	private Class<T> dataClass;
	private TagNameManager<T> tagNameManager;
	
	//for display the entries in the data as a list	panel
	private DataListPanel<T> dataListPanel;
	
	//===================================================================
	//
	//variables for the main panel
	
	//container for the entire main panel
	private JPanel mainDisplayPanel;
	
	//store the tag name suffix
	private String tagName = "Data_Set";
	
	//panel for displaying the tag name
	private TagNamePanel<T> tagNamePanel;
	
	//panel for display an entry of the data
	private JPanel dataDisplayPanel;
	
	/*
	 * requires the sub-class to determine how to display the data
	 */
	protected abstract JPanel initNullDisplayPanel();
	protected abstract JPanel initDataDisplayPanel(T entry);
	
	//constructor
	public AbstractDataListViewer(Class<T> clazz, List<T> dataRoot, List<T> data,
			String listTitle, String tagName){
		this.dataClass = clazz;
		this.root = dataRoot;
		this.data = data;
		
		if (listTitle != null){
			this.listTitle = listTitle;
		}
		if (tagName != null){
			this.tagName = tagName;
		}
		
		//set up tag names for the list of data
		tagNameManager = new TagNameManager<T>(this.data, this.tagName);
		
		initDataListPanel();
		initMainDisplayPanel();
		initMainPanel();
	}
	
	public void initDataListPanel(){
		//initialise the list panel
		dataListPanel = new DataListPanel<T>(
				dataClass, root, data, tagNameManager, null){
			protected void mouseClickedOnListEntry(T entry, int clickCount){
				changeDataDisplayPanel(entry);
			}
			protected void addButtonClicked(T entry){
				changeDataDisplayPanel(entry);
			}
			protected void removeButtonClicked(){
				changeDataDisplayPanel(null);
			}
			protected void copyButtonClicked(T entry){
				changeDataDisplayPanel(entry);
			}
		};
		dataListPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>" + listTitle + "</h3></html>"));		
	}
	
	public void initMainDisplayPanel(){
		//initialise the tag name panel
		tagNamePanel = new TagNamePanel<T>();
		
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
		mainDisplayPanel.remove(tagNamePanel);
		
		if (entry == null){
			dataDisplayPanel = initNullDisplayPanel();
			mainDisplayPanel.setBorder(BorderFactory.createTitledBorder("<html><h3>&nbsp;</h3></html>"));	
			
		} else {
			dataDisplayPanel = initDataDisplayPanel(entry);
			tagNamePanel = new TagNamePanel<T>(tagNameManager, entry){
				public void tagNameChanged(String newTagName){
					dataListPanel.updateListPanel();
					mainDisplayPanel.setBorder(BorderFactory.createTitledBorder(
							"<html><h3>" + newTagName + "</h3></html>"));	
				}
			};
			
			mainDisplayPanel.add(tagNamePanel, BorderLayout.NORTH);
			mainDisplayPanel.setBorder(BorderFactory.createTitledBorder(
					"<html><h3>" + tagNamePanel.getTagName() + "</h3></html>"));	
		}
		
		mainDisplayPanel.add(dataDisplayPanel, BorderLayout.CENTER);
		
		//refresh the display panel
		mainDisplayPanel.validate();
	}
}
