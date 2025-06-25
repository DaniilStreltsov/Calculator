package main.java;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * Main GUI class for the Scientific Calculator
 * Handles the overall layout and mode switching
 */
public class CalculatorGUI extends JFrame implements KeyListener {
    private DisplayPanel displayPanel;
    private NumericKeypadPanel numericPanel;
    private BasicOperationPanel basicOpPanel;
    private ScientificFunctionPanel scientificPanel;
    private MemoryPanel memoryPanel;
    private CalculatorEngine engine;
    private JToggleButton modeToggle;
    private JPanel scientificContainer;
    private JButton historyButton;
    private JButton aboutButton;
    
    private boolean isScientificMode = false;
    
    public CalculatorGUI() {
        initializeComponents();
        setupGUI();
        setupEventHandlers();
        setFocusable(true);
        addKeyListener(this);
    }
    
    private void initializeComponents() {
        engine = new CalculatorEngine();
        displayPanel = new DisplayPanel(engine);
        numericPanel = new NumericKeypadPanel(engine, displayPanel);
        basicOpPanel = new BasicOperationPanel(engine, displayPanel);
        scientificPanel = new ScientificFunctionPanel(engine, displayPanel);
        memoryPanel = new MemoryPanel(engine, displayPanel);
        
        modeToggle = new JToggleButton("Scientific Mode");
        modeToggle.setFont(new Font("Arial", Font.BOLD, 12));
        modeToggle.setPreferredSize(new Dimension(120, 30));
        
        // New buttons
        historyButton = new JButton("History");
        historyButton.setFont(new Font("Arial", Font.BOLD, 10));
        historyButton.setPreferredSize(new Dimension(70, 30));
        historyButton.setBackground(new Color(173, 216, 230));
        historyButton.setToolTipText("View calculation history");
        
        aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Arial", Font.BOLD, 10));
        aboutButton.setPreferredSize(new Dimension(70, 30));
        aboutButton.setBackground(new Color(255, 228, 196));
        aboutButton.setToolTipText("About developers");
    }
    
    private void setupGUI() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Top panel with display and mode toggle
        JPanel topPanel = new JPanel(new BorderLayout());
        
        // Control buttons panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        controlPanel.add(historyButton);
        controlPanel.add(aboutButton);
        controlPanel.add(modeToggle);
        
        topPanel.add(displayPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.SOUTH);
        
        // Main calculator panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Left side - numbers and basic operations
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(numericPanel, BorderLayout.CENTER);
        leftPanel.add(basicOpPanel, BorderLayout.EAST);
        
        // Scientific functions container
        scientificContainer = new JPanel(new BorderLayout());
        scientificContainer.add(scientificPanel, BorderLayout.CENTER);
        scientificContainer.add(memoryPanel, BorderLayout.SOUTH);
        scientificContainer.setVisible(false);
        
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(scientificContainer, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void setupEventHandlers() {
        modeToggle.addActionListener(e -> {
            isScientificMode = modeToggle.isSelected();
            scientificContainer.setVisible(isScientificMode);
            
            if (isScientificMode) {
                modeToggle.setText("Simple Mode");
            } else {
                modeToggle.setText("Scientific Mode");
            }
            
            pack();
            setLocationRelativeTo(null);
        });
        
        // History button event handler
        historyButton.addActionListener(e -> {
            HistoryWindow historyWindow = new HistoryWindow(this, engine);
            historyWindow.setVisible(true);
        });
        
        // About button event handler
        aboutButton.addActionListener(e -> {
            DeveloperInfoWindow aboutWindow = new DeveloperInfoWindow(this);
            aboutWindow.setVisible(true);
        });
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        
        // Handle numeric input
        if (Character.isDigit(key)) {
            engine.inputDigit(Character.getNumericValue(key));
            displayPanel.updateDisplay();
        }
        // Handle operators
        else if (key == '+' || key == '-' || key == '*' || key == '/') {
            String operator = String.valueOf(key);
            if (key == '*') operator = "×";
            if (key == '/') operator = "÷";
            
            engine.setOperator(operator);
            displayPanel.updateDisplay();
        }
        // Handle special keys
        else if (key == '=' || keyCode == KeyEvent.VK_ENTER) {
            engine.calculate();
            displayPanel.updateDisplay();
        }
        else if (key == '.' || key == ',') {
            engine.inputDecimal();
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            engine.backspace();
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_ESCAPE || key == 'c' || key == 'C') {
            engine.clear();
            displayPanel.updateDisplay();
        }
        // Keyboard shortcuts for new windows
        else if (keyCode == KeyEvent.VK_H && e.isControlDown()) {
            // Ctrl+H for history
            HistoryWindow historyWindow = new HistoryWindow(this, engine);
            historyWindow.setVisible(true);
        }
        else if (keyCode == KeyEvent.VK_I && e.isControlDown()) {
            // Ctrl+I for about/info
            DeveloperInfoWindow aboutWindow = new DeveloperInfoWindow(this);
            aboutWindow.setVisible(true);
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void keyTyped(KeyEvent e) {}
}