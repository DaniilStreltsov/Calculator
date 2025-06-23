package help.developer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class ProfileGui extends JFrame {
	//**
	// Variable Declaration 																	#*******D*******#
	//**
	JLabel jLabelMain;
	JLabel jLabelName, jLabelInstitute, jLabelDate, jLabelEmail;
    JTextArea jTextAreaInformation;
    
    //other variables
    int numberOfPhotos;		//number of profile photos
	// End of Variable Declaration 																#_______D_______#

	/***##Constructor##***/
	public ProfileGui() {
		initialComponent();
	}

	
	/**
	 * Method for Initializing all the GUI variables and placing them all to specific space on 
	 * the frame. It also specifies criteria of the main frame.
	 */
	private void initialComponent() {
		//**
		// Initialization 																		#*******I*******#
		//**
		jLabelMain = new JLabel();
        jLabelName = new JLabel();
        jLabelInstitute = new JLabel();
        jLabelDate = new JLabel();
        jLabelEmail = new JLabel();
     
        jTextAreaInformation = new JTextArea();
		// End of Initialization																#_______I_______#

		//**
		// Setting Bounds and Attributes of the Elements 										#*******S*******#
		//**
        jLabelMain.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/imgs/DeveloperBackGround.jpg")));
        jLabelMain.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabelMain.setBounds(0, 0, 525, 340);
        jLabelMain.setLayout(null);

        jLabelName.setFont(new java.awt.Font("Lucida Bright", 2, 15)); // NOI18N
        jLabelName.setText("Ruslan Sabitov, Daniil Streltsov, Konstantin Shevtsov");
        jLabelName.setToolTipText("Name");
        jLabelName.setBounds(20, 60, 500, 20);

        jLabelInstitute.setFont(new java.awt.Font("Lucida Bright", 2, 15)); // NOI18N
        jLabelInstitute.setText("University of Europe for Applied Sciences, Germany");
        jLabelInstitute.setToolTipText("Educational Institute");
        jLabelInstitute.setBounds(20, 90, 500, 20);

        jLabelDate.setFont(new java.awt.Font("Lucida Bright", 2, 15)); // NOI18N
        jLabelDate.setText("19.06.2025");
        jLabelDate.setToolTipText("Date of Completion");
        jLabelDate.setBounds(20, 120, 200, 20);

        jLabelEmail.setFont(new java.awt.Font("Lucida Bright", 2, 15)); // NOI18N
        jLabelEmail.setText("Email: ...@ue-germany.com");
        jLabelEmail.setBounds(20, 150, 260, 20);
       
        //text area
        jTextAreaInformation.setEditable(false);
        jTextAreaInformation.setBackground(new java.awt.Color(210, 210, 210));
        jTextAreaInformation.setColumns(20);
        jTextAreaInformation.setFont(new java.awt.Font("Times New Roman", 0, 13)); // NOI18N
        jTextAreaInformation.setRows(5);
        jTextAreaInformation.setToolTipText("My Study Life");
        jTextAreaInformation.setBorder((new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        jTextAreaInformation.setBounds(10, 225, 500, 75);
        
		// End of Setting Bounds and Attributes 												#_______S_______#

		//**
		// Adding Components 																	#*******A*******#
		//**
       
        //adding features to the main panel
        jLabelMain.add(jLabelName); jLabelMain.add(jLabelInstitute); jLabelMain.add(jLabelDate); jLabelMain.add(jLabelEmail);
        jLabelMain.add(jTextAreaInformation);
		// End of Adding Components 															#_______A_______#

		//**Setting Criterion of the Frame**//
        setIconImage(new ImageIcon(getClass().getResource("/res/imgs/DeveloperIcon.png")).getImage());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); 
        setTitle("Developer's Profile");
        setLayout(null);
        setSize(525, 340);
        setLocation(250, 200);
        setResizable(false);
        add(jLabelMain);
	}

	/********* Main Method *********/
	public static void main(String args[]) {
		/*// Set the NIMBUS look and feel //*/
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {
			// do nothing if operation is unsuccessful
		}

		/* Create and display the form */
		ProfileGui gui = new ProfileGui();
		gui.setVisible(true);
	}
}
