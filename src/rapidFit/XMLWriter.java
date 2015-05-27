package rapidFit;

import java.io.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.bind.util.*;
import javax.xml.validation.*;
import org.xml.sax.SAXException;
import rapidFit.rpfit.*;

public class XMLWriter {
	
	public void writeFile (RapidFitType rpfit, String fileURL, String schemaURL){
		//System.out.println("Writing File...");
		
		//validate xml document
		Schema mySchema = null;
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		JAXBContext jc = null;
		Marshaller m = null;
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
            m = jc.createMarshaller();
           // u.setSchema(mySchema);
            //u.setEventHandler(vec);
            
            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
            ObjectFactory of = new ObjectFactory();
            JAXBElement<RapidFitType> root = of.createRapidFit(rpfit);
            
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal(root, new FileOutputStream(fileURL));
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
        } catch(IOException e){
        	e.printStackTrace();
        }
    }
}
