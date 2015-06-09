package rapidFit;


import java.util.List;
import java.awt.*;
import java.lang.reflect.*;

import javax.swing.*;
import javax.swing.border.*;

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
		setDefaultProperties();
	}
	
	
	public PDFInspectorPanel(Object pdf){
		setLayout(new BorderLayout());
		setDefaultProperties();
		if (pdf instanceof SumPDFType){
			setPanelForSumPDF((SumPDFType) pdf);
		} else if (pdf instanceof ProdPDFType){
			setPanelForProdPDF((ProdPDFType) pdf);
		} else if (pdf instanceof PDFType){
			setPanelForPDF((PDFType) pdf);
		}
		add(scrollPane);
	}
	
	private void setDefaultProperties(){
		Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border title = BorderFactory.createTitledBorder("<html><b>PDF Inspector</b></html>");
		setBorder(new CompoundBorder(border, title));
	}
	
	private void setPanelForPDF(PDFType pdf){
		//display all info of the PDF
		JLabel lblInfo;
		String info = "<html>"
				+ "<body>"
				+ "<h3>PDF Info</h3>"
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
		info += "</table></p><br><p><b>Configuration Paramereters</h4></b>";
		for (String config : pdf.getConfigurationParameter()){
			info += "<li>" + config + "</li>"; 
		}
		info += "</ul></p></font></body></html>";
		lblInfo = new JLabel(info);
		scrollPane = new JScrollPane(lblInfo);
	}
	
	private void setPanelForSumPDF(SumPDFType pdf){
		//display all info of the PDF sum
		JLabel lblInfo;
		String info = "<html><body><h3>PDF Sum Info</h3><p><b>This is a PDF sum of:</b><ul>";
		for (Object obj : ((SumPDFType) pdf).getProdPDFOrNormalisedSumPDFOrPDF()){
			if (obj instanceof PDFType){
				info += "<li>" + ((PDFType) obj).getName() + "</li>";
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
	
	private void setPanelForProdPDF(ProdPDFType pdf){
		JLabel lblInfo;
		String info = "<html><body><h3>PDF Product Info</h3><p><b>This is a PDF product of:</b><ul>";
		for (Object obj : ((ProdPDFType) pdf).getProdPDFOrNormalisedSumPDFOrPDF()){
			if (obj instanceof PDFType){
				info += "<li>" + ((PDFType) obj).getName() + "</li>";
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
