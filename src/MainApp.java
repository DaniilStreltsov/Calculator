import calculators.scientificCalculator.ScientificCalculator;

public class MainApp {
	public static void main(String args[]) {
		
		/**///using NIMBUS 
		try {
			//javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
			javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");			
			//javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			//javax.swing.UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
			
		} catch (Exception ex) {
			//do nothing
		}
		
		new ScientificCalculator();
	}
}
