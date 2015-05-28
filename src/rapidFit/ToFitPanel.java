package rapidFit;

import java.util.List;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import rapidFit.rpfit.*;

public class ToFitPanel extends JPanel implements ActionListener{
	
	private ArrayList<ToFitType> actualFits;
	
	private JPanel constMatrixControlPanel;
	private JPanel constraintControlPanel;
	private JPanel constraintPanel;
	private JPanel constMatrixPanel;
	private JPanel mainConstraintPanel;
	
	private DataPanel<ExternalConstraintType> constraintTablePanel;
	private DataPanel<ExternalConstMatrixType> constMatrixTablePanel;
	
	private JButton btnAddConstraint;
	private JButton btnRemoveConstraint;
	
	private JButton btnAddConstMatrix;
	private JButton btnRemoveConstMatrix;
	
	public ToFitPanel(List<ToFitType> toFits){
		
		actualFits = new ArrayList<ToFitType>();
		
		ToFitType constraintFit = new ToFitType();
		ConstraintFunctionType constraint = new ConstraintFunctionType();
		
		//separate the constraint functions and the actual fits
		Iterator<ToFitType> it = toFits.iterator();
		
		while (it.hasNext()){
			ToFitType fit = it.next();
			//is constraint function
			if (fit.getConstraintFunction() != null){
				//merge all external constraint into one group
				constraint.getExternalConstraint().addAll(
						fit.getConstraintFunction().getExternalConstraint());
				constraint.getExternalConstMatrix().addAll(
						fit.getConstraintFunction().getExternalConstMatrix());
				//remove constraint for combining them into single constraint
				it.remove();
			} else {
				actualFits.add(fit);
			}
		}
		
		/*
		 * combine all external constraints and external constant matrix into
		 * single constraint function
		 */
		constraintFit.setConstraintFunction(constraint);
		toFits.add(constraintFit);
		
		constraintTablePanel = new DataPanel<ExternalConstraintType>(
				ExternalConstraintType.class, 
				constraint.getExternalConstraint(), null);
		
		btnAddConstraint = new JButton("Add Constraint");
		btnAddConstraint.addActionListener(this);
		btnRemoveConstraint = new JButton("Remove Constraint(s)");
		btnRemoveConstraint.addActionListener(this);
		
		constraintControlPanel = new JPanel();
		constraintControlPanel.add(btnAddConstraint);
		constraintControlPanel.add(btnRemoveConstraint);
		
		constMatrixTablePanel = new DataPanel<ExternalConstMatrixType>(
				ExternalConstMatrixType.class, 
				constraint.getExternalConstMatrix(), null);
		
		btnAddConstMatrix = new JButton("Add Constraint Matrix");
		btnAddConstMatrix.addActionListener(this);
		btnRemoveConstMatrix = new JButton("Remove Constraint Matrix");
		btnRemoveConstMatrix.addActionListener(this);
		
		constMatrixControlPanel = new JPanel();
		constMatrixControlPanel.add(btnAddConstMatrix);
		constMatrixControlPanel.add(btnRemoveConstMatrix);
		
		constraintPanel = new JPanel();
		constraintPanel.setLayout(new BorderLayout());
		constraintPanel.add(constraintTablePanel, BorderLayout.CENTER);
		constraintPanel.add(constraintControlPanel, BorderLayout.SOUTH);
		
		Border border = BorderFactory.createTitledBorder(
				"<html><h3>External Constraints</html></h3>");
		Border margin = BorderFactory.createEmptyBorder(10,10,10,10);
		constraintPanel.setBorder(new CompoundBorder(margin, border));
		
		constMatrixPanel = new JPanel();
		constMatrixPanel.setLayout(new BorderLayout());
		constMatrixPanel.add(constMatrixTablePanel, BorderLayout.CENTER);
		constMatrixPanel.add(constMatrixControlPanel, BorderLayout.SOUTH);
		
		border = BorderFactory.createTitledBorder(
				"<html><h3>External Constraint Matrices</html></h3>");
		constMatrixPanel.setBorder(new CompoundBorder(margin, border));
		
		mainConstraintPanel = new JPanel();
		mainConstraintPanel.setLayout(new GridLayout(2,1));
		mainConstraintPanel.add(constraintPanel);
		mainConstraintPanel.add(constMatrixPanel);
		
		setLayout(new BorderLayout());
		add(mainConstraintPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddConstraint){
			constraintTablePanel.addRow();
			
		} else if (e.getSource() == btnRemoveConstraint){
			constraintTablePanel.removeSelectedRows();
			
		} else if (e.getSource() == btnAddConstMatrix){
			constMatrixTablePanel.addRow();
			
		} else if (e.getSource() == btnRemoveConstMatrix){
			constMatrixTablePanel.removeSelectedRows();
		}
	}
}
