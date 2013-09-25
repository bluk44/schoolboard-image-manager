package imageprocessing.segmentation;

import ij.ImagePlus;
import ij.plugin.Thresholder;
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
		
		bb.show();
	}
}
