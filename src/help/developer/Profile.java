package help.developer;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import utils.fileIO.FileIO;

public class Profile implements Runnable{
	// GUI Declaration
	private ProfileGui gui;
	
	//**
	// Variable Declaration 																	#*******D*******#
	//**
	private JLabel jLabelDate;
    private JTextArea jTextAreaInformation;
    
    //other variables
    private String date;			//Change it accordingly
    private int position;			//position determiner 
    private boolean isStanding;		//picture show is standing or not
    private String developerInfo;
    
    //thread
    private Thread thread;			//main thread
	// End of Variable Declaration 																#_______D_______#

	/***##Constructor##***/
	public Profile() {
		date="19-Jun-2025";
    	isStanding=false;
    	position=0;
    	
		initialComponent();
		
		//*Thread*//
        thread = new Thread(this);
        thread.start();
	}
	
	public Profile(String date) {
    	this.date=date;
    	isStanding=false;
    	position=0;

    	initialComponent();
    	
    	//*Thread*//
        thread = new Thread(this);
        thread.start();
    }

	
	/**
	 * Method for Initializing all the GUI variables and placing them all to specific space on 
	 * the frame. It also specifies criteria of the main frame.
	 */
	private void initialComponent() {
		// GUI Initialization
		gui = new ProfileGui();
		gui.setVisible(true);
		
		//**
		// Assignation 																			#*******A*******#
		//**
        jLabelDate = gui.jLabelDate;
        jTextAreaInformation = gui.jTextAreaInformation;
        
        try {
        	developerInfo = FileIO.readWholeFile(getClass().getResourceAsStream("/res/txts/Developer.txt"));
		} catch (Exception e) {
			developerInfo = "EMPTY";
		}
		// End of Assignation																	#_______A_______#

		//**
		// Adding Action Events & Other Attributes												#*******AA*******#
		//**
        jLabelDate.setText("Date: " + date);
        jTextAreaInformation.setText(developerInfo);

		// End of Adding Action Events & Other Attributes										#_______AA_______#
	}

	//**
	// Action Events 																			#*******AE*******#
	//**
	private void jPanelPhotoActionPerformed(java.awt.event.ActionEvent evt) {  
		if(!isStanding){
			isStanding=true;
		}else{
			isStanding=false;
		}
    }
	// End of Action Events 																	#_______AE_______#

	//**
	// Auxiliary Methods 																		#*******AM*******#
	//**
	
	// End of Auxiliary Methods 																#_______AM_______#
	
	//**
	// Unimplemented Methods 																	#*******UM*******#
	//**
	@SuppressWarnings("static-access")
	public void run() {

		while(true){

		}
	}
	// End of Unimplemented Methods 															#_______UM_______#
	
	
	/********* Main Method *********/
	public static void main(String args[]) {
		/*// Set the NIMBUS look and feel //*/
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception ex) {
			// do nothing if operation is unsuccessful
		}

		/* Create */
		new Profile();
	}
}
