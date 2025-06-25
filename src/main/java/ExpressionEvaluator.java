package main.java;

import java.util.*;

public class ExpressionEvaluator {
    
    public static double evaluate(String expression, boolean isDegreeMode) throws IllegalArgumentException {
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        
        expression = expression.replaceAll("\\s+", "");
        validateExpression(expression);
        
        List<String> postfix = infixToPostfix(expression);
        
        return evaluatePostfix(postfix, isDegreeMode);
    }
    
    private static void validateExpression(String expression) {
        if (expression.isEmpty()) {
            throw new IllegalArgumentException("Empty expression");
        }
        
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
        
        String validChars = "0123456789.+-×÷()^%sincotan!lqrt";
        for (char c : expression.toCharArray()) {
            if (validChars.indexOf(c) == -1) {
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        }
    }
    
    private static List<String> infixToPostfix(String expression) {
        List<String> output = new ArrayList<>();
        Stack<String> operators = new Stack<>();
        
        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            
            if (Character.isDigit(c) || c == '.') {
                StringBuilder number = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    number.append(expression.charAt(i));
                    i++;
                }
                output.add(number.toString());
                continue;
            }
            
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
                    operators.pop();
                }
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
    
    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("×") || 
               token.equals("÷") || token.equals("^") || token.equals("%");
    }
    
    private static boolean isFunction(String token) {
        return token.equals("sin") || token.equals("cos") || token.equals("tan") ||
               token.equals("log") || token.equals("ln") || token.equals("sqrt");
    }
    
    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}