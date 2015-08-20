package rapidFit.model;

import java.util.ArrayList;
import java.util.List;

import rapidFit.data.DataSetType;
import rapidFit.data.PDFConfiguratorType;
import rapidFit.data.PDFType;
import rapidFit.data.PhaseSpaceBoundaryType;
import rapidFit.data.ProdPDFType;
import rapidFit.data.SumPDFType;
import rapidFit.data.ToFitType;
import rapidFit.model.dataModel.DataModel;

public class DataSetModel extends DataModel<ToFitType>{
	
	//for the case when the data set requires an individual pdf
	private PDFManager pdfManager;
	
	public DataSetModel(List<ToFitType> data,
			ArrayList<String> ignoreAttributes) {
		super(ToFitType.class, data, ignoreAttributes);
	}

	public void add(int index) {
		//manually instantiating a new object
		ToFitType toFit = new ToFitType();
		/*
		 * configure the newly instantiated to fit object
		 * such that the data set is non null and it uses 
		 * the common PDF and the common phase space
		 */
		createDefaultDataSet(toFit);
		useCommonPDF(toFit);
		super.add(index, toFit);
	}

	@Override
	public void add(int index, ToFitType toFit) {
		if (toFit.getDataSet() == null){
			createDefaultDataSet(toFit);
		}
		if (toFit.getNormalisedSumPDF() == null &&
				toFit.getProdPDF() == null &&
				toFit.getPDF() == null &&
				toFit.isCommonPDF() == null &&
				toFit.getPDFConfigurator() == null){
			useCommonPDF(toFit);
		}
		super.add(index, toFit);
	}
	
	public void set(int index, ToFitType toFit) {
		if (toFit.getDataSet() == null){
			createDefaultDataSet(toFit);
		}
		if (toFit.getNormalisedSumPDF() == null &&
				toFit.getProdPDF() == null &&
				toFit.getPDF() == null &&
				toFit.isCommonPDF() == null){
			useCommonPDF(toFit);
		}
		super.set(index, toFit);
	}
	
	private void createDefaultDataSet(ToFitType toFit){
		DataSetType dataSet = new DataSetType();
		dataSet.setCommonPhaseSpace(new PhaseSpaceBoundaryType());
		dataSet.setPhaseSpaceBoundary(null);	
		toFit.setDataSet(dataSet);
	}

	private void useCommonPDF(ToFitType toFit){
		toFit.setCommonPDF(true);
		toFit.setNormalisedSumPDF(null);
		toFit.setProdPDF(null);
		toFit.setPDF(null);
		toFit.setPDFConfigurator(new PDFConfiguratorType());
	}

	public void switchToIndividualPhaseSpace(int index) {
		ToFitType toFit = get(index);
		if (toFit != null){
			/*
			 * it is ensured that each to fit object will 
			 * have a data set object 
			 */
			DataSetType dataSet = toFit.getDataSet();
			PhaseSpaceBoundaryType space = new PhaseSpaceBoundaryType();
			space.getObservable().addAll(
					dataSet.getCommonPhaseSpace().getObservable());
			dataSet.setCommonPhaseSpace(null);
			dataSet.setPhaseSpaceBoundary(space);
			notifyDataListener(new SwitchPhaseSpaceEvent(this, index, false));
		}
	}

	public void switchToCommonPhaseSpace(int index) {
		ToFitType toFit = get(index);
		if (toFit != null){
			/*
			 * it is ensured that each to fit object will 
			 * have a data set object 
			 */
			DataSetType dataSet = toFit.getDataSet();
			PhaseSpaceBoundaryType space = new PhaseSpaceBoundaryType();
			space.getObservable().addAll(
					dataSet.getPhaseSpaceBoundary().getObservable());
			dataSet.setCommonPhaseSpace(space);
			dataSet.setPhaseSpaceBoundary(null);
			notifyDataListener(new SwitchPhaseSpaceEvent(this, index, true));
		}
	}

	public void switchToCommonPDF(int index, PDFConfiguratorType configurator) {
		ToFitType toFit = get(index);
		if (toFit != null){
			toFit.setCommonPDF(true);
			if (configurator != null){
				toFit.setPDFConfigurator(configurator);
			} else {
				toFit.setPDFConfigurator(new PDFConfiguratorType());
			}
			pdfManager = null;
		}
	}
	
	public void switchToIndividualPDF(int index, PDFManager manager){
		ToFitType toFit = get(index);
		if (toFit != null){
			toFit.setCommonPDF(false);
			toFit.setPDFConfigurator(null);
			toFit.setNormalisedSumPDF(null);
			toFit.setProdPDF(null);
			toFit.setPDF(null);
			if (manager != null){
				Object pdfRoot = manager.getTreeModel().getRoot();
				if (pdfRoot instanceof SumPDFType){
					toFit.setNormalisedSumPDF((SumPDFType) pdfRoot);
				} else if (pdfRoot instanceof ProdPDFType){
					toFit.setProdPDF((ProdPDFType) pdfRoot);
				} else if (pdfRoot instanceof PDFType){
					toFit.setPDF((PDFType) pdfRoot); 
				}
				pdfManager = manager;
			
			//for the case of creating a new composite PDF
			} else {
				toFit.setPDF(new PDFType());
				pdfManager = new PDFManager(toFit, true, true);
			}
		}
	}
	
	public PDFManager getPDFManager(int index) {
		return pdfManager;
	}
}
