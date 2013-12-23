package imagemanager;

import imagemanager.BoardImage.BoardImageCreateException;
import imageprocessing.Util;
import imageprocessing.geometry.Polygon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import dataaccess.IODatabase;

public class SourceImage extends AbstractImage{
	private static String[] VALID_FORMATS = {"png", "jpg", "JPG", "gif", "bmp"};
	
	private String name;
	protected Long timeStamp;
	

	private Map<Integer, BoardImage> boardImages = new HashMap<Integer, BoardImage>();
	private SourceImage(){};
	
	public static SourceImage readImage(File file) throws ImageReadException{
		String ext = file.getName().split("\\.")[1];
		boolean valid = false;
		for (String str : VALID_FORMATS) {
			if(str.equals(ext)){
				valid = true;
				break;
			}
		}
		
		if(!valid) throw new ImageReadException(file.getName()+" image format not supported");
		BufferedImage imagePixels = Util.readFromFile(file);
		if(imagePixels == null) throw new ImageReadException(file.getName()+" image unable to read image");
		BufferedImage thumbPixels = Util.resize(imagePixels, THUMB_SCALE);	
		
		SourceImage inst = new SourceImage();
		inst.name = file.getName();
		inst.timeStamp = file.lastModified();
		inst.imagePixels = imagePixels;
		inst.thumbnailPixels = thumbPixels;
		
		Integer id = IODatabase.getInstance().exportNewSourceImage(inst);
		if(id == null ) throw new ImageReadException(file.getName()+" unable to export to database");
		inst.id = id;
		
		inst.disposeContent();
		
		return inst;
	}
	
	public static SourceImage createThumb(Integer id, String name, Long timeStamp, BufferedImage thumbnail){
		SourceImage inst = new SourceImage();
		inst.id = id;
		inst.name = name;
		inst.timeStamp = timeStamp;
		inst.thumbnailPixels = thumbnail;
		
		return inst;
	}
	
	public void send(){
		
	}
	
	public String getName() {
		return name;
	}
	
	public Long getTimestamp(){
		return timeStamp;
	}
	
	public BoardImage getBoardImage(Integer id){	
		return boardImages.get(id);
	}
		
	public void findBoardRegion(){
		// TODO
		return;
	}
	
	public void selectBoardRegion(Polygon sourceQuadrangle){
		try {
			BoardImage newBoardImage  = BoardImage.createBoardImage(this, sourceQuadrangle);
			boardImages.put(newBoardImage.getId(), newBoardImage);
			
		} catch (BoardImageCreateException e) {}
		
	}
		
	public void deleteBoardRegion(Integer id){
		boardImages.remove(id);
	}

	public Collection<BoardImage> getBoardImages(){
		return boardImages.values();
	}
	
	@Override
	public void importContent() {
		BufferedImage bigImage = IODatabase.getInstance().importImagePixels(getId());
		this.imagePixels = bigImage;
	}

	@Override
	public void disposeContent() {
		this.imagePixels = null;
	}
	
	@Override
	public String toString() {
		return "SourceImage [id=" + id + ", name=" + name + "]";
	}
	
	public static class ImageReadException extends Exception{
		public String message;
		
		public ImageReadException(String message){
			this.message = message;
		}
		
		@Override
		public String getMessage() {
			return message;
		}
	}
} 
