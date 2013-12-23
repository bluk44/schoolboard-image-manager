package imagemanager;

import java.awt.image.BufferedImage;

public abstract class AbstractImage {
	
	protected static float THUMB_SCALE = 0.15f;

	
	protected Integer id;
	
	protected BufferedImage imagePixels;
	protected BufferedImage thumbnailPixels;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
		
	public BufferedImage getThumbnailPixels() {
		return thumbnailPixels;
	}
	
	public BufferedImage getImagePixels(){
		return imagePixels;
	}
	
	public abstract void importContent();
	
	public abstract void disposeContent();
	
	
}
