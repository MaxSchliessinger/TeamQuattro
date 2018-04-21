import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.WindowManager;
import ij.gui.GenericDialog;
import ij.process.ByteProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;

public class functions {

	static String title;
	static boolean separated = true;
	static String to = "A7_1";
	static String from = "RGB";
	static boolean modulo = false;
	
	static ImagePlus[] imps = null;
	static ImageProcessor[] ips = null;
	
	static int wide = 0; 			//Angepasste Version des standartmäßig in ImageJ implemenierten "StackMaker" Tools.
	static int high = 0;			//Anzahl der Blöcke in X und Y Richtung
	
	


	// Funktion die das Bild zerteilt und die Blöcke auf einen Stack legt
	public static ImageStack createStack(ImageProcessor iproc, int wide, int high) { 

		int width = iproc.getWidth() / wide;
		int height = iproc.getHeight() / high;

		ImageStack stack = new ImageStack(width, height); // Anlegen eines ImageStacks

		for (int y = 0; y < high; y++) {
			for (int x = 0; x < wide; x++) {
				iproc.setRoi(x * width, y * height, width, height);
				stack.addSlice(null, iproc.crop());
			}
		}

		// TESTAUSGABE GRAFISCH
		iprun(stack.getProcessor(1));
		
		return stack;

	}
	
