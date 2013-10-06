package imageprocessing.segmentation;

import test.Test;
import ij.ImagePlus;
import ij.plugin.filter.Binary;
import ij.process.ImageProcessor;
import imageprocessing.Util;
import imageprocessing.plugin.ij.AWTImageWrapper;

public class BackgroundCleanerTest {
	
	public static void main(String[] args){
		//WBCleanerTest();
		BBCleanerTest();
	}
	
	public static void WBCleanerTest(){
		ImagePlus wb = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/biala-internet/wb010.png"));
		
		WhiteboardBackgroundCleaner wbCleaner = new WhiteboardBackgroundCleaner();
		wbCleaner.run(wb);
		
		wb.show();
	}
	
	public static void BBCleanerTest(){
		ImagePlus bb = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png"));
		BlackboardBackgroundCleaner bbCleaner = new BlackboardBackgroundCleaner();
		bbCleaner.run(bb);
//		
//		Binary binary = new Binary();
//		bb.show();
//		binary.setup("close", bb);
//		try{
//			System.in.read();
//		} catch(Exception ex){
//			
//		}
//		ImageProcessor ip = bb.getProcessor();
//		binary.run(ip);
//		bb.setProcessor(ip);
//		//Test.showImage(ip.getBufferedImage(), "after");
//		System.out.println("done...");
		//bb.show();
		Util.writeToFile("out.png", "png", bb.getBufferedImage());
		
	}
}
