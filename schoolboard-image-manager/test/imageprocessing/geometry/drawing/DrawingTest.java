package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Line;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import test.Test;
import testJcomps.GeometryComponent;

public class DrawingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage bg = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
//		Point p = new Point(250, 250);
//		Point p2 = new Point(300, 371);
//		DrawablePoint dp = new DrawablePoint(p);
//		DrawablePoint dp2 = new DrawablePoint(p2);
//		DrawableSegment ds = new DrawableSegment(new Segment(11, 45, 23, 67));
//		DrawableLine l1 = new DrawableLine(new Line(14, 100, 55, 80));
//		GeometryComponent gc = new GeometryComponent();
//		gc.setBg(bg);
//		gc.addDrawable(dp);
//		gc.addDrawable(dp2);
//		gc.addDrawable(l1);
//		gc.addDrawable(ds);
//		Util.showComponent(gc, " point ");
		
		Point[] pts = new Point[5];
		pts[0] = new Point(100, 100);
		pts[1] = new Point(140, 100);
		pts[2] = new Point(150, 130);
		pts[3] = new Point(120, 150);
		pts[4] = new Point(90, 130);
		Polygon poly = new Polygon(pts);
		Line l = new Line(96, 120, 99, 120.2);
		
		List<Point> interPoints =  new ArrayList<Point>();
		Geo.intersect(l, poly, interPoints);
		GeometryComponent gc = new GeometryComponent();
		DrawablePolygon dpoly = new DrawablePolygon(poly);
		DrawableLine dline = new DrawableLine(l);
		gc.setBg(bg);
		gc.addDrawable(dpoly);
		gc.addDrawable(dline);
		DrawablePoint[] dps = new DrawablePoint[interPoints.size()];
		for(int i = 0; i < dps.length; i++){
			dps[i] = new DrawablePoint(interPoints.get(i));
			gc.addDrawable(dps[i]);
		}

		Test.showComponent(gc, " polygon line intersection ");

	}

}
