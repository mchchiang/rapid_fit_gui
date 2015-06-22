package rapidFit.view.blocks;

import rapidFit.main.RapidFitException;

@SuppressWarnings("serial")
public class TagNameException extends RapidFitException {
	
	public enum ErrorType {
		DUPLICATE_TAG_NAME,
		DUPLICATE_ENTRY,
		ENTRY_NOT_EXIST,
		UNKNOWN_ERROR
	}
	
	private ErrorType error;
	
	public TagNameException(Exception e, ErrorType type){
		super(e, null);
		error = type;
	}
	
	public TagNameException(Exception e, ErrorType type, String msg){
		super(e, msg);
		error = type;
	}
	
	public ErrorType getErrorType(){return error;}
}
