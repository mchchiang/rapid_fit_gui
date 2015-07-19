package rapidFit.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

import rapidFit.controller.IListPanelController;
import rapidFit.controller.ITreePanelController;
import rapidFit.data.PDFType;

@SuppressWarnings("serial")
public class PDFBuilderFrame extends JDialog {
	
	private JButton btnReplaceWithPDF;
	private JButton btnReplaceWithSum;
	private JButton btnReplaceWithProd;
	
	private JRadioButton rbInspectFromPDFTree;
	private JRadioButton rbInspectFromPDFList;
	
	public PDFBuilderFrame(IListPanelController<PDFType> listPanelController,
			ITreePanelController treePanelController){
		
	}
	
}
