package rapidFit;

import java.util.*;

import rapidFit.rpfit.*;


//a class for handling tag names for the pdfs
public class PDFManager {
	private IdentityHashMap<PDFType, String> nameMap;
	
	public PDFManager(PDFExpressionType root){
		//find all the PDFs
		nameMap = new IdentityHashMap<PDFType, String>();
		if (root.getNormalisedSumPDF() != null){
			getPDFs(root.getNormalisedSumPDF());
		} else if (root.getProdPDF() != null){
			getPDFs(root.getProdPDF());
		} else if (root.getPDF() != null){
			getPDFs(root.getPDF());
		}	
		updateTagName();		
	}
	
	private void getPDFs(Object node){
		if (node instanceof SumPDFType){
			getPDFs(((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(((SumPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof ProdPDFType){
			getPDFs(((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(((ProdPDFType) node).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			
		} else if (node instanceof PDFType){
			nameMap.put((PDFType) node, ((PDFType) node).getName()); 
		}
	}
	
	public void addPDF(PDFType pdf){
		nameMap.put(pdf, pdf.getName());
		updateTagName();
	}
	
	public void removePDF(PDFType pdf){
		if (nameMap.containsKey(pdf)){
			nameMap.remove(pdf);
			updateTagName();
		}
	}
	
	public void updateTagName(){
		HashMap<String, Integer> nameCount = new HashMap<String, Integer>();
		//set the pdf tag name
		for (PDFType pdf : nameMap.keySet()){
			String name = pdf.getName();
			if (nameCount.containsKey(name)){
				nameCount.put(name, nameCount.get(name)+1);
				nameMap.put(pdf, name + "_" + nameCount.get(name));
			} else {
				nameCount.put(name, 0);
				nameMap.put(pdf, name);
			}		
		}
	}
	
	public String getTagName(PDFType pdf){
		if (nameMap.containsKey(pdf)){
			return nameMap.get(pdf);
		}
		return null;
	}
	
	public IdentityHashMap<PDFType, String> getPDFAsKeyMap(){return nameMap;}
	
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
