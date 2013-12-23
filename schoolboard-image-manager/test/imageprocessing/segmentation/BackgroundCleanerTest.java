package imageprocessing.segmentation;

import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.io.File;

import test.Test;

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
	
	//public static String path = "images/bg_remove/czarna-szkola/bb02.png";
	//public static String path = "images/bg_remove/biala-szkola/sk01.png";

	//public static String path = "images/bg_remove/czarna-szkola/bb1.png";
	public static String path = "images/blackboard/bb02.png";
	public static void main(String[] args) {
		//WBCleanerTest();
		//BBCleanerTest();
		BGcleanerTest();
	}

//	public static void WBCleanerTest() {
//		new ImageJ();
//
//		File f = new File(path);
//		if (f.isFile()) {		
//			ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(f));
//			WhiteboardBackgroundCleaner wbCleaner = new WhiteboardBackgroundCleaner();
//			wbCleaner.run(wb);
//			wb.show();
//		} else if (f.isDirectory()) {
//			File[] imageFiles = f.listFiles();
//			for (int i = 0; i < imageFiles.length; i++) {
//
//				if (!imageFiles[i].isFile())
//					continue;
//				ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(imageFiles[i]));
//				WhiteboardBackgroundCleaner wbCleaner = new WhiteboardBackgroundCleaner();
//				wbCleaner.run(wb);
//				Util.writeToFile(path + "/out/" + imageFiles[i].getName(), "png", wb.getBufferedImage());
//				System.out.println(imageFiles[i].getName() + " done");
//			}
//			System.out.println("all done");
//			System.exit(0);
//		}
//
//	}
//
//	public static void BBCleanerTest() {
//		File f = new File(path);
//		
//		if(f.isFile()){
//			boolean end = false;
//			ImagePlus bb = AWTImageWrapper.toImagePlus(Util.readFromFile(f));
//			BlackboardBackgroundCleaner bbCleaner = new BlackboardBackgroundCleaner();
//			Scanner scanner = new Scanner(System.in);
//
//			while(true){
//			double smooth = scanner.nextDouble();
//			if(smooth == -1.0){scanner.close(); return; }
//
//			double low = scanner.nextDouble();
//			double hi = scanner.nextDouble();
//			
//			ImagePlus bb_copy = bb.duplicate();
//			bbCleaner.setSmooth(smooth);
//			bbCleaner.setLow(low);
//			bbCleaner.setHigh(hi);
//			
//			bbCleaner.run(bb_copy);
//			bb_copy.setTitle("s "+smooth+" l "+low+" h "+hi);
//			bb_copy.show();
//			//scanner.next();
//			//bb_copy.changes = false;
//			//bb_copy.close();
//			//scanner.close();
//			}
//		} else if(f.isDirectory()){
//			File[] imageFiles = f.listFiles();
//			for (int i = 0; i < imageFiles.length; i++) {
//
//				if (!imageFiles[i].isFile())
//					continue;
//				ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(imageFiles[i]));
//				BlackboardBackgroundCleaner bbCleaner = new BlackboardBackgroundCleaner();
//				bbCleaner.run(wb);
//				Util.writeToFile(path + "/out/" + imageFiles[i].getName(), "png", wb.getBufferedImage());
//				System.out.println(imageFiles[i].getName() + " done");
//			}
//			System.out.println("all done");
//			System.exit(0);
//		}
//	}
	
	public static void BGcleanerTest(){
		File f = new File(path);
		if (f.isFile()) {		
			//ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(f));
			BufferedImage image = Util.readFromFile(f);
			BufferedImage after = BackgroundCleaner.run(image);
			Test.showImage(after, "after");
			//wb.show();
		} else if (f.isDirectory()) {
//			File[] imageFiles = f.listFiles();
//			for (int i = 0; i < imageFiles.length; i++) {
//
//				if (!imageFiles[i].isFile())
//					continue;
//				ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile(imageFiles[i]));
//
//				Util.writeToFile(path + "/out/" + imageFiles[i].getName(), "png", wb.getBufferedImage());
//				System.out.println(imageFiles[i].getName() + " done");
//			}
//			System.out.println("all done");
//			System.exit(0);
		}
	}
}
