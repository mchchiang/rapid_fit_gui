package rapidFit;


import java.io.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.bind.util.*;
import javax.xml.validation.*;

import org.xml.sax.*;

import rapidFit.rpfit.*;

public class XMLIO {
	
	private static String tempXMLFileURL = "temp.xml";
	
	//convert the input XML to a properly formatted XML
	private static void preReadFile(String fileURL){
		try {
			
			PrintWriter writer = new PrintWriter(new FileWriter(tempXMLFileURL));
			BufferedReader reader = new BufferedReader(new FileReader(fileURL));
			
			while (reader.ready()){
				/*
				 * format the text in <CutString> tag to make the file well-formed
				 */
				String line = reader.readLine();
				
				/*
				 * replace all "True" with "true" and all
				 * "False" with "false" (XML parser does not recognise "True"
				 * and "False" as legal boolean values)
				 */
				line = line.replaceAll("True", "true");
				line = line.replaceAll("False", "false");
				
				if (line.indexOf("<Minimiser>") != -1 && line.indexOf("</Minimiser>") != -1){
					int startIndex = line.indexOf("<Minimiser>");
					int endIndex = line.indexOf("</Minimiser>");
					
					writer.println(line.substring(0, startIndex) + "<Minimiser>");
					String text = line.substring(startIndex + "<Minimiser>".length(), endIndex);
					writer.println(line.substring(0, startIndex) + "\t" +
							"<MinimiserName>" + text + "</MinimiserName>");
					writer.println(line.substring(0, startIndex) + "</Minimiser>");
				
				} else if (line.indexOf("<FitFunction>") != -1 && line.indexOf("</FitFunction>") != -1){
					int startIndex = line.indexOf("<FitFunction>");
					int endIndex = line.indexOf("</FitFunction>");
					
					writer.println(line.substring(0, startIndex) + "<FitFunction>");
					String text = line.substring(startIndex + "<FitFunction>".length(), endIndex);
					writer.println(line.substring(0, startIndex) + "\t" +
							"<FunctionName>" + text + "</FunctionName>");
					writer.println(line.substring(0, startIndex) + "</FitFunction>");
				
					
				} else if (line.indexOf("<CutString>") != -1 && line.indexOf("</CutString>") != -1){
					
					int startIndex = line.indexOf("<CutString>");
					int endIndex = line.indexOf("</CutString>");
					
					writer.print(line.substring(0, startIndex) + "<CutString>");
					
					String text = line.substring(
							startIndex+"<CutString>".length(), endIndex);
					text = text.replaceAll("&", "&amp;");
					text = text.replaceAll("<", "&lt;");
					text = text.replaceAll(">", "&gt;");
					text = text.replaceAll("\"", "&quot;");
					text = text.replaceAll("'", "&apos;");
					writer.print(text);
					writer.print(line.substring(endIndex)+"\n");
				} else {
					writer.println(line);
				}
			}
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void postWriteFile(String fileURL){
		try {
			
			PrintWriter writer = new PrintWriter(new FileWriter(fileURL));
			BufferedReader reader = new BufferedReader(new FileReader(tempXMLFileURL));
			
			while (reader.ready()){
				/*
				 * format the text in <CutString> tag to make the file well-formed
				 */
				String line = reader.readLine();
				
				line = line.replaceAll("true", "True");
				line = line.replaceAll("false", "False");
				
				int startIndex = line.indexOf("<CutString>");
				int endIndex = line.indexOf("</CutString>");
				if (startIndex != -1 && endIndex != -1){
					writer.print(line.substring(0, startIndex) + "<CutString>");
					String text = line.substring(
							startIndex+"<CutString>".length(), endIndex);
					text = text.replaceAll("&amp;", "&");
					text = text.replaceAll("&lt;", "<");
					text = text.replaceAll("&gt;", ">");
					text = text.replaceAll("&quot;", "\"");
					text = text.replaceAll("&apos;", "'");
					writer.print(text);
					writer.print(line.substring(endIndex)+"\n");
				} else {
					/*
					 * fix the problem that the rapid fit programme does not like
					 * empty tags <tagName/>. This changes the tag to:
					 * <tagName></tagName>
					 */
					startIndex = line.indexOf("<");
					endIndex = line.indexOf(">");
					if (line.charAt(endIndex-1) == '/'){
						String tagName = line.substring(startIndex+1,endIndex-1);
						writer.println(line.substring(0, startIndex)
								+ "<" + tagName + "></" + tagName + ">");
						
					//don't write the XML header line
					} else if (!line.equals(
							"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>")) {
						writer.println(line);
					}
				}
			}
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static RapidFitType readFile (String fileURL, String schemaURL){
		preReadFile(fileURL);
		
		RapidFitType root = null;
		
		//validate xml document
		Schema mySchema = null;
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		JAXBContext jc = null;
		Unmarshaller u = null;
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
            JAXBElement<?> jbe = (JAXBElement<?>) u.unmarshal( new FileInputStream(tempXMLFileURL));
            root = (RapidFitType) jbe.getValue();
            
        } catch( JAXBException je ) {
        	if (vec != null && vec.hasEvents()){
        		for (ValidationEvent ve : vec.getEvents()){
        			String msg = ve.getMessage();
        			ValidationEventLocator vel = ve.getLocator();
        			int line = vel.getLineNumber();
        			int column = vel.getColumnNumber();
        			System.err.println(fileURL + ": " + line + "." +
        								column + ": " + msg);
        		}
        	}
        	
        } catch( IOException ioe ) {
            ioe.printStackTrace();
            
        }  catch (Exception e){
        	e.printStackTrace();
        }
       
        return root;
    }
	
	public static void writeFile (RapidFitType rpfit, String fileURL, String schemaURL){
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
            m.marshal(root, new FileOutputStream(tempXMLFileURL));
            
            postWriteFile(fileURL);
            
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
