package main.java;

import java.util.ArrayList;
import java.util.List;

/**
 * Core calculation engine for the calculator
 * Handles all mathematical operations and state management
 */
public class CalculatorEngine {
    private double currentValue;
    private double previousValue;
    private String currentOperator;
    private String currentInput;
    private boolean isNewCalculation;
    private boolean isError;
    private String errorMessage;
    
    // Memory storage
    private double memoryValue;
    
    // Calculation history
    private List<String> history;
    
    // Angle mode for trigonometric functions
    private boolean isDegreeMode = true;
    
    // Add these new fields to the class
    private StringBuilder expressionBuilder;
    private boolean isExpressionMode;
    
    public CalculatorEngine() {
        this.history = new ArrayList<>();
        this.expressionBuilder = new StringBuilder();
        clear();
    }
    
    /**
     * Clear all values and reset calculator
     */
    public void clear() {
        currentValue = 0;
        previousValue = 0;
        currentOperator = null;
        currentInput = "0";
        isNewCalculation = true;
        isError = false;
        errorMessage = null;
        expressionBuilder.setLength(0);
        isExpressionMode = false;
    }
    
    /**
     * Clear calculation history
     */
    public void clearHistory() {
        history.clear();
    }
    
    /**
     * Input a digit
     */
    public void inputDigit(int digit) {
        if (isError) clear();
        
        if (isNewCalculation) {
            currentInput = String.valueOf(digit);
            isNewCalculation = false;
        } else {
            if (currentInput.equals("0")) {
                currentInput = String.valueOf(digit);
            } else {
                currentInput += digit;
            }
        }
        currentValue = Double.parseDouble(currentInput);
    }
    
    /**
     * Input decimal point
     */
    public void inputDecimal() {
        if (isError) clear();
        
        if (isNewCalculation) {
            currentInput = "0.";
            isNewCalculation = false;
        } else if (!currentInput.contains(".")) {
            currentInput += ".";
        }
    }
    
    /**
     * Set operator for calculation
     */
    public void setOperator(String operator) {
        if (isError) return;

        if (currentOperator != null && !isNewCalculation) {
            calculate();
        }

        previousValue = currentValue;
        currentOperator = operator;
        isNewCalculation = true;
    }
    
    /**
     * Perform calculation
     */
    public void calculate() {
        if (isError || currentOperator == null) return;
        
        try {
            double result = performOperation(previousValue, currentValue, currentOperator);
            
            String calculation = formatNumber(previousValue) + " " + currentOperator + " " + 
                               formatNumber(currentValue) + " = " + formatNumber(result);
            history.add(calculation);
            
            currentValue = result;
            currentInput = formatNumber(result);
            currentOperator = null;
            isNewCalculation = true;
            
        } catch (ArithmeticException e) {
            setError("Math Error: " + e.getMessage());
        } catch (Exception e) {
            setError("Error: " + e.getMessage());
        }
    }
    
    /**
     * Perform basic arithmetic operations
     */
    private double performOperation(double a, double b, String operator) throws ArithmeticException {
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
            case "%":
                // Handle percentage calculation
                // If second operand is 0, treat as percentage conversion (a/100)
                // Example: 3%= becomes 3/100 = 0.03
                if (b == 0) {
                    return a / 100.0;
                } else {
                    // If second operand is not 0, use modulo operation
                    // Example: 100%3 = 1 (remainder of 100/3)
                    if (b == 0) throw new ArithmeticException("Division by zero");
                    return a % b;
                }
            case "x^y":
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Handle percentage operation specifically
     * This method can be called directly for percentage button
     */
    public void performPercentage() {
        if (isError) return;
        
        try {
            // Convert current value to percentage (divide by 100)
            double result = currentValue / 100.0;
            
            String calculation = formatNumber(currentValue) + "% = " + formatNumber(result);
            history.add(calculation);
            
            currentValue = result;
            currentInput = formatNumber(result);
            isNewCalculation = true;
            
        } catch (Exception e) {
            setError("Error: " + e.getMessage());
        }
    }
    
    /**
     * Backspace functionality
     */
    public void backspace() {
        if (isError) {
            clear();
            return;
        }
        
        if (currentInput.length() > 1) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            currentValue = Double.parseDouble(currentInput);
        } else {
            currentInput = "0";
            currentValue = 0;
        }
    }
    
    /**
     * Toggle positive/negative
     */
    public void toggleSign() {
        if (isError) return;
        
        currentValue = -currentValue;
        currentInput = formatNumber(currentValue);
    }
    
