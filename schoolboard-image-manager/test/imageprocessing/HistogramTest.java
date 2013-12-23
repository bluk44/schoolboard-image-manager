package imageprocessing;

import java.awt.image.BufferedImage;

public class HistogramTest {

	public static void main(String[] args){
		BufferedImage img = Util.readFromFile("images/bg_remove/czarna-szkola/bb6.png");
		Histogram h = new Histogram(img);
		System.out.println(h.getTypeString());
		System.out.println(h.getMean(0));
		System.out.println(h.getMean(1));
		System.out.println(h.getMean(2));
		
		System.out.println(h.getTotalMean());
	}
}
