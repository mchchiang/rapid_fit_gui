package rapidFit.controller;

import java.awt.GridLayout;
import java.awt.Window;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import rapidFit.RapidFitEditorMenuBar;
import rapidFit.controller.command.Command;
import rapidFit.data.ComponentProjectionType;
import rapidFit.data.ExternalConstMatrixType;
import rapidFit.data.ExternalConstraintType;
import rapidFit.data.FitFunctionType;
import rapidFit.data.MinimiserType;
import rapidFit.data.ObservableType;
import rapidFit.data.PhysicsParameterType;
import rapidFit.data.PrecalculatorType;
import rapidFit.data.RapidFitType;
import rapidFit.data.ScanParamType;
import rapidFit.data.ToFitType;
import rapidFit.data.TwoDScanType;
import rapidFit.model.dataModel.ClassModel;
import rapidFit.model.dataModel.DataModel;
import rapidFit.model.dataModel.IClassModel;
import rapidFit.model.dataModel.IDataModel;
import rapidFit.model.dataModel.ITagNameDataModel;
import rapidFit.model.dataModel.TagNameDataModel;
import rapidFit.view.RapidFitMainFrame;

/**
 * 
 * The main controller and the entry point of the programme
 * 
 * @author MichaelChiang
 *
 */

public class RapidFitEditor extends UIController implements CommandListener{
	
	private RapidFitMainFrame mainFrame;
	
	private CommandHandler commandHandler;
	
	private HashMap<Controller, JComponent> controlViewPair;
	
	public RapidFitEditor(){
		commandHandler = new CommandHandler(this);
		
		mainFrame = new RapidFitMainFrame();
		//showStartUpScene();
	}
	
	public void showStartUpScene(){
		mainFrame.createStartUpScene();
	}
	
