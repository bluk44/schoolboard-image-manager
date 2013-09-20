package imageprocessing;

import ij.process.AutoThresholder.Method;
import ij.process.ImageProcessor;

public class Thresholding {

	public static void maxEntropy(ImageProcessor ip){
		ip.setAutoThreshold(Method.MaxEntropy, false, ImageProcessor.BLACK_AND_WHITE_LUT);
	}
}
