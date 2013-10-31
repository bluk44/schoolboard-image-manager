package imageprocessing.segmentation;

import java.util.Iterator;

import ij.ImagePlus;
import imageprocessing.CSConverter;
import imageprocessing.ContrastEnhance;
import imageprocessing.EdgeDetection;
import imageprocessing.Morphology;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.ConnectedRegionsLabeling;
import imageprocessing.ConnectedRegionsLabeling.Region;
import imageprocessing.ConnectedRegionsLabeling.Results;
import imageprocessing.Morphology.OpType;
import imageprocessing.Morphology.StructElType;
import imageprocessing.Thresholding;

public class BlackboardBackgroundCleaner extends BackgroundCleaner {
	
	//private static int MIN_REG_SIZE = 50;
	
	private static double SMOOTHING_SCALE = 2.0;
	private static boolean SUPRESS = true;
	private static double LOWER = 5.0;
	private static double HIGHER = 10.0;
	
	private static float DILATION_RADIUS = 7.0f;
	
	@Override
	public void separateForeground(ImagePlus image) {
		
		try{
			CSConverter.run(Conversion.GRAY_8, image);
			// kopiowanie
			ImagePlus mask = image.duplicate();
			
			// utworzenie maski
			EdgeDetection.canny(mask, SMOOTHING_SCALE, SUPRESS, LOWER, HIGHER);
			//mask.show();
			//System.out.println(mask.getType());
			
			Morphology.run(mask, OpType.DILATE, StructElType.CIRCLE, DILATION_RADIUS);
					
			// oznaczanie polaczonych regionow
			Results r = ConnectedRegionsLabeling.run(mask.getProcessor(), 0);
			
			// wyrownywanie histogramow
			for (Iterator it = r.getAllRegions().iterator(); it.hasNext();) {
				Region reg = (Region) it.next();
				if(reg.getId() == 0) continue;
				reg.setImage(image.getProcessor());
				int[] pixels = reg.getPixels();
				ContrastEnhance.equalizeHistogram(pixels, false);
				Thresholding.IJisodata(pixels);
				
				reg.setPixels(pixels);
			}
			
		} catch(UnsupportedConversionException e){
			System.out.println("conversion to 8 bit gray failed");
		}
		
//		try {
//		CSConverter.run(Conversion.GRAY_8, image);
//		Thresholding.maxEntropy(image);
//		
//		Results r = ConnectedRegionsLabeling.run(image.getProcessor(), 0);
//		for (Iterator iterator = r.getAllRegions().iterator(); iterator.hasNext();) {
//			Region reg = (Region) iterator.next();
//			if(reg.getSize() > MIN_REG_SIZE) reg.setPixels(128);
//			//reg.setPixels(50);
//		}
//	} catch (UnsupportedConversionException e) {
//		e.printStackTrace();
//	}
	
	}

	@Override
	public void assingColors(ImagePlus original, ImagePlus foreground) {
		
		double[] bgMean = new double[3];
		int[] pix;
		int width = original.getWidth(), height = original.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				pix = original.getPixel(j, i);
				bgMean[0] += pix[0];
				bgMean[1] += pix[1];
				bgMean[2] += pix[2];
			}
		}

		bgMean[0] /= width * height;
		bgMean[1] /= width * height;
		bgMean[2] /= width * height;

		int[] bgColor = new int[3];
		bgColor[0] = (int) Math.round(bgMean[0]);
		bgColor[1] = (int) Math.round(bgMean[1]);
		bgColor[2] = (int) Math.round(bgMean[2]);
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(foreground.getProcessor().getPixelValue(j, i) != 255){
					original.getProcessor().putPixel(j, i, bgColor);
				} else {
					original.getProcessor().putPixel(j, i, fgColor);
				}
			}
		}
		
	}

}
