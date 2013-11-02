package imageprocessing.segmentation;

import imageprocessing.Util;
import imageprocessing.color.ColorConversion;

import java.awt.image.BufferedImage;
import java.util.Scanner;

import test.Test;
import testJcomps.RowProfileComp;

public class BackgroundExtractionTest {
	static String[] inputFiles = {"wb01.png", "wb02.png", "wb03.png", "wb04.png", "wb05.png", 
									"wb06.png", "wb07.png", "wb08.png", "wb09.png", "wb10.png"};
	
	static String[] bgFiles = {"bgwb01.png", "bgwb02.png", "bgwb03.png", "bgwb04.png", "bgwb05.png", 
		"bgwb06.png", "bgwb07.png", "bgwb08.png", "bgwb09.png", "bgwb10.png"};
	
	public static void main(String[] args) {
//			String inputPath = "images/bg_remove/czarna-szkola/";
//			String bgPath = "images/bg_remove/czarna-szkola/gauss/bg/";

			String inputPath = "images/bg_remove/biala-internet/";
			String bgPath = "images/bg_remove/biala-internet/gauss/bg/";
			int i = 5;
			System.out.println("start...");
			BufferedImage img = Util.readFromFile(inputPath+"wb0"+i+".png");
			BufferedImage background = Util.readFromFile(bgPath+"bgwb0"+i+".png");
			BufferedImage result = ColorConversion.subtractBackground(img, background);
			System.out.println("done...");
		//	Test.showImage(result, "result");
		//	Util.writeToFile("images/bg_remove/czarna-szkola/bg_removed/bb0"+i+".png", "png", result);

			RowProfileComp inComp = new RowProfileComp(img, "input", 1);
			RowProfileComp bgComp = new RowProfileComp(background, "background", 0);
			RowProfileComp resComp = new RowProfileComp(result, "result", 1);
			
		//	Test.showComponent(inComp, "input", 0, 0, inComp.getWidth(), inComp.getHeight());
		//	Test.showComponent(bgComp, "background", inComp.getWidth(), 0, inComp.getWidth(), inComp.getHeight());
		//	Test.showComponent(resComp, "result", inComp.getWidth()*2, 0, inComp.getWidth(), inComp.getHeight());
			
			
			int ii = 0;
			Scanner scanner = new Scanner(System.in);
			while(ii >= 0){
				ii = scanner.nextInt();
				System.out.println(ii);
				inComp.selectRow(ii);
				bgComp.selectRow(ii);
				resComp.selectRow(ii);
			}
			scanner.close();
			
//			Test.showImage(img, "input");
//			Test.showImage(background, "input");
//			Test.showImage(result, "output");

		
	}
}