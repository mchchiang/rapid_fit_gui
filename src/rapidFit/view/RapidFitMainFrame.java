package rapidFit.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.util.LinkedHashMap;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import rapidFit.RapidFitEditorMenuBar;

@SuppressWarnings("serial")
public class RapidFitMainFrame extends JFrame {

	private final int width = 1000;
	private final int height = 800;
	
	private JTextArea txtNoData;
	private JTabbedPane tabs;

	public RapidFitMainFrame() {
		
		setSize(new Dimension(width, height));
		setPreferredSize(new Dimension(width, height));
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(RapidFitEditorMenuBar.getInstance());
		
		//createStartUpScene();
	}

	public void createStartUpScene(){
		//remove previously displayed contents
		Container content = getContentPane();
		content.removeAll();

		setTitle("Rapid Fit Editor");
		
		txtNoData = new JTextArea("No data is loaded. Choose File/Import "
				+ "in the menu bar to import a RapidFit XML file or "
				+ "choose File/New to create a new RapidFit XML file.");
		txtNoData.setEditable(false);
		txtNoData.setBackground(getBackground());
		txtNoData.setLineWrap(true);
		
		content.add(txtNoData, BorderLayout.CENTER);
		validate();
		setVisible(true);
	}

	public void createLoadedFileScene(LinkedHashMap<JComponent, String> components, String fileName) {
		//remove previously displayed contents
		Container content = getContentPane();
		content.removeAll();
		
		//set title
		setTitle("Rapid Fit Editor - " + fileName);
		
		tabs = new JTabbedPane();
		
		for (JComponent cmp : components.keySet()){
			tabs.add(cmp, components.get(cmp));
		}
		
		content.add(tabs, BorderLayout.CENTER);
		validate();
		setVisible(true);
	}
	
	public void setActiveTab(JComponent cmp) {
		if (tabs.indexOfComponent(cmp) != -1){
			tabs.setSelectedComponent(cmp);
		}
	}

}
