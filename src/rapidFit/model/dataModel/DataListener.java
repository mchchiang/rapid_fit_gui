package rapidFit.model.dataModel;

import rapidFit.model.dataModel.event.DataEvent;

/**
 * 
 * An interface that any objects wishing to listen to events occurring 
 * to a data model must implement.
 * 
 * @author MichaelChiang
 *
 */
public interface DataListener {
	
	/**
	 * 
	 * A method fired by the data model to notify the listeners that
	 * an event has occurred to the data set within the model. 
	 * This can be adding or removing an entry from the data set, 
	 * duplicating an entry, editing a field of an entry, etc. 
	 * 
	 * @param e The event that has occurred in the data set
	 * @see rapidFit.model.dataModel.event.DataEvent
	 * 
	 */
	public void update(DataEvent e);
	
}
