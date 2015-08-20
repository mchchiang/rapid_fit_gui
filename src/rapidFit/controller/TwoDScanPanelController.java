package rapidFit.controller;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

import com.google.common.collect.HashBiMap;

import rapidFit.data.ScanParamType;
import rapidFit.data.TwoDScanType;
import rapidFit.model.TwoDScanModel;
import rapidFit.model.dataModel.ClassModel;
import rapidFit.model.dataModel.DataEvent;
import rapidFit.model.dataModel.DataListener;
import rapidFit.model.dataModel.EditTagNameEvent;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.NullClassModel;
import rapidFit.model.dataModel.RemoveElementEvent;
import rapidFit.model.dataModel.TagNameDataModel;
import rapidFit.view.NullPanel;
import rapidFit.view.bldblocks.ListViewPanel;

public class TwoDScanPanelController implements 
Controller, DataListener, ListPanelListener {

	private ITagNameDataModel<TwoDScanType> tagNameTwoDScanModel;

	private UIController mainController;

	private IAttributeTableController<ScanParamType> xParamTableController;
	private IAttributeTableController<ScanParamType> yParamTableController;

	private IListPanelController<TwoDScanType> listPanelController;

	//private HashMap<TwoDScanType, IClassModel<ScanParamType>> xParamModelMap;
	//private HashMap<TwoDScanType, IClassModel<ScanParamType>> yParamModelMap;
	private HashBiMap<TwoDScanType, IClassModel<ScanParamType>> xParamModelMap;
	private HashBiMap<TwoDScanType, IClassModel<ScanParamType>> yParamModelMap;
	
	private JPanel displayPanel;
	private NullPanel nullPanel;
	private ListViewPanel listViewPanel;

	private TwoDScanType displayScan;

	@SuppressWarnings("unchecked")
	public TwoDScanPanelController(UIController mainController,
			Controller parentController, List<TwoDScanType> twoDScans){

		this.mainController = mainController;

		tagNameTwoDScanModel = new TagNameDataModel<TwoDScanType>(
				new TwoDScanModel(twoDScans, null), "2D_Scan");
		tagNameTwoDScanModel.addDataListener(this);

		listPanelController = new ListPanelController<TwoDScanType>(
				mainController, this, tagNameTwoDScanModel);
		listPanelController.addListPanelListener(this);

		xParamTableController = new AttributeTableController<ScanParamType>(
				mainController, this, new NullClassModel(), "X Param");
		yParamTableController = new AttributeTableController<ScanParamType>(
				mainController, this, new NullClassModel(), "Y Param");
		
		xParamModelMap = HashBiMap.create();
		yParamModelMap = HashBiMap.create();

		//create view
		displayPanel = new JPanel();
		JComponent xParamTable = xParamTableController.getView();
		JComponent yParamTable = yParamTableController.getView();
		displayPanel.setLayout(new GridLayout(0,2));
		displayPanel.add(xParamTable);
		displayPanel.add(yParamTable);	

		nullPanel = new NullPanel();

		listViewPanel = new ListViewPanel(
				listPanelController.getView(), nullPanel, "2D Scans", null);
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
		ArrayList<Controller> childControllers = new ArrayList<Controller>();
		childControllers.add(xParamTableController);
		childControllers.add(yParamTableController);
		return childControllers;
	}

	public void update(DataEvent e){
		IDataModel<?> model = e.getDataModel();
		System.out.println(e);
		if (model == tagNameTwoDScanModel.getActualModel()){
			if (e instanceof RemoveElementEvent){
				//RemoveElementEvent evt = (RemoveElementEvent) e;
				//TwoDScanType twoDScan = (TwoDScanType) evt.getRemovedElement();
				/*if (xParamModelMap.containsKey(twoDScan)){
					xParamModelMap.get(twoDScan).removeDataListener(this);
					xParamModelMap.remove(twoDScan);
				}
				if (yParamModelMap.containsKey(twoDScan)){
					yParamModelMap.get(twoDScan).removeDataListener(this);
					yParamModelMap.remove(twoDScan);
				}*/
				changeDisplayElement(null);
				listPanelController.clearSelection();
			} else if (e instanceof EditTagNameEvent){
				EditTagNameEvent evt = (EditTagNameEvent) e;
				if (displayScan == tagNameTwoDScanModel.get(evt.getIndex())){
					listViewPanel.setDisplayTitle(evt.getNewTagName());
				}
			}
		} else if (xParamModelMap.containsValue(model)) {
			TwoDScanType twoDScan = xParamModelMap.inverse().get(model);
			System.out.println(twoDScan);
			changeDisplayElement(twoDScan);
			listPanelController.setSelectedIndex(
					listPanelController.indexOf(twoDScan));
		} else if (yParamModelMap.containsValue(model)){
			TwoDScanType twoDScan = yParamModelMap.inverse().get(model);
			System.out.println(twoDScan);
			changeDisplayElement(twoDScan);
			listPanelController.setSelectedIndex(
					listPanelController.indexOf(twoDScan));
		}
	}

	@Override
	public void changedSelectedElement(int index) {
		System.out.println("Changed selected index: " + index);
		changeDisplayElement(listPanelController.get(index));		
	}

	private void changeDisplayElement(TwoDScanType twoDScan){
		System.out.println("Change display element: " + twoDScan);
		if (displayScan != twoDScan){
			displayScan = twoDScan;
			if (displayScan == null){
				listViewPanel.updateDisplayPanel(nullPanel, null);

			} else {
				if (!xParamModelMap.containsKey(twoDScan)){
					ClassModel<ScanParamType> model = 
							new ClassModel<ScanParamType>(
									ScanParamType.class, twoDScan.getXParam(), null);
					System.out.println("Add listener");
					model.addDataListener(this);
					xParamModelMap.put(twoDScan, model);
				}
				xParamTableController.setModel(xParamModelMap.get(twoDScan));

				if (!yParamModelMap.containsKey(twoDScan)){
					ClassModel<ScanParamType> model = 
							new ClassModel<ScanParamType>(
									ScanParamType.class, twoDScan.getYParam(), null);
					model.addDataListener(this);
					yParamModelMap.put(twoDScan, model);
				}
				yParamTableController.setModel(yParamModelMap.get(twoDScan));

				listViewPanel.updateDisplayPanel(displayPanel, 
						tagNameTwoDScanModel.getTagName(
								tagNameTwoDScanModel.indexOf(twoDScan)));
			}
		}
	}

	@Override
	public JComponent getView() {
		return listViewPanel;
	}
}
