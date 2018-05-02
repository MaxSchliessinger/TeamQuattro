import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;


public class RCTmain_ implements PlugIn {
	
	static int wide;
	static int high;
	static String [] rcts = {"A1_1" , "A1_2" , "A1_3", "A1_4", "A1_5" , "A1_6", "A1_7" , "A1_8", "A1_9" , "A1_10", "A1_11", "A1_12", 
			"A2_1", "A2_2", "A2_3", "A2_4", "A2_5", "A2_6", "A2_7", "A2_8", "A2_9", "A2_10", "A2_11", "A2_12",
		    "A3_1", "A3_2", "A3_3", "A3_4", "A3_5", "A3_6", "A3_7", "A3_8", "A3_9", "A3_10", "A3_11", "A3_12",
		    "A4_1", "A4_2", "A4_3", "A4_4", "A4_5", "A4_6", "A4_7", "A4_8", "A4_9", "A4_10", "A4_11", "A4_12",
		    "A5_1", "A5_2", "A5_3", "A5_4", "A5_5", "A5_6", "A5_7", "A5_8", "A5_9", "A5_10", "A5_11", "A5_12",
		    "A6_1", "A6_2", "A6_3", "A6_4", "A6_5", "A6_6", "A6_7", "A6_8", "A6_9", "A6_10", "A6_11", "A6_12",
		    "A7_1", "A7_2", "A7_3", "A7_4", "A7_5", "A7_6", "A7_7", "A7_8", "A7_9", "A7_10", "A7_11", "A7_12",
		    "A8_1", "A8_2", "A8_3", "A8_4", "A8_5", "A8_6", "A8_7", "A8_8", "A8_9", "A8_10", "A8_11", "A8_12",
		    "A9_1", "A9_2", "A9_3", "A9_4", "A9_5", "A9_6", "A9_7", "A9_8", "A9_9", "A9_10", "A9_11", "A9_12",
		    "B1_1", "B1_2", "B2_1", "B2_3", "B3_2", "B3_3", "B4_1", "B5_2", "B6_3", "PEI09"};
	
	
	// Dialog um Eingaben vom Nutzer bzgl. der Blockgr��e einzuholen
	public static void addDialogue() { 
				
		// Nutzen des in ImageJ vorgefertigten "GenericDialog"
		GenericDialog dialog = new GenericDialog("Enter the number of blocks!"); 
		
		// Hinzuf�gen von Eingabefeldern
		dialog.addNumericField("Number of blocks in a row: ", 2, 0); 
		dialog.addNumericField("Number of blocks in a column: ", 2, 0);
		
		// Anzeigen des Dialoges
		dialog.showDialog();

		// Pr�fen ob Dialog abgebrochenb wurde
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
		
		// Dialog um Eingaben vom Nutzer bzgl. der Blockgr��e einzuholen
		addDialogue(); 
		
		// Anzeige: new ImagePlus("Blocks", stack).show(); 
		// Funktion die das Bild zerteilt und die Bl�cke auf einen Stack legt
		ImageStack stack = functions.createStack(iplus.getProcessor(), wide, high);  

		
		
		/*
		 * RCT f�r ersten Block "A7_1" encoden , YUV Werte f�r jeden pixel speichern, beste RCT ermitteln
		 * 
		 * Array mit allen YUV Werten
		 * 
		 */
		
		
		//letzte for schleife mit wide*high sodass jeder block abgearbeitet wird
		
		for(int q = 0 ; q < rcts.length ; q++ ){
			
		
		System.out.println("Fange an mit RCT " + rcts[q] );
				
		int yvu[][][] = functions.iprun(stack.getProcessor(2), "enc", rcts[q] );
		

		// Testweise ausgabe der einzelnen YVU Werte
		for(int i = 0 ; i < yvu.length ; i++){
			for(int y = 0 ; y < yvu[0].length ; y++){
				System.out.println("Pixel " + y + " | " + i + "   y:" + yvu[y][i][0]+ "   v:" + yvu[y][i][1]+ "   u:" + yvu[y][i][2]);
			}
		}
		}
		
		/*
		 * Folgenden Block mit RCT des ersten encoden, yuv Werte f�r jeden pixel speichern, beste RCT ermitteln
		 */
		
		
		
		
	}


	
	
}
	

