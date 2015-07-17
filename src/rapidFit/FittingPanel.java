package rapidFit;

import java.lang.reflect.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

import rapidFit.data.*;

@SuppressWarnings("serial")
public class FittingPanel extends JPanel {
	private AttributePanel<FitFunctionType> fitFunctionPanel;
	private AttributePanel<MinimiserType> minimiserPanel;
	private AttributePanel<PrecalculatorType> preCalPanel;
	private AttributePanel<RapidFitType> otherAttrPanel;
	
	public FittingPanel(RapidFitType root){
		
		fitFunctionPanel = new AttributePanel<FitFunctionType>
				(FitFunctionType.class, root.getFitFunction(), "Fit Function", null);
		
		minimiserPanel = new AttributePanel<MinimiserType>
		(MinimiserType.class, root.getMinimiser(), "Minimiser", null);
		
		preCalPanel = new AttributePanel<PrecalculatorType>(
				PrecalculatorType.class, root.getPrecalculator(), "Precalculator", null);
		
		//find all attributes to be ignored
		ArrayList<String> ignoreAttr = new ArrayList<String>();
		for (Method m : RapidFitType.class.getDeclaredMethods()){
			if (!m.getName().equals("getSeed") && 
				!m.getName().equals("getNumberRepeats")){
				if (m.getName().startsWith("get")){
					ignoreAttr.add(m.getName().substring(3));
				} else if (m.getName().startsWith("is")){
					ignoreAttr.add(m.getName().substring(2));
				}
			}
		}
		otherAttrPanel = new AttributePanel<RapidFitType>(
				RapidFitType.class, root, "Other Fitting Options", ignoreAttr);
		
		setLayout(new GridLayout(2,2));
		add(fitFunctionPanel);
		add(minimiserPanel);
		add(preCalPanel);
		add(otherAttrPanel);
	}	
}
