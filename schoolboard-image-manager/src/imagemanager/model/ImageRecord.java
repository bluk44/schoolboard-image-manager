package imagemanager.model;

import java.awt.image.BufferedImage;

public class ImageRecord {

	private Integer id;
	private String name;
	private BufferedImage thumbnail;
	
	public ImageRecord(Integer id, String name, BufferedImage thumbnail ) {
		super();
		this.id = id;
		this.name = name;
		this.thumbnail = thumbnail;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public BufferedImage getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(BufferedImage thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "ImageRecord [id=" + id + ", name=" + name + "]";
	}
	
	
}
