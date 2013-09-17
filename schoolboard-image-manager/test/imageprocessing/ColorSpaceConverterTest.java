package imageprocessing;

import test.Test;
import ij.ImagePlus;

public class ColorSpaceConverterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus ip = new ImagePlus("images/bg_remove/czarna-szkola/bb2.png");
		ColorSpaceConverter.toHSBStack(ip);
		Test.showImage(ip.getStack().getProcessor(2).getBufferedImage(), "lol");
	}

}
