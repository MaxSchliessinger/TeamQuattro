import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class TeamQuattro_ implements PlugIn {

	static int wide;
	static int high;
	static String mode;

	

	static String[] rcts = { "A1_1", "A1_2", "A1_3", "A1_4", "A1_5", "A1_6", "A1_7", "A1_8", "A1_9", "A1_10", "A1_11",
			"A1_12", "A2_1", "A2_2", "A2_3", "A2_4", "A2_5", "A2_6", "A2_7", "A2_8", "A2_9", "A2_10", "A2_11", "A2_12",
			"A3_1", "A3_2", "A3_3", "A3_4", "A3_5", "A3_6", "A3_7", "A3_8", "A3_9", "A3_10", "A3_11", "A3_12", "A4_1",
			"A4_2", "A4_3", "A4_4", "A4_5", "A4_6", "A4_7", "A4_8", "A4_9", "A4_10", "A4_11", "A4_12", "A5_1", "A5_2",
			"A5_3", "A5_4", "A5_5", "A5_6", "A5_7", "A5_8", "A5_9", "A5_10", "A5_11", "A5_12", "A6_1", "A6_2", "A6_3",
			"A6_4", "A6_5", "A6_6", "A6_7", "A6_8", "A6_9", "A6_10", "A6_11", "A6_12", "A7_1", "A7_2", "A7_3", "A7_4",
			"A7_5", "A7_6", "A7_7", "A7_8", "A7_9", "A7_10", "A7_11", "A7_12", "A8_1", "A8_2", "A8_3", "A8_4", "A8_5",
			"A8_6", "A8_7", "A8_8", "A8_9", "A8_10", "A8_11", "A8_12", "A9_1", "A9_2", "A9_3", "A9_4", "A9_5", "A9_6",
			"A9_7", "A9_8", "A9_9", "A9_10", "A9_11", "A9_12", "B1_1", "B1_2", "B2_1", "B2_3", "B3_2", "B3_3", "B4_1",
			"B5_2", "B6_3", "PEI09" };

	// Dialog um Eingaben vom Nutzer bzgl. der Blockgröße einzuholen
	public static void addDialogue() {

		// Nutzen des in ImageJ vorgefertigten "GenericDialog"
		GenericDialog dialog = new GenericDialog("Enter the number of blocks!");

		// HinzufÃ¼gen von Eingabefeldern
		dialog.addNumericField("Number of blocks in a row: ", 2, 0);
		dialog.addNumericField("Number of blocks in a column: ", 2, 0);

		// Modes
		String[] modes = { "enc", "dec" };
		dialog.addChoice("Mode:", modes, modes[0]);
		
		// YUV TO RGB  Einzelbilder

		

		// Anzeigen des Dialoges
		dialog.showDialog();

		// Prüfen ob Dialog abgebrochen wurde
		if (dialog.wasCanceled()) {
			return ;
		}

		// Speichern der Eingaben in Variablen (muss in int umgewandelt werden)
		wide = (int) dialog.getNextNumber();
		high = (int) dialog.getNextNumber();
		mode = dialog.getNextChoice();
		
	}
	
	public void encode(String[] sigen , ImageStack stack, int w, int h){
		functions.debug = true;
		
		ImagePlus[] imps = null;
		ImageProcessor[] ips = null;
		ImageProcessor[] ipstemp = null;
		
		imps = new ImagePlus[3];
		ips = new ImageProcessor[3];
		ipstemp = new ImageProcessor[3];
		
		
		ips[0] = new FloatProcessor(w, h);
		ips[1] = new FloatProcessor(w, h);
		ips[2] = new FloatProcessor(w, h);
		
		ipstemp[0] = new FloatProcessor(w/wide, h/high);
		ipstemp[1] = new FloatProcessor(w/wide, h/high);
		ipstemp[2] = new FloatProcessor(w/wide, h/high);
		
		imps[0] = new ImagePlus("1", ips[0]);
		imps[1] = new ImagePlus("2", ips[1]);
		imps[2] = new ImagePlus("3", ips[2]);
				 
		 
		
		for (int b = 0 ; b < high ; b++){
			for (int m = 0 ; m < wide ; m++){
								
					if(m==0 && b==0){
						//Erster Durchlauf mit fester RCT
						
						ipstemp[0] = new FloatProcessor(w/wide, h/high);
						ipstemp[1] = new FloatProcessor(w/wide, h/high);
						ipstemp[2] = new FloatProcessor(w/wide, h/high);
						
						
						
						ipstemp = functions.iprun(stack.getProcessor(1), mode , "A7_1",true);
						
						ips[0].insert(ipstemp[0], 0, 0);
						ips[1].insert(ipstemp[1], 0, 0);
						ips[2].insert(ipstemp[2], 0, 0);
						
						
					}else{
	
						ipstemp[0] = new FloatProcessor(w/wide, h/high);
						ipstemp[1] = new FloatProcessor(w/wide, h/high);
						ipstemp[2] = new FloatProcessor(w/wide, h/high);
						
						ipstemp = functions.iprun(stack.getProcessor(m+b*wide+1), mode , "A7_1",true);
						 
						ips[0].insert(ipstemp[0], w/wide*m, h/high*b); 
						ips[1].insert(ipstemp[1], w/wide*m, h/high*b);
						ips[2].insert(ipstemp[2], w/wide*m, h/high*b);
					}

			}
		}
		 
		
		
//			
//			if(i % wide == 1){  
//				
//				functions.iprun(stack.getProcessor(i+1), mode , sigen[(i+1)-(wide-1)],true);
//				
////				if(i > sigen.length/wide){
////				
////				
////				ips[0].insert(ipstemp[0], w/wide*i, 0);
////				ips[1].insert(ipstemp[1], w/wide*i, 0);
////				ips[2].insert(ipstemp[2], w/wide*i, 0);
//				
//				
//			} else {
//				functions.iprun(stack.getProcessor(i+1), mode , sigen[i-1],true);
//				
////				ips[0].insert(ipstemp[0], w/wide*i, 0);
////				ips[1].insert(ipstemp[1], w/wide*i, 0);
////				ips[2].insert(ipstemp[2], w/wide*i, 0);
//				
//			}
//			
//		}
		
		imps[0].show();
		imps[1].show();
		imps[2].show();
		
	}
	
	//Folgt noch!
	public void decode(){
		
	}

	
	public void run(String arg) {

		
		ImagePlus iplus = WindowManager.getCurrentImage();
		
		// Test ob ein Bild vorhanden ist
		functions.failcheck();
		
		// Dialog um Eingaben vom Nutzer bzgl. der Blockgröße einzuholen
		addDialogue();
		
		// 3 Bilder bei Rücktransformation 
		if(mode == "dec"){
			if(WindowManager.getImageCount() < 3){
				IJ.showMessage("Bitte drei Bilder auswählen!");
				return;
			}
		}
		
		// Ungerade Bildmaße abfangen
		if(iplus.getWidth()%wide > 0 || iplus.getHeight()%high > 0 ){
				IJ.showMessage("Bitte Bild(er) mit geraden Bildmaßen wählen!");
				return;
		}

		// Anzeige: new ImagePlus("Blocks", stack).show();
		// Funktion die das Bild zerteilt und die Blöcke auf einen Stack legt
		ImageStack stack = functions.createStack(iplus.getProcessor(), wide, high);

		// Signalenergien speichern
		String sigenergy[] = new String[wide*high];  //Signalwerte speichern

		
		
		// BESTE RCT FÜR JEDEN BLOCK ERMITTELN
		// Für alle Bildabschnitte
		for (int x = 1; x <= stack.getSize(); x++) {
			
			int sigtemp = 0;
			int sigact = 0;
			
			// Jede RCT
			for (int q = 0; q < rcts.length; q++) {

				System.out.println("***************  Abschnitt : " + x + "     Fange an mit RCT " + rcts[q]);
				System.out.println("BEST : " + sigenergy[x-1]);

				
				int yvu[][][] = functions.iprun(stack.getProcessor(x), mode, rcts[q]);
						
				// Signalenergie per Pixel im Block , Bester wird gespeichert in sigenergy[]
				for (int i = 0; i < yvu.length; i++) {
					for (int y = 0; y < yvu[0].length; y++) {
					
						sigact = (yvu[i][y][0]*yvu[i][y][0])+(yvu[i][y][1]*yvu[i][y][1])+(yvu[i][y][2]*yvu[i][y][2]);
						
						if( sigtemp == 0){
							sigtemp = sigact;
						} else if( sigact < sigtemp){
							
							System.out.println("SWAP : "  + sigtemp + " = " + sigact);
							
							sigtemp = sigact;

							sigenergy[x-1] = rcts[q];
						}
					}
				}

			}

		}

		for (int z = 0 ; z < sigenergy.length ; z++){
			System.out.println(sigenergy[z]);
		}
	
		if(mode == "enc"){
		encode(sigenergy , stack,iplus.getWidth(),iplus.getHeight());
		}
		else{
			decode();
		}
		
		
		for (int z = 0 ; z < sigenergy.length ; z++){					//NUR FÜR TEST
			System.out.println("       **********************   "+sigenergy[z]);
		} 


	}



}
