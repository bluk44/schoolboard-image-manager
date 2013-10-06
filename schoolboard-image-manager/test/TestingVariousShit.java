import ij.ImagePlus;
import ij.ImageStack;
import ij.process.AutoThresholder.Method;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import imageprocessing.CSConverter;
import imageprocessing.Util;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.image.BufferedImage;

import test.Test;


public class TestingVariousShit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus image = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/biala-internet/wb010.png"));
		CSConverter.toHSBStack(image);

		ImageStack stack = image.getImageStack();
		
		ImageProcessor red = stack.getProcessor(1);
		ImageProcessor green = stack.getProcessor(2);
		ImageProcessor blue =  stack.getProcessor(3);
		
		red.setAutoThreshold(Method.MaxEntropy, false, ImageProcessor.BLACK_AND_WHITE_LUT);
		green.setAutoThreshold(Method.MaxEntropy, false, ImageProcessor.BLACK_AND_WHITE_LUT);
		blue.setAutoThreshold(Method.MaxEntropy, false, ImageProcessor.BLACK_AND_WHITE_LUT);
		
//		for(int i = 0; i < 32; i++){
//			for (int j = 0; j < 32; j++) {
//				System.out.print(" "+red.getPixelValue(j, i));
//			}
//			System.out.println();
//		}
//		red.getBufferedImage();
//		System.out.println();
//		
//		for(int i = 0; i < 32; i++){
//			for (int j = 0; j < 32; j++) {
//				System.out.print(" "+red.getPixelValue(j, i));
//			}
//			System.out.println();
//		}
		
		Test.showImage(red.getBufferedImage(), "after");
		

	}
		


}
