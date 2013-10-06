package imageprocessing;

import ij.ImagePlus;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.boundsdetection.QuadrangleTransformation;
import imageprocessing.geometry.drawing.DrawablePerimeter;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.Color;
import java.awt.image.BufferedImage;

import test.Test;
import testJcomps.BDTestComp;

public class ImageProcessingTest {

	static String[] inputFolders = { /*"images/whiteboard/hi_skewness", "images/whiteboard/low_skewness", "images/whiteboard/complex_background",*/ "images/blackboard" };
	
	
	static double SMOOTHING_SCALE = 1.0;
	static double LOWER = 5.0, HIGHER = 10.0;
	static boolean SUPPRESS = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
// TESTY WSZYSTKICH ZDJEC
//		for (int i = 0; i < inputFolders.length; i++) {
//			File imgDir = new File(inputFolders[i]);
//			File[] imgs = imgDir.listFiles();
//			BufferedImage[] input = null;
//			input = new BufferedImage[imgs.length];
//
//			for (int j = 0; j < imgs.length; j++) {
//				try {
//					input[j] = ImageIO.read(imgs[j]);
//				} catch (IOException e) {
//					System.out.println("unable to read " + imgs[j].getPath());
//				}
//			}
//
//			for (int k = 0; k < input.length; k++) {
//				if (input[k] != null) {
//					BufferedImage out = boundaryAndPerspectiveTest(input[k]);
//					try {
//						System.in.read();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					//Util.writeToFile(imgDir + "/canny_s1l5h10_" + imgs[k].getName(), "png", out);
//				}
//			}
//
//		}

// TESTY POJEDYNCZEGO ZDJECIA		
		BufferedImage img = Util.readFromFile("images/whiteboard/low_skewness/wb01.png");
		BufferedImage out = boundaryAndPerspectiveTest(img);
		Test.showImage(out, "out");
		// przyciecie TODO

		// binaryzacja TODO

		// szukanie regionow z tekstem TODO
	}

	public static BufferedImage boundaryAndPerspectiveTest(BufferedImage input) {
		BufferedImage output = null;
		ImagePlus ip = AWTImageWrapper.toImagePlus(input);
		try {
			CSConverter.run(Conversion.GRAY_8, ip);
		} catch (UnsupportedConversionException e) {
			System.out.println("unable to convert ");
		}
		EdgeDetection.canny(ip, SMOOTHING_SCALE, SUPPRESS, LOWER, HIGHER);
		//output = ip.getBufferedImage();
		BoundaryDetector bd = new BoundaryDetector();
		bd.setImageEdge(ip);
		
		try {
			BoardQuadrangle bq = bd.detectBestQuadrangle();
			output = QuadrangleTransformation.transform(input, bq);
			
//			BDTestComp comp = new BDTestComp(input);
//			DrawablePerimeter dp = new DrawablePerimeter(bq);
//			dp.setVerticesColor(Color.RED);
//			dp.setEdgesColor(Color.BLUE);
//			comp.addDrawable(dp);
			
			//Test.showComponent(comp, "best quadrangle");
		} catch (QuadrangleNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("nie znaleziono wlasciwego czworokata ");
		}
		
		// poprawa perspektywy TODO
		
		return output;

	}
}
