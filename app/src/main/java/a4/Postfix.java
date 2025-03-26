
package a4;

import java.util.ArrayDeque;

public class Postfix {
    /**
     * This method evaluates a postfix expression and computes the result.
     * @param tokens the queue of tokens in postfix expression
     * @return the computed result
     */

    public static Double postfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Double> stack = new ArrayDeque<>();
        
        while (!tokens.isEmpty()) {
            Object token = tokens.removeFirst();
            
            if (token instanceof Double) {
                stack.addFirst((Double) token);
            } else if (token instanceof Character) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Malformed expression: Not enough operands for operator " + token);
                }
                double b = stack.removeFirst();
                double a = stack.removeFirst();
                char operator = (Character) token;
                
                if (operator == '+') {
                    stack.addFirst(a + b);
                } else if (operator == '-') {
                    stack.addFirst(a - b);
                } else if (operator == '*') {
                    stack.addFirst(a * b);
                } else if (operator == '/') {
                    if (b == 0) {
                        throw new ArithmeticException("Division by zero is not allowed");
                    }
                    stack.addFirst(a / b);
                } else {
                    throw new IllegalArgumentException("Invalid operator: " + operator);
                }
            } else {
                throw new IllegalArgumentException("Invalid token type: " + token);
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Malformed expression: Stack should contain exactly one result at the end");
        }
        
        return stack.removeFirst();
    }
}