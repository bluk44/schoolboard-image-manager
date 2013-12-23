package imageprocessing;

import imagemanager.gui.ImageComponent;
import imageprocessing.TextLocating;
import imageprocessing.Util;
import imageprocessing.geometry.Polygon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import test.Test;

public class TextLocatingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage img = Util.readFromFile("images/text-locating/bin.png");
		List<Polygon> polys = TextLocating.findTextPolygons(img, Color.BLUE, Color.WHITE);
		ImageComponent ic = new ImageComponent(img);

		for (Polygon polygon : polys) {
			polygon.setColor(Color.RED);
			ic.addShape(polygon);
		}
		
		Test.showComponent(ic, "lulz");
		System.out.println(polys.size());
		
	}

}
