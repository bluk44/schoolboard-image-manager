package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.image.BufferedImage;

import test.Test;

public class DrawingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage canvas = new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR);
	
//		Line l1 = new Line(new Point(1,1), new Point(2,2));
//		l1.resize(2);
//		l1.setColor(Color.RED);
//		l1.setRad(2);
//		l1.draw(canvas);
		
//		Segment s1 = new Segment(new Point(10,20), new Point(20, 40));
//		s1.resize(5);
//		s1.draw(canvas);
		
		Polygon troly = new Polygon(new Point(50,50), new Point(60,55), new Point(57, 77), new Point(50, 77));
		troly.draw(canvas);
		troly.resize(2);
		troly.draw(canvas);
		troly.resize(2);
		troly.draw(canvas);		
		Test.showImage(canvas, "shape");
	}

}