	public void showLoadedXMLFrame(RapidFitType root, String fileName){
		controlViewPair = new HashMap<Controller, JComponent>();
		LinkedHashMap<JComponent, String> componentTitlePair = new LinkedHashMap<JComponent, String>();
		
		//load all data and create all the controllers
		
		//create the controller for the parameter set panel
		IDataModel<PhysicsParameterType> physicsParams = 
				new DataModel<PhysicsParameterType>(PhysicsParameterType.class,
						root.getParameterSet().getPhysicsParameter(), null);
		IDataTableController<PhysicsParameterType> physicsParamsController = new 
				DataTableController<PhysicsParameterType>(this, this, physicsParams,
						"Add Parameter", "Remove Parameter(s)", "Duplicate Parameter(s)");
		controlViewPair.put(physicsParamsController, physicsParamsController.getView());
		componentTitlePair.put(physicsParamsController.getView(), "Parameter Set");
		
		//create the controllers for the fitting options panel
		IClassModel<FitFunctionType> fitFunction = 
				new ClassModel<FitFunctionType>(FitFunctionType.class, root.getFitFunction(), null);
		IAttributeTableController<FitFunctionType> fitFunctionController = 
				new AttributeTableController<FitFunctionType>(this, this, fitFunction, "Fit Function");
		
		IClassModel<MinimiserType> minimiser = 
				new ClassModel<MinimiserType>(MinimiserType.class, root.getMinimiser(), null);
		IAttributeTableController<MinimiserType> minimiserController = 
				new AttributeTableController<MinimiserType>(this, this, minimiser, "Miminiser");
		
		IClassModel<PrecalculatorType> precalculator =
				new ClassModel<PrecalculatorType>(PrecalculatorType.class,	root.getPrecalculator(), null);
		IAttributeTableController<PrecalculatorType> precalculatorController = 
				new AttributeTableController<PrecalculatorType>(this, this, precalculator, "Precalculator");
		
		//find all attributes to be ignored
		ArrayList<String> ignoreAttr = new ArrayList<String>();
		for (Method m : RapidFitType.class.getDeclaredMethods()){
			if (!m.getName().equals("getSeed") && 
					!m.getName().equals("getNumberRepeats")){
				if (m.getName().startsWith("get")){
					ignoreAttr.add(m.getName().substring(3));
				} else if (m.getName().startsWith("is")){
					ignoreAttr.add(m.getName().substring(2));
				}
			}
		}
		
		IClassModel<RapidFitType> otherFitOptions = 
				new ClassModel<RapidFitType>(RapidFitType.class, root, ignoreAttr);
		IAttributeTableController<RapidFitType> otherFitOptionsController = 
				new AttributeTableController<RapidFitType>(this, this, otherFitOptions, "Other Options");
		
		JPanel fitOptionsPanel = new JPanel();
		fitOptionsPanel.setLayout(new GridLayout(2,2));
		fitOptionsPanel.add(fitFunctionController.getView());
		fitOptionsPanel.add(minimiserController.getView());
		fitOptionsPanel.add(precalculatorController.getView());
		fitOptionsPanel.add(otherFitOptionsController.getView());
		
		
		controlViewPair.put(fitFunctionController, fitOptionsPanel);
		controlViewPair.put(minimiserController, fitOptionsPanel);
		controlViewPair.put(precalculatorController, fitOptionsPanel);
		controlViewPair.put(otherFitOptionsController, fitOptionsPanel);
		componentTitlePair.put(fitOptionsPanel, "Fit Options");
		
		//common properties panel
		//common phase space
		IDataModel<ObservableType> observables = 
				new DataModel<ObservableType>(ObservableType.class, 
						root.getCommonPhaseSpace().getPhaseSpaceBoundary().getObservable(), null);
		IDataTableController<ObservableType> observablesController = new 
				DataTableController<ObservableType>(this, this, observables,
						"Add Observable", "Remove Observable(s)", "Duplicate Observable(s)");
		JComponent observablesTable = observablesController.getView();
		observablesTable.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common Phase Space</h3></html>"));
		
		//common PDF
		PDFViewController commonPDFController = new PDFViewController(
				this, this, root.getCommonPDF(), 
				root.getParameterSet().getPhysicsParameter());
		JComponent commonPDFViewer = commonPDFController.getView();
		commonPDFViewer.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Common PDF</h3></html>"));
		
		JPanel commonPropertiesPanel = new JPanel();
		commonPropertiesPanel.setLayout(new GridLayout(0,1));
		commonPropertiesPanel.add(observablesTable);
		commonPropertiesPanel.add(commonPDFViewer);
		
		controlViewPair.put(observablesController, commonPropertiesPanel);
		controlViewPair.put(commonPDFController, commonPropertiesPanel);
		componentTitlePair.put(commonPropertiesPanel, "Common Options");
		
		//separate actual fit and fit constraints
		IDataModel<ToFitType> fits = new DataModel<ToFitType>(
				ToFitType.class, root.getToFit(), null);
		
		List<ToFitType> toFits = root.getToFit();
		ToFitType constraintFit = null;
		ArrayList<ToFitType> actualFits = new ArrayList<ToFitType>();

		for (ToFitType fit : toFits){
			if (fit.getConstraintFunction() == null){
				actualFits.add(fit);

				/*
				 * there should only be one constraint function. All constraint
				 * functions are combined into a single constraint function in 
				 * the factory
				 */
			} else {
				constraintFit = fit;
			}
		}
		
		DataSetPanelController dataSetPanelController = new DataSetPanelController(
				this, actualFits, null);
		controlViewPair.put(dataSetPanelController, dataSetPanelController.getView());
		componentTitlePair.put(dataSetPanelController.getView(), "Data Set");
		
		//for displaying external constraints and external constraint matrices
		IDataModel<ExternalConstraintType> constraints = new DataModel<ExternalConstraintType>(
				ExternalConstraintType.class, 
				constraintFit.getConstraintFunction().getExternalConstraint(), null);
		IDataTableController<ExternalConstraintType> constraintsTableController = 
				new DataTableController<ExternalConstraintType>(
						this, this, constraints, 
						"Add Constraint", "Remove Constraint(s)", "Duplicate Constraint(s)");
		
		IDataModel<ExternalConstMatrixType> constMatrices = new DataModel<ExternalConstMatrixType>(
				ExternalConstMatrixType.class, 
				constraintFit.getConstraintFunction().getExternalConstMatrix(), null);
		IDataTableController<ExternalConstMatrixType> constMatrixTableController = 
				new DataTableController<ExternalConstMatrixType>(
						this, this, constMatrices, 
						"Add Const. Matrix", "Remove Const. Matrix", "Duplicate Const. Matrix");
		
		
		JComponent constraintTablePanel = constraintsTableController.getView();
		JComponent constMatrixTablePanel = constMatrixTableController.getView();
		
		Border border = BorderFactory.createTitledBorder(
				"<html><h3>External Constraints</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(0,10,10,0);
		constraintTablePanel.setBorder(new CompoundBorder(margin, border));
		
		border = BorderFactory.createTitledBorder(
				"<html><h3>External Constraint Matrices</html></h3>");
		constMatrixTablePanel.setBorder(new CompoundBorder(margin, border));
		
		JPanel constraintPanel = new JPanel();
		constraintPanel.setLayout(new GridLayout(0,2));
		constraintPanel.add(constraintTablePanel);
		constraintPanel.add(constMatrixTablePanel);
		
		controlViewPair.put(constraintsTableController, constraintPanel);
		controlViewPair.put(constMatrixTableController, constraintPanel);
		componentTitlePair.put(constraintPanel, "Fit Constraints");
		
		//for output projections
		ITagNameDataModel<ComponentProjectionType> projections = 
				new TagNameDataModel<ComponentProjectionType>(
						new DataModel<ComponentProjectionType>(
								ComponentProjectionType.class, 
								root.getOutput().getComponentProjection(), null), "Comp_Proj");	
		ListViewController<ComponentProjectionType> projectionsListController =
				new ListViewController<ComponentProjectionType>(
						this, projections, "Available Projections", "Projection Details");
		controlViewPair.put(projectionsListController, projectionsListController.getView());
		componentTitlePair.put(projectionsListController.getView(), "Ouptut - Projections");
		
		
		//for scans and 2D scans
		ITagNameDataModel<ScanParamType> scans =
				new TagNameDataModel<ScanParamType>(
						new DataModel<ScanParamType>(ScanParamType.class,
								root.getOutput().getScan(), null), "Scan");
		
		ListViewController<ScanParamType> scansListController =
				new ListViewController<ScanParamType>(
						this, scans, "Available Scans", "Scan Details");
		
		
		TwoDScanPanelController twoDScanController =
				new TwoDScanPanelController(this, this, root.getOutput().getTwoDScan());
		controlViewPair.put(twoDScanController, twoDScanController.getView());
		componentTitlePair.put(twoDScanController.getView(), "2D Scans");
		
		
		mainFrame.createLoadedFileScene(componentTitlePair, fileName);
	}
	
	public void undo(){
		commandHandler.undo();
	}
	
	public void redo(){
		commandHandler.redo();
	}
	
	@Override
	public void undoOccurred(Command cmd) {
		RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(true);
		if (commandHandler.hasUndoableCommand()){
			RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(true);
		} else {
			RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(false);
		}
	}

	@Override
	public void redoOccurred(Command cmd) {
		RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(true);
		if (commandHandler.hasRedoableCommand()){
			RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(true);
		} else {
			RapidFitEditorMenuBar.getInstance().getRedoButton().setEnabled(false);
		}
	}

	@Override
	public void commandExecuted(Command cmd) {
		if (commandHandler.hasUndoableCommand()){
			RapidFitEditorMenuBar.getInstance().getUndoButton().setEnabled(true);
		} 
	}

	@Override
	public void setCommand(Command cmd) {
		commandHandler.setCommand(cmd);
	}

	@Override
	public void setActiveController(Controller controller) {
		//for switching to the tab where the active controller is
		if (commandHandler.getActiveController() != controller) {
			commandHandler.setActiveController(controller);
			Controller c = findParentController(controller);
			JComponent cmp = controlViewPair.get(c);
			if (cmp != null){
				mainFrame.setActiveTab(cmp);
			}
		}
	}
	
	/*
	 * a recursive method to find the top controller that controls
	 * the sub-controller (excluding this controller)
	 */
	private Controller findParentController(Controller c) {
		if (c == null) {
			return null;
		} else if (c.getParentController() == this) {
			return c;
		} else {
			return findParentController(c.getParentController());
		}
	}

	@Override
	public Controller getActiveController() {
		return commandHandler.getActiveController();
	}

	@Override
	public Controller getParentController() {return null;}

	@Override
	public List<Controller> getChildControllers() {
		List<Controller> controllers = new ArrayList<Controller>();
		controllers.addAll(controlViewPair.keySet());
		return controllers;
	}

	@Override
	public JComponent getView() {
		return null;
	}

	@Override
	public Window getWindow() {
		return mainFrame;
	}

}
