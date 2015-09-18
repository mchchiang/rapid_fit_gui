package rapidFit.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;

import rapidFit.controller.command.SwitchPhaseSpaceCommand;
import rapidFit.data.DataSetType;
import rapidFit.data.ObservableType;
import rapidFit.data.PDFConfiguratorType;
import rapidFit.data.PhaseSpaceBoundaryType;
import rapidFit.data.PhysicsParameterType;
import rapidFit.data.ToFitType;
import rapidFit.model.DataSetModel;
import rapidFit.model.dataModel.ClassModel;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.DataModel;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.NullClassModel;
import rapidFit.model.dataModel.NullDataModel;
import rapidFit.model.dataModel.TagNameDataModel;
import rapidFit.model.dataModel.event.DataEvent;
import rapidFit.model.dataModel.event.RemoveElementEvent;
import rapidFit.view.DataSetPanel;
import rapidFit.view.NullPanel;
import rapidFit.view.bldblocks.ListViewPanel;

public class DataSetPanelController implements Controller, DataListener, ListPanelListener {
	
	private UIController mainController;
	private IListPanelController<ToFitType> listPanelController;
	private ITagNamePanelController tagNamePanelController;
	private IAttributeTableController<DataSetType> dataSetDetailsTableController;
	private IDataTableController<ObservableType> observablesTableController;
	private PDFViewController pdfViewController;
	
	private HashMap<DataSetType, IClassModel<DataSetType>> dataSetModelMap;
	private HashMap<DataSetType, IDataModel<ObservableType>> observableModelMap;
	
	private DataSetModel dataSetModel;
	private ITagNameDataModel<ToFitType> tagNameDataSetModel;
	
	private ArrayList<String> ignoreAttributes;
	
	private List<PhysicsParameterType> physicsParams;
	
	private DataSetPanel dataSetPanel;
	private NullPanel nullPanel;
	
	private ListViewPanel listViewPanel;
	
	private ToFitType displayElement;
	
	@SuppressWarnings("unchecked")
	public DataSetPanelController(UIController mainController, 
			List<ToFitType> toFits,
			List<PhysicsParameterType> physicsParams){
		this.mainController = mainController;
		this.physicsParams = physicsParams;
		
		//create models
		dataSetModel = new DataSetModel(toFits, null);
		tagNameDataSetModel = new TagNameDataModel<ToFitType>(dataSetModel, "Data_Set");
			
		ignoreAttributes = new ArrayList<String>();
		ignoreAttributes.add("CommonPhaseSpace");
		ignoreAttributes.add("PhaseSpaceBoundary");
		
		dataSetModelMap = new HashMap<DataSetType, IClassModel<DataSetType>>();
		observableModelMap = new HashMap<DataSetType, IDataModel<ObservableType>>();
		
		//create controllers
		listPanelController = new ListPanelController<ToFitType>(
				mainController, this, tagNameDataSetModel);
		listPanelController.addListPanelListener(this);
		
		tagNamePanelController = new TagNamePanelController(
				mainController, listPanelController, this, tagNameDataSetModel, -1);
		dataSetDetailsTableController = new AttributeTableController<DataSetType>(
				mainController, mainController, new NullClassModel(), "Data Set Details");
		observablesTableController = new DataTableController<ObservableType>(
				mainController, this, new NullDataModel(), 
				"Add Observable", "Remove Observable(s)", "Duplicate Observable(s)");
		
		//create view
		dataSetPanel = new DataSetPanel(this, 
				tagNamePanelController, 
				dataSetDetailsTableController, 
				observablesTableController);
		nullPanel = new NullPanel();
		listViewPanel = new ListViewPanel(
				listPanelController.getView(), nullPanel, "Data Sets", null);
	}

	@Override
	public void activateController() {}

	@Override
	public void deactivateController() {}

	@Override
	public Controller getParentController() {
		return mainController;
	}

	@Override
	public List<Controller> getChildControllers() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void switchToCommonPhaseSpace(){
		mainController.setCommand(new SwitchPhaseSpaceCommand(
				dataSetModel, listPanelController.getSelectedIndex(), true));
	}
	
	public void switchToIndividualPhaseSpace(){
		mainController.setCommand(new SwitchPhaseSpaceCommand(
				dataSetModel, listPanelController.getSelectedIndex(), false));
	}
	
	public void switchToCommonPDF(){
		
	}
	
	public void switchToIndividualPDF(){
		
	}
	
	public void changeDisplayElement(ToFitType toFit){
		if (displayElement != toFit){
			displayElement = toFit;
			if (displayElement == null){
				listViewPanel.updateDisplayPanel(nullPanel, null);
				
			} else {
				DataSetType dataSet = toFit.getDataSet();
				/*
				 * create models for displaying the data set details
				 * and the phase space observables for elements 
				 * that have not been displayed before
				 */
				if (!dataSetModelMap.containsKey(dataSet)){
					dataSetModelMap.put(dataSet, new ClassModel<DataSetType>(
							DataSetType.class, dataSet, ignoreAttributes));
				}
				dataSetDetailsTableController.setModel(dataSetModelMap.get(dataSet));
				
				if (!observableModelMap.containsKey(dataSet)){
					if (dataSet.getCommonPhaseSpace() != null){
						observableModelMap.put(dataSet, new DataModel<ObservableType>(
								ObservableType.class, 
								dataSet.getCommonPhaseSpace().getObservable(), null));
					} else {
						observableModelMap.put(dataSet, new DataModel<ObservableType>(
								ObservableType.class,
								dataSet.getPhaseSpaceBoundary().getObservable(), null));
					}
				}
				observablesTableController.setModel(observableModelMap.get(dataSet));
				
				//display the PDF Configurator panel if the fit uses common PDF
				/*
				 * pdf configurator is guaranteed to be non-null for fits that use
				 * common PDF as this is checked during instantiation
				 */
				
				if (toFit.isCommonPDF()){
					
				} else {
					pdfViewController = new PDFViewController(
							mainController, this, toFit, physicsParams);
				}
				
				//update the entire panel to display the new selected data set
				int index = dataSetModel.indexOf(displayElement);
				listViewPanel.updateDisplayPanel(dataSetPanel, 
						tagNameDataSetModel.getTagName(index));
			}
		}
	}
	
	@Override
	public void update(DataEvent e){
		if (e.getDataModel() == dataSetModel){
			if (e instanceof RemoveElementEvent){
				changeDisplayElement(null);
				listPanelController.clearSelection();
			} 
		} else if (dataSetModelMap.containsValue(e.getDataModel())){
			
			
		} else if (observableModelMap.containsValue(e.getDataModel())){
			
		}
	}
	
	@Override
	public void changedSelectedElement(int index){
		changeDisplayElement(listPanelController.get(index));
	}
	
	@Override
	public JComponent getView(){
		return listViewPanel;
	}
}
