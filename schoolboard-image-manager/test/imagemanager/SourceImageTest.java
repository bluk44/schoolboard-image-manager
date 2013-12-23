package imagemanager;

import imagemanager.SourceImage.ImageReadException;

import java.io.File;
import java.util.Collection;

import dataaccess.IODatabase;

import test.Test;

public class SourceImageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		readNewImageTest("images/blackboard/bb03.png"); // OK
		//readThumbnailsTest();						  // OK
		//readContentTest();						// OK
	}
	
	public static void readNewImageTest(String filename){
		try {
			SourceImage newImage = SourceImage.readImage(new File(filename));
			System.out.println(newImage);
			
		} catch (ImageReadException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void readThumbnailsTest(){
		Collection<SourceImage> srcImages = IODatabase.getInstance().importSourceThumbnails();
		
		for (SourceImage image : srcImages) {
			System.out.println(image);
			Test.showImage(image.getThumbnailPixels(), "thumb");
		}
	}
	
	public static void readContentTest(){
		Collection<SourceImage> srcImages = IODatabase.getInstance().importSourceThumbnails();
		
		for (SourceImage image : srcImages) {
			image.importContent();
			Test.showImage(image.getImagePixels(), "lulz");
		}
		
		//Test.showImage(IODatabase.getInstance().importImagePixels(9), title)
	}
	
	
}

