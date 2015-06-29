package rapidFit.main;

import java.awt.*;
import javax.swing.*;

import rapidFit.view.blocks.TagNameException;

public class RapidFitExceptionHandler {

	public static void handles (RapidFitException e){
		String message = "";
		String title = "";

		if (e instanceof XMLIOException){
			XMLIOException xe = (XMLIOException) e;

			if (xe.getErrorType() == XMLIOException.ErrorType.READ_FILE_ERROR){
				message = "An error occurred when reading the XML file.<br>"
						+ "Please check that the file exists.";
				title = "Read File Error";

			} else if (xe.getErrorType() == XMLIOException.ErrorType.WRITE_FILE_ERROR){
				message = "An error occurred when writing the XML file.<br>"
						+ "Please check that the file directory is valid.";
				title = "Write File Error";

			} else if (xe.getErrorType() == XMLIOException.ErrorType.XML_PARSING_ERROR){
				message = "An error occurred when parsing the XML file into "
						+ "the editor.<br>Please check that the XML file is properly formatted.";
				title = "XML Parsing Error";

			} else if (xe.getErrorType() == XMLIOException.ErrorType.READ_SCHEMA_ERROR){
				message = "An error occurred when reading the XML Schema file for parsing.";
				title = "Read Schema Error";
			
			} else if (xe.getErrorType() == XMLIOException.ErrorType.READ_XML_SCHEMA_VALIDATION_ERROR){
				message = "An error occured when validating the XML file "
						+ "against the XML Schema.<br>Please check that the XML file is well-formed "
						+ "according to the Schema.";
				title = "Schema Validation Error";
				
			} else if (xe.getErrorType() == XMLIOException.ErrorType.WRITE_XML_SCHEMA_VALIDATION_ERROR){
				message = "An error occured when validating the XML output file against the XML Schema."
						+ "<br>Please check that the content provided in the programme is correct "
						+ "according to the Schema.";
				title = "Schema Validation Error";
				
			} else if (xe.getErrorType() == XMLIOException.ErrorType.INTERNAL_ERROR){
				message = "An internal error occurred in the XML I/O process.";
				title = "Internal XML Error";

			} else if (xe.getErrorType() == XMLIOException.ErrorType.UNKNOWN_ERROR){
				message = "An unknown error occurred in the XML I/O process.";
				title = "Unknown XML Error";
			} 

		} else if (e instanceof TagNameException){
			TagNameException te = (TagNameException) e;

			title = "Tag Name Error";

			if (te.getErrorType() == TagNameException.ErrorType.DUPLICATE_ENTRY){
				message = "Data entry already exists in the list.";

			} else if (te.getErrorType() == TagNameException.ErrorType.DUPLICATE_TAG_NAME){
				message = "The tag name already exists. Please choose another tag name.";

			} else if (te.getErrorType() == TagNameException.ErrorType.ENTRY_NOT_EXIST){
				message = "Data entry does not exist in the list.";

			} else if (te.getErrorType() == TagNameException.ErrorType.UNKNOWN_ERROR){
				message = "An unknown error has occurred when assigning tag names.";

			}
		}

		displayErrorMessage(e, message, title);
	}

	private static void displayErrorMessage(RapidFitException e, String message, String title){
		//display the error message
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		if ((e.stackTraceToString() != null && e.stackTraceToString() != "") || 
				(e.getMessage() != null && e.getMessage() != "")){

			JLabel lblErrorMsg = new JLabel("<html>" + message + "<br>&nbsp;</html>");

			JLabel lblErrorDetails;
			String errorDetails = "<html>";
			if (e.getMessage() != null && e.getMessage() != ""){
				String msg = e.getMessage().replaceAll("\n", "<br>");
				msg = msg.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
				errorDetails += "<p><b>Detailed Error Message:</b><br>" + msg + "</p><br>";
			}

			if (e.stackTraceToString() != null && e.stackTraceToString() != ""){
				String stackTrace = e.stackTraceToString().replaceAll("\n", "<br>");
				stackTrace = stackTrace.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
				errorDetails += "<p><b>Stack Trace:</b><br>" + stackTrace;
			}
			errorDetails += "</html>";

			lblErrorDetails = new JLabel(errorDetails);
			lblErrorDetails.setBackground(Color.WHITE);

			JScrollPane scrollpane = new JScrollPane(lblErrorDetails);
			scrollpane.setPreferredSize(new Dimension(500,150));
			panel.add(lblErrorMsg, BorderLayout.NORTH);
			panel.add(scrollpane, BorderLayout.CENTER);

		} else {
			JLabel lblErrorMsg = new JLabel("<html>" + message + "</html>");
			panel.add(lblErrorMsg, BorderLayout.CENTER);
		}

		JOptionPane.showMessageDialog(null, panel, title, JOptionPane.WARNING_MESSAGE);
	}
}
