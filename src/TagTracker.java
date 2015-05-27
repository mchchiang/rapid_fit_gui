
import java.util.*;

public class TagTracker {
	private Hashtable<String, TagTracker> trackers = 
			new Hashtable<String, TagTracker>();
	private static SkippingTagTracker skip = new SkippingTagTracker();
	private String announcement ="";
	private boolean haveAnnouncement = false;
	
	//constructor
	public TagTracker(String message){
		haveAnnouncement = true;
		announcement = message;
	}
	public TagTracker(){}
	
	public void track(String tagName, TagTracker tracker){
		trackers.put(tagName, tracker);
	}
	
	public void onStart(String tagName, Stack<TagTracker> tagStack){
		TagTracker tracker = trackers.get(tagName);
		
		if (tracker == null){
			tagStack.push(skip);
		} else {
			tagStack.push(tracker);
			tracker.announce();
		}
	}
	public void onEnd(Stack<TagTracker> tagStack){
		tagStack.pop();
	}
	
	public void announce(){
		if (haveAnnouncement){
			System.out.println(announcement);
		}
	}
}
