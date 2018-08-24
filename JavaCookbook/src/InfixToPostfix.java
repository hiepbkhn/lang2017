
/* Java implementation to convert infix expression to postfix*/
//Note that here we use Stack class for Stack operations

import java.util.Stack;

class InfixToPostfix {
	// A utility function to return precedence of a given operator
	// Higher returned value means higher precedence
	static int Prec(char ch) {
		switch (ch) {
		case '+':
		case '-':
			return 1;

		case '*':
		case '/':
		case '%':
			return 2;

		}
		return -1;
	}

	// The main method that converts given infix expression
	// to postfix expression.
	static String infixToPostfix(String exp) {
		// initializing empty String for result
		String result = new String("");

		// initializing empty stack
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < exp.length(); ++i) {
			char c = exp.charAt(i);

			// If the scanned character is an operand, add it to output.
			if (Character.isLetterOrDigit(c) || c == '.')		// Decimal character
				result += c;

			// If the scanned character is an '(', push it to the stack.
			else if (c == '(')
				stack.push(c);

			// If the scanned character is an ')', pop and output from the stack
			// until an '(' is encountered.
			else if (c == ')') {
				while (!stack.isEmpty() && stack.peek() != '(')
					result += stack.pop();

				if (!stack.isEmpty() && stack.peek() != '(')
					return "Invalid Expression"; // invalid expression
				else
					stack.pop();
			} else // an operator is encountered
			{
				while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
					result += stack.pop();
				stack.push(c);
			}

		}

		// pop all the operators from the stack
		while (!stack.isEmpty())
			result += stack.pop();

		return result;
	}

	// Method to evaluate value of a postfix expression
	static double evaluatePostfix(String exp) {
		// create a stack
		Stack<Double> stack = new Stack<>();
		double x = 72.59;

		// Scan all characters one by one
		for (int i = 0; i < exp.length(); i++) {
			char c = exp.charAt(i);

			// If the scanned character is an operand (number here),
			// push it to the stack.
			if(c == ' ')
	            continue;
			
			else if (c == 'x')
				stack.push(x);
	             
            // If the scanned character is an operand 
            // (number here),extract the number
            // Push it to the stack.
            else if(Character.isDigit(c))
            {
                double n = 0;
                 
                //extract the characters and store it in num
                while(Character.isDigit(c))
                {
                    n = n*10 + (int)(c-'0');
                    i++;
                    c = exp.charAt(i);
                }
//                i--;
                
                //
                if (c == '.'){
                	i ++;
                	c = exp.charAt(i);
                	double f = 0;
                	int numF = 0;
                	while(Character.isDigit(c))	
                    {
                		numF ++;
                        f = f*10 + (int)(c-'0');
                        i++;
                        c = exp.charAt(i);
                    }
                	i--;
                	
                	for (int j = 0; j < numF; j++)
                		f = f / 10.0;
                	
                	n = n + f;
                }
                System.out.println("n = " + n);
                
                //push the number in stack
                stack.push(n);
            }

			// If the scanned character is an operator, pop two
			// elements from stack apply the operator
			else {
				double val1 = stack.pop();
				double val2 = stack.pop();

				switch (c) {
				case '+':
					stack.push(val2 + val1);
					break;

				case '-':
					stack.push(val2 - val1);
					break;

				case '/':
					stack.push(val2 / val1);
					break;

				case '*':
					stack.push(val2 * val1);
					break;
				}
			}
		}
		return stack.pop();
	}

	// Driver method
	public static void main(String[] args) {
//		String exp = "2+3*1-9";
//		String postfix = infixToPostfix(exp);
//		System.out.println(postfix);
//		double val = evaluatePostfix(postfix);
//		System.out.println(val);
		
		//
		String exp = "((((x/7.25)+101.496-((x/4597924)-(x*4))*x/10)/4200+38.5)/0.51+715*x+x/8.5)*0.17295+(x*1.46-519000)/72.925+(x/10.375)-3.154/x";
		String postfix = infixToPostfix(exp);
		System.out.println(postfix);
		double val = evaluatePostfix(postfix);
		System.out.println(val);
	}
}
