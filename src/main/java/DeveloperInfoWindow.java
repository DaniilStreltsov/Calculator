package main.java;

import java.awt.*;
import java.net.URL;
import javax.swing.*;

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
        setSize(650, 650); // Increased height even more
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
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // Changed from GridLayout
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        // Developer 1
        JPanel dev1Panel = createDeveloperPanel(
            "Developer 1",
            "Konstantin Shevtsov",
            "Matriculation number: 85030549",
            "Frontend Developer and GUI Designer",
            new Color(220, 240, 255),
            "dev1.jpg"
        );
        
        // Developer 2
        JPanel dev2Panel = createDeveloperPanel(
            "Developer 2", 
            "Daniil Streltsov",
            "Matriculation number: 32930509",
            "Backend Engineer & Algorithm Specialist",
            new Color(255, 240, 220),
            "dev2.jpg"
        );

        // Developer 3
        JPanel dev3Panel = createDeveloperPanel(
            "Developer 3",
            "Ruslan Sabitov",
            "Matriculation number: 99774243",
            "Testing & Algorithm Specialist",
            new Color(255, 240, 195),
            "dev3.jpg"
        );
        
        mainPanel.add(dev1Panel);
        mainPanel.add(Box.createVerticalStrut(15)); // Add spacing
        mainPanel.add(dev2Panel);
        mainPanel.add(Box.createVerticalStrut(15)); // Add spacing
        mainPanel.add(dev3Panel);
        
        return mainPanel;
    }
    
    private JPanel createDeveloperPanel(String role, String name, String matrixNo, String specialty, Color bgColor, String imageName) {
        JPanel devPanel = new JPanel();
        devPanel.setLayout(new BorderLayout(15, 0));
        devPanel.setBackground(bgColor);
        devPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createRaisedBevelBorder(),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        devPanel.setPreferredSize(new Dimension(580, 130)); // Increased height
        devPanel.setMaximumSize(new Dimension(580, 130)); // Increased height
        
        // Photo panel
        JPanel photoPanel = new JPanel();
        photoPanel.setBackground(bgColor);
        photoPanel.setPreferredSize(new Dimension(120, 100)); // Increased height
        photoPanel.setLayout(new BorderLayout());
        
        // Load image from /img directory
        JLabel photoLabel = createPhotoLabel(imageName);
        photoPanel.add(photoLabel, BorderLayout.CENTER);
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(bgColor);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Increased padding
        
        JLabel roleLabel = new JLabel(role);
        roleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        roleLabel.setForeground(new Color(70, 70, 70));
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18)); // Slightly larger
        nameLabel.setForeground(new Color(25, 25, 112));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel matrixLabel = new JLabel(matrixNo);
        matrixLabel.setFont(new Font("Arial", Font.PLAIN, 13)); // Slightly larger
        matrixLabel.setForeground(new Color(100, 100, 100));
        matrixLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel specialtyLabel = new JLabel(specialty);
        specialtyLabel.setFont(new Font("Arial", Font.ITALIC, 12)); // Slightly larger
        specialtyLabel.setForeground(new Color(120, 120, 120));
        specialtyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        infoPanel.add(roleLabel);
        infoPanel.add(Box.createVerticalStrut(5)); // Increased spacing
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5)); // Increased spacing
        infoPanel.add(matrixLabel);
        infoPanel.add(Box.createVerticalStrut(5)); // Increased spacing
        infoPanel.add(specialtyLabel);
        infoPanel.add(Box.createVerticalGlue()); // Push everything to top
        
        devPanel.add(photoPanel, BorderLayout.WEST);
        devPanel.add(infoPanel, BorderLayout.CENTER);
        
        return devPanel;
    }
    
    private JLabel createPhotoLabel(String imageName) {
        JLabel photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(300, 300)); // Increased size
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        photoLabel.setVerticalAlignment(SwingConstants.CENTER);
        
        try {
            // Try to load image from /img directory in resources
            URL imageUrl = getClass().getResource("/img/" + imageName);
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                Image img = originalIcon.getImage();
                
                // Scale to 86x86 to fit within the label with border
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImg);
                photoLabel.setIcon(scaledIcon);
            } else {
                // Fallback if image not found
                photoLabel.setOpaque(true);
                photoLabel.setBackground(new Color(200, 200, 200));
                photoLabel.setText("📷");
                photoLabel.setFont(new Font("Arial", Font.PLAIN, 30));
            }
        } catch (Exception e) {
            // Fallback if image loading fails
            photoLabel.setOpaque(true);
            photoLabel.setBackground(new Color(200, 200, 200));
            photoLabel.setText("📷");
            photoLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        }
        
        return photoLabel;
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
        
        projectInfoPanel.add(projectLabel);
        projectInfoPanel.add(Box.createVerticalStrut(3));
        projectInfoPanel.add(courseLabel);
        projectInfoPanel.add(Box.createVerticalStrut(3));
        
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