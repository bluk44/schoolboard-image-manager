package test;

import ij.Executer;
import ij.ImageJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.Thresholder;
import ij.plugin.frame.ThresholdAdjuster;
import ij.process.ByteProcessor;
import ij.process.ImageConverter;
import ij.process.StackConverter;
import imageprocessing.Thresholding;
import imageprocessing.Util;
import imageprocessing.plugin.ImgConverter;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.image.BufferedImage;

public class ImageJTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImageJ ij = new ImageJ();
//		BufferedImage img = Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png");
//		ImagePlus ip = AWTImageWrapper.toImagePlus(img);
//		ImageConverter ic = new ImageConverter(ip);
//	//	ip.show();
//
//		ic.convertToHSB();
//	
//	
////		StackConverter sc = new StackConverter(ip);
////		sc.convertToGray8();
//		
//		StackConverter sc2 = new StackConverter(ip);
//		sc2.convertToRGB();
//		
//		ImageConverter ic2 = new ImageConverter(ip);
//		ic2.convertToRGB();
//		
//		ip.show();
	}

}
