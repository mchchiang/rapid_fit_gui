
import java.util.*;

public class SkippingTagTracker extends TagTracker{
	public void onStart(String tagName, Stack<TagTracker> tagStack){
		tagStack.push(this);
	}
	public void onEnd(Stack<TagTracker> tagStack){
		tagStack.pop();
	}
}
