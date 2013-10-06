package test;

import ij.ImagePlus;
import ij.process.ColorProcessor;
import imageprocessing.Util;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.image.BufferedImage;

public class ImageJCopyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedImage bi = Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png");
		//ColorProcessor cp = (ColorProcessor) ImgConverter.getImageProcessor(bi);
		ImagePlus ipl = AWTImageWrapper.toImagePlus(bi);
		ColorProcessor cpl = (ColorProcessor)ipl.getStack().getProcessor(1);
		
		for(int i = 0; i < 100;i ++){
			for(int j = 0; j < 100; j++){
				cpl.set(j, i, 0);
			}
		}
		
		Test.showImage(ipl.getBufferedImage(), "");
	}

}
