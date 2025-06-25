package main.java;

import java.awt.*;
import javax.swing.*;

public class NumericKeypadPanel extends JPanel {
    private CalculatorEngine engine;
    private DisplayPanel displayPanel;
    
    public NumericKeypadPanel(CalculatorEngine engine, DisplayPanel displayPanel) {
        this.engine = engine;
        this.displayPanel = displayPanel;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new GridLayout(4, 3, 5, 5));
        setBorder(BorderFactory.createTitledBorder("Numbers"));
        
        for (int i = 1; i <= 9; i++) {
            JButton button = createNumberButton(String.valueOf(i));
            add(button);
        }
        
        add(createNumberButton("0"));
        add(createDecimalButton());
        add(createSignToggleButton());
    }
    
    private JButton createNumberButton(String number) {
        JButton button = new JButton(number);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(60, 50));
        
        button.addActionListener(e -> {
            if (engine.isExpressionMode()) {
                engine.addToExpression(number);
            } else {
                engine.inputDigit(Integer.parseInt(number));
            }
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createDecimalButton() {
        JButton button = new JButton(".");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(60, 50));
        
        button.addActionListener(e -> {
            if (engine.isExpressionMode()) {
                engine.addToExpression(".");
            } else {
                engine.inputDecimal();
            }
            displayPanel.updateDisplay();
        });
        
        return button;
    }
    
    private JButton createSignToggleButton() {
        JButton button = new JButton("±");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(60, 50));
        
        button.addActionListener(e -> {
            engine.toggleSign();
            displayPanel.updateDisplay();
        });
        
        return button;
    }
}