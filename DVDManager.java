//import java.util.*;
import javax.swing.JOptionPane;

/**
 * 	Program to display and modify a simple DVD collection
 */

public class DVDManager {

	public static void main(String[] args) {
		DVDUserInterface dvdInterface;
		DVDCollection dvdCollection = new DVDCollection();

		String filename = JOptionPane.showInputDialog(
				null, 
				"Please enter a file name", 
				"Choose a file name", 
				JOptionPane.QUESTION_MESSAGE);
		
		// REMOVE THIS LINE AFTER TESTING !!!
//		String filename = "dvddata.txt"; // FOR TESTING
		// REMOVE THIS LINE AFTER TESTING !!!
		
		if (filename == null) {
			return; // canceled
		}
		
		dvdCollection.loadData(filename); // open file, load into DVDCollection

		dvdInterface = new DVDGUI(dvdCollection);
		dvdInterface.processCommands();
	}
}
