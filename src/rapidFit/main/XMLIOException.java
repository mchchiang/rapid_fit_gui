package rapidFit.main;

@SuppressWarnings("serial")
public class XMLIOException extends RapidFitException {
	
	public enum ErrorType {
		WRITE_FILE_ERROR, 
		READ_FILE_ERROR, 
		XML_PARSING_ERROR,
		WRITE_XML_SCHEMA_VALIDATION_ERROR,
		READ_XML_SCHEMA_VALIDATION_ERROR,
		READ_SCHEMA_ERROR,
		INTERNAL_ERROR,
		UNKNOWN_ERROR
	}
	
	private ErrorType error;
	
	public XMLIOException(Exception e, ErrorType type){
		super(e, null);
		error = type;
	}
	
	public XMLIOException(Exception e, ErrorType type, String msg){
		super(e, msg);
		error = type;
	}
	
	public ErrorType getErrorType(){return error;}
}
