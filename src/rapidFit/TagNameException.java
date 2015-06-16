package rapidFit;

@SuppressWarnings("serial")
public class TagNameException extends Exception {
	
	public enum ErrorType {
		DUPLICATE_TAG_NAME,
		DUPLICATE_ENTRY,
		ENTRY_NOT_EXIST,
		UNKNOWN_ERROR
	}
	
	private ErrorType error;
	
	public TagNameException(ErrorType type){
		super(type.toString());
		error = type;
	}
	
	public ErrorType getErrorType(){return error;}
}
