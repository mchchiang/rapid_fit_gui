package rapidFit;

import java.io.*;

import javax.swing.*;

public class RapidFitExceptionHandler {
	
	private static String getStackTrace(Exception e){
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		return(writer.toString());
	}
	
	public static void handle (Exception e){
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
			 
		 }
	 }
}
