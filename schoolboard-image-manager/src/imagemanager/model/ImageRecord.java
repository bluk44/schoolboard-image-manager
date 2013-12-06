package imagemanager.model;

import java.awt.image.BufferedImage;

public class ImageRecord {

	private Integer id;
	private Integer sourceId;
	
	private String name;
	private BufferedImage thumbnail;
	
	public ImageRecord(Integer id, Integer sourceId, String name, BufferedImage thumbnail ) {
		super();
		this.id = id;
		this.sourceId = sourceId;
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

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	
	@Override
	public String toString() {
		return "ImageRecord [id=" + id + ", name=" + name + "]";
	}
	
}
