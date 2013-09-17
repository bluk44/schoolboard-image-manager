package imageprocessing.plugin.ij;

import ij.ImagePlus;
import ij.ImageStack;
import ij.process.ImageConverter;
import ij.process.StackConverter;
import imageprocessing.plugin.ColorSpacePlugin;
import imageprocessing.plugin.ImgConverter;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class ColorSpacePluginIJ extends ColorSpacePlugin {

	@Override
	public BufferedImage[] getHSBchannels(BufferedImage rgbImage) {
		ImagePlus imp = ImgConverter.getImagePlus(rgbImage);
		ImageConverter ic = new ImageConverter(imp);
		ic.convertToHSB();
		
		BufferedImage[] channels = new BufferedImage[3];
		channels[0] = imp.getStack().getProcessor(1).getBufferedImage();
		channels[1] = imp.getStack().getProcessor(2).getBufferedImage();
		channels[2] = imp.getStack().getProcessor(3).getBufferedImage();
		
		return channels;
	}

	@Override
	public BufferedImage mergeHSBchannels(BufferedImage[] channels) {
		ImageStack stack = new ImageStack(channels[0].getWidth(), channels[0].getHeight());
	
		stack.addSlice("Hue", AWTImageWrapper.toImageProcessor(channels[0]));
		stack.addSlice("Saturation", AWTImageWrapper.toImageProcessor(channels[1]));
		stack.addSlice("Brightness", AWTImageWrapper.toImageProcessor(channels[2]));	
		
		ImagePlus imp = new ImagePlus();
		imp.setStack(stack);
		ImageConverter ic = new ImageConverter(imp);
		ic.convertHSBToRGB();
		
		return imp.getBufferedImage();
	}
	
	
}
