package imageprocessing;

import ij.plugin.filter.GaussianBlur;
import ij.process.ImageProcessor;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.image.BufferedImage;

import test.Test;


public class FiltersTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage img = Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png");
		ImageProcessor ip = AWTImageWrapper.toImageProcessor(img);
		GaussianBlur gb = new GaussianBlur();
		gb.blurGaussian(ip, 10, 10, 0.2);
		Test.showImage(ip.getBufferedImage(), "blurred");
	}

}
