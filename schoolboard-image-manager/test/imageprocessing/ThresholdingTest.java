package imageprocessing;

import ij.process.ImageProcessor;
import imageprocessing.plugin.ij.AWTImageWrapper;
import test.Test;

public class ThresholdingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageProcessor ip = AWTImageWrapper.toImageProcessor(Util.readFromFile("images/bg_remove/czarna-szkola/greyscale/bb2.png"));
		Thresholding.maxEntropy(ip);
		Test.showImage(ip.getBufferedImage(), "thresh");

	}

}
