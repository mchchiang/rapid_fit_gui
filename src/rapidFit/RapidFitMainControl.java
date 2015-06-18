package rapidFit;

import java.io.*;
//import java.awt.event.*;
import rapidFit.rpfit.*;

//a class to store global settings
public class RapidFitMainControl {

	private static RapidFitMainControl instance;
	
	private boolean enforceSchema = false;
	private boolean unsavedEdits = false;
	private Table currentEditingTable = null;
	
	private File file = null;
	private RapidFitType root = null;
	
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
		unsavedEdits = b;
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
	public void setRoot(RapidFitType root){
		this.root = root;
	}
	
	public boolean hasUnsavedEdits(){return unsavedEdits;}
	public boolean isSchemaEnforced(){return enforceSchema;}
	public Table getCurrentEditingTable(){return currentEditingTable;}
	public File getFile(){return file;}
	public RapidFitType getRoot(){return root;}
	
}
