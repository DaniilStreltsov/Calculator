package main.java;

import java.awt.*;
import javax.swing.*;

public class MemoryPanel extends JPanel {
    private CalculatorEngine engine;
    private DisplayPanel displayPanel;
    
    public MemoryPanel(CalculatorEngine engine, DisplayPanel displayPanel) {
        this.engine = engine;
        this.displayPanel = displayPanel;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setLayout(new GridLayout(1, 5, 3, 3));
        setBorder(BorderFactory.createTitledBorder("Memory"));
        
        add(createMemoryButton("MS", "Memory Store"));
        add(createMemoryButton("MR", "Memory Recall"));
        add(createMemoryButton("MC", "Memory Clear"));
        add(createMemoryButton("M+", "Memory Add"));
        add(createMemoryButton("M-", "Memory Subtract"));
    }
    
    private JButton createMemoryButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setPreferredSize(new Dimension(60, 30));
        button.setBackground(new Color(255, 240, 245));
        button.setToolTipText(tooltip);
        
        button.addActionListener(e -> {
            switch (text) {
                case "MS":
                    engine.memoryStore();
                    break;
                case "MR":
                    engine.memoryRecall();
                    break;
                case "MC":
                    engine.memoryClear();
                    break;
                case "M+":
                    engine.memoryAdd();
                    break;
                case "M-":
                    engine.memorySubtract();
                    break;
            }
            displayPanel.updateDisplay();
        });
        
        return button;
    }
}