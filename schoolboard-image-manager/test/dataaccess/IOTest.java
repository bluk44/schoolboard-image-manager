package dataaccess;

import java.awt.image.BufferedImage;

import test.Test;


public class IOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//BufferedImage image = Util.readFromFile("images/whiteboard/complex_background/wb22_.png");
//		IOFileSystem ioFS = IOFileSystem.getInstance();
		
//		List<ImageRecord> records = ioFS.importImages(new File("images/whiteboard/complex_background/wb22_.png"));
		
//		for (ImageRecord imageRecord : records) {
//			System.out.println(imageRecord);
//		}
		
//		BufferedImage image = Util.readFromFile("images/szkola/sk01.JPG");
//		
//		SourceImage srcImg = new SourceImage("sk01.JPG", 1l, image);
//		srcImg.send();
		
//		IODatabase db = IODatabase.getInstance();
//		
//		Map<Integer, AbstractImage> images = db.importThumbnails();
//		for (AbstractImage image : images.values()) {
//			
//			System.out.println(image);
//			Test.showImage(image.getImagePixels(), image.toString());
//
//		}
//		
		
//		IODatabase.getInstance().deleteImage(1);
		
//		IODatabase.getInstance().addLabel("kurwa");
		importImagePixelsTest(3);
	}
	
	private static void importImagePixelsTest(int id){
		Test.showImage(IODatabase.getInstance().importImagePixels(id), ""+id);
		
	}
}
