package dataaccess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageIOFileSystem {
	
	private String imageDir = null;
	
	public ImageIOFileSystem(String imageDir){
		this.imageDir = imageDir;
	}
	
	public Map<String, BufferedImage> importImages(){
		File imgDir = new File(imageDir);
		
		if(!imgDir.isDirectory()) return null;
		File[] imgFiles = imgDir.listFiles();
		Map<String, BufferedImage> imageMap = new HashMap<String, BufferedImage>();
		for(int i = 0; i < imgFiles.length; i++){
			if(!imgFiles[i].isFile()) continue;
			try {
					BufferedImage image = ImageIO.read(imgFiles[i]); 
					imageMap.put(imgFiles[i].getName(), image); 
				}catch (IOException e){
				System.out.println("[Image import] unable to read from " + imgFiles[i].getName() + " "+ e.getMessage());
			}
		}
		
		return imageMap;
	}
	
	public BufferedImage importImage(String fileName){
		BufferedImage image = null;
		String imgPath = imageDir+"/"+fileName;
		File imgFile = new File(imgPath);
		try { image = ImageIO.read(imgFile); } catch (IOException e) {
			System.out.println("[Image import] unable to read from " + imgFile.getName() + " "+ e.getMessage() );			
		}
		
		return image;
	}
	
	public void exportImage(BufferedImage image, String fileName, String formatName){
		String imgPath = imageDir+"/"+fileName;
		File imgFile = new File(imgPath);
		try {
			imgFile.createNewFile();
			ImageIO.write(image, formatName, imgFile);
		} catch (IOException e) {
			System.out.println("[Image export] unable to write file "+imgFile.getName()+" "+e.getMessage());
			//System.out.println("[Image import] unable to write to "+ newF " "+ e.getMessage() );			

		}
	}
}
