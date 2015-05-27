package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFEditor extends JDialog implements ActionListener {
	
	private AttributePanel<PDFType> pdfPanel;
	
	private DataPanel<ConfigParam> configTablePanel;
	private JPanel configControlPanel;
	private JPanel configPanel;
	
	private JPanel mainPanel;
	
	private JButton btnAddConfig;
	private JButton btnRemoveConfig;
	
	private JButton btnSave;
	
	private List<ConfigParam> configs;
	private PDFType pdf;
	
	protected static class ConfigParam {
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
		
		configTablePanel = new DataPanel<ConfigParam>
		(ConfigParam.class, configs, null);
		
		btnAddConfig = new JButton("Add Config");
		btnAddConfig.addActionListener(this);
		btnRemoveConfig = new JButton("Remove Config");
		btnRemoveConfig.addActionListener(this);
		
		configControlPanel = new JPanel();
		configControlPanel.add(btnAddConfig);
		configControlPanel.add(btnRemoveConfig);
		
		configPanel = new JPanel();
		configPanel.setLayout(new BorderLayout());
		configPanel.add(configTablePanel, BorderLayout.CENTER);
		configPanel.add(configControlPanel, BorderLayout.SOUTH);
		
		Border border = BorderFactory.createTitledBorder(
				"<html><h3>Configure Parameters</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		configPanel.setBorder(new CompoundBorder(margin, border));

		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		mainPanel.add(pdfPanel);
		mainPanel.add(configPanel);
		
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
			pdf.getConfigurationParameter().clear();
			for (ConfigParam config : configs){
				pdf.getConfigurationParameter().add(
						config.getConfigurationParameter());
			}
			dispose();
		} else if (e.getSource() == btnAddConfig){
			configTablePanel.addRow();
		} else if (e.getSource() == btnRemoveConfig){
			configTablePanel.removeSelectedRows();
		}
	}
	
}
