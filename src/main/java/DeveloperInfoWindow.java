package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Window to display developer information
 */
public class DeveloperInfoWindow extends JDialog {
    
    public DeveloperInfoWindow(JFrame parent) {
        super(parent, "About Developers", true);
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }
    
    private void initializeComponents() {
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        
        // Main content panel
        JPanel mainPanel = createMainContentPanel();
        
        // Footer panel
        JPanel footerPanel = createFooterPanel();
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 25, 112));
        headerPanel.setPreferredSize(new Dimension(500, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        
        JLabel titleLabel = new JLabel("Scientific Calculator Development Team");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        headerPanel.add(titleLabel);
        return headerPanel;
    }
    
    private JPanel createMainContentPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Developer 1
        JPanel dev1Panel = createDeveloperPanel(
            "Developer 1",
            "John Doe",
            "Matrix No: 2023001234",
            "Lead Developer & UI Designer",
            new Color(220, 240, 255)
        );
        
        // Developer 2
        JPanel dev2Panel = createDeveloperPanel(
            "Developer 2", 
            "Jane Smith",
            "Matrix No: 2023005678",
            "Backend Engineer & Algorithm Specialist",
            new Color(255, 240, 220)
        );

        JPanel dev3Panel = createDeveloperPanel(
                "Developer 2",
                "Jane Smith",
                "Matrix No: 2023005678",
                "Backend Engineer & Algorithm Specialist",
                new Color(255, 240, 195)
        );
        
        mainPanel.add(dev1Panel);
        mainPanel.add(dev2Panel);
        mainPanel.add(dev3Panel);
        
        return mainPanel;
    }
    
    private JPanel createDeveloperPanel(String role, String name, String matrixNo, String specialty, Color bgColor) {
        JPanel devPanel = new JPanel();
        devPanel.setLayout(new BorderLayout());
        devPanel.setBackground(bgColor);
        devPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Photo placeholder
        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(bgColor);
        photoPanel.setPreferredSize(new Dimension(80, 80));
        
        // Create a placeholder image
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(70, 70));
        photoLabel.setOpaque(true);
        photoLabel.setBackground(new Color(200, 200, 200));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setText("📷");
        photoLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        
        photoPanel.add(photoLabel);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(bgColor);
        
        JLabel roleLabel = new JLabel(role);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        roleLabel.setForeground(new Color(70, 70, 70));
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(new Color(25, 25, 112));
        
        JLabel matrixLabel = new JLabel(matrixNo);
        matrixLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        matrixLabel.setForeground(new Color(100, 100, 100));
        
        JLabel specialtyLabel = new JLabel(specialty);
        specialtyLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        specialtyLabel.setForeground(new Color(120, 120, 120));
        
        infoPanel.add(roleLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(matrixLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(specialtyLabel);
        
        devPanel.add(photoPanel, BorderLayout.WEST);
        devPanel.add(infoPanel, BorderLayout.CENTER);
        
        return devPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(245, 245, 245));
        footerPanel.setLayout(new BorderLayout());
        footerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Project info
        JPanel projectInfoPanel = new JPanel();
        projectInfoPanel.setLayout(new BoxLayout(projectInfoPanel, BoxLayout.Y_AXIS));
        projectInfoPanel.setBackground(new Color(245, 245, 245));
        
        JLabel projectLabel = new JLabel("Project: Scientific Calculator Application");
        projectLabel.setFont(new Font("Arial", Font.BOLD, 12));
        projectLabel.setForeground(new Color(50, 50, 50));
        
        JLabel courseLabel = new JLabel("Course: Backend Programming - 2nd Semester");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        courseLabel.setForeground(new Color(80, 80, 80));
        
        JLabel versionLabel = new JLabel("Version: 1.0.0 | " + java.time.LocalDate.now());
        versionLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        versionLabel.setForeground(new Color(120, 120, 120));
        
        projectInfoPanel.add(projectLabel);
        projectInfoPanel.add(Box.createVerticalStrut(3));
        projectInfoPanel.add(courseLabel);
        projectInfoPanel.add(Box.createVerticalStrut(3));
        projectInfoPanel.add(versionLabel);
        
        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setBackground(new Color(144, 238, 144));
        closeButton.setPreferredSize(new Dimension(80, 30));
        
        footerPanel.add(projectInfoPanel, BorderLayout.CENTER);
        footerPanel.add(closeButton, BorderLayout.EAST);
        
        // Event handler for close button
        closeButton.addActionListener(e -> dispose());
        
        return footerPanel;
    }
    
    private void setupEventHandlers() {
        // Additional event handlers can be added here if needed
    }
}