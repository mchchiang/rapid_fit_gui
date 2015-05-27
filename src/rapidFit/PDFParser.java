package rapidFit;

import java.util.*;

import javax.xml.bind.*;
import javax.xml.namespace.QName;

import rapidFit.rpfit.*;

public class PDFParser {
	
	//both normalised sum and multiply are left associative
	
	//map storing the operators and their precedence
	private static class Operator{
		private String symbol;
		private int precedence;
		private QName qname;
		
		public Operator(String sym, int prec, QName qname){
			this.symbol = sym;
			this.precedence = prec;
			this.qname = qname;
		}
		
		public String getSymbol(){return symbol;}
		public int getPrecedence(){return precedence;}
		public QName getQName(){return qname;}
	}
	
	private static final ArrayList<Operator> operators = new ArrayList<Operator>();
	static {
		operators.add(new Operator("+", 0, new QName("","NormalisedSumPDF")));
		operators.add(new Operator("*", 1, new QName("","ProdPDF")));
	}

	private static QName getOperatorQName(String symbol){
		for (Operator o: operators){
			if (o.getSymbol().equals(symbol)){
				return o.getQName();
			}
		}
		return null;
	}
	
	private static String getOperatorSymbol(QName qname){
		for (Operator o : operators){
			if (o.getQName().equals(qname)){
				return o.getSymbol();
			}
		}
		return null;
	}
	
	private static int getOperatorPrecedence(String symbol){
		for (Operator o: operators){
			if (o.getSymbol().equals(symbol)){
				return o.getPrecedence();
			}
		}
		return 0;
	}
	
	private static boolean isOperator(String token){
		for (Operator o: operators){
			if (o.getSymbol().equals(token)){
				return true;
			}
		}
		return false;
	}
	
	private static final int cmpPrecedence(String token1, String token2){
		if (!isOperator(token1) || !isOperator(token2)){
			throw new IllegalArgumentException("Invalid tokens: " +
					token1 + " " + token2);
		}
		return getOperatorPrecedence(token1) - getOperatorPrecedence(token2);
	}
	
	
	public static JAXBElement<?> convertToXML(String expr, HashMap<String, PDFType> pdfMap){
		return buildPDFTree(RPNStringToJAXB(infixToXML(expr, pdfMap), pdfMap));
	}
	
	//implement the shunting yard algorithm
	private static ArrayList<String> infixToXML(String expr, HashMap<String, PDFType> pdfMap){
		StringTokenizer st = new StringTokenizer(expr," \"");
		
		ArrayList<String> output = new ArrayList<String>();
		Stack<String> stack = new Stack<String>();

		//for each token
		while (st.hasMoreTokens()){
			String token = st.nextToken();
			if (isOperator(token)){
				//System.out.println("Token: " + token + " is an operator.");
				while (!stack.empty() && isOperator(stack.peek()) &&
					cmpPrecedence(token, stack.peek()) <= 0){
						output.add(stack.pop());
				}	
				stack.push(token);
				
			} else if (token.equals("(")){
				stack.push(token);
				//System.out.println(tokens.size());
				//System.out.println("Token " + token + " is a LEFT bracket.");
				
			} else if (token.equals(")")){
				//System.out.println("Token " + token + " is a RIGHT bracket.");
				while (!stack.empty() && !stack.peek().equals("(")){
					output.add(stack.pop());
				}
				if (stack.empty()){
					throw new IllegalArgumentException("Unmatch brackets!!");
				}
				stack.pop(); //remove the left bracket
				
			} else {
				if (!pdfMap.containsKey(token)){
					throw new IllegalArgumentException("No such PDF!!");
				}
				output.add(token);
				//System.out.println("Token " + token + " is NOT an operator.");
			}
		}
		
		while (!stack.empty()){
			if (!isOperator(stack.peek())){
				throw new IllegalArgumentException ("Unmatch brackets!!");
			}
			output.add(stack.pop());
		}
		return output;
	}
	
	//convert from string tokens to JAXBElements (equiv. to xml tags)
	private static ArrayList<JAXBElement<?>> RPNStringToJAXB(ArrayList<String> input, HashMap<String, PDFType> pdfMap){
		ObjectFactory of = new ObjectFactory();
		ArrayList<JAXBElement<?>> output = new ArrayList<JAXBElement<?>>();
		for (String token : input){
			if (isOperator(token)){
				try{
					output.add((JAXBElement<?>) ObjectFactory.class.getDeclaredMethod
					("createPDFOperatorType" + getOperatorQName(token), PDFOperatorType.class).invoke
					(of, new PDFOperatorType()));
				} catch (Exception e){
					e.printStackTrace();
				}
			} else {
				output.add(of.createPDFOperatorTypePDF(pdfMap.get(token)));
			}
		}
		return output;
	}
	
