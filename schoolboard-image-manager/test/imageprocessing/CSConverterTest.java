package imageprocessing;

import ij.ImagePlus;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import test.Test;

public class CSConverterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus ip = new ImagePlus("images/bg_remove/czarna-szkola/bb2.png");
		ip.show();
		
		try {
			CSConverter.run(Conversion.STACK_HSB, ip);
			CSConverter.run(Conversion.COLOR_RGB, ip);
		} catch (UnsupportedConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
