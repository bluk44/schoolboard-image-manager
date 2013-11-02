package imageprocessing.boundsdetection;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mpicbg.ij.InverseTransformMapping;
import mpicbg.models.HomographyModel2D;
import mpicbg.models.IllDefinedDataPointsException;
import mpicbg.models.NotEnoughDataPointsException;
import mpicbg.models.PointMatch;

public final class QuadrangleTransformation {
	
	public static Polygon transform(ImagePlus imp, BoardQuadrangle distorted){
				
		double x1 = (distorted.getLeftVerticalSegment().p1.x + distorted.getLeftVerticalSegment().p2.x) / 2.0;
		double x2 = (distorted.getRightVerticalSegment().p1.x + distorted.getRightVerticalSegment().p2.x) / 2.0;
		double y1 = (distorted.getUpperHorizontalSegment().p1.y + distorted.getUpperHorizontalSegment().p2.y) / 2.0;
		double y2 = (distorted.getLowerHorizontalSegment().p1.y + distorted.getRightVerticalSegment().p2.y) / 2.0;
		
		Polygon fixed = new Polygon(new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2));
		//System.out.println("fixed "+fixed);
		
		HomographyModel2D model = new HomographyModel2D();
		InverseTransformMapping<HomographyModel2D> mapping = new InverseTransformMapping<HomographyModel2D>(model);
		
		ImageProcessor ip = imp.getProcessor();
		ImageProcessor ip2 = ip.duplicate();
		
		mpicbg.models.Point[] p = new mpicbg.models.Point[4], q = new mpicbg.models.Point[4];
		ArrayList<PointMatch> pm = new ArrayList<PointMatch>(4);
		
		for(int i = 0; i < 4; i++){
			p[i] = new mpicbg.models.Point(new float[] {(float)distorted.getPoint(i).x, (float)distorted.getPoint(i).y});
			q[i] = new mpicbg.models.Point(new float[] {(float)fixed.getPoint(i).x, (float)fixed.getPoint(i).y});
			pm.add(new PointMatch(p[i], q[i]));
		}
			try {
				model.fit(pm);
				mapping.map(ip, ip2);
				imp.setProcessor(ip2);
			} catch (NotEnoughDataPointsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllDefinedDataPointsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return fixed;
	}
	
	public static BufferedImage transform(BufferedImage img, Polygon distorted, Polygon fixed){
		
		HomographyModel2D model = new HomographyModel2D();
		InverseTransformMapping<HomographyModel2D> mapping = new InverseTransformMapping<HomographyModel2D>(model);
		
		ImageProcessor ip = Util.convertToImageProcessor(img);
		ImageProcessor ip2 = ip.duplicate();
		mpicbg.models.Point[] p = new mpicbg.models.Point[4], q = new mpicbg.models.Point[4];
		ArrayList<PointMatch> pm = new ArrayList<PointMatch>(4);
		
		for(int i = 0; i < 4; i++){
			p[i] = new mpicbg.models.Point(new float[] {(float)distorted.getPoint(i).x, (float)distorted.getPoint(i).y});
			q[i] = new mpicbg.models.Point(new float[] {(float)fixed.getPoint(i).x, (float)fixed.getPoint(i).y});
			pm.add(new PointMatch(p[i], q[i]));
		}
			try {
				model.fit(pm);
				mapping.map(ip, ip2);
			} catch (NotEnoughDataPointsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllDefinedDataPointsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ip2.getBufferedImage();
	}
}
