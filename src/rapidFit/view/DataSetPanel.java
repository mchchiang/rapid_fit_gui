package rapidFit.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import rapidFit.controller.DataSetPanelController;
import rapidFit.controller.IAttributeTableController;
import rapidFit.controller.IDataTableController;
import rapidFit.controller.ITagNamePanelController;
import rapidFit.data.DataSetType;
import rapidFit.data.ObservableType;

@SuppressWarnings("serial")
public class DataSetPanel extends JPanel {
	
	private DataSetPanelController mainController;
	
	private JCheckBox cbCommonPhaseSpace;
	private JCheckBox cbCommonPDF;

	private JPanel phaseSpacePanel;
	private JPanel phaseSpaceOptionPanel;

	private JPanel pdfPanel;
	private JPanel pdfOptionPanel;

	private JLabel lblUseCommonPhaseSpace;
	private JLabel lblUseCommonPDF;
	
	private JPanel mainPanel;

	public DataSetPanel(DataSetPanelController dataSetPanelController,
			ITagNamePanelController tagNamePanelController,
			IAttributeTableController<DataSetType> pdfDetailsController,
			IDataTableController<ObservableType> observablesTableController){
		
		mainController = dataSetPanelController;
		
		cbCommonPhaseSpace = new JCheckBox();	
		cbCommonPhaseSpace.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbCommonPhaseSpace.isSelected()){
					mainController.switchToCommonPhaseSpace();
				} else {
					mainController.switchToIndividualPhaseSpace();
				}
			}
		});
		
		lblUseCommonPhaseSpace = new JLabel("Use Common Phase Space");
		
		phaseSpaceOptionPanel = new JPanel();
		phaseSpaceOptionPanel.add(lblUseCommonPhaseSpace);
		phaseSpaceOptionPanel.add(cbCommonPhaseSpace);
		
		phaseSpacePanel = new JPanel();
		phaseSpacePanel.setLayout(new BorderLayout());
		phaseSpacePanel.add(observablesTableController.getView(), BorderLayout.CENTER);
		phaseSpacePanel.add(phaseSpaceOptionPanel, BorderLayout.SOUTH);
		phaseSpacePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Phase Space</h3></html>"));
		
		cbCommonPDF = new JCheckBox();	
		cbCommonPDF.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbCommonPDF.isSelected()){
					mainController.switchToCommonPDF();
				} else {
					mainController.switchToIndividualPDF();
				}
			}
		});
		
		lblUseCommonPDF = new JLabel("Use Common PDF");
		
		pdfOptionPanel = new JPanel();
		pdfOptionPanel.add(lblUseCommonPDF);
		pdfOptionPanel.add(cbCommonPDF);
		
		pdfPanel = new JPanel();
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF</h3></html>"));
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(pdfOptionPanel, BorderLayout.SOUTH);
		
		//set layout for the components
		mainPanel = new JPanel();		
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;

		//for details panel
		c.insets = new Insets(-10,10,0,10);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 0.5;
		mainPanel.add(pdfDetailsController.getView(), c);

		//for phase space panel
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,10,0,10);
		c.ipadx = 10;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		mainPanel.add(phaseSpacePanel, c);

		//for pdf panel
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0,10,0,10);
		c.ipadx = 10;
		c.ipady = 5;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 0.8;
		mainPanel.add(pdfPanel, c);
		
		this.setLayout(new BorderLayout());
		this.add(tagNamePanelController.getView(), BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}
	
	public void selectCommonPhaseSpaceCheckBox(boolean b){
		cbCommonPhaseSpace.setSelected(b);
	}
	
	public void selectCommonPDFCheckBox(boolean b){
		cbCommonPDF.setSelected(b);
	}
	
	
}
