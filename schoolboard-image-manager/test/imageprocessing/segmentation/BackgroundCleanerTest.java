package imageprocessing.segmentation;

import ij.ImageJ;
import ij.ImagePlus;
import imageprocessing.Util;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.io.File;

public class BackgroundCleanerTest {
	
	// optymalne ustawienia dla bialej tablicy
	
	// LUM_CORRECTION_RADIUS = 30;
	//
	// SMOOTHING_SCALE = 2.0;
	// SUPRESS = true;
	// LOWER = 1.0;
	// HIGHER = 2.0;
	//
	// DILATION_RADIUS = 3.0f;
	
	// optymalne ustawienia dla czarnej tablicy
	
	// LUM_CORRECTION_RADIUS = 30;
	//
	// SMOOTHING_SCALE = 2.0;
	// SUPRESS = true;
	// LOWER = 10.0;
	// HIGHER = 20.0;
	//
	// DILATION_RADIUS = 7.0f;
	
	public static String path = "images/bg_remove/biala-szkola/";

	public static void main(String[] args) {
		WBCleanerTest();
		//BBCleanerTest();
	}

	public static void WBCleanerTest() {
		new ImageJ();

		File f = new File(path);
		if (f.isFile()) {
			ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(f));
			WhiteboardBackgroundCleaner wbCleaner = new WhiteboardBackgroundCleaner();
			wbCleaner.run(wb);
			wb.show();
		} else if (f.isDirectory()) {
			File[] imageFiles = f.listFiles();
			for (int i = 0; i < imageFiles.length; i++) {

				if (!imageFiles[i].isFile())
					continue;
				ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(imageFiles[i]));
				WhiteboardBackgroundCleaner wbCleaner = new WhiteboardBackgroundCleaner();
				wbCleaner.setFgColor(new int[]{0, 0, 0});
				wbCleaner.run(wb);
				Util.writeToFile(path + "/out/" + imageFiles[i].getName(), "png", wb.getBufferedImage());
				System.out.println(imageFiles[i].getName() + " done");
			}
			System.out.println("all done");
			System.exit(0);
		}

	}

	public static void BBCleanerTest() {
		File f = new File(path);
		if(f.isFile()){
			ImagePlus bb = AWTImageWrapper.toImagePlus(Util.readFromFile(f));
			BlackboardBackgroundCleaner bbCleaner = new BlackboardBackgroundCleaner();
			bbCleaner.run(bb);
			bb.show();
			new ImageJ();
		} else if(f.isDirectory()){
			File[] imageFiles = f.listFiles();
			for (int i = 0; i < imageFiles.length; i++) {

				if (!imageFiles[i].isFile())
					continue;
				ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(imageFiles[i]));
				BlackboardBackgroundCleaner bbCleaner = new BlackboardBackgroundCleaner();
				bbCleaner.setFgColor(new int[]{255, 255, 255});
				bbCleaner.run(wb);
				Util.writeToFile(path + "/out/" + imageFiles[i].getName(), "png", wb.getBufferedImage());
				System.out.println(imageFiles[i].getName() + " done");
			}
			System.out.println("all done");
			System.exit(0);
		}
	}
}
