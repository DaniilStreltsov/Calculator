package main.java;

import java.awt.*;
import javax.swing.*;

public class BasicOperationPanel extends JPanel {
    private CalculatorEngine engine;
    private DisplayPanel displayPanel;
    
    public BasicOperationPanel(CalculatorEngine engine, DisplayPanel displayPanel) {
        this.engine = engine;
        this.displayPanel = displayPanel;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new GridLayout(8, 1, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Operations"));
        
        add(createParenthesesButton("("));
        add(createParenthesesButton(")"));
        
        add(createOperationButton("+"));
        add(createOperationButton("-"));
        add(createOperationButton("×"));
        add(createOperationButton("÷"));
        add(createModuloButton()); 
        add(createPercentageButton());
        
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        controlPanel.add(createEqualsButton());
        controlPanel.add(createClearButton());
        add(controlPanel);
    }
    
    private JButton createParenthesesButton(String paren) {
        JButton button = new JButton(paren);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(60, 40));
        button.setBackground(new Color(255, 255, 224));
        button.setToolTipText("Parentheses for grouping");
        
        button.addActionListener(e -> {
            engine.addToExpression(paren);
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createOperationButton(String operation) {
        JButton button = new JButton(operation);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(60, 40));
        button.setBackground(new Color(230, 230, 250));
        
        button.addActionListener(e -> {
            if (engine.isExpressionMode()) {
                engine.addToExpression(operation);
            } else {
                engine.setOperator(operation);
            }
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createModuloButton() {
        JButton button = new JButton("mod");
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setPreferredSize(new Dimension(60, 40));
        button.setBackground(new Color(230, 230, 250));
        button.setToolTipText("Modulo (remainder) operation");
        
        button.addActionListener(e -> {
            engine.setOperator("%");
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createPercentageButton() {
        JButton button = new JButton("%");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(60, 40));
        button.setBackground(new Color(255, 255, 224));
        button.setToolTipText("Convert to percentage (divide by 100)");
        
        button.addActionListener(e -> {
            engine.performPercentage();
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createEqualsButton() {
        JButton button = new JButton("=");
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(144, 238, 144));
        
        button.addActionListener(e -> {
            if (engine.isExpressionMode()) {
                engine.evaluateExpression();
            } else {
                engine.calculate();
            }
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createClearButton() {
        JButton button = new JButton("C");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(255, 182, 193));
        
        button.addActionListener(e -> {
            engine.clear();
            displayPanel.updateDisplay();
        });
        
        return button;
    }
}