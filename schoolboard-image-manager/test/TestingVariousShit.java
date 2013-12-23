import imagemanager.gui.ImageComponent;
import imageprocessing.ImageProcessing;
import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import test.Test;

public class TestingVariousShit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = Util
				.readFromFile("images/bg_remove/czarna-szkola/bb1.png");
		Polygon dis = new Polygon(new Point(10, 10), new Point(
				image.getWidth() - 10, 10), new Point(image.getWidth() - 10,
				image.getHeight() - 10), new Point(10, image.getHeight() - 10));
		Color[] colors = new Color[2];

		BufferedImage result = ImageProcessing.extractBoardRegion(image, dis,
				dis, colors);
		List<Polygon> polys = ImageProcessing.findTextRegions(result,
				colors[1], colors[0]);

		ImageComponent imc = new ImageComponent(result);

		for (Polygon polygon : polys) {
			polygon.setColor(Color.BLUE);
			polygon.setRad(0);
			System.out.println(polygon.getClass());
			imc.addShape(polygon);
		}

		Test.showComponent(imc, "lulz");
	}

	class MyObj {

		public int hashCode = 0;

		@Override
		public int hashCode() {
			return hashCode;
		}
	}
}