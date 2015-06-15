package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFEditor extends JDialog implements ActionListener {
	
	private AttributePanel<PDFType> pdfPanel;
	
	private DataTablePanel<ConfigParam> configTablePanel;
	
	private JPanel mainPanel;
	
	private JButton btnSave;
	
	private List<ConfigParam> configs;
	private PDFType pdf;
	
	private static class ConfigParam {
		private String configParam;
		public ConfigParam(){}
		public ConfigParam(String config){
			setConfigurationParameter(config);
		}
		public void setConfigurationParameter(String config){
			configParam = config;
		}
		public String getConfigurationParameter(){
			return configParam;
		}
	}
	
	public PDFEditor(PDFType pdf){
		setTitle("PDF Editor");
		setResizable(true);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.pdf = pdf;
		
		pdfPanel = new AttributePanel<PDFType>
		(PDFType.class, pdf, "PDF Info", null);
		
		configs = new ArrayList<ConfigParam>();
		for (String config : pdf.getConfigurationParameter()){
			new ConfigParam();
			configs.add(new ConfigParam(config));
		}
		
		configTablePanel = new DataTablePanel<ConfigParam>(
				ConfigParam.class, configs, null,
				"Add Config", "Remov Config", "Copy Config");
		
		Border border = BorderFactory.createTitledBorder(
				"<html><h3>Configuration Parameters</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		configTablePanel.setBorder(new CompoundBorder(margin, border));

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		mainPanel.add(pdfPanel);
		mainPanel.add(configTablePanel);
		
		btnSave = new JButton("Save Edit and Close");
		btnSave.addActionListener(this);
		
		Container content = getContentPane();
		content.add(mainPanel, BorderLayout.CENTER);
		content.add(btnSave, BorderLayout.SOUTH);
		
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave){
			/*
			 * need to retrieve all the new configs from
			 * the table and save it to the pdf
			 */
			//clear old configurations
			pdf.getConfigurationParameter().clear();
			for (ConfigParam config : configs){
				pdf.getConfigurationParameter().add(
						config.getConfigurationParameter());
			}
			
			dispose();	
		} 
	}
	
}
