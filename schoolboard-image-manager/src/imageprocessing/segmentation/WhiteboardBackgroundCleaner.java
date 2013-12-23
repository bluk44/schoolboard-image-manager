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

import java.awt.Color;
import java.util.Iterator;

/**
 * Oddzielanie tła dla tablicy białej
 * 
 * @author Lucas Budkowski
 *
 */
public class WhiteboardBackgroundCleaner extends BackgroundCleaner {
	
	/*
	 * parametry algorytmu cannyego 
	 */
	private static double SMOOTHING_SCALE = 2.0;
	private static boolean SUPRESS = true;
	private static double LOWER = 1.0;
	private static double HIGHER = 2.0;
	
	/*
	 * promień dylacji (szerokosc pisma) / (wysokosc zdjęcia)
	 */
	static{
		DILATION_RATIO = 4.d / 1000.d;
	}
		
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
		
		calculateDilationRadius(rgbImage.getHeight());
		System.out.println("dilation radius: "+dilation);

		Morphology.run(mask, OpType.DILATE, StructElType.CIRCLE, dilation);

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
	protected Color[] assingColors(ImagePlus original, ImagePlus foreground) {
		
		foreground.show();
		
		int[] fgColor = new int[3];
		
		fgColor[0] = 0;
		fgColor[1] = 0;
		fgColor[2] = 0;
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

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if(foreground.getProcessor().getPixelValue(j, i) != 0){
					original.getProcessor().putPixel(j, i, bgColor);
				} else {
					original.getProcessor().putPixel(j, i, fgColor);
				}
			} 
		}
		
		Color[] colors= new Color[2];
		colors[0] = new Color(fgColor[0], fgColor[1], fgColor[2]);
		colors[1] = new Color(bgColor[0], bgColor[1], bgColor[2]);
		
		return colors;
	}

}
