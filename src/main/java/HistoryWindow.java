package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Window to display calculation history
 */
public class HistoryWindow extends JDialog {
    private CalculatorEngine engine;
    private JTextArea historyArea;
    private JButton clearHistoryButton;
    private JButton closeButton;
    
    public HistoryWindow(JFrame parent, CalculatorEngine engine) {
        super(parent, "Calculation History", true);
        this.engine = engine;
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        updateHistoryDisplay();
    }
    
    private void initializeComponents() {
        historyArea = new JTextArea(15, 40);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        historyArea.setBackground(new Color(248, 248, 248));
        historyArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        clearHistoryButton = new JButton("Clear History");
        clearHistoryButton.setFont(new Font("Arial", Font.BOLD, 12));
        clearHistoryButton.setBackground(new Color(255, 182, 193));
        
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setBackground(new Color(144, 238, 144));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(70, 130, 180));
        JLabel titleLabel = new JLabel("Calculation History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        // Scroll pane for history
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(clearHistoryButton);
        buttonPanel.add(closeButton);
        
        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void setupEventHandlers() {
        clearHistoryButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to clear all calculation history?",
                "Clear History",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                engine.getHistory().clear();
                updateHistoryDisplay();
            }
        });
        
        closeButton.addActionListener(e -> dispose());
    }
    
    public void updateHistoryDisplay() {
        List<String> history = engine.getHistory();
        StringBuilder sb = new StringBuilder();
        
        if (history.isEmpty()) {
            sb.append("No calculations performed yet.\n\n");
            sb.append("Perform some calculations to see them here!");
        } else {
            sb.append("Total Calculations: ").append(history.size()).append("\n");
            sb.append("═".repeat(50)).append("\n\n");
            
            for (int i = 0; i < history.size(); i++) {
                sb.append(String.format("%3d. %s\n", i + 1, history.get(i)));
            }
        }
        
        historyArea.setText(sb.toString());
        historyArea.setCaretPosition(historyArea.getDocument().getLength());
    }
}