	private static JAXBElement<?> buildPDFTree(ArrayList<JAXBElement<?>> input){
		Stack<JAXBElement<?>> stack = new Stack<JAXBElement<?>>();
		for (JAXBElement<?> token : input){
			if (token.getName().equals(new QName("","PDF"))){
				stack.add(token);
			} else {
				((PDFOperatorType)token.getValue()).getProdPDFOrNormalisedSumPDFOrPDF().add(stack.pop());
				((PDFOperatorType)token.getValue()).getProdPDFOrNormalisedSumPDFOrPDF().add(stack.pop());
				
				//push result stack
				stack.push(token);
			}
		}
		return stack.pop();
	}
	
	//=================================================================================================
	
	//convert a XML PDF tree into a regular mathematical expression
	public static String convertToExpression(JAXBElement<?> pdfRoot){
		return RPNToInfix(JAXBToRPNString(unpackPDFTree(pdfRoot)));
	}
	
	//unpack a PDF tree to RPN form using recursion
	private static ArrayList<JAXBElement<?>> unpackPDFTree(JAXBElement<?> input){
		ArrayList<JAXBElement<?>> output = new ArrayList<JAXBElement<?>>();
		unpackPDF(output, input);
		return output;
	}
	
	/*
	 * recursively going to all branches of the tree structure to construct a RPN
	 * expression of the resulting PDF
	 */
	private static void unpackPDF(ArrayList<JAXBElement<?>> output, JAXBElement<?> input){
		if (input.getName().equals(new QName("","PDF"))){
			output.add(input);
		} else {
			unpackPDF(output, ((PDFOperatorType) 
					input.getValue()).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			unpackPDF(output, ((PDFOperatorType) 
					input.getValue()).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
			output.add(input);
		}
	}
	
	//convert the JAXBElement in the RPN expression into string tokens
	private static String [] JAXBToRPNString(ArrayList<JAXBElement<?>> input){
		String [] output = new String [input.size()];
		for (int i = 0; i < input.size(); i++){
			if (input.get(i).getName().equals(new QName("","PDF"))){
				output[i] = (String) ((PDFType)input.get(i).getValue()).getName();
			} else {
				output[i] = getOperatorSymbol(input.get(i).getName());
			}
		}
		return output;
	}
	
	//convert a RPN expression in an array of string tokens into regular infix expression
	private static String RPNToInfix(String [] tokens){
		class Expression {
			private String operator;
			private String expression;
			public Expression(String expr){
				expression = expr;
			}
			public Expression(String expr, String oper){
				expression = expr;
				operator = oper;
			}
		}
		
		Stack<Expression> stack = new Stack<Expression>();
				
		for (String token : tokens){
			
			//it is an operator
			if (isOperator(token)){
				if (stack.size() < 2){
					throw new IllegalArgumentException("Not enough PDFs!");
				}
				Expression left = stack.pop();
				Expression right = stack.pop();
				
				if (left.operator != null && cmpPrecedence(left.operator,token) < 0){
					left.expression = " ( " + left.expression + " ) ";
				}
				if (right.operator != null && cmpPrecedence(right.operator,token) < 0){
					right.expression = " ( " + right.expression + " ) ";
				}
				stack.push(new Expression(left.expression + " " + token + " " + right.expression, token));
				
			//it is a PDF
			} else {
				stack.push(new Expression("\"" + token + "\""));
			}
		}
		return stack.pop().expression;
	}
	
	//get a list of actual PDFs in the XML PDF tree using recursion
	public static ArrayList<PDFType> getListOfPDFs(JAXBElement<?> pdfTree){
		ArrayList<PDFType> listOfPDFs = new ArrayList<PDFType>();
		getPDFs(listOfPDFs, pdfTree);
		return listOfPDFs;
	}
	
	//using recursion to get pdfs from all branches of the pdf tree
	private static void getPDFs(ArrayList<PDFType> pdfList, JAXBElement<?> input){
		if (input.getName().equals(new QName("","PDF"))){
			pdfList.add((PDFType) input.getValue());
		} else {
			getPDFs(pdfList, ((PDFOperatorType) 
					input.getValue()).getProdPDFOrNormalisedSumPDFOrPDF().get(0));
			getPDFs(pdfList, ((PDFOperatorType) 
					input.getValue()).getProdPDFOrNormalisedSumPDFOrPDF().get(1));
		}
	}
	
	/*private static void printList(ArrayList<JAXBElement<?>> list){
		for (JAXBElement<?> e : list){
			System.out.println(e.getName());
		}
		System.out.println();
	}
	
	private static void printStringList(String [] list){
		for (String s : list){
			System.out.println(s);
		}
		System.out.println();
	}
	
	private static void printStringList(ArrayList<String> list){
		for (String s : list){
			System.out.print(s + " ");
		}
		System.out.println();
	}
	
	private static void printStringList(Stack<String> list){
		for (String s : list){
			System.out.print(s);
		}
		System.out.println();
	}*/
}
