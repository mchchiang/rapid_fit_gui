package rapidFit.model;

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
	
	public PDFNode(TreeNode parent, Object pdf) {
		if (pdf instanceof SumPDFType) {
			SumPDFType sumPDF = (SumPDFType) pdf;
			children = new TreeNode [2];
			children[0] = new PDFNode(this, sumPDF.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			children[1] = new PDFNode(this, sumPDF.getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			actualObject = pdf;
			this.parent = parent;
			isLeaf = false;
			
		} else if (pdf instanceof ProdPDFType) {
			ProdPDFType prodPDF = (ProdPDFType) pdf;
			children = new TreeNode [2];
			children[0] = new PDFNode(this, prodPDF.getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			children[1] = new PDFNode(this, prodPDF.getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			actualObject = pdf;
			this.parent = parent;
			isLeaf = false;
			
		} else if (pdf instanceof PDFType) {
			actualObject = pdf;
			this.parent = parent;
			isLeaf = true;
		}
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
	
	public static void main (String [] args) {
		SumPDFType pdf = new SumPDFType();
		pdf.getProdPDFOrNormalisedSumPDFOrPDF().add(new PDFType());
		pdf.getProdPDFOrNormalisedSumPDFOrPDF().add(new PDFType());
		PDFNode parent = new PDFNode(null, pdf);
		System.out.println(parent.getChild(0).getActualObject().toString());
	}

}
