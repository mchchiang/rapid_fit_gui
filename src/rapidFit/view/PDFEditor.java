package rapidFit.view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Iterator;

import javax.swing.*;

import rapidFit.main.RapidFitMainControl;
import rapidFit.model.*;
import rapidFit.view.blocks.AttributePanel;
import rapidFit.view.blocks.DataTablePanel;
import rapidFit.view.blocks.TagNamePanel;

@SuppressWarnings("serial")
public class PDFEditor extends JDialog implements ActionListener {
	
	private final int width = 700;
	private final int height = 550;
	
	private AttributePanel<PDFType> pdfInfoPanel;
	private TagNamePanel<PDFType> tagNamePanel;
	private JPanel pdfPanel;
	
	private DataTablePanel<String> configTablePanel;
	private DataTablePanel<String> paramSubTablePanel;
	
	private JPanel configAndParamSubPanel;
	private JPanel mainPanel;
	
	private JButton btnSave;
	private PDFType pdf;
	
	public PDFEditor(PDFType pdf, PDFManager manager){
		
		//set window properties
		setTitle("PDF Editor");
		setResizable(true);
		setModal(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));
		
		this.pdf = pdf;
		
		pdfInfoPanel = new AttributePanel<PDFType>
		(PDFType.class, pdf, "PDF Info", null);
		
		tagNamePanel = new TagNamePanel<PDFType>(manager, pdf);
		
		pdfPanel = new JPanel();
		pdfPanel.setLayout(new BorderLayout());
		pdfPanel.add(tagNamePanel, BorderLayout.NORTH);
		pdfPanel.add(pdfInfoPanel, BorderLayout.CENTER);
		pdfPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>PDF Details</html></h3>"));
		
		configTablePanel = new DataTablePanel<String>(
				String.class, pdf.getConfigurationParameter(), null,
				"Add Config", "Remove Config", "Copy Config");
		configTablePanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Configuration Parameters</html></h3>"));
		
		paramSubTablePanel = new DataTablePanel<String>(
				String.class, pdf.getParameterSubstitution(), null,
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
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				RapidFitMainControl.getInstance().stopTableEditing();
				savePDF();
				dispose();
			}
		});
		pack();
	}
	
	private void removeNullString(List<String> data){
		Iterator<String> it = data.iterator();
		while (it.hasNext()){
			String value = it.next();
		
			if (value == null || value.equals("")){
				it.remove();
			}
		}
	}
	
	private void savePDF(){
		/*
		 * remove any null String in configuration parameter and
		 * parameter substitution
		 */
		removeNullString(pdf.getConfigurationParameter());
		removeNullString(pdf.getParameterSubstitution());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnSave){
			savePDF();
			dispose();	
		} 
	}
	
}
