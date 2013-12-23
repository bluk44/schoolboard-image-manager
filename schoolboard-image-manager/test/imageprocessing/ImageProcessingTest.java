package imageprocessing;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.boundsdetection.QuadrangleTransformation;
import imageprocessing.geometry.Polygon;
import imageprocessing.plugin.ij.AWTImageWrapper;
import imageprocessing.segmentation.BackgroundCleaner;
import imageprocessing.segmentation.WhiteboardBackgroundCleaner;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class ImageProcessingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage test5 = Util
				.readFromFile("images/text-locating/test5.png");
		
	}
}
