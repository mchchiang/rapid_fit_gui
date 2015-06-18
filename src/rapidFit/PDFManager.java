package rapidFit;

import java.util.*;
import rapidFit.rpfit.*;

/*
 * a class to handle tag names for PDFs. This is needed as it is possible
 * to have multiple PDFs in the PDF expression with the same name but
 * have different configurations.
 */

public class PDFManager extends TagNameManager<PDFType> {
	
	private HashMap<String, Integer> tagNameCounter;
	
	public PDFManager(PDFExpressionType root){

		super();//for initialising the name map
		
		tagNameCounter = new HashMap<String, Integer>();
		
		//find all the PDFs
		if (root.getNormalisedSumPDF() != null){
			getPDFs(root.getNormalisedSumPDF());
		} else if (root.getProdPDF() != null){
			getPDFs(root.getProdPDF());
		} else if (root.getPDF() != null){
			getPDFs(root.getPDF());
		}
	}
	
	//a recursive method to find all the PDFs
	private void getPDFs(Object node) {
		if (node instanceof SumPDFType){
			getPDFs(((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof ProdPDFType){
			getPDFs(((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof PDFType && !nameMap.containsKey((PDFType) node)){
			addEntry((PDFType) node, ((PDFType) node).getName());
		}
	}
	
	//only for adding a new entry to the PDF map (not for setting the tag name)
	public void addEntry(PDFType entry, String name){
		//ensure that the map does not contain the entry
		if (!nameMap.containsKey(entry)){
			
			if (tagNameCounter.containsKey(name)){
				tagNameCounter.put(name, tagNameCounter.get(name)+1);
			} else {
				tagNameCounter.put(name, new Integer(1));
			}
			
			nameMap.put(entry, name + "_" + tagNameCounter.get(name));
		}
	}
	
	public HashMap<String, PDFType> getTagNameAsKeyMap(){
		HashMap<String, PDFType> map = new HashMap<String, PDFType>();
		for (PDFType pdf : nameMap.keySet()){
			map.put(nameMap.get(pdf), pdf);
		}
		return map;
	}
	
	public ArrayList<PDFType> getListOfPDFs(){
		ArrayList<PDFType> pdfs = new ArrayList<PDFType>();
		pdfs.addAll(nameMap.keySet());
		return pdfs;
	}
}
