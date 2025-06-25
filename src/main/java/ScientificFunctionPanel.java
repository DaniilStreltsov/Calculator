package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * Panel containing scientific function buttons
 */
public class ScientificFunctionPanel extends JPanel {
    private CalculatorEngine engine;
    private DisplayPanel displayPanel;
    
    public ScientificFunctionPanel(CalculatorEngine engine, DisplayPanel displayPanel) {
        this.engine = engine;
        this.displayPanel = displayPanel;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new GridLayout(6, 3, 3, 3));
        setBorder(BorderFactory.createTitledBorder("Scientific Functions"));
        
        // Trigonometric functions
        add(createFunctionButton("sin"));
        add(createFunctionButton("cos"));
        add(createFunctionButton("tan"));
        
        add(createFunctionButton("asin"));
        add(createFunctionButton("acos"));
        add(createFunctionButton("atan"));
        
        // Logarithmic and exponential
        add(createFunctionButton("log"));
        add(createFunctionButton("ln"));
        add(createFunctionButton("e^x"));
        
        add(createFunctionButton("10^x"));
        add(createOperationButton("x^y"));
        add(createFunctionButton("sqrt"));
        
        // Special functions
        add(createFunctionButton("x!"));
        add(createBackspaceButton());
        add(createAngleModeButton());
    }
    
    private JButton createFunctionButton(String function) {
        JButton button = new JButton(function);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setPreferredSize(new Dimension(70, 35));
        button.setBackground(new Color(240, 248, 255));
        
        button.addActionListener(e -> {
            engine.performScientificOperation(function);
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createOperationButton(String operation) {
        JButton button = new JButton(operation);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setPreferredSize(new Dimension(70, 35));
        button.setBackground(new Color(230, 230, 250));
        
        button.addActionListener(e -> {
            engine.setOperator(operation);
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createBackspaceButton() {
        JButton button = new JButton("<");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(70, 35));
        button.setBackground(new Color(255, 222, 173));
        
        button.addActionListener(e -> {
            engine.backspace();
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createAngleModeButton() {
        JButton button = new JButton("DEG");
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setPreferredSize(new Dimension(70, 35));
        button.setBackground(new Color(255, 255, 224));
        
        button.addActionListener(e -> {
            engine.toggleAngleMode();
            button.setText(engine.isDegreeMode() ? "DEG" : "RAD");
            displayPanel.updateDisplay();
        });
        
        return button;
    }
}