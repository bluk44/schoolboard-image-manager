package imageprocessing.segmentation;

import ij.ImagePlus;
import imageprocessing.CSConverter;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.Thresholding;


public class WhiteboardBackgroundCleaner extends BackgroundCleaner {

	@Override
	public void clearBackground(ImagePlus rgbImage) {

		
		try{
			CSConverter.run(Conversion.STACK_RGB, rgbImage);
			Thresholding.maxEntropy(rgbImage);
			CSConverter.run(Conversion.COLOR_RGB, rgbImage);
		} catch(UnsupportedConversionException ex){
			ex.printStackTrace();
		}
	}
	
}
