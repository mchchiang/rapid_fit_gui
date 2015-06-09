package rapidFit;

import java.awt.*;

import javax.swing.*;

import rapidFit.rpfit.*;

@SuppressWarnings("serial")
public class FittingPanel extends JPanel {
	private AttributePanel<FitFunctionType> fitFunctionPanel;
	private AttributePanel<MinimiserType> minimiserPanel;
	private AttributePanel<PrecalculatorType> preCalPanel;
	
	private JPanel otherAttrPanel;
	
	public FittingPanel(FitFunctionType fitFunction, 
			MinimiserType minimiser, PrecalculatorType precal){
		
		fitFunctionPanel = new AttributePanel<FitFunctionType>
				(FitFunctionType.class, fitFunction, "Fit Function", null);
		
		minimiserPanel = new AttributePanel<MinimiserType>
		(MinimiserType.class, minimiser, "Minimiser", null);
		
		preCalPanel = new AttributePanel<PrecalculatorType>(
				PrecalculatorType.class, precal, "Precalculator", null);
		
		
		
		otherAttrPanel = new JPanel();
		
		setLayout(new GridLayout(2,2));
		add(fitFunctionPanel);
		add(minimiserPanel);
		add(preCalPanel);
	}	
}
