package imageprocessing;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.boundsdetection.QuadrangleTransformation;
import imageprocessing.geometry.Polygon;
import imageprocessing.plugin.ij.AWTImageWrapper;
import imageprocessing.segmentation.BackgroundCleaner;
import imageprocessing.segmentation.WhiteboardBackgroundCleaner;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImageProcessingTest {

	static String[] inputFolders = { /*
									 * "images/whiteboard/hi_skewness",
									 * "images/whiteboard/low_skewness",
									 * "images/whiteboard/complex_background",
									 */"images/blackboard" };

	static double SMOOTHING_SCALE = 1.0;
	static double LOWER = 5.0, HIGHER = 10.0;
	static boolean SUPPRESS = true;

	static double GAUSSIAN_BLUR_RAD = 10;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// TESTY WSZYSTKICH ZDJEC
		// for (int i = 0; i < inputFolders.length; i++) {
		// File imgDir = new File(inputFolders[i]);
		// File[] imgs = imgDir.listFiles();
		// BufferedImage[] input = null;
		// input = new BufferedImage[imgs.length];
		//
		// for (int j = 0; j < imgs.length; j++) {
		// try {
		// input[j] = ImageIO.read(imgs[j]);
		// } catch (IOException e) {
		// System.out.println("unable to read " + imgs[j].getPath());
		// }
		// }
		//
		// for (int k = 0; k < input.length; k++) {
		// if (input[k] != null) {
		// BufferedImage out = boundaryAndPerspectiveTest(input[k]);
		// try {
		// System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// //Util.writeToFile(imgDir + "/canny_s1l5h10_" + imgs[k].getName(),
		// "png", out);
		// }
		// }
		//
		// }

		// TESTY POJEDYNCZEGO ZDJECIA
		BufferedImage img = Util.readFromFile("images/whiteboard/complex_background/wb06_.png");
		ImagePlus out = boundaryAndPerspectiveTest(img);
		//out.show();
		
		binarizationWhiteboardTest(out);
		out.show();
		// szukanie regionow z tekstem TODO
	}

	public static ImagePlus boundaryAndPerspectiveTest(BufferedImage input) {
		ImagePlus output = null;
		ImagePlus ip = AWTImageWrapper.toImagePlus(input);
		ImagePlus edges = AWTImageWrapper.toImagePlus(input);
		try {
			CSConverter.run(Conversion.GRAY_8, edges);
		} catch (UnsupportedConversionException e) {
			System.out.println("unable to convert ");
		}
		EdgeDetection.canny(edges, SMOOTHING_SCALE, SUPPRESS, LOWER, HIGHER);
		// output = ip.getBufferedImage();
		BoundaryDetector bd = new BoundaryDetector();
		bd.setImageEdge(edges);

		// poprawa perspektywy
		try {
			BoardQuadrangle bq = bd.detectBestQuadrangle();

			// BDTestComp comp = new BDTestComp(input);
			// DrawablePerimeter dp = new DrawablePerimeter(bq);
			// dp.setVerticesColor(Color.RED);
			// dp.setEdgesColor(Color.BLUE);
			// comp.addDrawable(dp);

			// Test.showComponent(comp, "best quadrangle");

			Polygon fixed = QuadrangleTransformation.transform(ip, bq);

			ImageProcessor ipr = ip.getProcessor();
			ipr.setRoi(new Rectangle((int) fixed.getPoint(0).x, (int) fixed.getPoint(0).y, (int) (fixed.getPoint(2).x - fixed.getPoint(0).x), (int) (fixed
					.getPoint(2).y - fixed.getPoint(0).y)));
			ipr = ipr.crop();
			ip.setProcessor(ipr);
			output = ip;

		} catch (QuadrangleNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("nie znaleziono wlasciwego czworokata ");
		}

		return output;

	}

	public static ImagePlus binarizationWhiteboardTest(ImagePlus imp) {
		BackgroundCleaner cleaner = new WhiteboardBackgroundCleaner();
		cleaner.setLumCorrectionBlurRadius(GAUSSIAN_BLUR_RAD);
		cleaner.separateForeground(imp);
		
		return imp;
	}
}
