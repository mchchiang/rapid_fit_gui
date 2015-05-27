
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.util.*;

public class ParsingWithSAXandTagTracker extends DefaultHandler {
	private Stack<TagTracker> tagStack = new Stack<TagTracker>();
	private TagTracker root;
	private CharArrayWriter contents = new CharArrayWriter();
	
	public void startElement(String namespaceURI, String localName, 
			String qName, Attributes attr) throws SAXException{
		contents.reset();
		TagTracker activeTracker = tagStack.peek();
		activeTracker.onStart(localName, tagStack);
	}
	
	public void endElement(String namespaceURI, String localName,
			String qName) throws SAXException{
		TagTracker activeTracker = tagStack.peek();
		activeTracker.onEnd(tagStack);
	}
	
	public void characters(char [] ch, int start, int length){
		contents.write(ch, start, length);
	}
	
	public void setRoot(TagTracker root){
		tagStack.push(root);
	}
	
	private String getTagPath(){
		String buffer ="";
		Enumeration<TagTracker> e = tagStack.elements();
		while(e.hasMoreElements()){
			buffer += "/" + e.nextElement();
		}
		return buffer;
	}
	
	public static void main (String [] args){
		System.out.println("Example");
		try{
			XMLReader xr = XMLReaderFactory.createXMLReader();
			ParsingWithSAXandTagTracker handler = new 
					ParsingWithSAXandTagTracker();
			
			//add the tracking
			TagTracker root = new TagTracker();
			TagTracker contacts = new TagTracker();
			root.track("contacts", contacts);
			
			
			TagTracker person = new TagTracker();
			contacts.track("person", person);
			
			TagTracker personName = new TagTracker("Found: "
					+ "[/contacts/person/name]");
			person.track("name", personName);
			
			TagTracker vip = new TagTracker();
			contacts.track("vip", vip);
			
			TagTracker vipPhone = new TagTracker("Found: "
					+ "[/contacts/vip/phone");
			vip.track("phone", vipPhone);
			handler.setRoot(root);
			xr.setContentHandler(handler);
			xr.parse(new InputSource(new FileReader("MyData.xml")));
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
}
