package dataaccess;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import test.Test;

public class ImageIOTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ImageIOFileSystem iof = new ImageIOFileSystem("images/bg_remove/biala-szkola");
//		Map<String, BufferedImage> imgMap = iof.importImages();
//		Collection<Entry<String, BufferedImage>> es = imgMap.entrySet();
//		
		
		ImageIODatabase iod = new ImageIODatabase("jdbc:mysql://127.0.0.1:3306/image_test", "imgmanager","idefix");
		iod.connect();
		
//		for (Iterator<Entry<String, BufferedImage>> iterator = es.iterator(); iterator.hasNext();) {
//			Entry<String, BufferedImage> e = (Entry<String, BufferedImage>) iterator.next();
//			iod.exportImage(e.getValue(), e.getKey());
//		}
//		
		
		Map<String, BufferedImage> map= iod.importImages("sk01.png");
		Collection<Entry<String, BufferedImage>> es = map.entrySet();
		
		for (Iterator<Entry<String, BufferedImage>> iterator = es.iterator(); iterator.hasNext();) {
			Entry<String, BufferedImage> e = (Entry<String, BufferedImage>) iterator.next();
			Test.showImage(e.getValue(), e.getKey());
		}
		
	}
	
}
