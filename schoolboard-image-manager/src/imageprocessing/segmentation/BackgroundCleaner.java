package imageprocessing.segmentation;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.CSConverter;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.Filters;
import imageprocessing.Util;

import java.awt.Color;

public abstract class BackgroundCleaner {
	
	private double LUM_CORRECTION_BLUR_RADIUS = 30;
	protected int[] fgColor = new int[3];
	
	public final void run(ImagePlus image){
		correctIllumination(image);
		ImagePlus foreground = image.duplicate();
		separateForeground(foreground);
		assingColors(image, foreground);
	}
	
	private void correctIllumination(ImagePlus image){
		try {
			CSConverter.run(Conversion.COLOR_RGB, image);
			CSConverter.run(Conversion.STACK_HSB, image);
			
			ImageProcessor brightness = image.getStack().getProcessor(3);
			ImageProcessor blurred = Util.copy(image.getStack().getProcessor(3));
			
			Filters.gauss(blurred, LUM_CORRECTION_BLUR_RADIUS);
			subBChan(brightness, blurred);
			
			CSConverter.run(Conversion.COLOR_RGB, image);
		} catch (UnsupportedConversionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	}
	
	public abstract void separateForeground(ImagePlus rgbImage);
	
	public abstract void assingColors(ImagePlus original, ImagePlus foreground);
	
	public double getLumCorrectionBlurRadius() {
		return LUM_CORRECTION_BLUR_RADIUS;
	}

	public void setLumCorrectionBlurRadius(double r) {
		this.LUM_CORRECTION_BLUR_RADIUS = r;
	}
	
	public int[] getFgColor(){
		return fgColor;
	}
	
	public void setFgColor(int[] color){
		fgColor[0] = color[0];
		fgColor[1] = color[1];
		fgColor[2] = color[2];
	}
	
	private void subBChan(ImageProcessor ipBR, ImageProcessor ipGA){
		int w = ipBR.getWidth(), h = ipBR.getHeight();
		
		float[] lum = new float[w*h];
		float[] blur = new float[w*h];
		
		for(int i = 0; i < h; i++){
			for (int j = 0; j < w; j++) {
				lum[i*w+j] = ipBR.getPixelValue(j, i);
			}
		}
		
		for(int i = 0; i < h; i++){
			for (int j = 0; j < w; j++) {
				blur[i*w+j] = ipGA.getPixelValue(j, i);
			}
		}
		
		float mean = 0;
		for (int i = 0; i < blur.length; i++) {
			mean += blur[i];
		}
		mean = Math.round(mean /= blur.length);
		
		for (int i = 0; i < lum.length; i++) {
		float val = lum[i] - blur[i] + mean;
		lum[i] = val < 255 ? val : 255;
		lum[i] = lum[i] > 0 ? lum[i] : 0;
		
	}
	
		for(int i = 0; i < h; i++){
			for(int j = 0; j < w; j++){
				ipBR.putPixelValue(j, i, lum[i*w+j]);
			}
		}
		
	}
}
