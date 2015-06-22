package rapidFit.view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import rapidFit.main.RapidFitMainControl;

@SuppressWarnings("serial")
public class SettingsDialog extends JDialog implements ActionListener {
	
	private final int width = 400;
	private final int height = 150;
	
	private JPanel settingPanel;
	
	private JLabel lblEnforceSchema;
	private JCheckBox cbEnforceSchema;
	
	private JLabel lblFileInfo;
	private JTextArea txtFileInfo;
	
	public SettingsDialog(){
		
		//set window properties
		setTitle("Settings and File Info");
		setModal(true);
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		ArrayList<JComponent> components = new ArrayList<JComponent>();
		
		lblEnforceSchema = new JLabel("Enforce Schema: ");
		components.add(lblEnforceSchema);
		
		cbEnforceSchema = new JCheckBox();
		cbEnforceSchema.setSelected(
				RapidFitMainControl.getInstance().isSchemaEnforced());
		cbEnforceSchema.addActionListener(this);
		components.add(cbEnforceSchema);
		
		lblFileInfo = new JLabel("File Directory: ");
		components.add(lblFileInfo);
		
		if (RapidFitMainControl.getInstance().getFile() != null){
			txtFileInfo = new JTextArea(
					RapidFitMainControl.getInstance().getFile().getAbsolutePath());
		} else {
			txtFileInfo = new JTextArea("No XML file displayed currently.");
		}
		
		txtFileInfo.setEditable(false);
		txtFileInfo.setLineWrap(true);
		txtFileInfo.setBackground(getBackground());
		components.add(txtFileInfo);
		
		settingPanel = new JPanel();
		settingPanel.setLayout(new GridBagLayout());
		settingPanel.setBorder(BorderFactory.createTitledBorder(
				"<html><h3>Settings and File Info</h3></html>"));
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,10,0,10);
		
		for (int i = 0; i < components.size(); i++){
			JComponent component = components.get(i);
			if (i % 2 == 0){
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.NORTHWEST;
				c.gridx = 0;
				c.gridy = i / 2;
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.gridwidth = 1;
				c.ipadx = 10;
				c.ipady = 10;
				settingPanel.add(component, c);
				
			} else {
				//for phase space panel
				c.fill = GridBagConstraints.HORIZONTAL;
				c.anchor = GridBagConstraints.NORTHWEST;
				c.gridx = 1;
				c.gridy = i / 2;
				c.weightx = 1.0;
				c.weighty = 1.0;
				c.gridwidth = 3;
				c.ipadx = 10;
				c.ipady = 10;
				settingPanel.add(component, c);
			}	
			
			this.add(settingPanel, BorderLayout.CENTER);
			pack();
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if (e.getSource() == cbEnforceSchema){
			RapidFitMainControl.getInstance().setEnforceSchema(
					cbEnforceSchema.isSelected());
		}
	}
}
