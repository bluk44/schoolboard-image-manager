package imageprocessing.boundsdetection;

import imageprocessing.Util;
import imageprocessing.color.ColorConversion;
import imageprocessing.matrix.MatrixB;

import java.awt.image.BufferedImage;

import test.Test;

public class DetectionAndTransformTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage imgEdge = Util.readFromFile("images/rect_detection/canny_edges_1.0_5_10/whiteboard02.png");
		imgEdge = ColorConversion.rgb2gray(imgEdge);
		
		BufferedImage img = Util.readFromFile("images/rect_detection/whiteboard02.png");
		
		MatrixB edges = Util.grayToMatrixB(imgEdge, 0);
				
		BoundaryDetector bd = new BoundaryDetector();
		bd.setImageEdge(edges);
		BoardQuadrangle bq = (BoardQuadrangle) bd.detectBestQuadrangle(imgEdge);
		
		BufferedImage transformed = QuadrangleTransformation.transform(img, bq);
		Test.showImage(transformed, "transformed");
		Util.writeToFile("images/rect_detection/rectified/rectified.png", "png", transformed);
		
	}

}
