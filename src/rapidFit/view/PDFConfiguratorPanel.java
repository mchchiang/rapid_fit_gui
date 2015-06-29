package rapidFit.view;

import java.awt.*;

import javax.swing.*;

import rapidFit.model.*;
import rapidFit.view.blocks.*;

@SuppressWarnings("serial")
public class PDFConfiguratorPanel extends JPanel {
	
	
	private PDFConfiguratorType pdfConfigurator;
	private AttributePanel<PDFConfiguratorType> pdfConfigInfoPanel;
	private DataTablePanel<String> configTablePanel;
	private DataTablePanel<String> paramSubTablePanel;
	
	private JPanel configAndParamSubPanel;
	
	public PDFConfiguratorPanel (PDFConfiguratorType config){
		this.pdfConfigurator = config;
		
		pdfConfigInfoPanel = new AttributePanel<PDFConfiguratorType>(
				PDFConfiguratorType.class, pdfConfigurator, "Configuration Details", null);
		
		configTablePanel = new DataTablePanel<String>(
				String.class, pdfConfigurator.getConfigurationParameter(), null, 
				"Add Config", "Remove Config", "Copy Config");
		configTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Configuration Parameters</html></h3>"));
		
		paramSubTablePanel = new DataTablePanel<String>(String.class, pdfConfigurator.getParameterSubstitution(), null,
				"Add Param Sub", "Remove Param Sub", "Copy Param Sub");
		paramSubTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Parameter Substitutions</html></h3>"));
		
		configAndParamSubPanel = new JPanel();
		configAndParamSubPanel.setLayout(new GridLayout(0,2));
		configAndParamSubPanel.add(configTablePanel);
		configAndParamSubPanel.add(paramSubTablePanel);
		
		setLayout(new GridLayout(0,1));
		add(pdfConfigInfoPanel);
		add(configAndParamSubPanel);		
	}
}
