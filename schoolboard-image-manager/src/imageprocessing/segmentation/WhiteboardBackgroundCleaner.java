package imageprocessing.segmentation;

import java.util.Iterator;

import ij.ImagePlus;
import imageprocessing.CSConverter;
import imageprocessing.ConnectedRegionsLabeling;
import imageprocessing.ContrastEnhance;
import imageprocessing.EdgeDetection;
import imageprocessing.Morphology;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.ConnectedRegionsLabeling.Region;
import imageprocessing.ConnectedRegionsLabeling.Results;
import imageprocessing.Morphology.OpType;
import imageprocessing.Morphology.StructElType;
import imageprocessing.Thresholding;


public class WhiteboardBackgroundCleaner extends BackgroundCleaner {
	private static double SMOOTHING_SCALE = 2.0;
	private static boolean SUPRESS = true;
	private static double LOWER = 1.0;
	private static double HIGHER = 2.0;
	
	private static float DILATION_RADIUS = 3.0f;
	
	@Override
	public void clearBackground(ImagePlus rgbImage) {
		// zamiana na 8 bit gray
				try {
					CSConverter.run(Conversion.GRAY_8, rgbImage);
				} catch (UnsupportedConversionException e) {
					System.out.println("conversion failed, terminating... ");
					return;
				}
				
				// kopiowanie
				ImagePlus mask = rgbImage.duplicate();
				
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
					reg.setImage(rgbImage.getProcessor());
					int[] pixels = reg.getPixels();
					ContrastEnhance.equalizeHistogram(pixels, false);
					Thresholding.IJisodata(pixels);
					
					reg.setPixels(pixels);
				}
	}
	
}
