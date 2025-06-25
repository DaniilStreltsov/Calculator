package main.java;

import java.awt.*;
import javax.swing.*;

public class DisplayPanel extends JPanel {
    private JTextField displayField;
    private JLabel historyLabel;
    private JLabel modeLabel;
    private CalculatorEngine engine;
    
    public DisplayPanel(CalculatorEngine engine) {
        this.engine = engine;
        initializeComponents();
        setupLayout();
    }
    
    public void updateDisplay() {
        String displayText = engine.getCurrentInput();
        
        if (engine.isExpressionMode()) {
            displayText = "Expr: " + displayText;
        }
        
        displayField.setText(displayText);
        
        String mode = engine.isDegreeMode() ? "DEG" : "RAD";
        String memory = engine.hasMemoryValue() ? "M: " + formatMemoryValue() : "M: 0";
        String exprMode = engine.isExpressionMode() ? " | EXPR" : "";
        modeLabel.setText(mode + " | " + memory + exprMode);
        
        java.util.List<String> history = engine.getHistory();
        if (!history.isEmpty()) {
            historyLabel.setText(history.get(history.size() - 1));
        } else {
            historyLabel.setText(" ");
        }
        
        if (engine.isError()) {
            displayField.setBackground(new Color(255, 230, 230));
        } else {
            displayField.setBackground(Color.WHITE);
        }
    }
    
    private void initializeComponents() {
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE);
        displayField.setBorder(BorderFactory.createLoweredBevelBorder());
        displayField.setPreferredSize(new Dimension(400, 50));
        displayField.setToolTipText("Use keyboard: Numbers, +, -, *, /, (, ), =, Backspace, Esc");
        
        historyLabel = new JLabel(" ");
        historyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        historyLabel.setForeground(Color.GRAY);
        historyLabel.setToolTipText("Last calculation - Press Ctrl+H for full history");
        
        modeLabel = new JLabel("DEG | M: 0");
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        modeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modeLabel.setForeground(Color.BLUE);
        modeLabel.setToolTipText("Angle mode and Memory status - Ctrl+D to toggle DEG/RAD");
        
        updateDisplay();
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(historyLabel, BorderLayout.CENTER);
        topPanel.add(modeLabel, BorderLayout.WEST);
        
        add(topPanel, BorderLayout.NORTH);
        add(displayField, BorderLayout.CENTER);
    }
    
    private String formatMemoryValue() {
        double memValue = engine.getMemoryValue();
        if (memValue == Math.floor(memValue) && !Double.isInfinite(memValue)) {
            return String.valueOf((long)memValue);
        } else {
            return String.format("%.3g", memValue);
        }
    }
}