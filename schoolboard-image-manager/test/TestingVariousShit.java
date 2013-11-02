import java.util.Iterator;

import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import imageprocessing.color.ColorConversion;


public class TestingVariousShit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double r = 0, g = 241, b = 255;
		double[] hsv = ColorConversion.rgb2hsv(r, g, b);
		System.out.println("hue "+hsv[0]);
		System.out.println("sat "+hsv[1]);
		System.out.println("val "+hsv[2]);

	}
		


}
