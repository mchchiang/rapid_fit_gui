package rapidFit.controller.command;

/**
 * 
 * The interface that
 * 
 * @author MichaelChiang
 *
 */
public interface Command {
	
	/**
	 * 
	 * Execute the tasks encapsulated by the command. 
	 * Return <code>true</code> if the command is executed
	 * successfully without any exception.
	 * 
	 */
	public boolean execute();
	
	
}
