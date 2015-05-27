package rapidFit;

import java.util.*;
import javax.swing.*;
import rapidFit.rpfit.*;

public class ToFitPanel extends JPanel {
	
	private ArrayList<ToFitType> constraints;
	private ArrayList<ToFitType> actualFits;
	
	public ToFitPanel(List<ToFitType> toFits){
		
		//separate the constraint functions and the actual fits
		for (ToFitType fit : toFits){
			//is constraint function
			if (fit.getConstraintFunction() != null){
				constraints.add(fit);
			} else {
				actualFits.add(fit);
			}
		}
	}
}
