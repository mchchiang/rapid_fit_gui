package rapidFit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rapidFit.rpfit.*;

//for creating the rapid fit database model

public class RapidFitFactory {
	
	public static RapidFitType createEmptyFit(){
		RapidFitType root = new RapidFitType();
		root.setParameterSet(new ParameterSetType());
		root.setPrecalculator(new PrecalculatorType());
		root.setFitFunction(new FitFunctionType());
		root.setMinimiser(new MinimiserType());
		root.setNumberRepeats(BigInteger.valueOf(1));
		root.setCommonPDF(new PDFExpressionType());
		
		PDFType pdf = new PDFType();
		pdf.setName("PDF");
		root.getCommonPDF().setPDF(pdf);
		
		root.setCommonPhaseSpace(new CommonPhaseSpaceType());
		root.getCommonPhaseSpace().setPhaseSpaceBoundary(new PhaseSpaceBoundaryType());
		
		root.setOutput(new OutputType());
		
		return root;
	}
	
	public static RapidFitType createFitFromFile(String fileURL, String schemaURL){
		RapidFitType root = XMLIO.readFile(fileURL, schemaURL);
		
		//check that all components/classes of the fit are initialised
		if (root.getParameterSet() == null) root.setParameterSet(new ParameterSetType());
		if (root.getPrecalculator() == null) root.setPrecalculator(new PrecalculatorType());
		if (root.getFitFunction() == null) root.setFitFunction(new FitFunctionType());
		if (root.getMinimiser() == null) root.setMinimiser(new MinimiserType());
		if (root.getNumberRepeats() == null) root.setNumberRepeats(BigInteger.valueOf(1));
		if (root.getCommonPDF() == null) {
			root.setCommonPDF(new PDFExpressionType());
			PDFType pdf = new PDFType();
			pdf.setName("null");
			root.getCommonPDF().setPDF(pdf);
		}
		if (root.getCommonPhaseSpace() == null){
			root.setCommonPhaseSpace(new CommonPhaseSpaceType());
			root.getCommonPhaseSpace().setPhaseSpaceBoundary(new PhaseSpaceBoundaryType());
		}
		
		//combine all the constraint function
		List<ToFitType> toFits = root.getToFit();
		ArrayList<ToFitType> constraints = new ArrayList<ToFitType>();
		
		Iterator<ToFitType> it = toFits.iterator();
		
		while(it.hasNext()){
			ToFitType fit = it.next();
			//check if the fit is a constraint function
			if (fit.getConstraintFunction() != null){
				constraints.add(fit);	
				it.remove();
			}
		}
		
		ToFitType constraintFit = new ToFitType();
		ConstraintFunctionType constraint = new ConstraintFunctionType();
		
		for (ToFitType fit : constraints){
			constraint.getExternalConstraint().addAll(
					fit.getConstraintFunction().getExternalConstraint());
			constraint.getExternalConstMatrix().addAll(
					fit.getConstraintFunction().getExternalConstMatrix());
		}
		
		constraintFit.setConstraintFunction(constraint);
		root.getToFit().add(constraintFit);
		
		return root;
	}
}
