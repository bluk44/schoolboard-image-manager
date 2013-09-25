package imageprocessing.segmentation;

import ij.ImagePlus;
import imageprocessing.CSConverter;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.Thresholding;

public class BlackboardBackgroundCleaner extends BackgroundCleaner {

	@Override
	public void clearBackground(ImagePlus image) {
		
		try {
			CSConverter.run(Conversion.GRAY_8, image);
			Thresholding.maxEntropy(image);
		} catch (UnsupportedConversionException e) {
			e.printStackTrace();
		}
	}
}
