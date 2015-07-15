package rapidFit.controller;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import rapidFit.model.ClassModelAdapter;
import rapidFit.model.IClassModel;
import rapidFit.model.ITagNameDataModel;
import rapidFit.model.NullClassModel;

public class ListViewController<T> extends IListViewController<T> {
	
	private UIController mainController;
	private IAttributeTableController<T> attributeTableController;
	private IListPanelController<T> listPanelController;
	private ITagNamePanelController tagNamePanelController;
	private ITagNameDataModel<T> model;
	private HashMap<T, IClassModel<T>> modelMap;
	
	private JPanel displayPanel;
	
	private JPanel nullPanel;

	@SuppressWarnings("unchecked")
	public ListViewController(UIController mainController, 
			ITagNameDataModel<T> model, String listTitle, 
			String attributeTableTitle) {
		this.model = model;
		this.mainController = mainController;
		
		listPanelController = new ListPanelController<T>(
				mainController, this, model);
		tagNamePanelController = new TagNamePanelController(
				mainController, listPanelController, this, model);
		attributeTableController = new AttributeTableController<T>(
				mainController, this, new NullClassModel(), attributeTableTitle);
		
		modelMap = new HashMap<T, IClassModel<T>>();
		
		displayPanel = new JPanel();
		displayPanel.setLayout(new BorderLayout());
		
		//create display panel
		displayPanel.add(tagNamePanelController.getView(), BorderLayout.NORTH);
		displayPanel.add(attributeTableController.getView(), BorderLayout.CENTER);
		
		//create null panel
		nullPanel = new JPanel();
		nullPanel.setLayout(new BorderLayout());
		
		JTextPane txtNoData = new JTextPane();
		txtNoData.setText("There is no entry selected.");
		StyledDocument doc = txtNoData.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		txtNoData.setEditable(false);
		txtNoData.setBackground(nullPanel.getBackground());
		
		nullPanel.add(txtNoData, BorderLayout.CENTER);
		
		setModel(model);
		setListPanelController(listPanelController);
		initView(listTitle, null);
	}
	
	@Override
	public JPanel getView(T object){
		
		if (object != null){
			IClassModel<T> model;
			if (!modelMap.containsKey(object)){
				model = createModel(object);
				modelMap.put(object, model);
			} else {
				model = modelMap.get(object);
			}
			attributeTableController.setModel(model);
			
			//setDisplayTitle(this.model.getTagName(object));
			return displayPanel;
		
		//create the display when no element is selected
		} else {
			//setDisplayTitle(null);
			return nullPanel;
		}
	}
	
	private IClassModel<T> createModel(T object){
		IClassModel<T> model = new ClassModelAdapter<T>(
				listPanelController.getModel(), object);
		return model;
	}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		ArrayList<Controller> childControllers = new ArrayList<Controller>();
		childControllers.add(attributeTableController);
		childControllers.add(listPanelController);
		childControllers.add(tagNamePanelController);
		return childControllers;
	}
	
	@Override
	public void activateController() {
		mainController.setActiveController(this);
	}
}
