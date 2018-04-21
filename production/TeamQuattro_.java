import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class TeamQuattro_ implements PlugIn {
	
	
	static int wide = 0; 			//Angepasste Version des standartmäßig in ImageJ implemenierten "StackMaker" Tools.
	static int high = 0;			//Anzahl der Blöcke in X und Y Richtung

	private static int w = 2, h = 2;

	
	public static void addDialogue() { // Dialog um Eingaben vom Nutzer bzgl. der Blockgröße einzuholen

		GenericDialog dialog = new GenericDialog("Enter the number of blocks!"); // Nutzen des in ImageJ vorgefertigten "GenericDialog"
		dialog.addNumericField("Number of blocks in a row: ", wide, 0); // Hinzufügen von Eingabefeldern
		dialog.addNumericField("Number of blocks in a column: ", high, 0);
		dialog.showDialog(); // Anzeigen des Dialoges

		if (dialog.wasCanceled()) { // Prüfen ob Dialog abgebrochenb wurde
			return;
		}

		wide = (int) dialog.getNextNumber(); // Speichern der Eingaben in Variablen (muss in int umgewandelt werden)
		high = (int) dialog.getNextNumber();

	}
	
	public void run(String arg) {
		ImagePlus iplus = WindowManager.getCurrentImage();
		functions.failcheck(); // Test ob ein Bild vorhanden ist
		addDialogue(); // Dialog um Eingaben vom Nutzer bzgl. der Blockgröße einzuholen
		ImageStack stack = functions.createStack(iplus.getProcessor(), wide, high); // Anzeige: new ImagePlus("Blocks", stack).show(); //Funktion die das Bild zerteilt und die Blöcke auf einen Stack legt 

	}





	
	
}
