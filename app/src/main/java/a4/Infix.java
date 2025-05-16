package a4;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class Infix {

    /**
     * Converts an infix expression (with parentheses) to a postfix expression.
     * The conversion uses a stack and a queue to rearrange the tokens according to 
     * operator precedence and associativity.
     * 
     * @param tokens A queue of tokens representing the infix expression to be converted.
     * @return A queue of tokens representing the postfix expression.
     * @throws IllegalArgumentException If the expression contains mismatched parentheses.
     */
    public static Queue<Object> infixToPostfix(Queue<Token> tokens) throws IllegalArgumentException {
        // Stack for operators and parentheses
        ArrayDeque<Object> stack = new ArrayDeque<>();
        // Queue for output (postfix expression)
        Queue<Object> output = new LinkedList<>();

        while (!tokens.isEmpty()) {
            Token token = tokens.poll();
            // If the token is a number, add it to the output queue
            if (token instanceof DoubleToken) {
                output.add(token.getValue());
            }
            // If the token is an operator, process it
            else if (token instanceof OperatorToken) {
                char operator = ((OperatorToken) token).getOperator();
                while (!stack.isEmpty() && stack.peek() instanceof OperatorToken) {
                    char topOperator = (char) stack.peek();
                    if (precedence(topOperator) >= precedence(operator)) {
                        output.add(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(operator);
            }
            // If the token is a left parenthesis, push it onto the stack
            else if (token instanceof LeftParenthesisToken) {
                stack.push('(');
            }
            // If the token is a right parenthesis, pop until left parenthesis is found
            else if (token instanceof RightParenthesisToken) {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.add(stack.pop());
                }
                if (stack.isEmpty() || stack.peek() != '(') {
                    throw new IllegalArgumentException("Mismatched parentheses");
                }
                stack.pop(); // pop the '(' from the stack
            }
        }

        // Pop remaining operators in the stack to the output
        while (!stack.isEmpty()) {
            if (stack.peek() == '(') {
                throw new IllegalArgumentException("Mismatched parentheses");
            }
            output.add(stack.pop());
        }

        return output;
    }

    /**
     * Determines the precedence of the given operator. 
     * Operators with higher precedence are processed first.
     * 
     * @param op The operator whose precedence is to be determined.
     * @return The precedence level of the operator. Higher values indicate higher precedence.
     */
    private static int precedence(char op) {
        if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        } else if (op == '^') {
            return 3;
        } else {
            return -1; // For unknown operators
        }
    }
}