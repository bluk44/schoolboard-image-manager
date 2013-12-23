package imageprocessing.geometry;

import java.awt.image.BufferedImage;

import test.Test;

public class DrawLineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Line line = new Line(new Point(10, 7), new Point(11 , 2));
//		
		Line ox = new Line(new Point(20, 0), new Point(40, 20));
		System.out.println(ox);
		
		BufferedImage image = new BufferedImage(400, 400, BufferedImage.TYPE_3BYTE_BGR);
		
		ox.draw(image);
		Test.showImage(image, "line");
	}

}
