package dataaccess;

import imagemanager.model.ImageRecord;
import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

public class IOFileSystem {
	
	
	private static IOFileSystem instance;
	private static float thumbnailScale = 0.18f;
	
	private IOFileSystem(){}
	
	private final static Logger LOG = Logger.getLogger(IOFileSystem.class
			.getName());
	static { LOG.addAppender(new ConsoleAppender(new SimpleLayout())); }
	
	public static IOFileSystem getInstance(){
		if(instance == null) instance = new IOFileSystem();
		
		return instance;
	}
	
	public BufferedImage readImage(File file){
		return Util.readFromFile(file);
	}
	
	
	private ImageRecord exportToDatabase(File file) throws SQLException{
		String type = file.getPath().split("\\.")[1];
		boolean isValid = false;
		for (String valid : validFormats){
			if(valid.equals(type)){isValid = true; break;}
		}
		
		if(!isValid) return null;
		
		BufferedImage image = Util.readFromFile(file);
		IODatabase ioDB = IODatabase.getInstance();
		ioDB.exportImage(image, file.getName(), file.lastModified(), Util.resize(image, thumbnailScale));
		
		return ioDB.importImageRecord(file.getName());
	}
	
// TODO
//	public void exportImage(BufferedImage image, String fileName, String formatName){
//		String imgPath = imageDir+"/"+fileName;
//		File imgFile = new File(imgPath);
//		try {
//			imgFile.createNewFile();
//			ImageIO.write(image, formatName, imgFile);
//		} catch (IOException e) {
//			System.out.println("[Image export] unable to write file "+imgFile.getName()+" "+e.getMessage());
//			//System.out.println("[Image import] unable to write to "+ newF " "+ e.getMessage() );			
//
//		}
//	}
	
	
}
