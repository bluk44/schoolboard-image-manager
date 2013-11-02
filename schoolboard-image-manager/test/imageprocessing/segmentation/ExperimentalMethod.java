package imageprocessing.segmentation;

import ij.ImageJ;
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
import imageprocessing.Util;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.io.File;
import java.util.Iterator;

public class ExperimentalMethod {
	
	private static double SMOOTHING_SCALE = 2.0;
	private static boolean SUPRESS = true;
	private static double LOWER = 1.0;
	private static double HIGHER = 2.0;
	
	private static float DILATION_RADIUS = 5.0f;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImageJ();
		
		// TODO automatyczne stworzenie maski
		// TODO zwiekszenie kontrastu -> mnożenie zdjecia przez maske, zwiekszanie kontrastu 
		
		// read dilated binary image
		//File f2 = new File("images/bg_remove/biala-szkola/eq_light/gauss/high_contrast_area.png");
		//File f2 = new File("images/bg_remove/biala-szkola/eq_light/gauss/sk03.png");
		//File f2 = new File("images/bg_remove/biala-internet/eq_light/gauss/wb29.png");
		File f2 = new File("images/bg_remove/biala-internet/gauss/result/wb04.png");
		
		// zdjecie wejsciowe
		ImagePlus inputImage = AWTImageWrapper.toImagePlus(Util.readFromFile(f2));
		
		// zamiana na 8 bit gray
		try {
			CSConverter.run(Conversion.GRAY_8, inputImage);
		} catch (UnsupportedConversionException e) {
			System.out.println("conversion failed, terminating... ");
			return;
		}
		
		// kopiowanie
		ImagePlus mask = inputImage.duplicate();
		
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
			reg.setImage(inputImage.getProcessor());
			int[] pixels = reg.getPixels();
			ContrastEnhance.equalizeHistogram(pixels, false);
			Thresholding.IJisodata(pixels);
			
			reg.setPixels(pixels);
		}
		
		inputImage.show();
	}
	
	

}
