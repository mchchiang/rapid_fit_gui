package rapidFit;

import javax.swing.*;
import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class PDFViewer extends JPanel {
	
	//variables for the PDF expression tree
	private PDFExpressionType pdfRoot;
	private PDFTreeModel treeModel;
	private OldPDFManager pdfManager;
	private PDFTree pdfTree;
	private JScrollPane scrollPane;
	
	//variables for the PDF inspector
	private PDFInspectorPanel pdfInspector;
	
	public PDFViewer(PDFExpressionType root){
		pdfRoot = root;
		
		
	}
}
