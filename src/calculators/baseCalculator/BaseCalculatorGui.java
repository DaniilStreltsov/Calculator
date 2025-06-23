package calculators.baseCalculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class BaseCalculatorGui extends JLabel {

	// Constants for styling
	private static final Color BACKGROUND_COLOR = new Color(45, 45, 48);
	private static final Color DISPLAY_COLOR = new Color(32, 32, 35);
	private static final Color BUTTON_COLOR = new Color(65, 65, 68);
	private static final Color NUMBER_BUTTON_COLOR = new Color(80, 80, 83);
	private static final Color OPERATOR_COLOR = new Color(255, 159, 10);
	private static final Color BASE_BUTTON_COLOR = new Color(100, 100, 103);
	private static final Color BASE_ACTIVE_COLOR = new Color(255, 159, 10);
	private static final Color CLEAR_BUTTON_COLOR = new Color(220, 53, 69);
	private static final Color TEXT_COLOR = Color.WHITE;
	private static final Color DISPLAY_TEXT_COLOR = Color.WHITE;

	private static final Font DISPLAY_FONT = new Font("Segoe UI", Font.PLAIN, 24);
	private static final Font BUTTON_FONT = new Font("Segoe UI", Font.PLAIN, 18);
	private static final Font BASE_FONT = new Font("Segoe UI", Font.PLAIN, 14);

	// Original component names
	JToggleButton[] jTButtonBase;
	JButton[] jButtonNumerical;
	JButton[] jButtonAlphaNumerical;
	JButton[] jButtonOperator;
	JButton[] jButtonMemory;
	JButton[] jButtonClear;
	JButton jButtonPoint;
	JLabel[] jLabelDisplay;

	public BaseCalculatorGui() {
		initialComponent();
	}

	private void initialComponent() {
		// Initialization
		jTButtonBase = new JToggleButton[4];
		jButtonNumerical = new JButton[10];
		jButtonPoint = new JButton();
		jButtonAlphaNumerical = new JButton[6];
		jButtonOperator = new JButton[5];
		jButtonMemory = new JButton[2];
		jButtonClear = new JButton[3];
		jLabelDisplay = new JLabel[2];

		// Setting Bounds and Attributes of the Elements

		// Base toggle buttons
		String[] baseLabels = {"BIN", "OCT", "DEC", "HEX"};
		ButtonGroup baseGroup = new ButtonGroup();

		for (int i = 0; i < 4; i++) {
			jTButtonBase[i] = new JToggleButton(baseLabels[i]);
			jTButtonBase[i].setSize(80, 35);
			jTButtonBase[i].setLocation(20 + i * 85, 130);
			jTButtonBase[i].setBackground(BASE_BUTTON_COLOR);
			jTButtonBase[i].setForeground(TEXT_COLOR);
			jTButtonBase[i].setFont(BASE_FONT);
			jTButtonBase[i].setBorder(null);
			jTButtonBase[i].setFocusPainted(false);
			jTButtonBase[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			baseGroup.add(jTButtonBase[i]);

			// Add selection styling
			final int index = i;
			jTButtonBase[i].addActionListener(e -> {
				updateBaseButtonStyles();
				updateButtonStates();
			});
		}
		jTButtonBase[2].setSelected(true); // Default to decimal

		// Numeric Buttons
		int startX = 20, startY = 240, buttonSize = 60, gap = 10;

		for (int i = 0; i < 10; i++) {
			jButtonNumerical[i] = new JButton("" + i);
			jButtonNumerical[i].setSize(buttonSize, buttonSize);
			jButtonNumerical[i].setFont(BUTTON_FONT);
			jButtonNumerical[i].setBackground(NUMBER_BUTTON_COLOR);
			jButtonNumerical[i].setForeground(TEXT_COLOR);
			jButtonNumerical[i].setBorder(null);
			jButtonNumerical[i].setFocusPainted(false);
			jButtonNumerical[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			addHoverEffect(jButtonNumerical[i], NUMBER_BUTTON_COLOR);
		}

		// Calculator-style layout for numbers
		jButtonNumerical[7].setLocation(startX, startY);
		jButtonNumerical[8].setLocation(startX + (buttonSize + gap), startY);
		jButtonNumerical[9].setLocation(startX + 2 * (buttonSize + gap), startY);

		jButtonNumerical[4].setLocation(startX, startY + (buttonSize + gap));
		jButtonNumerical[5].setLocation(startX + (buttonSize + gap), startY + (buttonSize + gap));
		jButtonNumerical[6].setLocation(startX + 2 * (buttonSize + gap), startY + (buttonSize + gap));

		jButtonNumerical[1].setLocation(startX, startY + 2 * (buttonSize + gap));
		jButtonNumerical[2].setLocation(startX + (buttonSize + gap), startY + 2 * (buttonSize + gap));
		jButtonNumerical[3].setLocation(startX + 2 * (buttonSize + gap), startY + 2 * (buttonSize + gap));

		jButtonNumerical[0].setLocation(startX, startY + 3 * (buttonSize + gap));
		jButtonNumerical[0].setSize((buttonSize * 2) + gap, buttonSize);

		// Point
		jButtonPoint.setBackground(NUMBER_BUTTON_COLOR);
		jButtonPoint.setForeground(TEXT_COLOR);
		jButtonPoint.setText(".");
		jButtonPoint.setBounds(startX + 2 * (buttonSize + gap), startY + 3 * (buttonSize + gap), buttonSize, buttonSize);
		jButtonPoint.setFont(BUTTON_FONT);
		jButtonPoint.setBorder(null);
		jButtonPoint.setFocusPainted(false);
		jButtonPoint.setCursor(new Cursor(Cursor.HAND_CURSOR));
		addHoverEffect(jButtonPoint, NUMBER_BUTTON_COLOR);

		// Alpha Numerical Buttons (A-F)
		String[] hexLabels = {"A", "B", "C", "D", "E", "F"};
		int hexX = startX + 4 * (buttonSize + gap);

		for (int i = 0; i < 6; i++) {
			jButtonAlphaNumerical[i] = new JButton(hexLabels[i]);
			jButtonAlphaNumerical[i].setSize(buttonSize, buttonSize);
			jButtonAlphaNumerical[i].setFont(BUTTON_FONT);
			jButtonAlphaNumerical[i].setBorder(null);
			jButtonAlphaNumerical[i].setBackground(NUMBER_BUTTON_COLOR);
			jButtonAlphaNumerical[i].setForeground(TEXT_COLOR);
			jButtonAlphaNumerical[i].setFocusPainted(false);
			jButtonAlphaNumerical[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			addHoverEffect(jButtonAlphaNumerical[i], NUMBER_BUTTON_COLOR);
		}

		jButtonAlphaNumerical[0].setLocation(hexX, startY); // A
		jButtonAlphaNumerical[1].setLocation(hexX, startY + (buttonSize + gap)); // B
		jButtonAlphaNumerical[2].setLocation(hexX + (buttonSize + gap), startY); // C
		jButtonAlphaNumerical[3].setLocation(hexX + (buttonSize + gap), startY + (buttonSize + gap)); // D
		jButtonAlphaNumerical[4].setLocation(hexX + (buttonSize + gap), startY + 2 * (buttonSize + gap)); // E
		jButtonAlphaNumerical[5].setLocation(hexX + (buttonSize + gap), startY + 3 * (buttonSize + gap)); // F

		// Operator Buttons
		String[] operatorLabels = {"+", "−", "×", "÷", "="};

		for (int i = 0; i < 5; i++) {
			jButtonOperator[i] = new JButton(operatorLabels[i]);
			jButtonOperator[i].setSize(buttonSize, buttonSize);
			jButtonOperator[i].setFont(new Font("Segoe UI", Font.PLAIN, 20));
			jButtonOperator[i].setBackground(OPERATOR_COLOR);
			jButtonOperator[i].setForeground(Color.BLACK);
			jButtonOperator[i].setBorder(null);
			jButtonOperator[i].setFocusPainted(false);
			jButtonOperator[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			addHoverEffect(jButtonOperator[i], OPERATOR_COLOR);
		}

		jButtonOperator[0].setLocation(startX + 3 * (buttonSize + gap), startY + 3 * (buttonSize + gap)); // +
		jButtonOperator[1].setLocation(startX + 3 * (buttonSize + gap), startY + 2 * (buttonSize + gap)); // −
		jButtonOperator[2].setLocation(startX + 3 * (buttonSize + gap), startY + (buttonSize + gap)); // ×
		jButtonOperator[3].setLocation(startX + 3 * (buttonSize + gap), startY); // ÷
		jButtonOperator[4].setBounds(startX + 4 * (buttonSize + gap), startY + 2 * (buttonSize + gap), buttonSize, (buttonSize * 2) + gap); // =

		// Memory Buttons
		String[] memoryLabels = {"MR", "MS"};
		int memoryY = 180;

		for (int i = 0; i < 2; i++) {
			jButtonMemory[i] = new JButton(memoryLabels[i]);
			jButtonMemory[i].setSize(60, 45);
			jButtonMemory[i].setLocation(20 + i * 70, memoryY);
			jButtonMemory[i].setFont(new Font("Segoe UI", Font.PLAIN, 14));
			jButtonMemory[i].setBorder(null);
			jButtonMemory[i].setBackground(BUTTON_COLOR);
			jButtonMemory[i].setForeground(TEXT_COLOR);
			jButtonMemory[i].setFocusPainted(false);
			jButtonMemory[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			addHoverEffect(jButtonMemory[i], BUTTON_COLOR);
		}

		// Clear Buttons
		String[] clearLabels = {"C", "AC", "⌫"};

		for (int i = 0; i < 3; i++) {
			jButtonClear[i] = new JButton(clearLabels[i]);
			jButtonClear[i].setSize(60, 45);
			jButtonClear[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
			jButtonClear[i].setBorder(null);
			jButtonClear[i].setBackground(CLEAR_BUTTON_COLOR);
			jButtonClear[i].setForeground(TEXT_COLOR);
			jButtonClear[i].setFocusPainted(false);
			jButtonClear[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
			addHoverEffect(jButtonClear[i], CLEAR_BUTTON_COLOR);
		}

		jButtonClear[0].setLocation(230, memoryY); // C
		jButtonClear[1].setLocation(300, memoryY); // AC
		jButtonClear[2].setLocation(160, memoryY); // Backspace

		// Create displays
		jLabelDisplay[0] = new JLabel("", JLabel.RIGHT);
		jLabelDisplay[1] = new JLabel("0", JLabel.RIGHT);

		for (int i = 0; i < 2; i++) {
			jLabelDisplay[i].setBackground(DISPLAY_COLOR);
			jLabelDisplay[i].setForeground(DISPLAY_TEXT_COLOR);
			jLabelDisplay[i].setOpaque(true);
			jLabelDisplay[i].setBorder(new EmptyBorder(10, 15, 10, 15));
		}

		jLabelDisplay[0].setLocation(20, 20);
		jLabelDisplay[0].setSize(340, 35);
		jLabelDisplay[0].setFont(new Font("Segoe UI", Font.PLAIN, 16));
		jLabelDisplay[0].setForeground(new Color(170, 170, 170));

		jLabelDisplay[1].setLocation(20, 55);
		jLabelDisplay[1].setSize(340, 60);
		jLabelDisplay[1].setFont(DISPLAY_FONT);

		// Adding Components
		for (int i = 0; i < 4; i++) {
			add(jTButtonBase[i]);
		}

		for (int i = 0; i < 10; i++) {
			add(jButtonNumerical[i]);
		}
		for (int i = 0; i < 6; i++) {
			add(jButtonAlphaNumerical[i]);
		}
		add(jButtonPoint);
		for (int i = 0; i < 5; i++) {
			add(jButtonOperator[i]);
		}
		for (int i = 0; i < 2; i++) {
			add(jButtonMemory[i]);
		}
		for (int i = 0; i < 3; i++) {
			add(jButtonClear[i]);
		}

		for (int i = 0; i < 2; i++) {
			add(jLabelDisplay[i]);
		}

		// Setting Criterion of the Frame
		setBounds(0, 0, 380, 500);
		setBackground(BACKGROUND_COLOR);
		setOpaque(true);

		// Initial state update
		updateBaseButtonStyles();
		updateButtonStates();
	}

	private void addHoverEffect(JButton button, Color originalColor) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				if (button.isEnabled()) {
					if (originalColor == OPERATOR_COLOR) {
						button.setBackground(new Color(255, 179, 64));
					} else {
						button.setBackground(originalColor.brighter());
					}
				}
			}

			@Override
			public void mouseExited(java.awt.event.MouseEvent evt) {
				if (button.isEnabled()) {
					button.setBackground(originalColor);
				}
			}
		});
	}

	private void updateBaseButtonStyles() {
		for (int i = 0; i < jTButtonBase.length; i++) {
			if (jTButtonBase[i].isSelected()) {
				jTButtonBase[i].setBackground(BASE_ACTIVE_COLOR);
				jTButtonBase[i].setForeground(Color.BLACK);
			} else {
				jTButtonBase[i].setBackground(BASE_BUTTON_COLOR);
				jTButtonBase[i].setForeground(TEXT_COLOR);
			}
		}
	}

	private void updateButtonStates() {
		boolean isBinary = jTButtonBase[0].isSelected();
		boolean isOctal = jTButtonBase[1].isSelected();
		boolean isDecimal = jTButtonBase[2].isSelected();
		boolean isHex = jTButtonBase[3].isSelected();

		// Update number buttons based on base
		for (int i = 0; i < 10; i++) {
			if (isBinary) {
				jButtonNumerical[i].setEnabled(i < 2);
			} else if (isOctal) {
				jButtonNumerical[i].setEnabled(i < 8);
			} else {
				jButtonNumerical[i].setEnabled(true);
			}

			// Visual feedback for disabled buttons
			if (!jButtonNumerical[i].isEnabled()) {
				jButtonNumerical[i].setBackground(new Color(40, 40, 43));
				jButtonNumerical[i].setForeground(new Color(100, 100, 100));
			} else {
				jButtonNumerical[i].setBackground(NUMBER_BUTTON_COLOR);
				jButtonNumerical[i].setForeground(TEXT_COLOR);
			}
		}

		// Update hex buttons
		for (JButton btn : jButtonAlphaNumerical) {
			btn.setEnabled(isHex);
			if (!btn.isEnabled()) {
				btn.setBackground(new Color(40, 40, 43));
				btn.setForeground(new Color(100, 100, 100));
			} else {
				btn.setBackground(NUMBER_BUTTON_COLOR);
				btn.setForeground(TEXT_COLOR);
			}
		}

		// Point button only enabled for decimal
		jButtonPoint.setEnabled(isDecimal);
		if (!jButtonPoint.isEnabled()) {
			jButtonPoint.setBackground(new Color(40, 40, 43));
			jButtonPoint.setForeground(new Color(100, 100, 100));
		} else {
			jButtonPoint.setBackground(NUMBER_BUTTON_COLOR);
			jButtonPoint.setForeground(TEXT_COLOR);
		}
	}

	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			// do nothing if operation is unsuccessful
		}

		SwingUtilities.invokeLater(() -> {
			BaseCalculatorGui gui = new BaseCalculatorGui();

			JFrame jFrame = new JFrame("Base Calculator");
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setSize(395, 530);
			jFrame.setLayout(null);
			jFrame.add(gui);
			jFrame.setResizable(false);
			jFrame.setLocationRelativeTo(null); // центрирование окна
			jFrame.setVisible(true);
		});
	}
}