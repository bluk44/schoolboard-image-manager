package imageprocessing;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import ij.process.StackConverter;

public class ColorSpaceConverter {
	
	public static void to8bitGray(ImagePlus ip){
		ImageStack stack = getImageStack(ip);
		if(stack != null){
			new StackConverter(ip).convertToGray8();
		} else {
			ImageConverter ic = new ImageConverter(ip);
			ic.convertToGray8();
		}
		
	}
	
	public static void toRGB(ImagePlus ip){
		ImageStack stack = getImageStack(ip);
		if(stack != null){
			new StackConverter(ip).convertToRGB();
		} else {
			ImageConverter ic = new ImageConverter(ip);
			ic.convertToRGB();
		}
	}
	
	public static void toRGBStack(ImagePlus ip){
		ImageStack stack = getImageStack(ip);
		if(stack != null){
			new StackConverter(ip).convertToRGBHyperstack();
		} else {
			ImageConverter ic = new ImageConverter(ip);
			ic.convertToRGBStack();
		}
	}
	
	public static void toHSBStack(ImagePlus ip){
		ImageStack stack = getImageStack(ip);
		if(stack != null){
			new StackConverter(ip).convertToHSBHyperstack();
		} else {
			ImageConverter ic = new ImageConverter(ip);
			ic.convertToHSB();
		}
		
	}
	
	private static ImageStack getImageStack(ImagePlus ip){
		if (ip.getStackSize() > 1)
			return ip.getStack();
		else 
			return null;
	}
}
