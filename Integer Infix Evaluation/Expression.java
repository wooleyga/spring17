import java.util.Scanner;

public class Expression {
	private static final String listOfOperators = "+-*/%^";
	private static final String listOfDelimiters = "()[]{}";
	
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.print("Infix expression: ");
		String infixExpression = in.nextLine();
		System.out.println(infixToPostfix(infixExpression));
		System.out.println(evaluatePostfix(infixToPostfix(infixExpression)));
	}

	/*
	 * Converts an infix expression into a postfix expression.
	 */
	public static String infixToPostfix(String infix) throws Exception{
		assert checkBalance(infix) == true;
		infix = infix.replaceAll("\\s+", "");

		LinkedStack<Character> operatorStack = new LinkedStack<Character>();
		String postfix = "";
		
		for (int i = 0; i < infix.length(); i++) {
			char nextCharacter = infix.charAt(i);
			
			if(Character.isDigit(nextCharacter)){
				while(Character.isDigit(infix.charAt(i))) {
					postfix += infix.charAt(i);
					i++;
					
					if(i >= infix.length()){
						break;
					}
				}
				
				i--; //reset for the for-loop counter
				postfix += " ";
			}
			
			else if(nextCharacter == '(' || nextCharacter == '[' || nextCharacter == '{'){
				operatorStack.push(nextCharacter);
			}
			
			else if (nextCharacter == ')' || nextCharacter == ']' || nextCharacter == '}') {
				char topOperator = operatorStack.pop();
				if (nextCharacter == ')') {
					while (topOperator != '(') {
						postfix += topOperator + " ";
						topOperator = operatorStack.pop();
					}
				}
				else if (nextCharacter == ']') {
					while (topOperator != '[') {
						postfix += topOperator + " ";
						topOperator = operatorStack.pop();
					}
				}
				else if(nextCharacter == '}'){
					while(topOperator != '{'){
						postfix += topOperator + " ";
						topOperator = operatorStack.pop();
					}
				}
			}

			else if(isOperatorNoExponent(nextCharacter)){
				while(!operatorStack.isEmpty() && !isHigherPriorityOperator(nextCharacter, operatorStack.peek())){
					postfix += operatorStack.pop() + " "; 
				}
				operatorStack.push(infix.charAt(i));
			}
			
			else if(nextCharacter == '^'){
				operatorStack.push(nextCharacter);
			}

		}
		
		while(!operatorStack.isEmpty()){
			char topOperator = operatorStack.pop();
			if(topOperator == '(' || topOperator == '[' || topOperator == '{'){
				throw new Exception();
			}
			postfix += topOperator + " ";
		}

		return postfix;
	}

	/*
	 * Evaluates a postfix expression.
	 */
	public static int evaluatePostfix(String postfix) throws NullPointerException{
		LinkedStack<Integer> valueStack = new LinkedStack<Integer>();
		
		for(int i = 0; i < postfix.length(); i++){
			char nextCharacter = postfix.charAt(i);
			if(nextCharacter == ' '){
				continue;
			}
			
			else if(Character.isDigit(nextCharacter)){
				String currentInteger = "";
				int numberStringCounter = 0;
				while(Character.isDigit(postfix.charAt(i + numberStringCounter))) {
					currentInteger += postfix.charAt(i + numberStringCounter);
					numberStringCounter++;
					
					if(i + numberStringCounter >= postfix.length()){
						break;
					}
					
				}
				i = i + numberStringCounter - 1; //reset for the for-loop counter
				valueStack.push(Integer.parseInt(currentInteger));
			}
			
			else if(isOperator(nextCharacter)){
				int operand2 = valueStack.pop();
				int operand1 = valueStack.pop();
				int result = 0;
				
				if(nextCharacter == '+'){
					result = operand1 + operand2;
				}
				else if(nextCharacter == '-'){
					result = operand1 - operand2;
				}
				else if(nextCharacter == '*'){
					result = operand1 * operand2;
				}
				else if(nextCharacter == '/'){
					result = operand1 / operand2;
				}
				else if(nextCharacter == '%'){
					result = operand1 % operand2;
				}
				else if(nextCharacter == '^'){
					result = (int) Math.pow(operand1, operand2);
				}
				valueStack.push(result);
			}
		}
		
		return valueStack.peek();
	}

	/*
	 * Checks the delimiter balance of a given expression.
	 * 
	 * Note: Does not check for any other type of balance.
	 */
	public static boolean checkBalance(String expression) {
		LinkedStack<Character> delimStack = new LinkedStack<Character>();

		for(int i = 0; i < expression.length(); i++) {
			char nextCharacter = expression.charAt(i);
			if (nextCharacter == '(' || nextCharacter == '[' || nextCharacter == '{') {
				delimStack.push(nextCharacter);
			} else if (nextCharacter == ')' || nextCharacter == ']' || nextCharacter == '}') {
				if (delimStack.isEmpty() || !isPairOfDelimiters(delimStack.peek(), nextCharacter)) {
					return false;
				} else {
					delimStack.pop();
				}
			}
		}
		

		return delimStack.isEmpty();
	}

	/*
	 * Checks if two delimiters are a pair.
	 */
	private static boolean isPairOfDelimiters(char open, char close) {
		boolean isPair = false;

		if (open == '(' && close == ')') {
			isPair = true;
		}
		if (open == '[' && close == ']') {
			isPair = true;
		}
		if (open == '{' && close == '}') {
			isPair = true;
		}

		return isPair;
	}

	private static boolean isValidDelimiter(char delimiter) {
		return listOfDelimiters.contains(Character.toString(delimiter));
	}
	
	private static boolean isOperator(char c){
		return listOfOperators.contains(Character.toString(c));
	}
	
	private static boolean isOperatorNoExponent(char c){
		return "+-*/%".contains(Character.toString(c));
	}

	/*
	 * Checks if the current operator is higher priority than the next in the stack.
	 */
	private static boolean isHigherPriorityOperator(char current, char nextInStack) {
		if (isValidDelimiter(current)) {
			return true;
		}
		else if(isValidDelimiter(nextInStack)){
			return true;
		}
		else if (current == '+' || current == '-') {
			if (isValidDelimiter(nextInStack)) {
				return true;
			} else {
				return false;
			}

		} else if (current == '*' || current == '/' || current == '%') {
			if (nextInStack == '+' || nextInStack == '-') {
				return true;
			} else {
				return false;
			}
		} else if (current == '^') {
			return true;
		}

		if ((Character) nextInStack == null) {
			return true;
		}
		return false;
	}

}
