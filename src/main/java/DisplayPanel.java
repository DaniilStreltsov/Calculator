package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * Display panel for showing calculator input and results
 */
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
    
    private void initializeComponents() {
        displayField = new JTextField();
        displayField.setEditable(false);
        displayField.setFont(new Font("Arial", Font.BOLD, 24));
        displayField.setHorizontalAlignment(JTextField.RIGHT);
        displayField.setBackground(Color.WHITE);
        displayField.setBorder(BorderFactory.createLoweredBevelBorder());
        displayField.setPreferredSize(new Dimension(400, 50));
        
        historyLabel = new JLabel(" ");
        historyLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        historyLabel.setForeground(Color.GRAY);
        
        modeLabel = new JLabel("DEG | M: 0");
        modeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        modeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        modeLabel.setForeground(Color.BLUE);
        
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
    
    /**
     * Update the display with current calculator state
     */
    public void updateDisplay() {
        displayField.setText(engine.getCurrentInput());
        
        // Update mode and memory indicator
        String mode = engine.isDegreeMode() ? "DEG" : "RAD";
        String memory = engine.hasMemoryValue() ? "M: " + formatMemoryValue() : "M: 0";
        modeLabel.setText(mode + " | " + memory);
        
        // Show last calculation in history
        java.util.List<String> history = engine.getHistory();
        if (!history.isEmpty()) {
            historyLabel.setText(history.get(history.size() - 1));
        } else {
            historyLabel.setText(" ");
        }
        
        // Error highlighting
        if (engine.isError()) {
            displayField.setBackground(new Color(255, 230, 230));
        } else {
            displayField.setBackground(Color.WHITE);
        }
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