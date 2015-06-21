package rapidFit.main;

import java.io.*;

import javax.swing.*;

import rapidFit.view.blocks.TagNameException;

public class RapidFitExceptionHandler {
	
	private static String getStackTrace(Exception e){
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		return(writer.toString());
	}
	
	public static void handles (Exception e){
		 if (e instanceof XMLIOException){
			 XMLIOException xe = (XMLIOException) e;
			 
			 if (xe.getErrorType() == XMLIOException.ErrorType.READ_FILE_ERROR){
				 JOptionPane.showMessageDialog(null,
						 "An error occurred when reading the XML file.\n"
						 + "Please check that the file exists.\n\n"
						 + "==============================\n"
						 + getStackTrace(xe), 
						 "Read File Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (xe.getErrorType() == XMLIOException.ErrorType.WRITE_FILE_ERROR){
				 JOptionPane.showMessageDialog(null,
						 "An error occurred when writing the XML file.\n"
						 + "Please check that the file directory is valid.", 
						 "Write File Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (xe.getErrorType() == XMLIOException.ErrorType.XML_PARSING_ERROR){
				 JOptionPane.showMessageDialog(null, 
						 "An error occurred when parsing the XML file into "
						 + "the editor.\nPlease check that the xml file is properly formatted.",
						 "XML Parsing Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (xe.getErrorType() == XMLIOException.ErrorType.READ_SCHEMA_ERROR) {
				 JOptionPane.showMessageDialog(null, 
						 "An error occurred when reading the XML Schema file for parsing.",
						 "Read Schema Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (xe.getErrorType() == XMLIOException.ErrorType.UNKNOWN_ERROR){
				 JOptionPane.showMessageDialog(null,
						 "An unknown error has occurred.\n\n"
						 + "==============================\n"
						 + getStackTrace(xe),
						 "Unknown Error", JOptionPane.WARNING_MESSAGE);
			 } 
			 
		 } else if (e instanceof TagNameException){
			 TagNameException te = (TagNameException) e;
			 if (te.getErrorType() == TagNameException.ErrorType.DUPLICATE_ENTRY){
				 JOptionPane.showMessageDialog(null, 
						 "Data entry already exists in the list.",
						 "Tag Name Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (te.getErrorType() == TagNameException.ErrorType.DUPLICATE_TAG_NAME){
				 JOptionPane.showMessageDialog(null, 
						 "The tag name already exists. Please choose another tag name.",
						 "Tag Name Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (te.getErrorType() == TagNameException.ErrorType.ENTRY_NOT_EXIST){
				 JOptionPane.showMessageDialog(null, 
						 "Data entry does not exist in the list.",
						 "Tag Name Error", JOptionPane.WARNING_MESSAGE);
				 
			 } else if (te.getErrorType() == TagNameException.ErrorType.UNKNOWN_ERROR){
				 JOptionPane.showMessageDialog(null, 
						 "An unknown error has occurred.",
						 "Tag Name Error", JOptionPane.WARNING_MESSAGE);
			 }
		 }
	 }
}
