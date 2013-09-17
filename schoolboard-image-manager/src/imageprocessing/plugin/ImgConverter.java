package imageprocessing.plugin;

import ij.ImagePlus;
import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import ij.process.StackConverter;
import imageprocessing.Util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImgConverter {

	public static ImageProcessor getImageProcessor(BufferedImage image) {
		int numBands = image.getData().getNumBands();
		ImageProcessor ip = null;
		switch (numBands) {
		case 1:
			ip = new ByteProcessor(image);
			break;
		case 3:
			ip = new ColorProcessor(image);
			break;
		case 4:
			ip = new ColorProcessor(image);
			break;
		}
		ip.setRoi(new Rectangle(image.getWidth(), image.getHeight()));
		return ip;
	}

	public static ImagePlus getImagePlus(BufferedImage image) {
		ImagePlus imp = new ImagePlus("", image);
		return imp;
	}
	
	public static void main(String[] args){
		BufferedImage img = Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png");
		ImagePlus imp = getImagePlus(img);
		System.out.println(imp.getStackSize());
		StackConverter conv = new StackConverter(imp);
		conv.convertToHSBHyperstack();
	}
	
}
