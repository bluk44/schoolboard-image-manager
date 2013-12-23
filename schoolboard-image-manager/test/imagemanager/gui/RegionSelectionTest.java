package imagemanager.gui;

import imagemanager.BoardImage;
import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class RegionSelectionTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = Util.readFromFile("images/text-locating/test12.png");
		List<Polygon> polygons = new ArrayList<Polygon>();
		polygons.add(new Polygon(new Point(50, 30), new Point(80, 30), new Point(80, 60), new Point(50, 60)));
		BoardImage bi = new BoardImage(0, null, image);
		bi.createTextRegions(polygons);
		
		BoardImageFrame frame = new BoardImageFrame(bi);
		frame.setVisible(true);
	}

}
