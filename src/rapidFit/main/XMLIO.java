package rapidFit.main;

import java.io.*;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.bind.util.*;
import javax.xml.validation.*;

import org.xml.sax.*;

import rapidFit.model.*;

public class XMLIO {
	
	private static XMLIO xmlio = null;	
	private final String tempXMLFileURL = "~temp.xml";
	private final String jaxbContextPackage = "rapidFit.model";
	private final URL schemaFileURL = this.getClass().getClassLoader().
			getResource("rapidFit/main/RapidFit.xsd");
	
	//private constructor to implement singleton pattern
	private XMLIO() {};
	
	public static XMLIO getInstance(){
		if (xmlio == null){
			xmlio = new XMLIO();
		} 
		return xmlio;
	}
	
	//convert the input XML to a properly formatted XML
	private void preReadFile(String fileURL) throws XMLIOException {
		PrintWriter writer = null;
		BufferedReader reader = null;
		
		try {
			writer = new PrintWriter(new FileWriter(new File(tempXMLFileURL)));
			reader = new BufferedReader(new FileReader(fileURL));
			
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
				
				//ignore line starts with #
				if (!line.trim().startsWith("#")){
					/*
					 * fix the problem that some XML files specify the name of the minimiser
					 * and the fit function in the main container tag instead of in the "name"
					 * tag within the container tag
					 */
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
						writer.println(formatLineToProperXML(line, "CutString"));
						
					} else if (line.indexOf("<TF1>") != -1 && line.indexOf("</TF1>") != -1){
						writer.println(formatLineToProperXML(line, "TF1"));
						
					} else {
						writer.println(line);
					}
				} 
			}
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {
			throw new XMLIOException(e, XMLIOException.ErrorType.READ_FILE_ERROR);
			
		} finally {
			try {
				reader.close();
			} catch (IOException e){
				throw new XMLIOException(e, XMLIOException.ErrorType.INTERNAL_ERROR,
						"Unable to close file stream from XML input file.");
			}
			writer.close();
		}
	}
	
	private String formatLineToProperXML(String line, String tagName){
		int startIndex = line.indexOf("<" + tagName + ">");
		int endIndex = line.indexOf("</" + tagName + ">");
		String output = line.substring(0, startIndex) + "<" + tagName + ">";
		String text = line.substring(startIndex+("<"+tagName+">").length(), endIndex);
		text = text.replaceAll("&", "&amp;");
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
		text = text.replaceAll("\"", "&quot;");
		text = text.replaceAll("'", "&apos;");
		output += (text + "</" + tagName + ">");
		return output;
	}
	
	private String formatLineToRapidFitXML(String line, String tagName){
		int startIndex = line.indexOf("<" + tagName + ">");
		int endIndex = line.indexOf("</" + tagName + ">");
		String output = line.substring(0, startIndex) + "<" + tagName + ">";
		String text = line.substring(startIndex+("<"+tagName+">").length(), endIndex);
		text = text.replaceAll("&amp;", "&");
		text = text.replaceAll("&lt;", "<");
		text = text.replaceAll("&gt;", ">");
		text = text.replaceAll("&quot;", "\"");
		text = text.replaceAll("&apos;", "'");
		output += (text + "</" + tagName + ">");
		return output;
	}
	
	//remove objects in a List that have null properties
	private void removeNullElements(Iterator<?> it){
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
	private boolean isNull (Object obj){
		if (obj instanceof String){
			if (obj == null || obj.equals("")){
				return true;
			} else {
				return false;
			}
		} else if (obj instanceof BigInteger ||
				obj instanceof Double ||
				obj instanceof Boolean){
			return obj == null;
		} else {
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
			return true;
		}
	}
	
	private void preWriteFile(RapidFitType root){
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
					if (fit.isCommonPDF() != null && fit.isCommonPDF() 
							&& fit.getPDFConfigurator() != null){ 
						if (fit.getPDFConfigurator().getConfigurationParameter() != null){
							removeNullElements(fit.getPDFConfigurator().getConfigurationParameter().iterator());
						}
						if (fit.getPDFConfigurator().getParameterSubstitution() != null){
							removeNullElements(fit.getPDFConfigurator().getParameterSubstitution().iterator());
						}
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
					boolean useCommonPhaseSpace = true;
					if (fit.getDataSet().getCommonPhaseSpace() != null){
						removeNullElements(fit.getDataSet().
								getCommonPhaseSpace().getObservable().iterator());
						if (fit.getDataSet().getCommonPhaseSpace().getObservable().size() == 0){
							fit.getDataSet().setCommonPhaseSpace(null);
						}
						
					//for data set that uses individual phase space	
					} else if (fit.getDataSet().getPhaseSpaceBoundary() != null){
						useCommonPhaseSpace = false;
						removeNullElements(fit.getDataSet().
								getPhaseSpaceBoundary().getObservable().iterator());
						if (fit.getDataSet().getPhaseSpaceBoundary().getObservable().size() == 0){
							fit.getDataSet().setPhaseSpaceBoundary(null);
						}
					}
					
					if (isNull(fit.getDataSet())){
						fit.setDataSet(null);
					} else {
						/*
						 * preserve the needed <CommonPhaseSpace> or <PhaseSpaceBoundary> tag
						 * even if there are no observables. 
						 */
						if (useCommonPhaseSpace && 
								fit.getDataSet().getCommonPhaseSpace() == null){
							fit.getDataSet().setCommonPhaseSpace(new PhaseSpaceBoundaryType());
						} else if (!useCommonPhaseSpace && 
								fit.getDataSet().getPhaseSpaceBoundary() == null){
							fit.getDataSet().setPhaseSpaceBoundary(new PhaseSpaceBoundaryType());
						}
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
	
	private void postWriteFile(String fileURL) throws XMLIOException {
		PrintWriter writer = null;
		BufferedReader reader = null;
		
		try {
			writer = new PrintWriter(new FileWriter(fileURL));
			reader = new BufferedReader(new FileReader(new File(tempXMLFileURL)));
			
			while (reader.ready()){
				/*
				 * convert the text in <CutString> and <TF1> tag to the format recognised
				 * by the RapidFit programme (i.e. convert all special characters in XML
				 * to their original form)
				 */
				String line = reader.readLine();
				
				line = line.replaceAll("true", "True");
				line = line.replaceAll("false", "False");
				
				if (line.indexOf("<CutString>") != -1 && line.indexOf("</CutString>") != -1){
					line = formatLineToRapidFitXML(line, "CutString");
				} else if (line.indexOf("<TF1>") != -1 && line.indexOf("</TF1>") != -1){
					line = formatLineToRapidFitXML(line, "TF1");
				}
				
				/*
				 * fix the problem that the rapid fit programme does not like
				 * empty tags <tagName/>. This changes the tag to:
				 * <tagName></tagName>*/ 
				int startIndex = line.indexOf("<");
				int endIndex = line.indexOf(">");
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
			
			reader.close();
			writer.close();
			
		} catch (IOException e) {
			throw new XMLIOException(e, XMLIOException.ErrorType.WRITE_FILE_ERROR);
			
		} finally {
			try {
				reader.close();
			} catch (IOException e){
				throw new XMLIOException(e, XMLIOException.ErrorType.INTERNAL_ERROR,
						"Unable to close file stream from temp file.");
			}
			writer.close();
		}
	}
	
	public RapidFitType readFile (String fileURL) throws XMLIOException {
		preReadFile(fileURL);
		
		RapidFitType root = null;
		
		//validate xml document
		boolean enforceSchema = RapidFitMainControl.getInstance().isSchemaEnforced();
		Schema mySchema = null;
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		JAXBContext jc = null;
		Unmarshaller u = null;
		ValidationEventCollector vec = new ValidationEventCollector();
		
		if (enforceSchema){
			try {
				mySchema = sf.newSchema(schemaFileURL);
			} catch (SAXException saxe){
				throw new XMLIOException(saxe, XMLIOException.ErrorType.READ_SCHEMA_ERROR);
			}
		}
		
        try {
        	
            // create a JAXBContext capable of handling classes generated into
            // the primer.po package
            jc = JAXBContext.newInstance(jaxbContextPackage);
            
            // create an Unmarshaller
            u = jc.createUnmarshaller();
            
            if (enforceSchema){
            	u.setSchema(mySchema);
                u.setEventHandler(vec);
            }
            
            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
            FileInputStream inputStream = new FileInputStream(tempXMLFileURL);
            JAXBElement<?> jbe = (JAXBElement<?>) u.unmarshal(inputStream);
            root = (RapidFitType) jbe.getValue();
            
            inputStream.close();
            
            /*
             * delete the temporary file used for pre-processing the input XML
             * file after the file is read
             */
           try {
            	File file = new File(tempXMLFileURL);
            	if (file.delete() == false){
            		throw new XMLIOException(null, XMLIOException.ErrorType.INTERNAL_ERROR,
                			"Unable to delete temp file.");
            	}
            } catch (Exception e){
            	throw new XMLIOException(e, XMLIOException.ErrorType.INTERNAL_ERROR,
            			"Unable to delete temp file.");
            }
            
        } catch( JAXBException je ) {
        	if (vec != null && vec.hasEvents()){
        		String errorDetails = "";
        		for (ValidationEvent ve : vec.getEvents()){
        			String msg = ve.getMessage();
        			ValidationEventLocator vel = ve.getLocator();
        			int line = vel.getLineNumber();
        			int column = vel.getColumnNumber();
        			errorDetails += "line " + line + " column " +
        								column + ": " + msg + "\n";
        		}
        		throw new XMLIOException(je, XMLIOException.ErrorType.READ_XML_SCHEMA_VALIDATION_ERROR,
        				errorDetails);
        	}
        	throw new XMLIOException(je, XMLIOException.ErrorType.INTERNAL_ERROR, 
        			"Error in JAXB marshalling process.");
        	
        } catch (IOException e) {
            throw new XMLIOException(e, XMLIOException.ErrorType.INTERNAL_ERROR,
            		"Temp file " + tempXMLFileURL + " not found.");
            
        } catch (Exception e){
        	throw new XMLIOException(e, XMLIOException.ErrorType.UNKNOWN_ERROR);
        }
        
        if (root == null){
        	throw new XMLIOException(null, XMLIOException.ErrorType.XML_PARSING_ERROR);
        }
        return root;
    }
	
	public void writeFile (RapidFitType rpfit, String fileURL) throws XMLIOException {
		/*
		 * make a copy of the data (so the edits in the GUI is not
		 * affected by the removal of empty elements in pre-processing
		 * the data for exporting them to an XML file)
		 */
		RapidFitType copyOfFit = (RapidFitType) Cloner.deepClone(rpfit);
		
		preWriteFile(copyOfFit);
		
		//validate xml document
		boolean enforceSchema = RapidFitMainControl.getInstance().isSchemaEnforced();
		Schema mySchema = null;
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		JAXBContext jc = null;
		Marshaller m = null;
		ValidationEventCollector vec = new ValidationEventCollector();
		
		if (enforceSchema){
			try {
				mySchema = sf.newSchema(schemaFileURL);
			} catch (SAXException saxe){
				throw new XMLIOException(saxe, XMLIOException.ErrorType.READ_SCHEMA_ERROR);
			}
		}
		
        try {
            // create a JAXBContext capable of handling classes generated into
            // the primer.po package
            jc = JAXBContext.newInstance(jaxbContextPackage);
            
            // create an Unmarshaller
            m = jc.createMarshaller();
           
            if (enforceSchema){
            	m.setSchema(mySchema);
                m.setEventHandler(vec);
            }
             
            // unmarshal a po instance document into a tree of Java content
            // objects composed of classes from the primer.po package.
            ObjectFactory of = new ObjectFactory();
            JAXBElement<RapidFitType> root = of.createRapidFit(copyOfFit);
            
            FileOutputStream outputStream = new FileOutputStream(tempXMLFileURL);
            
            m.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            m.marshal(root, outputStream);
            
            outputStream.close();
            
            postWriteFile(fileURL);
            
            /*
             * delete the temporary file used for generating the output XML file
             */
            try {
            	File file = new File(tempXMLFileURL);
            	if (file.delete() == false){
            		throw new XMLIOException(null, XMLIOException.ErrorType.INTERNAL_ERROR,
                			"Unable to delete temp file.");
            	}
            } catch (Exception e){
            	throw new XMLIOException (e, XMLIOException.ErrorType.INTERNAL_ERROR,
            			"Unable to delete temp file.");
            }
            
        } catch (JAXBException je) {
        	if (vec != null && vec.hasEvents()){
        		String errorDetails = "";
        		for (ValidationEvent ve : vec.getEvents()){
        			String msg = ve.getMessage();
        			ValidationEventLocator vel = ve.getLocator();
        			int line = vel.getLineNumber();
        			int column = vel.getColumnNumber();
        			errorDetails += "line " + line + " column " +
							column + ": " + msg + "\n";
        		}
        		throw new XMLIOException(je, XMLIOException.ErrorType.WRITE_XML_SCHEMA_VALIDATION_ERROR,
        				errorDetails);
        	}
        	throw new XMLIOException(je, XMLIOException.ErrorType.INTERNAL_ERROR, 
        			"Error in JAXB marshalling process.");
        	
        } catch (IOException e){
        	throw new XMLIOException(e, XMLIOException.ErrorType.INTERNAL_ERROR,
        			"Temp file " + tempXMLFileURL + " not found.");
        	
        } catch (Exception e){
        	throw new XMLIOException(e, XMLIOException.ErrorType.UNKNOWN_ERROR);
        }
    }
}
