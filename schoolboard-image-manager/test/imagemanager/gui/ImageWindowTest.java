package imagemanager.gui;

import ij.ImageJ;
import ij.ImagePlus;
import imageprocessing.Util;
import imageprocessing.plugin.ij.AWTImageWrapper;

public class ImageWindowTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new ImageJ();
		ImagePlus ip = AWTImageWrapper.toImagePlus(Util.readFromFile("images/blackboard/bb01.png"));
		MyImageWindow window = new MyImageWindow(ip);
		window.setTitle("lulz");

//		window.setLocationAndSize(10, 10, 200, 200);
//		JButton jb = new JButton("lulzy button");
//		jb.setSize(100, 50);
		System.out.println(window.createSubtitle());
	}

}
