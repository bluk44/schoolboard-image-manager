package imageprocessing.geometry;

import java.awt.Color;
import java.awt.image.BufferedImage;

import test.Test;

public class DrawSegmentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Segment s = new Segment(new Point(1,1), new Point(5, 70));
		s.setColor(Color.RED);
		BufferedImage canvas = new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR);
		
		s.draw(canvas);
		Test.showImage(canvas, "image");
	}

}
