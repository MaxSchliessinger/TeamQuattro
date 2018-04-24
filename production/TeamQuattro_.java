import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;


public class TeamQuattro_ implements PlugIn {
	
	static int wide;
	static int high;
	
	
	// Dialog um Eingaben vom Nutzer bzgl. der Blockgröße einzuholen
	public static void addDialogue() { 
				
		// Nutzen des in ImageJ vorgefertigten "GenericDialog"
		GenericDialog dialog = new GenericDialog("Enter the number of blocks!"); 
		
		// Hinzufügen von Eingabefeldern
		dialog.addNumericField("Number of blocks in a row: ", 2, 0); 
		dialog.addNumericField("Number of blocks in a column: ", 2, 0);
		
		// Anzeigen des Dialoges
		dialog.showDialog();

		// Prüfen ob Dialog abgebrochenb wurde
		if (dialog.wasCanceled()) { 
			return;
		}

		// Speichern der Eingaben in Variablen (muss in int umgewandelt werden)
		wide = (int) dialog.getNextNumber(); 
		high = (int) dialog.getNextNumber();

	}
	
	
	
	public void run(String arg) {
		ImagePlus iplus = WindowManager.getCurrentImage();
		
		// Test ob ein Bild vorhanden ist
		functions.failcheck(); 
		
		// Dialog um Eingaben vom Nutzer bzgl. der Blockgröße einzuholen
		addDialogue(); 
		
		// Anzeige: new ImagePlus("Blocks", stack).show(); 
		// Funktion die das Bild zerteilt und die Blöcke auf einen Stack legt
		ImageStack stack = functions.createStack(iplus.getProcessor(), wide, high);  

		
		
		/*
		 * RCT für ersten Block "A7_1" encoden , YUV Werte für jeden pixel speichern, beste RCT ermitteln
		 * 
		 * Array mit allen YUV Werten
		 * 
		 */
				
		int yvu[][][] = functions.iprun(stack.getProcessor(2), "enc", "A7_1");
		

		// Testweise ausgabe der einzelnen YVU Werte
		for(int i = 0 ; i < yvu.length ; i++){
			for(int y = 0 ; y < yvu[0].length ; y++){
				System.out.println("Pixel " + y + " | " + i + "   y:" + yvu[y][i][0]+ "   v:" + yvu[y][i][1]+ "   u:" + yvu[y][i][2]);
			}
		}
		
		/*
		 * Folgenden Block mit RCT des ersten encoden, yuv Werte für jeden pixel speichern, beste RCT ermitteln
		 */
		
		
		
		
	}


	
	
}
