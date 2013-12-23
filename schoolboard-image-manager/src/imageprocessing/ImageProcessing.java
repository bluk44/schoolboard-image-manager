package imageprocessing;

import ij.ImagePlus;
import imagemanager.BoardImage;
import imagemanager.TextRegion;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.boundsdetection.QuadrangleTransformation;
import imageprocessing.geometry.Polygon;
import imageprocessing.matrix.MatrixB;
import imageprocessing.plugin.ij.AWTImageWrapper;
import imageprocessing.segmentation.BackgroundCleaner;
import imageprocessing.segmentation.BlackboardBackgroundCleaner;
import imageprocessing.segmentation.WhiteboardBackgroundCleaner;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class ImageProcessing {
	
	private static final double CANNY_BLUR = 1.0;
	private static final double CANNY_LOWER_THRESHOLD = 5.0;
	private static final double CANNY_HIGHER_THRESHOLD = 5.0;
	private static final boolean CANNY_SUPRESS = true;
	
		
	private ImageProcessing() {
	};

	public static BoardQuadrangle findBoardRegion(BufferedImage source)
			throws QuadrangleNotFoundException {
		try {
			ImagePlus ip = AWTImageWrapper.toImagePlus(source);
			CSConverter.run(Conversion.GRAY_8, ip);
			EdgeDetection.canny(ip, CANNY_BLUR, CANNY_SUPRESS, CANNY_LOWER_THRESHOLD, CANNY_HIGHER_THRESHOLD);
			MatrixB imageEdge = new MatrixB(ip.getBufferedImage());

			BoundaryDetector detector = new BoundaryDetector();
			detector.setImageEdge(imageEdge);

			return detector.detectBestQuadrangle();
		} catch (UnsupportedConversionException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage extractBoardRegion(BufferedImage source,
			Polygon distorted, Polygon fixed, Color[] colors) {
		
     	BufferedImage trans = QuadrangleTransformation.transform(source, distorted, fixed);
		BufferedImage bRegion = Util.subImage(trans, (int) fixed.getPoint(0).x,
				(int) fixed.getPoint(0).y, (int) fixed.getPoint(2).x,
				(int) fixed.getPoint(2).y);

		 return BackgroundCleaner.run(bRegion, colors);
	}
		
	public static List<Polygon> findTextRegions(BufferedImage image, Color bgColor, Color fgColor){
     	List<Polygon> polys = TextLocating.findTextPolygons(image, bgColor, fgColor);
     	
     	return polys;
	}

}
