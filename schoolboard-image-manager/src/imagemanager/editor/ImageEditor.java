package imagemanager.editor;

import ij.ImagePlus;
import imagemanager.ImageManager;
import imagemanager.model.ImageRecord;
import imagemanager.model.SourceImage;
import imageprocessing.CSConverter;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.EdgeDetection;
import imageprocessing.Util;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.boundsdetection.QuadrangleTransformation;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;
import imageprocessing.matrix.MatrixB;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;

import test.Test;

// invoker
public class ImageEditor {

	private ImageManager imageManager;

	private SourceImage sourceImage;
	private Collection<ImageRecord> boardImages;
	
	private BoundaryDetector detector = new BoundaryDetector();
	
	private static double IM_EDGE_MAX = 400;

	public ImageEditor(int sourceId) {
		this.imageManager = ImageManager.getInstance();
		sourceImage = new SourceImage(sourceId,
				imageManager.getImageById(sourceId));
		boardImages = imageManager.getImagesByBoardId(sourceId);
	}

	// usunac po testach
	public ImageEditor(BufferedImage image) {
		sourceImage = new SourceImage(1, image);
	}

	public void autodetectBoardRegion() {
		try{
			BufferedImage src = sourceImage.getPixels();

			double scale = 0;
			scale = (src.getWidth() > src.getHeight()) ? IM_EDGE_MAX
					/ src.getWidth() : IM_EDGE_MAX / src.getHeight();
			BufferedImage scaled = Util.resize(src, scale);
			ImagePlus ipScaled = AWTImageWrapper.toImagePlus(scaled);

			CSConverter.run(Conversion.GRAY_8, ipScaled);

			EdgeDetection.canny(ipScaled, 1.0, true, 5, 10);
			MatrixB imageEdge = new MatrixB(ipScaled.getBufferedImage());
			detector.setImageEdge(imageEdge);
			BoardQuadrangle bq;
			bq = detector.detectBestQuadrangle();
		
			bq.resize(1 / scale);
			showBoardQuadrangle(bq, src);
			
//			boardPolys.add(bq.getPolygon());
			
//			ImagePlus ip1 = AWTImageWrapper.toImagePlus(src);
//			Polygon fixed = QuadrangleTransformation.transform(ip1, bq);
//			//ip1.show();
//
//			BufferedImage res = Util.subImage(ip1.getBufferedImage(), (int) fixed.getPoint(0).x,
//					(int) fixed.getPoint(0).y, (int) fixed.getPoint(2).x,
//					(int) fixed.getPoint(2).y);
//			
//			Test.showImage(res, "result");

		}  catch (UnsupportedConversionException  e) {
		}  catch(QuadrangleNotFoundException e){
			
		}
	}
	
	public BufferedImage getImagePixels() {
		return sourceImage.getPixels();
	}

	private void showBoardQuadrangle(BoardQuadrangle bq, BufferedImage imEdge) {
		bq.setParams(Color.RED, Color.BLUE, 2, 1);
		bq.draw(imEdge);
		Test.showImage(imEdge, "edges");
	}
}
