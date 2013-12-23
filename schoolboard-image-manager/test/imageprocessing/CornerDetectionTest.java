package imageprocessing;

import imageprocessing.geometry.Polygon;

import java.awt.Color;
import java.awt.image.BufferedImage;

import test.Test;

public class CornerDetectionTest {

	public static void main(String[] args) {
		BufferedImage test7 = Util.readFromFile("images/text-locating/test7.png");
		BufferedImage test8 = Util.readFromFile("images/text-locating/test8.png");
		BufferedImage test9 = Util.readFromFile("images/text-locating/test9.png");
		BufferedImage test11 = Util.readFromFile("images/text-locating/test11.png");

		Polygon poly = TextLocating.findPerimeter(test11);
		
		System.out.println();		
		System.out.println(poly);
		
	}

}
