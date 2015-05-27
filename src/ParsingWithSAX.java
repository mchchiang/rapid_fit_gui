
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
import java.util.*;

public class ParsingWithSAX extends DefaultHandler{
	private ArrayList<Person> contacts = new ArrayList<Person>();
	private Person person = new Person();
	
	private CharArrayWriter contents = new CharArrayWriter();
	
	public void startElement (String namespaceURI, String localName, 
			String qName, Attributes attr) throws SAXException {
		contents.reset();
		
		if (localName.equals("person")){
			person = new Person();
			contacts.add(person);
		}
	}
	
	public void endElement(String namesapceURI, String localName,
			String qName) throws SAXException {
		switch (localName){
		case "name":
			person.setName(contents.toString());
			break;
		case "phone":
			person.setPhone(contents.toString());
			break;
		case "email":
			person.setEmail(contents.toString());
			break;
		case "age":
			person.setAge(Integer.parseInt(contents.toString()));
			break;
		}
	}
	
	public void characters(char [] ch, int start, 
			int length) throws SAXException{
		contents.write(ch, start, length);
	}
	
	public ArrayList<Person> getContacts(){
		return contacts;
	}
	
	public static void main (String [] args){
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			ParsingWithSAX parser = new ParsingWithSAX();
			xr.setContentHandler(parser);
			
			xr.parse(new InputSource(new FileReader("MyData.xml")));
			ArrayList<Person> contacts = parser.getContacts();
			for (Person p : contacts){
				System.out.println(p.toString());
				System.out.println();
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
