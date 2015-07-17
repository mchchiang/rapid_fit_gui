package rapidFit.controller.exception;

@SuppressWarnings("serial")
public class XMLIOException extends Exception {
	
	public enum ErrorType {
		WRITE_FILE_ERROR, 
		READ_FILE_ERROR, 
		XML_PARSING_ERROR,
		WRITE_XML_SCHEMA_VALIDATION_ERROR,
		READ_XML_SCHEMA_VALIDATION_ERROR,
		READ_SCHEMA_ERROR,
		UNKNOWN_ERROR
	}
	
	private ErrorType error;
	
	public XMLIOException(ErrorType type){
		super(type.toString());	
		error = type;
	}
	
	public ErrorType getErrorType(){return error;}
}
