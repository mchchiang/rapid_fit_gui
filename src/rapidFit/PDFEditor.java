package rapidFit;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import rapidFit.data.*;

@SuppressWarnings("serial")
public class PDFEditor extends JDialog implements ActionListener {
	
	private final int width = 700;
	private final int height = 550;
	
	private AttributePanel<PDFType> pdfInfoPanel;
	private TagNamePanel<PDFType> tagNamePanel;
	private JPanel pdfPanel;
	
	private DataTablePanel<ConfigParam> configTablePanel;
	private DataTablePanel<ParameterSubstitution> paramSubTablePanel;
	
	private JPanel configAndParamSubPanel;
	private JPanel mainPanel;
	
	private JButton btnSave;
	
	private List<ConfigParam> configs;
	private List<ParameterSubstitution> paramSubs;
	private PDFType pdf;
	
	public PDFEditor(PDFType pdf, PDFManager manager){
		setTitle("PDF Editor");
		setResizable(true);
		setModal(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));
		
		this.pdf = pdf;
		
		pdfInfoPanel = new AttributePanel<PDFType>
		(PDFType.class, pdf, "PDF Info", null);
		
		tagNamePanel = new TagNamePanel<PDFType>(manager, this.pdf);
		
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(tagNamePanel, BorderLayout.NORTH);
		pdfPanel.add(pdfInfoPanel, BorderLayout.CENTER);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Details</html></h3>"));
		
		configs = new ArrayList<ConfigParam>();
		for (String config : pdf.getConfigurationParameter()){
			configs.add(new ConfigParam(config));
		}
		
		configTablePanel = new DataTablePanel<ConfigParam>(
				ConfigParam.class, configs, null,
				"Add Config", "Remove Config", "Copy Config");
		configTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Configuration Parameters</html></h3>"));
		
		paramSubs = new ArrayList<ParameterSubstitution>();
		for (String paramSub : pdf.getParameterSubstitution()){
			paramSubs.add(new ParameterSubstitution(paramSub));
		}
		
		paramSubTablePanel = new DataTablePanel<ParameterSubstitution>(
				ParameterSubstitution.class, paramSubs, null,
				"Add Param Sub", "Remove Param Sub", "Copy Param Sub");
		paramSubTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Parameter Substitutions</html></h3>"));
		
		configAndParamSubPanel = new JPanel();
		configAndParamSubPanel.setLayout(new GridLayout(2,1));
		configAndParamSubPanel.add(configTablePanel);
		configAndParamSubPanel.add(paramSubTablePanel);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,2));
		mainPanel.add(pdfPanel);
		mainPanel.add(configAndParamSubPanel);
		
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
				//only add non-null elements
				if (config.getConfigurationParameter() != null){
					pdf.getConfigurationParameter().add(
							config.getConfigurationParameter());
				}
			}
			
			pdf.getParameterSubstitution().clear();
			for (ParameterSubstitution paramSub : paramSubs){
				//only add non-null elements
				if (paramSub.getParameterSubstitution() != null){
					pdf.getParameterSubstitution().add(
							paramSub.getParameterSubstitution());
				}
			}
			
			dispose();	
		} 
	}
	
}
