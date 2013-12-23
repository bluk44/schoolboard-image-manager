package imageprocessing;

import imageprocessing.Labeling4.Region;

import java.awt.image.BufferedImage;
import java.util.List;

import test.Test;

public class LabelingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = Util.readFromFile("images/text-locating/regions.png");
		//BufferedImage image = Util.readFromFile("images/text-locating/test10.png");

		List<Region> regions = Labeling4.run(image);
		
		System.out.println(regions.size());
		
		
		for (Region region : regions) {
			System.out.println("x "+region.minX+" y "+region.minY+" h "+region.getHeight()+" w "+region.getWidth());
			
		}
//		Test.showImage(regions.get(3).image, "");
//
//		Test.showImage(regions.get(4).image, "");
		
	}

}
