package imageprocessing;

import ij.ImagePlus;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.image.BufferedImage;
import java.io.File;

import test.Test;

public class EdgeDetectionTest {

	static double SMOOTHING_SCALE = 4.0;
	static double LOWER = 1.0, HIGHER = 2.0;
	static boolean SUPPRESS = true;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File imgDir = new File("images/bg_remove/biala-szkola/");
		File[] imageFiles = imgDir.listFiles();
		for (int i = 0; i < imageFiles.length; i++) {
			if(!imageFiles[i].isFile()) continue;
			ImagePlus ipl = AWTImageWrapper.toImagePlus(Util.readFromFile(imageFiles[i]));
			try {
				CSConverter.run(Conversion.GRAY_8, ipl);
			} catch (UnsupportedConversionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			EdgeDetection.canny(ipl, SMOOTHING_SCALE, SUPPRESS, LOWER, HIGHER);
			Util.writeToFile(imgDir + "/canny/" + "" + SMOOTHING_SCALE + "_" + LOWER + "_" + HIGHER + "_" + SUPPRESS + "_" + imageFiles[i].getName(), "png",
					ipl.getBufferedImage());

		}
	}
}
