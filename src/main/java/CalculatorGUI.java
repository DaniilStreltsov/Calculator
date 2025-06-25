package main.java;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

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
        setupKeyboardFocus();
    }
    
    private void setupKeyboardFocus() {
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);
        
        SwingUtilities.invokeLater(() -> {
            requestFocus();
            requestFocusInWindow();
        });
        
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            @Override
            public void windowGainedFocus(java.awt.event.WindowEvent e) {
                requestFocus();
            }
            
            @Override
            public void windowLostFocus(java.awt.event.WindowEvent e) {
            }
        });
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
        modeToggle.setFocusable(false);
        
        historyButton = new JButton("History");
        historyButton.setFont(new Font("Arial", Font.BOLD, 10));
        historyButton.setPreferredSize(new Dimension(70, 30));
        historyButton.setBackground(new Color(173, 216, 230));
        historyButton.setToolTipText("View calculation history");
        historyButton.setFocusable(false);
        
        aboutButton = new JButton("About");
        aboutButton.setFont(new Font("Arial", Font.BOLD, 10));
        aboutButton.setPreferredSize(new Dimension(70, 30));
        aboutButton.setBackground(new Color(255, 228, 196));
        aboutButton.setToolTipText("About developers");
        aboutButton.setFocusable(false);
    }
    
    private void setupGUI() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        JPanel topPanel = new JPanel(new BorderLayout());
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        controlPanel.add(historyButton);
        controlPanel.add(aboutButton);
        controlPanel.add(modeToggle);
        
        topPanel.add(displayPanel, BorderLayout.CENTER);
        topPanel.add(controlPanel, BorderLayout.SOUTH);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(numericPanel, BorderLayout.CENTER);
        leftPanel.add(basicOpPanel, BorderLayout.EAST);
        
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
            requestFocus();
        });
        
        historyButton.addActionListener(e -> {
            HistoryWindow historyWindow = new HistoryWindow(this, engine);
            historyWindow.setVisible(true);
            SwingUtilities.invokeLater(() -> requestFocus());
        });
        
        aboutButton.addActionListener(e -> {
            DeveloperInfoWindow aboutWindow = new DeveloperInfoWindow(this);
            aboutWindow.setVisible(true);
            SwingUtilities.invokeLater(() -> requestFocus());
        });
        
        JMenuBar menuBar = new JMenuBar();
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem keyboardHelp = new JMenuItem("Keyboard Shortcuts (F12)");
        keyboardHelp.addActionListener(e -> {
            showKeyboardHelp();
            SwingUtilities.invokeLater(() -> requestFocus());
        });
        helpMenu.add(keyboardHelp);
        
        JMenuItem aboutItem = new JMenuItem("About (Ctrl+I)");
        aboutItem.addActionListener(e -> {
            DeveloperInfoWindow aboutWindow = new DeveloperInfoWindow(this);
            aboutWindow.setVisible(true);
            SwingUtilities.invokeLater(() -> requestFocus());
        });
        helpMenu.add(aboutItem);
        
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }
    
    private void showKeyboardHelp() {
        String helpText = """
            Keyboard Shortcuts:
            
            Basic Operations:
            • Numbers (0-9): Input digits
            • +, -, *, /: Basic operations
            • = or Enter: Calculate result
            • . or ,: Decimal point
            • ( ): Parentheses for expressions
            • ^ : Power operation
            • % : Percentage/Modulo
            
            Control Keys:
            • Backspace: Remove last character
            • Esc or C: Clear calculator
            • Delete: Clear calculator
            • F9 or Shift+_: Toggle sign (±)
            
            Scientific Functions:
            • Q: Square root
            • L: Logarithm (log)
            • N: Natural logarithm (ln)
            • Ctrl+S: Sine function
            
            Memory Operations:
            • Ctrl+M: Memory store
            • Ctrl+R: Memory recall
            • Ctrl+Shift+M: Memory recall
            
            Mode Controls:
            • Ctrl+D: Toggle DEG/RAD mode
            • Ctrl+E: Enter expression mode
            • F3: Toggle Scientific mode
            
            Windows:
            • Ctrl+H or F2: Show history
            • Ctrl+I or F1: About developers
            
            Expression Mode:
            When in expression mode, you can type complete 
            mathematical expressions with proper precedence.
            Example: (2+3)*sin(30)+sqrt(16)
            """;
        
        JTextArea textArea = new JTextArea(helpText);
        textArea.setEditable(false);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(450, 400));
        
        JOptionPane.showMessageDialog(
            this,
            scrollPane,
            "Keyboard Shortcuts",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        int keyCode = e.getKeyCode();
        
        if (Character.isDigit(key)) {
            if (engine.isExpressionMode()) {
                engine.addToExpression(String.valueOf(key));
            } else {
                engine.inputDigit(Character.getNumericValue(key));
            }
            displayPanel.updateDisplay();
        }
        else if (key == '+' || key == '-' || key == '*' || key == '/' || key == '^' || key == '%') {
            String operator = String.valueOf(key);
            if (key == '*') operator = "×";
            if (key == '/') operator = "÷";
            
            if (engine.isExpressionMode()) {
                engine.addToExpression(operator);
            } else {
                engine.setOperator(operator);
            }
            displayPanel.updateDisplay();
        }
        else if (key == '(' || key == ')') {
            engine.addToExpression(String.valueOf(key));
            displayPanel.updateDisplay();
        }
        else if (key == '.' || key == ',') {
            if (engine.isExpressionMode()) {
                engine.addToExpression(".");
            } else {
                engine.inputDecimal();
            }
            displayPanel.updateDisplay();
        }
        else if (key == '=' || keyCode == KeyEvent.VK_ENTER) {
            if (engine.isExpressionMode()) {
                engine.evaluateExpression();
            } else {
                engine.calculate();
            }
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (engine.isExpressionMode()) {
                engine.backspaceExpression();
            } else {
                engine.backspace();
            }
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_ESCAPE || key == 'c' || key == 'C') {
            engine.clear();
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_DELETE) {
            engine.clear();
            displayPanel.updateDisplay();
        }
        else if (key == 's' || key == 'S') {
            if (e.isControlDown()) {
                if (engine.isExpressionMode()) {
                    engine.addToExpression("sin(");
                } else {
                    engine.performScientificOperation("sin");
                }
                displayPanel.updateDisplay();
            }
        }
        else if (key == 'q' || key == 'Q') {
            if (engine.isExpressionMode()) {
                engine.addToExpression("sqrt(");
            } else {
                engine.performScientificOperation("sqrt");
            }
            displayPanel.updateDisplay();
        }
        else if (key == 'l' || key == 'L') {
            if (engine.isExpressionMode()) {
                engine.addToExpression("log(");
            } else {
                engine.performScientificOperation("log");
            }
            displayPanel.updateDisplay();
        }
        else if (key == 'n' || key == 'N') {
            if (engine.isExpressionMode()) {
                engine.addToExpression("ln(");
            } else {
                engine.performScientificOperation("ln");
            }
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_M && e.isControlDown()) {
            if (e.isShiftDown()) {
                engine.memoryRecall();
            } else {
                engine.memoryStore();
            }
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_R && e.isControlDown()) {
            engine.memoryRecall();
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_P && e.isControlDown()) {
            if (engine.isExpressionMode()) {
                engine.addToExpression("%");
            } else {
                engine.performPercentage();
            }
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_D && e.isControlDown()) {
            engine.toggleAngleMode();
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_F9 || (key == '_' && e.isShiftDown())) {
            engine.toggleSign();
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_H && e.isControlDown()) {
            HistoryWindow historyWindow = new HistoryWindow(this, engine);
            historyWindow.setVisible(true);
        }
        else if (keyCode == KeyEvent.VK_I && e.isControlDown()) {
            DeveloperInfoWindow aboutWindow = new DeveloperInfoWindow(this);
            aboutWindow.setVisible(true);
        }
        else if (keyCode == KeyEvent.VK_E && e.isControlDown()) {
            if (!engine.isExpressionMode()) {
                engine.addToExpression("");
            }
            displayPanel.updateDisplay();
        }
        else if (keyCode == KeyEvent.VK_F1) {
            DeveloperInfoWindow aboutWindow = new DeveloperInfoWindow(this);
            aboutWindow.setVisible(true);
        }
        else if (keyCode == KeyEvent.VK_F2) {
            HistoryWindow historyWindow = new HistoryWindow(this, engine);
            historyWindow.setVisible(true);
        }
        else if (keyCode == KeyEvent.VK_F3) {
            modeToggle.doClick();
        }
        else if (keyCode == KeyEvent.VK_F12) {
            showKeyboardHelp();
        }
        
        e.consume();
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        e.consume();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        e.consume();
    }
}