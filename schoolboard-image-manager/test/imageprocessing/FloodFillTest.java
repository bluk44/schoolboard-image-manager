package imageprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import test.Test;

public class FloodFillTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedImage image = Util.readFromFile("images/text-locating/regions.png");
		
		FloodFill.run(image, 14, 0, new Color(0,0,0), new Color(128, 128, 128));
		Color white = new Color(255,255,255);
		Color black = new Color(0, 0, 0);
		Color gray = new Color(128,128,128);

		Test.showImage(image, "filled");
		
		for(int i = 0; i < image.getHeight(); i++){
			for (int j = 0; j < image.getWidth(); j++) {
				if(image.getRGB(j, i) == white.getRGB()){
					System.out.print("+");
				} else if(image.getRGB(j, i) == black.getRGB()){
					System.out.print("-");					
				} else if(image.getRGB(j, i) == gray.getRGB()){
					System.out.print("*");					
					
				}
			}
			System.out.println();
		}
		Util.writeToFile("images/text-locating/filled.png", "png", image);
	}
	
	
}
