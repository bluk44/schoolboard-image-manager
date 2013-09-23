package imageprocessing.segmentation;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.ColorSpaceConverter;
import imageprocessing.Filters;
import imageprocessing.Util;
import test.Test;
import testJcomps.RowProfileComp;

public abstract class BackgroundCleaner {
	
	private double gaussianBlurRadius = 10;
	
	public final void run(ImagePlus image){
		correctIllumination(image);
		clearBackground(image);
	}
	
	private void correctIllumination(ImagePlus image){
		RowProfileComp inComp = new RowProfileComp(image.getBufferedImage(), "input", 0);
		Test.showComponent(inComp, "input", 0, 0, inComp.getWidth(), inComp.getHeight());
		
		ColorSpaceConverter.toHSBStack(image);
		
		ImageProcessor brightness = image.getStack().getProcessor(3);
		ImageProcessor blurred = Util.copy(image.getStack().getProcessor(3));
		

		Filters.gauss(blurred, gaussianBlurRadius);
		subBChan(brightness, blurred);
		
		ColorSpaceConverter.toRGB(image);
		RowProfileComp resComp = new RowProfileComp(image.getBufferedImage(), "result", 1);
		Test.showComponent(resComp, "output", 0, 0, inComp.getWidth(), inComp.getHeight());
		
		inComp.selectRow(100);
		resComp.selectRow(100);
 	}
	
	public abstract void clearBackground(ImagePlus image);

	public double getGaussianBlurRadius() {
		return gaussianBlurRadius;
	}

	public void setGaussianBlurRadius(double gaussianBlurRadius) {
		this.gaussianBlurRadius = gaussianBlurRadius;
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
