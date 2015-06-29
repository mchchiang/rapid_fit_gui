package rapidFit.main;

import java.io.*;

@SuppressWarnings("serial")
public class RapidFitException extends Exception {
	
	private String stackTrace = "";
	
	public RapidFitException (Exception e, String message){
		super(message);
		if (e != null){
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			stackTrace = writer.toString();
		}
	}
	
	public String stackTraceToString(){return stackTrace;}
}
