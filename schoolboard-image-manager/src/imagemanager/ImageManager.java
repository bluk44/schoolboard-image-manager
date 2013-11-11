package imagemanager;

import ij.process.ImageProcessor;
import imagemanager.gui.ThumbnailComponent;
import imagemanager.model.ImageRecord;
import imagemanager.model.LabelRecord;
import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedHashMap;

import dataaccess.IODatabase;
import dataaccess.IOFileSystem;

public class ImageManager {

	private LinkedHashMap<Integer, LabelRecord> allLabels;
	private LinkedHashMap<Integer, ImageRecord> allImages;
	private LinkedHashMap<Integer, Integer> imagesLabels;

	private IOFileSystem ioFS;
	public IODatabase ioDB;

	private static String DB_HOST_NAME = "127.0.0.1";
	private static String DB_PORT = "3306";
	private static String DB_NAME = "imagedb";

	private static String DB_LOGIN = "imgmanager";
	private static String DB_PASS = "idefix";

	private static ImageManager instance;
	private static final double thumbSize = 0.18;

	private ImageManager() {
		// Connect to database
		ioDB = new IODatabase("jdbc:mysql://" + DB_HOST_NAME + ":" + DB_PORT
				+ "/" + DB_NAME, DB_LOGIN, DB_PASS);
		ioDB.connect();
		allImages = ioDB.importAllImages();
		allLabels = ioDB.importAllLabels();
	}

	public static ImageManager getInstance() {
		if (instance == null)
			instance = new ImageManager();
		return instance;
	}


	public void importImagesFS(String path) {
		File imgDir = new File(path);
		if (!imgDir.isDirectory())
			return;

		// export zdjec do bazy
		File[] imgFiles = imgDir.listFiles();
		for (int i = 0; i < imgFiles.length; i++) {
			if (imgFiles[i].isDirectory())
				continue;
			BufferedImage image = Util.readFromFile(imgFiles[i]);
			BufferedImage thumb = Util.resize(image, thumbSize);
			
			ioDB.exportImage(image, imgFiles[i].getName().split("\\.")[0], 
					imgFiles[i].lastModified(), thumb);
		}
		
		
	}

	public void importRecords() {
		// import miniaturek i etykiet		
		allImages = ioDB.importAllImages();
		
	}

	public LinkedHashMap<Integer, LabelRecord> getAllLabels() {
		return allLabels;
	}

	public LinkedHashMap<Integer, ImageRecord> getAllImages() {
		return allImages;
	}

	public LinkedHashMap<Integer, Integer> getImagesLabels() {
		return imagesLabels;
	}
	
	
}
