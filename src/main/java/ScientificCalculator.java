package main.java;

import javax.swing.*;

/**
 * Main class for the Scientific Calculator application
 * Entry point of the application
 */
public class ScientificCalculator {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Failed to set system look and feel: " + e.getMessage());
            }
            
            CalculatorGUI calculator = new CalculatorGUI();
            calculator.setVisible(true);
        });
    }
}