    // Scientific functions
    public void performScientificOperation(String function) {
        if (isError) return;
        
        try {
            double result = 0;
            double angleInRadians = isDegreeMode ? Math.toRadians(currentValue) : currentValue;
            
            switch (function) {
                case "sin":
                    result = Math.sin(angleInRadians);
                    break;
                case "cos":
                    result = Math.cos(angleInRadians);
                    break;
                case "tan":
                    result = Math.tan(angleInRadians);
                    break;
                case "asin":
                    if (currentValue < -1 || currentValue > 1) {
                        throw new ArithmeticException("Domain error");
                    }
                    result = Math.asin(currentValue);
                    if (isDegreeMode) result = Math.toDegrees(result);
                    break;
                case "acos":
                    if (currentValue < -1 || currentValue > 1) {
                        throw new ArithmeticException("Domain error");
                    }
                    result = Math.acos(currentValue);
                    if (isDegreeMode) result = Math.toDegrees(result);
                    break;
                case "atan":
                    result = Math.atan(currentValue);
                    if (isDegreeMode) result = Math.toDegrees(result);
                    break;
                case "log":
                    if (currentValue <= 0) throw new ArithmeticException("Domain error");
                    result = Math.log10(currentValue);
                    break;
                case "ln":
                    if (currentValue <= 0) throw new ArithmeticException("Domain error");
                    result = Math.log(currentValue);
                    break;
                case "sqrt":
                    if (currentValue < 0) throw new ArithmeticException("Domain error");
                    result = Math.sqrt(currentValue);
                    break;
                case "e^x":
                    result = Math.exp(currentValue);
                    break;
                case "10^x":
                    result = Math.pow(10, currentValue);
                    break;
                case "x!":
                    if (currentValue < 0 || currentValue != Math.floor(currentValue)) {
                        throw new ArithmeticException("Factorial domain error");
                    }
                    result = factorial((int)currentValue);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown function: " + function);
            }
            
            String calculation = function + "(" + formatNumber(currentValue) + ") = " + formatNumber(result);
            history.add(calculation);
            
            currentValue = result;
            currentInput = formatNumber(result);
            isNewCalculation = true;
            
        } catch (ArithmeticException e) {
            setError("Math Error: " + e.getMessage());
        } catch (Exception e) {
            setError("Error: " + e.getMessage());
        }
    }
    
    /**
     * Calculate factorial
     */
    private double factorial(int n) {
        if (n > 170) throw new ArithmeticException("Number too large");
        if (n == 0 || n == 1) return 1;
        
        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    // Memory operations
    public void memoryStore() {
        memoryValue = currentValue;
    }
    
    public void memoryRecall() {
        currentValue = memoryValue;
        currentInput = formatNumber(currentValue);
        isNewCalculation = true;
    }
    
    public void memoryClear() {
        memoryValue = 0;
    }
    
    public void memoryAdd() {
        memoryValue += currentValue;
    }
    
    public void memorySubtract() {
        memoryValue -= currentValue;
    }
    
    /**
     * Toggle angle mode (degrees/radians)
     */
    public void toggleAngleMode() {
        isDegreeMode = !isDegreeMode;
    }
    
    /**
     * Set error state
     */
    private void setError(String message) {
        isError = true;
        errorMessage = message;
        currentInput = message;
    }
    
    /**
     * Format number for display
     */
    private String formatNumber(double number) {
        if (Double.isInfinite(number)) return "∞";
        if (Double.isNaN(number)) return "NaN";
        
        // Remove unnecessary decimal places
        if (number == Math.floor(number) && !Double.isInfinite(number)) {
            return String.valueOf((long)number);
        } else {
            return String.format("%.10g", number);
        }
    }
    
    // Getters
    public String getCurrentInput() { return currentInput; }
    public boolean isError() { return isError; }
    public String getErrorMessage() { return errorMessage; }
    public List<String> getHistory() { return history; } // Return the actual list for modification
    public boolean isDegreeMode() { return isDegreeMode; }
    public double getMemoryValue() { return memoryValue; }
    public boolean hasMemoryValue() { return memoryValue != 0; }
    
    // Add new methods for expression handling
    
    /**
     * Add character to expression
     */
    public void addToExpression(String token) {
        if (isError) clear();
        
        if (!isExpressionMode) {
            expressionBuilder.setLength(0);
            isExpressionMode = true;
        }
        
        expressionBuilder.append(token);
        currentInput = expressionBuilder.toString();
    }
    
    /**
     * Evaluate the current expression
     */
    public void evaluateExpression() {
        if (isError) return;
        
        String expression = expressionBuilder.toString();
        if (expression.isEmpty()) {
            expression = currentInput;
        }
        
        try {
            double result = ExpressionEvaluator.evaluate(expression, isDegreeMode);
            
            String calculation = expression + " = " + formatNumber(result);
            history.add(calculation);
            
            currentValue = result;
            currentInput = formatNumber(result);
            expressionBuilder.setLength(0);
            isExpressionMode = false;
            isNewCalculation = true;
            
        } catch (IllegalArgumentException e) {
            setError("Syntax Error: " + e.getMessage());
        } catch (ArithmeticException e) {
            setError("Math Error: " + e.getMessage());
        } catch (Exception e) {
            setError("Error: " + e.getMessage());
        }
    }
    
    /**
     * Remove last character from expression
     */
    public void backspaceExpression() {
        if (isError) {
            clear();
            return;
        }
        
        if (isExpressionMode && expressionBuilder.length() > 0) {
            expressionBuilder.setLength(expressionBuilder.length() - 1);
            currentInput = expressionBuilder.length() > 0 ? expressionBuilder.toString() : "0";
            if (expressionBuilder.length() == 0) {
                isExpressionMode = false;
            }
        } else {
            backspace(); // Use existing backspace for non-expression mode
        }
    }
    
    /**
     * Check if in expression mode
     */
    public boolean isExpressionMode() {
        return isExpressionMode;
    }
    
    /**
     * Get current expression
     */
    public String getCurrentExpression() {
        return expressionBuilder.toString();
    }
}