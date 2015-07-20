package rapidFit.model.treeModel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import rapidFit.data.PDFType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;

public class PDFNode implements TreeNode {
	
	private Object actualObject;
	private TreeNode [] children;
	private TreeNode parent;
	private boolean isLeaf;
	private String name;

	
	public PDFNode(PDFNode parent, SumPDFType pdf) {
		this.parent = parent;
		actualObject = pdf;
		isLeaf = false;
		name = "Sum PDF (" + pdf.getFractionName() + ")";
		createChildNodes(pdf.getProdPDFOrNormalisedSumPDFOrPDF());
	}
	
	public PDFNode(PDFNode parent, ProdPDFType pdf) {
		this.parent = parent;
		actualObject = pdf;
		isLeaf = false;
		name = "Product PDF";
		createChildNodes(pdf.getProdPDFOrNormalisedSumPDFOrPDF());
	}
	
	public PDFNode(PDFNode parent, PDFType pdf) {
		this.parent = parent;
		actualObject = pdf;
		isLeaf = true;
		name = pdf.getName();
	}
	
	private void createChildNodes(List<Serializable> pdfs){
		children = new TreeNode [2];
		children[0] = createPDFNode(pdfs.get(0));
		children[1] = createPDFNode(pdfs.get(1));
	}
	
	private PDFNode createPDFNode(Object pdf) {
		if (pdf instanceof SumPDFType){
			return new PDFNode(this, (SumPDFType) pdf);
		} else if (pdf instanceof ProdPDFType){
			return new PDFNode(this, (ProdPDFType) pdf);
		} else if (pdf instanceof PDFType){
			return new PDFNode(this, (PDFType) pdf);
		} 
		return null;
	}
	
	@Override
	public void setParent(TreeNode newParent){
		parent = newParent;
	}
	
	@Override
	public TreeNode getParent() {
		return parent;
	}

	@Override
	public List<TreeNode> children() {
		return Arrays.asList(children);
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public TreeNode getChild(int index) {
		if (!isLeaf && index >=0 && index < children.length) {
			return children[index];
		}
		return null;
	}

	@Override
	public int getIndexOfChild(TreeNode node) {
		for (int i = 0; i < children.length; i++){
			if (children[i] == node) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public Object getActualObject() {
		return actualObject;
	}

	@Override
	public void setChild(int index, TreeNode newNode) {
		if (!isLeaf && index >= 0 && index < children.length) {
			children[index] = newNode;
			if (actualObject instanceof SumPDFType) {
				((SumPDFType) actualObject).getProdPDFOrNormalisedSumPDFOrPDF().
				set(index, (Serializable) newNode.getActualObject());
			} else if (actualObject instanceof ProdPDFType) {
				((ProdPDFType) actualObject).getProdPDFOrNormalisedSumPDFOrPDF().
				set(index, (Serializable) newNode.getActualObject());
			}
		}
	}
	
	/*
	 * can't add and remove child from a PDFNode as there must be 
	 * zero or two children in each node (binary operation)
	 */
	@Override
	public void addChild(int index, TreeNode newNode) {}

	@Override
	public void removeChild(int index) {}

	@Override
	public void removeChild(TreeNode node) {}

	public void setTagName(String name){
		this.name = name;
	}
	
	public String getTagName(){
		return name;
	}
}
