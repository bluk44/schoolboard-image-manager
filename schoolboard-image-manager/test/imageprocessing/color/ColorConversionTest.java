package imageprocessing.color;

import imageprocessing.Util;

import java.awt.image.BufferedImage;

import test.Test;

public class ColorConversionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage rgbImage = Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png");
		BufferedImage[] rgbChannles = ColorConversion.rgb2hsv(rgbImage);
		
		Test.showImage(rgbChannles[0], "h");
		Test.showImage(rgbChannles[1], "s");
		Test.showImage(rgbChannles[2], "v");

	}

}
