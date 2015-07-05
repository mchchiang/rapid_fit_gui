package rapidFit.view;

import java.util.*;

import rapidFit.model.*;
import rapidFit.view.blocks.TagNameManager;

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
		for (PDFType pdf : getListOfPDFs(root)){
			addEntry(pdf, pdf.getName());
		}
	}
	
	//a constructor for creating a copy of a PDF manager
	public PDFManager(PDFExpressionType root, PDFExpressionType rootCopy, PDFManager originalManager){
		super();//for initialising the name map
		
		tagNameCounter = new HashMap<String, Integer>();
		
		//find all the PDFs
		ArrayList<PDFType> pdfList = getListOfPDFs(root);
		ArrayList<PDFType> pdfListCopy = getListOfPDFs(rootCopy);
		
		for (int i = 0; i < pdfList.size(); i++){
			nameMap.put(pdfListCopy.get(i), originalManager.getTagName(pdfList.get(i)));
		}
		
	}
	
	private ArrayList<PDFType> getListOfPDFs(PDFExpressionType root){
		ArrayList<PDFType> pdfList = new ArrayList<PDFType>();
		if (root.getNormalisedSumPDF() != null){
			getPDFs(pdfList, root.getNormalisedSumPDF());
		} else if (root.getProdPDF() != null){
			getPDFs(pdfList, root.getProdPDF());
		} else if (root.getPDF() != null){
			getPDFs(pdfList, root.getPDF());
		}
		return pdfList;
	}
	
	//a recursive method to find all the PDFs
	private void getPDFs(ArrayList<PDFType> pdfList, Object node) {
		if (node instanceof SumPDFType){
			getPDFs(pdfList, ((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(pdfList, ((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof ProdPDFType){
			getPDFs(pdfList, ((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(pdfList, ((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		/*} else if (node instanceof PDFType && !nameMap.containsKey((PDFType) node)){
			addEntry((PDFType) node, ((PDFType) node).getName());
		}*/
		} else if (node instanceof PDFType && !pdfList.contains((PDFType) node)){
			pdfList.add((PDFType) node);
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