	public static void iprun(ImageProcessor ip) {

		int w = ip.getWidth(); // get size of input image
		int h = ip.getHeight();
		int addval3 = (1 << 8) - 1;
		int col, row, p, r, g, b, y, u, v;
		int values[] = new int[3];
		double percent = 0;

		if (separated) {
			if (modulo == true) {
				imps = new ImagePlus[3];
				ips = new ImageProcessor[3];
				ips[0] = new ByteProcessor(w, h);
				ips[1] = new ByteProcessor(w, h);
				ips[2] = new ByteProcessor(w, h);
				String label[] = new String[3];
				if (to.equals("RGB")) {
					label[0] = " (R)";
					label[1] = " (G)";
					label[2] = " (B)";
				} else {
					label[0] = " (Y)";
					label[1] = " (U)";
					label[2] = " (V)";
				}
				imps[0] = new ImagePlus(title + " : " + to + label[0] + " with Modulo ", ips[0]);
				imps[1] = new ImagePlus(title + " : " + to + label[1] + " with Modulo ", ips[1]);
				imps[2] = new ImagePlus(title + " : " + to + label[2] + " with Modulo ", ips[2]);
			} else {
				imps = new ImagePlus[3];
				ips = new ImageProcessor[3];
				ips[0] = new FloatProcessor(w, h);
				ips[1] = new FloatProcessor(w, h);
				ips[2] = new FloatProcessor(w, h);
				String label[] = new String[3];
				if (to.equals("RGB")) {
					label[0] = " (R)";
					label[1] = " (G)";
					label[2] = " (B)";
				} else {
					label[0] = " (Y)";
					label[1] = " (U)";
					label[2] = " (V)";
				}
				imps[0] = new ImagePlus(title + " : " + to + label[0], ips[0]);
				imps[1] = new ImagePlus(title + " : " + to + label[1], ips[1]);
				imps[2] = new ImagePlus(title + " : " + to + label[2], ips[2]);
			}
		}

		y = 0;
		u = 0;
		v = 0;

		/*--------------------------------------------------------------------*/
		/* forward transformation */
		/*--------------------------------------------------------------------*/
		for (col = 0; col < w; col++) {
			for (row = 0; row < h; row++) {
				// get the pixel
				p = ip.getPixel(col, row);
				r = ((p & 0xff0000) >> 16); // R
				g = ((p & 0x00ff00) >> 8); // G
				b = (p & 0x0000ff); // B
				if (from.equals("RGB")) {

					if (to.equals("A1_1")) // RGB to A1_1
					{
						y = g;
						v = r - g;
						u = b - g;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_2")) // RGB to A1_2
					{
						y = g;
						v = g - r;
						u = b - r;
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_3")) // RGB to A1_3
					{
						y = g;
						v = r - b;
						u = g - b;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					if (to.equals("A1_4")) // RGB to A1_4
					{
						y = g;
						v = r - g;
						u = b - g;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_5")) // RGB to A1_5
					{
						v = g - r;
						u = b - r;
						y = r + v;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_6")) // RGB to A1_6
					{
						v = r - b;
						u = g - b;
						y = b + u;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_7")) // RGB to A1_7
					{
						y = g;
						v = b - g;
						u = r - g;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					if (to.equals("A1_8")) // RGB to A1_8
					{
						v = g - b;
						u = r - b;
						y = b + v;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					if (to.equals("A1_9")) // RGB to A1_9
					{
						v = b - r;
						u = g - r;
						y = r + u;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_10")) // RGB to A1_10
					{
						y = g;
						v = r - g;
						u = b - g;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_11")) // RGB to A1_11
					{
						v = r - b;
						u = g - b;
						y = b + u;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A1_12")) // RGB to A1_12
					{
						y = g;
						v = b - g;
						u = r - g;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					if (to.equals("A2_1")) // RGB to A2_1
					{
						y = r;
						v = r - g;
						u = b - g;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_2
					if (to.equals("A2_2")) {
						y = r;
						v = g - r;
						u = b - r;
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_3
					if (to.equals("A2_3")) {
						y = r;
						v = r - b;
						u = g - b;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A2_4
					if (to.equals("A2_4")) {
						y = r;
						v = r - g;
						u = b - g;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_5
					if (to.equals("A2_5")) {
						y = r;
						v = g - r;
						u = b - r;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_6
					if (to.equals("A2_6")) {
						v = r - b;
						u = g - b;
						y = b + v;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_7
					if (to.equals("A2_7")) {
						v = b - g;
						u = r - g;
						y = g + u;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A2_8
					if (to.equals("A2_8")) {
						v = g - b;
						u = r - b;
						y = b + u;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A2_9
					if (to.equals("A2_9")) {
						y = r;
						v = b - r;
						u = g - r;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					if (to.equals("A2_10")) // RGB to A2_10
					{
						v = r - g;
						u = b - g;
						y = g + v;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_11
					if (to.equals("A2_11")) {
						v = r - b;
						u = g - b;
						y = b + v;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A2_12
					if (to.equals("A2_12")) {
						v = b - g;
						u = r - g;
						y = g + u;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A3_1
					if (to.equals("A3_1")) {
						y = b;
						v = r - g;
						u = b - g;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_2
					if (to.equals("A3_2")) {
						y = b;
						v = g - r;
						u = b - r;
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_3
					if (to.equals("A3_3")) {
						y = b;
						v = r - b;
						u = g - b;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A3_4
					if (to.equals("A3_4")) {
						y = b;
						v = r - g;
						u = b - g;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_5
					if (to.equals("A3_5")) {
						v = g - r;
						u = b - r;
						y = r + u;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_6
					if (to.equals("A3_6")) {
						y = b;
						v = r - b;
						u = g - b;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_7
					if (to.equals("A3_7")) {
						v = b - g;
						u = r - g;
						y = g + v;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A3_8
					if (to.equals("A3_8")) {
						y = b;
						v = g - b;
						u = r - b;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A3_9
					if (to.equals("A3_9")) {
						v = b - r;
						u = g - r;
						y = r + v;
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_10
					if (to.equals("A3_10")) {
						v = r - g;
						u = b - g;
						y = g + u;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_11
					if (to.equals("A3_11")) {
						y = b;
						v = r - b;
						u = g - b;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A3_12
					if (to.equals("A3_12")) {
						v = b - g;
						u = r - g;
						y = g + v;
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A4_1
					if (to.equals("A4_1")) {
						v = r - g;
						u = b - g;
						y = g + (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_2
					if (to.equals("A4_2")) {
						v = g - r;
						u = b - r;
						y = r + (v >> 1);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_3
					if (to.equals("A4_3")) {
						v = r - b;
						u = g - b;
						y = b + ((v + u) >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A4_4
					if (to.equals("A4_4")) {
						v = r - g;
						u = b - g;
						y = g + (v >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_5
					if (to.equals("A4_5")) {
						v = g - r;
						u = b - r;
						y = r + (v >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_6
					if (to.equals("A4_6")) {
						v = r - b;
						u = g - b;
						y = b + ((v + u) >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_7
					if (to.equals("A4_7")) {
						v = b - g;
						u = r - g;
						y = g + (u >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A4_8
					if (to.equals("A4_8")) {
						v = g - b;
						u = r - b;
						y = b + ((v + u) >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A4_9
					if (to.equals("A4_9")) {
						v = b - r;
						u = g - r;
						y = r + (u >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_10
					if (to.equals("A4_10")) {
						v = r - g;
						u = b - g;
						y = g + (v >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_11
					if (to.equals("A4_11")) {
						v = r - b;
						u = g - b;
						y = b + ((v + u) >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A4_12
					if (to.equals("A4_12")) {
						v = b - g;
						u = r - g;
						y = g + (u >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A5_1
					if (to.equals("A5_1")) {
						v = r - g;
						u = b - g;
						y = g + (u >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_2
					if (to.equals("A5_2")) {
						v = g - r;
						u = b - r;
						y = r + ((u + v) >> 1);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_3
					if (to.equals("A5_3")) {
						v = r - b;
						u = g - b;
						y = b + (u >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A5_4
					if (to.equals("A5_4")) {
						v = r - g;
						u = b - g;
						y = g + (u >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_5
					if (to.equals("A5_5")) {
						v = g - r;
						u = b - r;
						y = r + ((v + u) >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_6
					if (to.equals("A5_6")) {
						v = r - b;
						u = g - b;
						y = b + (u >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_7
					if (to.equals("A5_7")) {
						v = b - g;
						u = r - g;
						y = g + (v >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A5_8
					if (to.equals("A5_8")) {
						v = g - b;
						u = r - b;
						y = b + (v >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A5_9
					if (to.equals("A5_9")) {
						v = b - r;
						u = g - r;
						y = r + ((v + u) >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_10
					if (to.equals("A5_10")) {
						v = r - g;
						u = b - g;
						y = g + (u >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_11
					if (to.equals("A5_11")) {
						v = r - b;
						u = g - b;
						y = b + (u >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A5_12
					if (to.equals("A5_12")) {
						v = b - g;
						u = r - g;
						y = g + (v >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A6_1
					if (to.equals("A6_1")) {
						v = r - g;
						u = b - g;
						y = g + ((v + u) >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_2
					if (to.equals("A6_2")) {
						v = g - r;
						u = b - r;
						y = r + (u >> 1);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_3
					if (to.equals("A6_3")) {
						v = r - b;
						u = g - b;
						y = b + (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A6_4
					if (to.equals("A6_4")) {
						v = r - g;
						u = b - g;
						y = g + ((v + u) >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_5
					if (to.equals("A6_5")) {
						v = g - r;
						u = b - r;
						y = r + (u >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_6
					if (to.equals("A6_6")) {
						v = r - b;
						u = g - b;
						y = b + (v >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_7
					if (to.equals("A6_7")) {
						v = b - g;
						u = r - g;
						y = g + ((v + u) >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A6_8
					if (to.equals("A6_8")) {
						v = g - b;
						u = r - b;
						y = b + (u >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A6_9
					if (to.equals("A6_9")) {
						v = b - r;
						u = g - r;
						y = r + (v >> 1);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_10
					if (to.equals("A6_10")) {
						v = r - g;
						u = b - g;
						y = g + ((v + u) >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_11
					if (to.equals("A6_11")) {
						v = r - b;
						u = g - b;
						y = b + (v >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A6_12
					if (to.equals("A6_12")) {
						v = b - g;
						u = r - g;
						y = g + ((v + u) >> 1);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A7_1
					if (to.equals("A7_1")) {
						v = r - g;
						u = b - g;
						y = g + ((u + v) >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_2
					if (to.equals("A7_2")) {
						v = g - r;
						u = b - r;
						y = r + (((v << 1) + u) >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_3
					if (to.equals("A7_3")) {
						v = r - b;
						u = g - b;
						y = b + ((v + (u << 1)) >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A7_4
					if (to.equals("A7_4")) {
						v = r - g;
						u = b - g;
						y = g + ((v + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_5
					if (to.equals("A7_5")) {
						v = g - r;
						u = b - r;
						y = r + (((v << 1) + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_6
					if (to.equals("A7_6")) {
						v = r - b;
						u = g - b;
						y = b + ((v + (u << 1)) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_7
					if (to.equals("A7_7")) {
						v = b - g;
						u = r - g;
						y = g + ((v + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A7_8
					if (to.equals("A7_8")) {
						v = g - b;
						u = r - b;
						y = b + (((v << 1) + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A7_9
					if (to.equals("A7_9")) {
						v = b - r;
						u = g - r;
						y = r + ((v + (u << 1)) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_10
					if (to.equals("A7_10")) {
						v = r - g;
						u = b - g;
						y = g + ((v + u) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_11
					if (to.equals("A7_11")) {
						v = r - b;
						u = g - b;
						y = b + ((v + (u << 1)) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A7_12
					if (to.equals("A7_12")) {
						v = b - g;
						u = r - g;
						y = g + ((v + u) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A8_1
					if (to.equals("A8_1")) {
						v = r - g;
						u = b - g;
						y = g + ((u + (v << 1)) >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_2
					if (to.equals("A8_2")) {
						v = g - r;
						u = b - r;
						y = r + ((u + v) >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_3
					if (to.equals("A8_3")) {
						v = r - b;
						u = g - b;
						y = b + ((u + (v << 1)) >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A8_4
					if (to.equals("A8_4")) {
						v = r - g;
						u = b - g;
						y = g + (((v << 1) + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_5
					if (to.equals("A8_5")) {
						v = g - r;
						u = b - r;
						y = r + ((v + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_6
					if (to.equals("A8_6")) {
						v = r - b;
						u = g - b;
						y = b + (((v << 1) + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_7
					if (to.equals("A8_7")) {
						v = b - g;
						u = r - g;
						y = g + ((v + (u << 1)) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A8_8
					if (to.equals("A8_8")) {
						v = g - b;
						u = r - b;
						y = b + ((v + (u << 1)) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A8_9
					if (to.equals("A8_9")) {
						v = b - r;
						u = g - r;
						y = r + ((v + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_10
					if (to.equals("A8_10")) {
						v = r - g;
						u = b - g;
						y = g + (((v << 1) + u) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_11
					if (to.equals("A8_11")) {
						v = r - b;
						u = g - b;
						y = b + (((v << 1) + u) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A8_12
					if (to.equals("A8_12")) {
						v = b - g;
						u = r - g;
						y = g + ((v + (u << 1)) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A9_1
					if (to.equals("A9_1")) {
						v = r - g;
						u = b - g;
						y = g + ((v + (u << 1)) >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_2
					if (to.equals("A9_2")) {
						v = g - r;
						u = b - r;
						y = r + ((v + (u << 1)) >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_3
					if (to.equals("A9_3")) {
						v = r - b;
						u = g - b;
						y = b + ((v + u) >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = -u + addval3;
					}

					// RGB to A9_4
					if (to.equals("A9_4")) {
						v = r - g;
						u = b - g;
						y = g + (((u << 1) + v) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_5
					if (to.equals("A9_5")) {
						v = g - r;
						u = b - r;
						y = r + (((u << 1) + v) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_6
					if (to.equals("A9_6")) {
						v = r - b;
						u = g - b;
						y = b + ((v + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_7
					if (to.equals("A9_7")) {
						v = b - g;
						u = r - g;
						y = g + (((v << 1) + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to A9_8
					if (to.equals("A9_8")) {
						v = g - b;
						u = r - b;
						y = b + ((v + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = -v + addval3;
					}

					// RGB to A9_9
					if (to.equals("A9_9")) {
						v = b - r;
						u = g - r;
						y = r + (((v << 1) + u) >> 2);
						u = u - (v >> 2);
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_10
					if (to.equals("A9_10")) {
						v = r - g;
						u = b - g;
						y = g + (((u << 1) + v) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_11
					if (to.equals("A9_11")) {
						v = r - b;
						u = g - b;
						y = b + ((u + v) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u + addval3;
					}

					// RGB to A9_12
					if (to.equals("A9_12")) {
						v = b - g;
						u = r - g;
						y = g + (((v << 1) + u) >> 2);
						u = u - (v >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

					// RGB to B1_1
					if (to.equals("B1_1")) {
						y = g;
						v = r - g;
						u = b;
						values[0] = y;
						values[1] = v + addval3;
						values[2] = u;
					}

					// RGB to B1_2
					if (to.equals("B1_2")) {
						y = g;
						v = b - g;
						u = r;
						values[0] = u;
						values[1] = y;
						values[2] = v + addval3;
					}

					// RGB to B2_1
					if (to.equals("B2_1")) {
						y = r;
						v = g - r;
						u = b;
						values[0] = y;
						values[1] = -v + addval3;
						values[2] = u;
					}

					// RGB to B2_3
					if (to.equals("B2_3")) {
						y = r;
						v = b - r;
						u = g;
						values[0] = y;
						values[1] = u;
						values[2] = -v + addval3;
					}

					// RGB to B3_2
					if (to.equals("B3_2")) {
						y = b;
						v = g - b;
						u = r;
						values[0] = u;
						values[1] = -v + addval3;
						values[2] = y;
					}

					// RGB to B3_3
					if (to.equals("B3_3")) {
						y = b;
						v = r - b;
						u = g;
						values[0] = u;
						values[1] = v + addval3;
						values[2] = y;
					}

					// RGB to B4_1
					if (to.equals("B4_1")) {
						u = r - g;
						v = b;
						y = g + (u >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v;
					}

					// RGB to B5_2
					if (to.equals("B5_2")) {
						u = g - b;
						v = r;
						y = b + (u >> 1);
						values[0] = v;
						values[1] = y;
						values[2] = -u + addval3;
					}

					// RGB to B6_3
					if (to.equals("B6_3")) {
						u = r - b;
						v = g;
						y = b + (u >> 1);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v;
					}

					// RGB to PEI09
					if (to.equals("PEI09")) {
						u = b - ((87 * r + 169 * g) >> 8);
						v = r - g;
						y = g + ((29 * u + 86 * v) >> 8);
						values[0] = y;
						values[1] = u + addval3;
						values[2] = v + addval3;
					}

				}
				/*------------------------------------------------------------------------------------------*/
				/*																																													*/
				/* back transformation */
				/*																																													*/
				/*------------------------------------------------------------------------------------------*/
				else if (to.equals("RGB")) {
					r = ((p & 0xff0000) >> 16); // Y
					g = ((p & 0x00ff00) >> 8); // U
					b = (p & 0x0000ff); // V

					if (from.equals("A1_1")) // A1_1 to RGB a1=a2=0
					{
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y;
						r = v + g;
						b = u + g;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_2")) // A1_2 to RGB a1=1 a2=0, R<=>G
					{
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - v;
						g = v + r;
						b = u + r;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_3")) // A1_3 to RGB a1=0 a2=1, B<=>G
					{
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - u;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_4")) // A1_4 to RGB a1=a2=0 e=1/4
					{
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_5")) // A1_5 to RGB a1=1 a2=0, e=1/4,
												// R<=>G
					{
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - v;
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_6")) // A1_6 to RGB a1=0 a2=1, e=1/4,
												// B<=>G
					{
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - u;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_7")) // A1_7 to RGB a1=a2=0 e=1/4 B<=>R
					{
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y;
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A1_8")) // A1_8 to RGB a1=1 a2=0, e=1/4,
												// R=>G=>B=>R
					{
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - v;
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A1_9 to RGB a1=0 a2=1, e=1/4, R=>B=>G=>R
					if (from.equals("A1_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - u;
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A1_10 to RGB a1=a2=0 e=1/2
					if (from.equals("A1_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A1_11 to RGB a1=a2=0 e=1/2
					if (from.equals("A1_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - u;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A1_12 to RGB a1=a2=0 e=1/2 R<=>B
					if (from.equals("A1_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y;
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_1 to RGB a1=1 a2=0
					if (from.equals("A2_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - v;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_2 to RGB a1=1 a2=0 R<=>G
					if (from.equals("A2_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y;
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_3 to RGB a1=1 a2=0, B<=>G
					if (from.equals("A2_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - v;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_4 to RGB a1=1 a2=0, e=1/4
					if (from.equals("A2_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - v;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_5 to RGB a1=1 a2=0 e=1/4 R<=>G
					if (from.equals("A2_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y;
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_6 to RGB a1=1 a2=0, e=1/4, B<=>G
					if (from.equals("A2_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - v;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_7 to RGB a1=1 a2=0, e=1/4, B<=>R
					if (from.equals("A2_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - u;
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_8 to RGB a1=0 a2=1, e=1/4, R=>G=>B=>R
					if (from.equals("A2_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - u;
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_9 to RGB a1=a2=0 e=1/4 R=>B=>G=>R
					if (from.equals("A2_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y;
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_10 to RGB a1=1 a2=0, e=1/2
					if (from.equals("A2_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - v;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_11 to RGB a1=1 a2=0, e=1/2, B<=>G
					if (from.equals("A2_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - v;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A2_12 to RGB a1=0 a2=1, e=1/2, B<=>R
					if (from.equals("A2_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - u;
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_1 to RGB a1=a2=0 e=1/2 B<=>G
					if (from.equals("A3_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - u;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_2 to RGB a1=0 a2=1, R<=>G
					if (from.equals("A3_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - u;
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_3 to RGB a1=a2=0 B<=>G
					if (from.equals("A3_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_4 to RGB a1=0 a2=1, e=1/4
					if (from.equals("A3_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - u;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_5 to RGB a1=0 a2=1, e=1/4, R<=>G
					if (from.equals("A3_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - u;
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_6 to RGB a1=a2=0 e=1/4 B<=>G
					if (from.equals("A3_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_7 to RGB a1=1 a2=0, e=1/4, B<=>R
					if (from.equals("A3_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - v;
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_8 to RGB a1=a2=0 e=1/4 R=>G=>B=>R
					if (from.equals("A3_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y;
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_9 to RGB a1=1 a2=0, e=1/4, R=>B=>G=>R
					if (from.equals("A3_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - v;
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_10 to RGB a1=0 a2=1, e=1/2
					if (from.equals("A3_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - u;
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_11 to RGB a1=a2=0 e=1/2 B<=>G
					if (from.equals("A3_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y;
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A3_12 to RGB a1=1 a2=0, e=1/2, B<=>R
					if (from.equals("A3_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - v;
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_1 to RGB a1=1/2 a2=0
					if (from.equals("A4_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - (v >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_2 to RGB a1=1/2 a2=0, R<=>G
					if (from.equals("A4_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - (v >> 1);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_3 to RGB a1=1/2 a2=0, B<=>G
					if (from.equals("A4_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - ((v + u) >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_4 to RGB a1=1/2 a2=0, e=1/4
					if (from.equals("A4_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - (v >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_5 to RGB a1=1/2 a2=0, e=1/4, R<=>G
					if (from.equals("A4_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (v >> 1);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_6 to RGB a1=1/2 a2=0, e=1/4, B<=>G
					if (from.equals("A4_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - ((v + u) >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_7 to RGB a2=1/2 a1=0, e=1/4, B<=>R
					if (from.equals("A4_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - (u >> 1);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_8 to RGB a1=a2=1/2, e=1/4, R=>G=>B=>R
					if (from.equals("A4_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - ((v + u) >> 1);
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_9 to RGB a2=1/2 a1=0, e=1/4, R=>B=>G=>R
					if (from.equals("A4_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (u >> 1);
						g = (u + r);
						b = (v + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_10 to RGB a1=1/2 a2=0, e=1/2
					if (from.equals("A4_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - (v >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_11 to RGB a1=a2=1/2, e=1/2, B<=>G
					if (from.equals("A4_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - ((v + u) >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A4_12 to RGB a2=1/2 a1=0, e=1/2, B<=>R
					if (from.equals("A4_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - (u >> 1);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_1 to RGB a1=1/2 a2=0, e=1/2, B<=>R
					if (from.equals("A5_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - (u >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_2 to RGB a1=a2=1/2, R<=>G
					if (from.equals("A5_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - ((v + u) >> 1);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_3 to RGB a2=1/2 a1=0, B<=>G
					if (from.equals("A5_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - (u >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_4 to RGB a2=1/2 a1=0, e=1/4
					if (from.equals("A5_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - (u >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_5 to RGB a1=a2=1/2, e=1/4, R<=>G
					if (from.equals("A5_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - ((v + u) >> 1);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_6 to RGB a2=1/2 a1=0, e=1/4, B<=>G
					if (from.equals("A5_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - (u >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_7 to RGB a1=1/2 a2=0, e=1/4, B<=>R
					if (from.equals("A5_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - (v >> 1);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_8 to RGB a1=1/2 a2=0, e=1/4, R=>G=>B=>R
					if (from.equals("A5_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - (v >> 1);
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_9 to RGB a1=a2=1/2, e=1/4, R=>B=>G=>R
					if (from.equals("A5_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - ((v + u) >> 1);
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_10 to RGB a2=1/2 a1=0, e=1/2
					if (from.equals("A5_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - (u >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_11 to RGB a2=1/2 a1=0, e=1/2, B<=>G
					if (from.equals("A5_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - (u >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A5_12 to RGB a1=1/2 a2=0, e=1/2, B<=>R
					if (from.equals("A5_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - (v >> 1);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_1 to RGB a1=a2=1/2
					if (from.equals("A6_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - ((v + u) >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_2 to RGB a2=1/2 a1=0, R<=>G
					if (from.equals("A6_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - (u >> 1);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_3 to RGB a1=1/2 a2=0, B<=>G
					if (from.equals("A6_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - (v >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_4 to RGB a1=a2=1/2, e=1/4
					if (from.equals("A6_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - ((v + u) >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_5 to RGB a2=1/2 a1=0, e=1/4, R<=>G
					if (from.equals("A6_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (u >> 1);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_6 to RGB a1=1/2 a2=0, e=1/4, B<=>G
					if (from.equals("A6_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - (v >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_7 to RGB a1=a2=1/2, e=1/4, B<=>R
					if (from.equals("A6_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - ((v + u) >> 1);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_8 to RGB a2=1/2 a1=0, e=1/4, R=>G=>B=>R
					if (from.equals("A6_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - (u >> 1);
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_9 to RGB a1=1/2 a2=0, e=1/4, R=>B=>G=>R
					if (from.equals("A6_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (v >> 1);
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_10 to RGB a1=a2=1/2, e=1/2
					if (from.equals("A6_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - ((v + u) >> 1);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_11 to RGB a1=1/2 a2=0, e=1/2, B<=>G
					if (from.equals("A6_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - (v >> 1);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A6_12 to RGB a1=a2=1/2, e=1/2, B<=>R
					if (from.equals("A6_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - ((v + u) >> 1);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_1 to RGB a1=a2=1/4 == YUV
					if (from.equals("A7_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - ((v + u) >> 2);
						r = v + (int) g;
						b = u + (int) g;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_2 to RGB a1=1/2 a2=1/4, R<=>G
					if (from.equals("A7_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - (((v << 1) + u) >> 2);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_3 to RGB a1=1/4 a2=1/2, B<=>G
					if (from.equals("A7_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - (((u << 1) + v) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_4 to RGB a1=a2=1/4, e=1/4
					if (from.equals("A7_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - ((v + u) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_5 to RGB a1=1/2 a2=1/4, e=1/4, R<=>G
					if (from.equals("A7_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (((v << 1) + u) >> 2);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_6 to RGB a1=1/4 a2=1/2, e=1/4, B<=>G
					if (from.equals("A7_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - (((u << 1) + v) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_7 to RGB a1=a2=1/4, e=1/4, B<=>R
					if (from.equals("A7_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - ((v + u) >> 2);
						r = (u + g);
						b = (v + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_8 to RGB a1=1/2 a2=1/4, e=1/4, R=>G=>B=>R
					if (from.equals("A7_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - (((v << 1) + u) >> 2);
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_9 to RGB a1=1/4 a2=1/2, e=1/4, R=>B=>G=>R
					if (from.equals("A7_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (((u << 1) + v) >> 2);
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_10 to RGB a1=a2=1/4, e=1/2
					if (from.equals("A7_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - ((v + u) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_11 to RGB a1=1/4 a2=1/2, e=1/2, B<=>G
					if (from.equals("A7_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - (((u << 1) + v) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A7_12 to RGB a1=a2=1/4, e=1/2, B<=>R
					if (from.equals("A7_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - ((v + u) >> 2);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_1 to RGB a1=1/2 a2=1/4
					if (from.equals("A8_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - (((v << 1) + u) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_2 to RGB a1=a2=1/4, R<=>G
					if (from.equals("A8_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - ((v + u) >> 2);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_3 to RGB a1=1/2 a2=1/4, B<=>G
					if (from.equals("A8_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - (((v << 1) + u) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_4 to RGB a1=1/2 a2=1/4, e=1/4
					if (from.equals("A8_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - (((v << 1) + u) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_5 to RGB a1=a2=1/4, e=1/4, R<=>G
					if (from.equals("A8_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - ((v + u) >> 2);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_6 to RGB a1=1/2 a2=1/4, e=1/4, B<=>G
					if (from.equals("A8_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - (((v << 1) + u) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_7 to RGB a1=1/4 a2=1/2, e=1/4, B<=>R
					if (from.equals("A8_7")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - (((u << 1) + v) >> 2);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_8 to RGB a1=1/4 a2=1/2, e=1/4, R=>G=>B=>R
					if (from.equals("A8_8")) {
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - (((u << 1) + v) >> 2);
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_9 to RGB a1=a2=1/4, e=1/4, R=>B=>G=>R
					if (from.equals("A8_9")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - ((v + u) >> 2);
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_10 to RGB a1=1/2 a2=1/4, e=1/2
					if (from.equals("A8_10")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - (((v << 1) + u) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_11 to RGB a1=1/2 a2=1/4, e=1/2, B<=>G
					if (from.equals("A8_11")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - (((v << 1) + u) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A8_12 to RGB a1=1/2 a2=1/4, e=1/2, B<=>R
					if (from.equals("A8_12")) {
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - (((u << 1) + v) >> 2);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A9_1 to RGB a1=1/4 a2=1/2
					if (from.equals("A9_1")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						g = y - (((u << 1) + v) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A9_2 to RGB a1=1/4 a2=1/2, R<=>G
					if (from.equals("A9_2")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						r = y - (((u << 1) + v) >> 2);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A9_3 to RGB a1=a2=1/4, B<=>G
					if (from.equals("A9_3")) {
						y = r;
						v = g - addval3;
						u = -b + addval3;
						b = y - ((v + u) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A9_4 to RGB a1=1/4 a2=1/2, e=1/4
					if (from.equals("A9_4")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						g = y - (((u << 1) + v) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A9_5 to RGB a1=1/4 a2=1/2, e=1/4, R<=>G
					if (from.equals("A9_5")) {
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (((u << 1) + v) >> 2);
						g = (v + r);
						b = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					// A9_6 to RGB a1=a2=1/4, e=1/4, B<=>G
					if (from.equals("A9_6")) {
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 2);
						b = y - ((v + u) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A9_7")) // A9_7 to RGB a1=1/2 a2=1/4,
												// e=1/4, B<=>R
					{
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 2);
						g = y - (((v << 1) + u) >> 2);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A9_8")) // A9_8 to RGB a1=a2=1/4, e=1/4,
												// R=>G=>B=>R
					{
						y = r;
						u = g - addval3;
						v = -b + addval3;
						u = u + (v >> 2);
						b = y - ((v + u) >> 2);
						g = (v + b);
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A9_9")) // A9_9 to RGB a1=1/2 a2=1/4,
												// e=1/4, R=>B=>G=>R
					{
						y = r;
						v = -g + addval3;
						u = b - addval3;
						u = u + (v >> 2);
						r = y - (((v << 1) + u) >> 2);
						b = (v + r);
						g = (u + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A9_10")) // A9_10 to RGB a1=1/4 a2=1/2,
												// e=1/2
					{
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						g = y - (((u << 1) + v) >> 2);
						r = (v + g);
						b = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A9_11")) // A9_11 to RGB a1=a2=1/4, e=1/2,
												// B<=>G
					{
						y = r;
						v = g - addval3;
						u = b - addval3;
						u = u + (v >> 1);
						b = y - ((v + u) >> 2);
						r = (v + b);
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("A9_12")) // A9_12 to RGB a1=1/2 a2=1/4,
												// e=1/2, B<=>R
					{
						y = r;
						u = g - addval3;
						v = b - addval3;
						u = u + (v >> 1);
						g = y - (((v << 1) + u) >> 2);
						b = (v + g);
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B1_1")) // B1_1 to RGB
					{
						y = r;
						v = g - addval3;
						u = b;
						g = y;
						r = (v + g);
						b = u;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B1_2")) // B1_2 to RGB
					{
						u = r;
						y = g;
						v = b - addval3;
						g = y;
						b = (v + g);
						r = u;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B2_1")) // B2_1 to RGB
					{
						y = r;
						v = -g + addval3;
						u = b;
						r = y;
						g = (v + r);
						b = u;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B2_3")) // B2_3 to RGB
					{
						y = r;
						u = g;
						v = -b + addval3;
						r = y;
						g = u;
						b = (v + r);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B3_2")) // B3_2 to RGB
					{
						u = r;
						v = -g + addval3;
						y = b;
						b = y;
						g = (v + b);
						r = u;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B3_3")) // B3_3 to RGB
					{
						u = r;
						v = g - addval3;
						y = b;
						b = y;
						r = (v + b);
						g = u;
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B4_1")) // B4_1 to RGB
					{
						y = r;
						u = g - addval3;
						v = b;
						g = y - (u >> 1);
						b = v;
						r = (u + g);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B5_2")) // B5_2 to RGB
					{
						v = r;
						y = g;
						u = -b + addval3;
						b = y - (u >> 1);
						r = v;
						g = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("B6_3")) // B6_3 to RGB
					{
						y = r;
						u = g - addval3;
						v = b;
						b = y - (u >> 1);
						g = v;
						r = (u + b);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

					if (from.equals("PEI09")) // PEI09 to RGB
					{
						y = r;
						u = g - addval3;
						v = b - addval3;
						g = y - ((29 * u + 86 * v) >> 8);
						r = v + g;
						b = u + ((87 * r + 169 * g) >> 8);
						values[0] = r;
						values[1] = g;
						values[2] = b;
					}

				} else {
					values[0] = r;
					values[1] = g;
					values[2] = b;
				}

				// put the pixel back
				ips[0].putPixelValue(col, row, values[0]);
				ips[1].putPixelValue(col, row, values[1]);
				ips[2].putPixelValue(col, row, values[2]);
			}
			percent = ((col + 1) * 100) / w;
			IJ.showStatus("CSCmod_ is loading, may take a while. Status: " + percent + "%");
		}

		// show separated images
		if (separated) {
			imps[0].show();
			imps[1].show();
			imps[2].show();
		}
	};

	public static void failcheck() { // Test ob ein Bild vorhanden ist

		ImagePlus iplus = WindowManager.getCurrentImage();
		if (iplus == null) {
			IJ.noImage();
			return;
		}

	}
	
	
	
}
