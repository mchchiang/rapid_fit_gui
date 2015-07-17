package rapidFit;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.*;

import rapidFit.data.*;

@SuppressWarnings("serial")
public class FitConstraintPanel extends JPanel{
	
	private DataTablePanel<ExternalConstraintType> constraintTablePanel;
	private DataTablePanel<ExternalConstMatrixType> constMatrixTablePanel;
	
	public FitConstraintPanel(ConstraintFunctionType constraint){		
		constraintTablePanel = new DataTablePanel<ExternalConstraintType>(
				ExternalConstraintType.class,
				constraint.getExternalConstraint(), null,
				"Add Constraint", "Remove Constraint(s)", "Duplicate Constraint(s)");
		
		constMatrixTablePanel = new DataTablePanel<ExternalConstMatrixType>(
				ExternalConstMatrixType.class,
				constraint.getExternalConstMatrix(), null,
				"Add Constraint Matrix", "Remove Constraint Matrix", 
				"Duplicate Constraint Matrix");
		
		Border border = BorderFactory.createTitledBorder(
				"<html><h3>External Constraints</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(0,10,10,0);
		constraintTablePanel.setBorder(new CompoundBorder(margin, border));
		
		border = BorderFactory.createTitledBorder(
				"<html><h3>External Constraint Matrices</html></h3>");
		constMatrixTablePanel.setBorder(new CompoundBorder(margin, border));
		
		setLayout(new GridLayout(1,2));
		this.add(constraintTablePanel);
		this.add(constMatrixTablePanel);
	}
}
