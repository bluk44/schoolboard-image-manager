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
	public void clearBackground(ImagePlus image) {
		
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

}
