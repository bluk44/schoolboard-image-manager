package imageprocessing;

import test.Test;
import ij.ImagePlus;
import imageprocessing.plugin.ij.AWTImageWrapper;


public class EdgeDetectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus ipl = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/czarna-szkola/greyscale/bb2.png"));
		
		EdgeDetection.canny(ipl, 1.0, false, 2, 4);
		Test.showImage(ipl.getBufferedImage(), "after");
	}

}
