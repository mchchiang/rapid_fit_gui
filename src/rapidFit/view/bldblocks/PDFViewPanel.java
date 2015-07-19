package rapidFit.view.bldblocks;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import rapidFit.PDFInspectorPanel;
import rapidFit.controller.ITreePanelController;
import rapidFit.view.PDFInspector;

@SuppressWarnings("serial")
public class PDFViewPanel extends JPanel {
	private JPanel pdfTreePanel;
	private PDFInspector pdfInspectorPanel;
	private JPanel mainPanel;
	private JPanel controlPanel;
	private JButton btnEditPDF;
	
	public PDFViewPanel(ITreePanelController pdfTreeController, PDFInspector inspector){
		
		pdfTreePanel = new JPanel();
		pdfTreePanel.setLayout(new BorderLayout());
		pdfTreePanel.add(pdfTreeController.getView(), BorderLayout.CENTER);
		
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border title = BorderFactory.createTitledBorder(
				"<html><b>PDF Expression</b></html>");
		pdfTreePanel.setBorder(new CompoundBorder(border, title));
		
		pdfInspectorPanel = inspector;
		
		title = BorderFactory.createTitledBorder(
				"<html><b>PDF Inspector</b></html>");	
		pdfInspectorPanel.setBorder(new CompoundBorder(border, title));
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,2));
		mainPanel.add(pdfTreePanel);
		mainPanel.add(pdfInspectorPanel);
		
		btnEditPDF = new JButton("Edit PDF");
		btnEditPDF.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
								
			}
			
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(btnEditPDF, BorderLayout.CENTER);
		
		setFocusable(false);
		setLayout(new BorderLayout());
		add(mainPanel, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);
	}
	
}
