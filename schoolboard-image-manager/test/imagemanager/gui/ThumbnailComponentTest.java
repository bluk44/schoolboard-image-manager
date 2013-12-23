package imagemanager.gui;

import imagemanager.model.ImageRecord;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.swing.JFrame;

import dataaccess.IODatabase;

public class ThumbnailComponentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IODatabase ioDB = new IODatabase("jdbc:mysql://127.0.0.1:3306/imagedb", "imgmanager", "idefix");
		ioDB.connect();
		LinkedHashMap<Integer, ImageRecord> allImages = ioDB.importThumbnails();
//		System.out.println(allImages.size());
		//ThumbnailComponent button = new ThumbnailComponent(allImages.get(2).getThumbnail());
		ThumbnailComponent button = new ThumbnailComponent(200);
		JFrame testFrame = new JFrame("button test");
		testFrame.add(button);
		testFrame.pack();
		testFrame.setSize(300, 300);
		testFrame.setVisible(true);

		//button.resize(1.5);
	}

}
