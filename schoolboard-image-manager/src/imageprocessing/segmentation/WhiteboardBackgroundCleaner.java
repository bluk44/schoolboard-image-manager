package imageprocessing.segmentation;

import ij.ImagePlus;
import imageprocessing.CSConverter;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.ConnectedRegionsLabeling;
import imageprocessing.ConnectedRegionsLabeling.Region;
import imageprocessing.ConnectedRegionsLabeling.Results;
import imageprocessing.ContrastEnhance;
import imageprocessing.EdgeDetection;
import imageprocessing.Morphology;
import imageprocessing.Morphology.OpType;
import imageprocessing.Morphology.StructElType;
import imageprocessing.Thresholding;

import java.util.Iterator;

public class WhiteboardBackgroundCleaner extends BackgroundCleaner {
	private static double SMOOTHING_SCALE = 2.0;
	private static boolean SUPRESS = true;
	private static double LOWER = 1.0;
	private static double HIGHER = 2.0;

	private static float DILATION_RADIUS = 3.0f;

	@Override
	public void separateForeground(ImagePlus rgbImage) {
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
		// mask.show();
		// System.out.println(mask.getType());

		Morphology.run(mask, OpType.DILATE, StructElType.CIRCLE, DILATION_RADIUS);

		// oznaczanie polaczonych regionow
		Results r = ConnectedRegionsLabeling.run(mask.getProcessor(), 0);

		// wyrownywanie histogramow
		for (Iterator it = r.getAllRegions().iterator(); it.hasNext();) {
			Region reg = (Region) it.next();
			if (reg.getId() == 0)
				continue;
			reg.setImage(rgbImage.getProcessor());
			int[] pixels = reg.getPixels();
			ContrastEnhance.equalizeHistogram(pixels, false);
			Thresholding.IJisodata(pixels);

			reg.setPixels(pixels);
		}
	}

	@Override
	public void assingColors(ImagePlus original, ImagePlus foreground) {
		// count background mean
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

		// for(int i = 0; i < height; i++){
		// for(int j = 0; j < width; j++){
		// if(foreground.getProcessor().getPixelValue(j, i) == 0){
		// // piksel obiektu
		// pix = original.getPixel(j, i);
		// double[] hsv = ColorConversion.rgb2hsv(pix[0], pix[1], pix[2]);
		// int h = (int) Math.round(hsv[0] / 30.0d);
		// hsv[0] = h* 30;
		// hsv[1] = 100;
		// hsv[2] = 100;
		// double[] rgb = ColorConversion.hsv2rgb(hsv[0], hsv[1], hsv[2]);
		//
		// pix[0] = (int) rgb[0];
		// pix[1] = (int) rgb[1];
		// pix[2] = (int) rgb[2];
		//
		// original.getProcessor().putPixel(j, i, pix);
		// } else{
		// // piksel tla
		// original.getProcessor().putPixel(j, i, bgColor);
		// }
		// }
		// }

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(foreground.getProcessor().getPixelValue(j, i) != 0){
					original.getProcessor().putPixel(j, i, bgColor);
				} else {
					original.getProcessor().putPixel(j, i, fgColor);
				}
			} 
		}

	}

	// public void normColor(int[] pixel){
	// int minVal = 255, minIdx = 0;
	// int maxVal = 0, maxIdx = 0;
	//
	// for(int i = 0; i < pixel.length; i++){
	// if(pixel[i] > maxVal){
	// maxVal = pixel[i];
	// maxIdx = i;
	// }
	// if(pixel[i])
	// }
	//
	// }
}
