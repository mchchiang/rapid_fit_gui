package rapidFit.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import rapidFit.data.PDFType;
import rapidFit.model.dataModel.IClassModel;


@SuppressWarnings("serial")
public class PDFInspector extends JPanel {

	private JScrollPane scrollPane;
	private JLabel lblInfo;

	public PDFInspector(){
		lblInfo = new JLabel();
		scrollPane = new JScrollPane(lblInfo);
		
		setFocusable(false);
		setLayout(new BorderLayout());
		add(scrollPane, BorderLayout.CENTER);
		
		displayNoPDF();
	}
	
	public void displayPDFInfo(String info){
		lblInfo.setText(info);
		this.validate();
	}

	public void displayNoPDF(){
		String info = "<html>No PDF selected.<br>"
				+ "Click on a PDF in the PDF expression <br> to display its info.</html>";
		lblInfo.setText(info);
		this.validate();
	}

	public void displaySumPDF(String fractionName, String pdfName1, String pdfName2){
		//display all info of the PDF sum

		String info = "<html>"
				+ "<body>"
				+ "<h3>Sum PDF Info</h3>"
				+ "<p>"
				+ "<b>This is a PDF sum of:</b>"
				+ "<ul>"
				+ "<li>" + pdfName1 + "</li>"
				+ "<li>" + pdfName2 + "</li>"
				+ "</ul>"
				+ "</p>"
				+ "<p><b>Fraction Name: </b>" + fractionName + "</p>"
				+ "</body>"
				+ "</html>";
		lblInfo.setText(info);
		this.validate();
	}

	public void displayProdPDF(String pdfName1, String pdfName2){
		String info = "<html>"
				+ "<body>"
				+ "<h3>Product PDF Info</h3>"
				+ "<p>"
				+ "<b>This is a PDF sum of:</b>"
				+ "<ul>"
				+ "<li>" + pdfName1 + "</li>"
				+ "<li>" + pdfName2 + "</li>"
				+ "</ul>"
				+ "</p>"
				+ "</body>"
				+ "</html>";
		lblInfo.setText(info);
		this.validate();
	}

	@SuppressWarnings("unchecked")
	public void displayPDF(IClassModel<PDFType> pdfModel, String tagName){
		String info = "<html>"
				+ "<body>"
				+ "<h3>PDF Info</h3>"
				+ "<p><b>Tag Name: </b>" + tagName + "</p><br>"
				+ "<p><b>Properties</b>"
				+ "<table border=\"1\">";
		
		ArrayList<String> listTypeAttributes = new ArrayList<String>();
		for (String fieldName : pdfModel.getFieldNames()){
			if (pdfModel.getFieldClass(fieldName) == List.class){
				listTypeAttributes.add(fieldName);
			} else {
				try {
					info += "<tr><td><b>" + fieldName + ": </b></td><td>" +
							pdfModel.get(fieldName) + "</td></tr>";
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		info += "</table></p><br>";
		
		for (String fieldName : listTypeAttributes){
			info += "<p><b>" + fieldName + "</b>";
			try {
				List<Object> list = (List<Object>) pdfModel.get(fieldName);
				if (list.size() == 0){
					info += "<br>There are no items in the " + fieldName + " list.";
				} else {
					info += "<ul>";
					for (Object obj : list){
						info += "<li>" + obj + "</li>"; 
					}
					info += "</ul>";
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			info += "</p><br>";
		}
		info += "</font></body></html>";
		lblInfo.setText(info);
		this.validate();
	}
}
