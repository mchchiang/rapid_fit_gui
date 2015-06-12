package rapidFit;


import java.io.*;
import java.lang.reflect.*;
import java.util.*;

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
				
				if (line.trim().startsWith("#")){
					//ignore line
				} else if (line.indexOf("<Minimiser>") != -1 && line.indexOf("</Minimiser>") != -1){
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
			e.printStackTrace();
		}
		
	}
	
	private static void removeNullElements(Iterator<?> it){
		while (it.hasNext()){
			if (isNull(it.next())){
				it.remove();
			}
		}
	}
	
	/*
	 * determine if an object is empty (null) by checking if the fields of
	 * the object that can be accessed by getter methods are null. If the field 
	 * is a List, it will be treated as null if it contains no element.
	 */
	private static boolean isNull (Object obj){
		try {
			for (Method m : obj.getClass().getDeclaredMethods()){
				//if the field is a List
				if (m.getName().startsWith("get") && m.getReturnType() == List.class &&
						m.invoke(obj, (Object[]) null) != null &&
						((List<?>) m.invoke(obj, (Object[]) null)).size() != 0){
					return false;
					
				} else if ((m.getName().startsWith("get") || m.getName().startsWith("is")) &&
					m.getReturnType() != List.class && m.invoke(obj, (Object []) null) != null){
					return false;
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//System.out.println("is null");
		return true;
	}
	
	public static void preWriteFile(RapidFitType root){
		//remove all empty components before writing file
		
		//ParameterSet
		removeNullElements(root.getParameterSet().
				getPhysicsParameter().iterator());
		if (root.getParameterSet().getPhysicsParameter().size() == 0){
			root.setParameterSet(null);
		}
		
		//Minimiser
		if (isNull(root.getMinimiser())){
			root.setMinimiser(null);
		}
		
		//FitFunction
		if (isNull(root.getFitFunction())){
			root.setFitFunction(null);
		}
		
		//Precalculator
		if (isNull(root.getPrecalculator())){
			root.setPrecalculator(null);
		}
		
		//NumberRepeats
		//Seed
		
		//CommonPhaseSpace
		removeNullElements(root.getCommonPhaseSpace().
				getPhaseSpaceBoundary().getObservable().iterator());
		if (root.getCommonPhaseSpace().getPhaseSpaceBoundary().getObservable().size() == 0){
			root.setCommonPhaseSpace(null);
		}
		
		//CommonPDF
		boolean noPDF = false;
		if (root.getCommonPDF().getNormalisedSumPDF() == null &&
			root.getCommonPDF().getProdPDF() == null){
			
			if (root.getCommonPDF().getPDF() != null){
				if (root.getCommonPDF().getPDF().getName().equalsIgnoreCase("null") ||
					root.getCommonPDF().getPDF().getName().equals("")){
					root.getCommonPDF().getPDF().setName(null);
				}				
				if (isNull(root.getCommonPDF().getPDF())){
					root.getCommonPDF().setPDF(null);
					noPDF = true;
				}
			} else {
				root.getCommonPDF().setPDF(null);
				noPDF = true;
			}
		}
		
		if (noPDF){
			root.setCommonPDF(null);
		}
		
		//ToFit
		if (root.getToFit().size() != 0){
			//remove any empty to fit section
			Iterator<ToFitType> it = root.getToFit().iterator();
			while (it.hasNext()){
				ToFitType fit = it.next();
				//it is a constraint function
				if (fit.getConstraintFunction() != null){
					removeNullElements(fit.getConstraintFunction().
							getExternalConstraint().iterator());

					removeNullElements(fit.getConstraintFunction().
							getExternalConstMatrix().iterator());
						
					if (fit.getConstraintFunction().getExternalConstraint().size() == 0 &&
						fit.getConstraintFunction().getExternalConstMatrix().size() == 0){
						it.remove();
					}
					
				// it is a data set
				} else {
					//for data set that uses common PDF
					if (fit.isCommonPDF() != null && fit.isCommonPDF()){ 
						if (isNull(fit.getPDFConfigurator())){
							fit.setPDFConfigurator(null);
						}
						
					//for data set that uses individual PDF
					} else {
						if (fit.getNormalisedSumPDF() == null &&
							fit.getProdPDF() == null &&
							fit.getPDF() != null){
								if (fit.getPDF().getName().equalsIgnoreCase("null") ||
									fit.getPDF().getName().equals("")){
									fit.getPDF().setName(null);
								}				
								if (isNull(fit.getPDF())){
									fit.setPDF(null);
								}
						} else {
							fit.setPDF(null);
						}	
					}
					
					//for data set that uses common phase space
					if (fit.getDataSet().getCommonPhaseSpace() != null){
						removeNullElements(fit.getDataSet().
								getCommonPhaseSpace().getObservable().iterator());
						/*if (fit.getDataSet().getCommonPhaseSpace().getObservable().size() == 0){
							fit.getDataSet().setCommonPhaseSpace(null);
						}*/
						
					//for data set that uses individual phase space	
					} else if (fit.getDataSet().getPhaseSpaceBoundary() != null){
						removeNullElements(fit.getDataSet().
								getPhaseSpaceBoundary().getObservable().iterator());
						if (fit.getDataSet().getPhaseSpaceBoundary().getObservable().size() == 0){
							fit.getDataSet().setPhaseSpaceBoundary(null);
						}
					}
					
					if (isNull(fit.getDataSet())){
						fit.setDataSet(null);
					}
					
					/*
					 * try setting the default field CommonPDF to null and check
					 * if the entire data set is empty. Remove the data set if it
					 * is empty; otherwise, reset the CommonPDF field
					 */
					Boolean temp = fit.isCommonPDF();
					fit.setCommonPDF(null);
					if (isNull(fit)){
						it.remove();
					} else {
						fit.setCommonPDF(temp);
					}
				}
			}
			
			//Output
			removeNullElements(root.getOutput().getComponentProjection().iterator());
			removeNullElements(root.getOutput().getScan().iterator());
			if (root.getOutput().getTwoDScan().size() != 0){
				Iterator<TwoDScanType> twoDScans = root.getOutput().getTwoDScan().iterator();
				while (twoDScans.hasNext()){
					TwoDScanType scan = twoDScans.next();
					if (isNull(scan.getXParam()) && isNull(scan.getYParam())){
						twoDScans.remove();
					}
				}
			}
			if (root.getOutput().getComponentProjection().size() == 0 &&
				root.getOutput().getScan().size() == 0 &&
				root.getOutput().getTwoDScan().size() == 0 &&
				root.getOutput().getDoPullPlots() == null){
				root.setOutput(null);
			}
		} 
		
	}
	
	private static void postWriteFile(String fileURL){
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
		/*
		 * make a copy of the data (so the edits in the GUI is not
		 * affected by the removal of empty elements in pre-processing
		 * the data for exporting them to an XML file
		 */
		RapidFitType copyOfFit = (RapidFitType) Cloner.deepClone(rpfit);
		
		preWriteFile(copyOfFit);
		
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
            JAXBElement<RapidFitType> root = of.createRapidFit(copyOfFit);
            
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
