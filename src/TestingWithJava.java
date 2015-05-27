import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TestingWithJava {
	public static void main (String [] args) {
		DocumentBuilderFactory builderFactory =
			DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document document = null;
		try {
			builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e){
			e.printStackTrace();
		}
		
		try {
			document = builder.parse(new FileInputStream("XMLExamplar/110227-ExampleFor_Bs2JPsiPhi_SignalAlt_MO_TaggedFit_NOCOMMENT.xml"));
			document.getDocumentElement().normalize();
			
			//root node
			Element root = document.getDocumentElement();
			visitChildNodes(0,root.getChildNodes());
			
			
		} catch (SAXException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}

	}
	
	public static void visitChildNodes (int layer, NodeList childNodes){
		System.out.println(layer);
		for (int i = 0; i < childNodes.getLength(); i++){
			//check that it is element type
			Node node = childNodes.item(i);
			if (node.getNodeType() == Document.ELEMENT_NODE){
				System.out.print(node.getNodeName() + ": ");
				if (node.getTextContent() != null){
					System.out.print(node.getTextContent());
				}
				System.out.println();
			}
			
			//check if child has more child nodes
			if (node.hasChildNodes()){
				visitChildNodes(layer+1,node.getChildNodes());
			}
		}
	}
}
