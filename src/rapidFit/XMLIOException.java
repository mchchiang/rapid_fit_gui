package rapidFit;

@SuppressWarnings("serial")
public class XMLIOException extends Exception {
	
	public enum ErrorType {WRITE_ERROR, READ_ERROR};
	
	private ErrorType t;
	
	public XMLIOException(ErrorType type){
		super();
		
		
	}
}
