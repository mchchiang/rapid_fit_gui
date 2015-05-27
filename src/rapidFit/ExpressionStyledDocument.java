package rapidFit;

import java.util.*;
import java.awt.*;

import javax.swing.text.*;

@SuppressWarnings("serial")
public class ExpressionStyledDocument extends DefaultStyledDocument{
	
	private final StyleContext cont = StyleContext.getDefaultStyleContext();
    private AttributeSet attrPDF;
    private AttributeSet attrOperator;
    private AttributeSet attr;
    
    private String pdfPattern;
    private final String opPattern = "(\\s+)*(\\+|\\*|\\(|\\))";
    
    public ExpressionStyledDocument(ArrayList<String> pdfs){
    	super();
    	
    	updatePDFNames(pdfs);
    	
    	//set the style for regular text, operators and pdfs
    	attr = cont.addAttribute(
        		cont.getEmptySet(), StyleConstants.Foreground, Color.BLACK);
    	attr = cont.addAttribute(attr, StyleConstants.Bold, false);
    	
    	attrPDF = cont.addAttribute(
    			cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
    	attrPDF = cont.addAttribute(attrPDF, StyleConstants.Bold, true);
    	
    	attrOperator = cont.addAttribute(
    			cont.getEmptySet(), StyleConstants.Foreground, Color.BLUE);
    	attrOperator = cont.addAttribute(attrOperator, StyleConstants.Bold, true);
    }
    
    public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);

        String text = getText(0, getLength());
        int before = findLastNonWordChar(text, offset);
        if (before < 0) before = 0;
        int after = findFirstNonWordChar(text, offset + str.length());
        int wordL = before;
        int wordR = before;

        while (wordR <= after) {
            if (wordR == after || String.valueOf(text.charAt(wordR)).matches("\\s+")) {
                if (text.substring(wordL, wordR).matches(pdfPattern))
                    setCharacterAttributes(wordL, wordR - wordL, attrPDF, false);
                else if (text.substring(wordL, wordR).matches(opPattern)){
                	setCharacterAttributes(wordL, wordR - wordL, attrOperator, false);
                } else {
                    setCharacterAttributes(wordL, wordR - wordL, attr, false);
                }
                wordL = wordR;
            }
            wordR++;
        }
    }

    public void remove (int offs, int len) throws BadLocationException {
        super.remove(offs, len);

        String text = getText(0, getLength());
        int before = findLastNonWordChar(text, offs);
        if (before < 0) before = 0;
        int after = findFirstNonWordChar(text, offs);

        if (text.substring(before, after).matches(pdfPattern)) {
            setCharacterAttributes(before, after - before, attrPDF, false);
        } else if (text.substring(before, after).matches(opPattern)) {
            setCharacterAttributes(before, after - before, attrOperator, false);
        } else {
            setCharacterAttributes(before, after - before, attr, false);
        }
    }
    
    public void updatePDFNames(ArrayList<String> pdfs){
    	if (pdfs != null){
    		pdfPattern = "(\\s+)*(";
    		for (String pdf : pdfs){
        		pdfPattern += "\"" + pdf + "\"|";
        	}
        	pdfPattern = pdfPattern.substring(0, pdfPattern.length()-1) + ")";
    	} else {
    		pdfPattern = "";
    	}
    }
    
    private int findLastNonWordChar (String text, int index) {
        while (--index >= 0) {
            if (String.valueOf(text.charAt(index)).matches("\\s+")) {
                break;
            }
        }
        return index;
    }

    private int findFirstNonWordChar (String text, int index) {
        while (index < text.length()) {
            if (String.valueOf(text.charAt(index)).matches("\\s+")) {
                break;
            }
            index++;
        }
        return index;
    }
}
