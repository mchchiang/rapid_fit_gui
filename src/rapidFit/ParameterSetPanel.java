package rapidFit;

import java.awt.*;

import javax.swing.*;

import rapidFit.rpfit.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class ParameterSetPanel extends JPanel implements ActionListener{
	
	private JPanel controlPanel;
	private JButton btnAddParam;
	private JButton btnRemoveParam;
	private DataPanel<PhysicsParameterType> dataPanel;
	//default constructor 
	public ParameterSetPanel(){}
	
	public ParameterSetPanel(ParameterSetType paramSet){
		dataPanel = new DataPanel<PhysicsParameterType>
		(PhysicsParameterType.class, paramSet.getPhysicsParameter(), null);
		
		btnAddParam = new JButton("Add Parameter");
		btnAddParam.addActionListener(this);
		btnRemoveParam = new JButton("Remove Parameter(s)");
		btnRemoveParam.addActionListener(this);
		controlPanel = new JPanel();
		controlPanel.add(btnAddParam);
		controlPanel.add(btnRemoveParam);
		
		this.setLayout(new BorderLayout());
		this.add(dataPanel, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddParam){
			dataPanel.addRow();
		} else if (e.getSource() == btnRemoveParam){
			dataPanel.removeSelectedRows();
		}
	}
}
