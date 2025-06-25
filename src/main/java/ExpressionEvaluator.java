package main.java;

import java.util.*;

/**
 * Expression evaluator that handles mathematical expressions with proper precedence
 * Supports parentheses, basic arithmetic, and scientific functions
 */
public class ExpressionEvaluator {
    
    /**
     * Evaluates a mathematical expression string
     * @param expression The mathematical expression to evaluate
     * @param isDegreeMode Whether trigonometric functions should use degrees
     * @return The result of the evaluation
     * @throws IllegalArgumentException for invalid expressions
     */
    public static double evaluate(String expression, boolean isDegreeMode) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        
        // Remove whitespace and validate
        expression = expression.replaceAll("\\s+", "");
        validateExpression(expression);
        
        // Convert infix to postfix (Shunting-yard algorithm)
        List<String> postfix = infixToPostfix(expression);
        
        // Evaluate postfix expression
        return evaluatePostfix(postfix, isDegreeMode);
    }
    
    /**
     * Validates the expression for basic syntax errors
     */
    private static void validateExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        
        // Check for balanced parentheses
        int parenthesesCount = 0;
        for (char c : expression.toCharArray()) {
            if (c == '(') {
                parenthesesCount++;
            } else if (c == ')') {
                parenthesesCount--;
                if (parenthesesCount < 0) {
                    throw new IllegalArgumentException("Unbalanced parentheses");
                }
            }
        }
        
        if (parenthesesCount != 0) {
            throw new IllegalArgumentException("Unbalanced parentheses");
        }
        
        // Check for invalid characters
        String validChars = "0123456789.+-×÷()^%sincotan!lqrt";
        for (char c : expression.toCharArray()) {
            if (validChars.indexOf(c) == -1) {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
    }
    
    /**
     * Converts infix expression to postfix using Shunting-yard algorithm
     */
    private static List<String> infixToPostfix(String expression) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        
        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c) || c == '.') {
                // Parse number
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                output.add(number.toString());
                continue;
            }
            
            // Handle functions (sin, cos, tan, log, ln, sqrt)
            if (Character.isLetter(c)) {
                StringBuilder function = new StringBuilder();
                while (i < expression.length() && Character.isLetter(expression.charAt(i))) {
                    function.append(expression.charAt(i));
                    i++;
                }
                operators.push(function.toString());
                continue;
            }
            
            if (c == '(') {
                operators.push("(");
            } else if (c == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                if (!operators.isEmpty()) {
                    operators.pop(); // Remove the '('
                }
                // If there's a function before the parentheses, add it to output
                if (!operators.isEmpty() && isFunction(operators.peek())) {
                    output.add(operators.pop());
                }
            } else if (isOperator(String.valueOf(c))) {
                while (!operators.isEmpty() && 
                       !operators.peek().equals("(") && 
                       getPrecedence(operators.peek()) >= getPrecedence(String.valueOf(c))) {
                    output.add(operators.pop());
                }
                operators.push(String.valueOf(c));
            }
            
            i++;
        }
        
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }
        
        return output;
    }
    
    /**
     * Evaluates postfix expression
     */
    private static double evaluatePostfix(List<String> postfix, boolean isDegreeMode) {
        Stack<Double> stack = new Stack<>();
        
        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                stack.push(performOperation(a, b, token));
            } else if (isFunction(token)) {
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Invalid expression");
                }
                double operand = stack.pop();
                stack.push(performFunction(operand, token, isDegreeMode));
            }
        }
        
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid expression");
        }
        
        return stack.pop();
    }
    
    /**
     * Performs arithmetic operations
     */
    private static double performOperation(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "×":
                return a * b;
            case "÷":
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a / b;
            case "^":
                return Math.pow(a, b);
            case "%":
                if (b == 0) throw new ArithmeticException("Division by zero");
                return a % b;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Performs mathematical functions
     */
    private static double performFunction(double operand, String function, boolean isDegreeMode) {
        double angleInRadians = isDegreeMode ? Math.toRadians(operand) : operand;
        
        switch (function) {
            case "sin":
                return Math.sin(angleInRadians);
            case "cos":
                return Math.cos(angleInRadians);
            case "tan":
                return Math.tan(angleInRadians);
            case "log":
                if (operand <= 0) throw new ArithmeticException("Domain error");
                return Math.log10(operand);
            case "ln":
                if (operand <= 0) throw new ArithmeticException("Domain error");
                return Math.log(operand);
            case "sqrt":
                if (operand < 0) throw new ArithmeticException("Domain error");
                return Math.sqrt(operand);
            default:
                throw new IllegalArgumentException("Unknown function: " + function);
        }
    }
    
    /**
     * Gets operator precedence
     */
    private static int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "×":
            case "÷":
            case "%":
                return 2;
            case "^":
                return 3;
            default:
                return 0;
        }
    }
    
    /**
     * Checks if token is an operator
     */
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("×") || 
               token.equals("÷") || token.equals("^") || token.equals("%");
    }
    
    /**
     * Checks if token is a function
     */
    private static boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("tan") ||
               token.equals("log") || token.equals("ln") || token.equals("sqrt");
    }
    
    /**
     * Checks if token is a number
     */
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}