package rapidFit.main;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import rapidFit.model.*;

//for creating the rapid fit database model

public class RapidFitFactory {
	
	//create an empty fit
	public static RapidFitType createEmptyFit(){
		RapidFitType root = new RapidFitType();
		root.setParameterSet(new ParameterSetType());
		root.setPrecalculator(new PrecalculatorType());
		root.setFitFunction(new FitFunctionType());		
		root.setMinimiser(new MinimiserType());
		root.setNumberRepeats(BigInteger.valueOf(1));
		root.setCommonPDF(new PDFExpressionType());
		
		PDFType pdf = new PDFType();
		pdf.setName("null");
		root.getCommonPDF().setPDF(pdf);
		
		root.setCommonPhaseSpace(new CommonPhaseSpaceType());
		root.getCommonPhaseSpace().setPhaseSpaceBoundary(new PhaseSpaceBoundaryType());
		
		ToFitType constraint = new ToFitType();
		constraint.setConstraintFunction(new ConstraintFunctionType());
		root.getToFit().add(constraint);
		
		root.setOutput(new OutputType());
		
		return root;
	}
	
	//create a fit from file
	public static RapidFitType createFitFromFile(String fileURL) throws XMLIOException {
		RapidFitType root = XMLIO.getInstance().readFile(fileURL);
		
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
		
		if (root.getOutput() == null){
			OutputType output = new OutputType();
			root.setOutput(output);
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
