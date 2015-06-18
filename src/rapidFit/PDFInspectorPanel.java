package rapidFit;


import java.util.List;
import java.awt.*;
import java.lang.reflect.*;

import javax.swing.*;

import rapidFit.rpfit.*;

//for inspecting elements of a PDF / composite PDF 
@SuppressWarnings("serial")
public class PDFInspectorPanel extends JPanel {
	
	private JScrollPane scrollPane;
	
	//constructor
	public PDFInspectorPanel(){
		JTextArea txtNoPDF = new JTextArea("No PDF selected.\n"
				+ "Click on a PDF in the PDF expression to display its info.");
		txtNoPDF.setLineWrap(true);
		txtNoPDF.setEditable(false);
		setLayout(new BorderLayout());
		scrollPane = new JScrollPane(txtNoPDF);
		txtNoPDF.setBackground(scrollPane.getBackground());
		add(scrollPane);
	}
	
	
	public PDFInspectorPanel(Object pdf, PDFManager pdfManager){
		setLayout(new BorderLayout());
		if (pdf instanceof SumPDFType){
			setPanelForSumPDF((SumPDFType) pdf, pdfManager);
		} else if (pdf instanceof ProdPDFType){
			setPanelForProdPDF((ProdPDFType) pdf, pdfManager);
		} else if (pdf instanceof PDFType){
			setPanelForPDF((PDFType) pdf, pdfManager);
		}
		add(scrollPane);
	}
	
	private void setPanelForPDF(PDFType pdf, PDFManager pdfManager){
		//display all info of the PDF
		JLabel lblInfo;
		String info = "<html>"
				+ "<body>"
				+ "<h3>PDF Info</h3>"
				+ "<p><b>Tag Name: </b>" + pdfManager.getTagName(pdf) + "</p><br>"
				+ "<p><b>Properties</b>"
				+ "<table border=\"1\">";
		Method [] getMethods = PDFType.class.getDeclaredMethods();
		for (Method m : getMethods){
			try {
				if (m.getName().startsWith("get") && m.getReturnType() != List.class){
					info += "<tr><td><b>" + m.getName().substring(3) + ": </b></td><td>" +
							m.invoke(pdf, (Object []) null) + "</td></tr>";
				} else if (m.getName().startsWith("is")){
					info += "<tr><td><b>" + m.getName().substring(2) + ": </b></td><td>" +
							m.invoke(pdf, (Object []) null) + "</td></tr>";
				}
			} catch (Exception e){
				e.printStackTrace();
			}
		}

		//also display the configuration info
		info += "</table></p><br>"
				+ "<p><b>Configuration Paramereters</b>";
		if (pdf.getConfigurationParameter().size() == 0){
			info += "<br>There is no configuration parameter.";
		} else {
			info += "<ul>";
			for (String config : pdf.getConfigurationParameter()){
				info += "<li>" + config + "</li>"; 
			}
			info += "</ul>";
		}
		
		info += "</p><br><p><b>Parameter Substitutions</b>";
		if (pdf.getParameterSubstitution().size() == 0){
			info += "<br>There is no parameter substitution.";
		} else {
			info += "<ul>";
			for (String paramSub : pdf.getParameterSubstitution()){
				info += "<li>" + paramSub + "</li>"; 
			}
			info += "</ul>";
		}	
		info += "</p><br></font></body></html>";
		lblInfo = new JLabel(info);
		scrollPane = new JScrollPane(lblInfo);
	}
	
	private void setPanelForSumPDF(SumPDFType pdf, PDFManager pdfManager){
		//display all info of the PDF sum
		JLabel lblInfo;
		String info = "<html><body><h3>PDF Sum Info</h3><p><b>This is a PDF sum of:</b><ul>";
		for (Object obj : ((SumPDFType) pdf).getProdPDFOrNormalisedSumPDFOrPDF()){
			if (obj instanceof PDFType){
				info += "<li>" + pdfManager.getTagName((PDFType) obj) + "</li>";
			} else if (obj instanceof SumPDFType){
				info += "<li>A PDF Sum</li>";
			} else if (obj instanceof ProdPDFType){
				info += "<li>A PDF Product</li>";
			}
		}
		info += "</ul></p><p><b>Fraction Name: </b>" + pdf.getFractionName() + "</p></body></html>";
		lblInfo = new JLabel(info);
		scrollPane = new JScrollPane(lblInfo);
	}
	
	private void setPanelForProdPDF(ProdPDFType pdf, PDFManager pdfManager){
		JLabel lblInfo;
		String info = "<html><body><h3>PDF Product Info</h3><p><b>This is a PDF product of:</b><ul>";
		for (Object obj : ((ProdPDFType) pdf).getProdPDFOrNormalisedSumPDFOrPDF()){
			if (obj instanceof PDFType){
				info += "<li>" + pdfManager.getTagName((PDFType) obj) + "</li>";
			} else if (obj instanceof SumPDFType){
				info += "<li>A PDF Sum</li>";
			} else if (obj instanceof ProdPDFType){
				info += "<li>A PDF Product</li>";
			}
		}
		info += "</ul></p></body></html>";
		lblInfo = new JLabel(info);
		scrollPane = new JScrollPane(lblInfo);
	}
}
