package rapidFit;


import java.io.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.bind.util.*;
import javax.xml.validation.*;

import org.xml.sax.*;

import rapidFit.rpfit.*;

public class XMLReader {
	
	public RapidFitType readFile (String fileURL, String schemaURL){
		RapidFitType root = null;
		
		//validate xml document
		Schema mySchema = null;
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		JAXBContext jc = null;
		Unmarshaller u = null;
		String file = "trial.xml";
		ValidationEventCollector vec = new ValidationEventCollector();
		try {
			mySchema = sf.newSchema(new File(schemaURL));
		} catch (SAXException saxe){
			saxe.printStackTrace();
		}
		
        try {
            // create a JAXBContext capable of handling classes generated into
            // the primer.po package
            jc = JAXBContext.newInstance( "rapidFit.rpfit" );
            
            // create an Unmarshaller
            u = jc.createUnmarshaller();
           // u.setSchema(mySchema);
            //u.setEventHandler(vec);
            
            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
            JAXBElement<?> jbe = (JAXBElement<?>) u.unmarshal( new FileInputStream(fileURL));
            root = (RapidFitType) jbe.getValue();
            
        } catch( JAXBException je ) {
        	if (vec != null && vec.hasEvents()){
        		for (ValidationEvent ve : vec.getEvents()){
        			String msg = ve.getMessage();
        			ValidationEventLocator vel = ve.getLocator();
        			int line = vel.getLineNumber();
        			int column = vel.getColumnNumber();
        			System.err.println(file + ": " + line + "." +
        								column + ": " + msg);
        		}
        	}
        	
        } catch( IOException ioe ) {
            ioe.printStackTrace();
        }
        
        return root;
    }
}
