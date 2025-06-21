package help.history;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Scanner;
import java.io.*;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class History {
    // GUI Declaration
    private HistoryGui gui;

    //**
    // Variable Declaration 																	#*******D*******#
    //**
    private JTextArea jTextAreaNotification;
    private JButton jButtonOK;

    //operational variable
    private String info;
    // End of Variable Declaration 																#_______D_______#

    /***##Constructor##***/
    public History() {

        initialComponent();
    }


    /**
     * Method for Initializing all the GUI variables and placing them all to specific space on
     * the frame. It also specifies criteria of the main frame.
     */
    @SuppressWarnings("serial")
    private void initialComponent() {
        // GUI Initialization
        gui = new HistoryGui();
        gui.setVisible(true);

        String path = "./src/res/txts/calculator_history.txt";

        //**
        // Assignation 																			#*******A*******#
        //**
        jTextAreaNotification = gui.jTextAreaNotification;
        jButtonOK = gui.jButtonOK;

        try {
            File file = new File(path);
            String inf = "";
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                inf += sc.nextLine() + "\n";
            }
            info = inf;
        } catch (Exception e) {
            info = "EMPTY";
        }
        // End of Assignation																	#_______A_______#

        //**
        // Adding Action Events & Other Attributes												#*******AA*******#
        //**
        jTextAreaNotification.setText(info);
        jTextAreaNotification.setCaretPosition(0);

        jButtonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        jButtonOK.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).
                put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "ENTER_pressed");
        jButtonOK.getActionMap().put("ENTER_pressed", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        // End of Adding Action Events & Other Attributes										#_______AA_______#
    }

    //**
    // Action Events 																			#*******AE*******#
    //**
    private void jButtonOKActionPerformed(ActionEvent evt){
        gui.dispose();
    }
    // End of Action Events 																	#_______AE_______#

    //**
    // Auxiliary Methods 																		#*******AM*******#
    //**

    // End of Auxiliary Methods 																#_______AM_______#

    //**
    // Unimplemented Methods 																	#*******UM*******#
    //**

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
        new help.history.History();
    }
}
