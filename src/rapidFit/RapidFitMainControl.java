package rapidFit;

import java.io.*;
import java.awt.event.*;

//a class to store global settings
public class RapidFitMainControl implements ActionListener {

	private static RapidFitMainControl instance;
	
	private boolean enforceSchema = false;
	private boolean unsaveEdits = false;
	private Table currentEditingTable = null;
	private File file = null;
	
	//singleton design
	private RapidFitMainControl(){}
	
	public static RapidFitMainControl getInstance(){
		if (instance == null){
			instance = new RapidFitMainControl();
		} 
		return instance;
	}
	
	//accessor methods
	public void setUnsavedEdits(boolean b){
		unsaveEdits = b;
	}
	public void setEnforceSchema(boolean b){
		enforceSchema = b;
	}
	public void setCurrentEditingTable(Table t){
		currentEditingTable = t;
	}
	public void setFile(File f){
		file = f;
	}
	
	public boolean hasUnsaveEdits(){return unsaveEdits;}
	public boolean isSchemaEnforced(){return enforceSchema;}
	public Table getCurrentEditingTable(){return currentEditingTable;}
	public File getFile(){return file;}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
