package imagemanager.model;

import ij.ImagePlus;

public class ImageRecord {

	private Integer id;
	private String name;
	private ImagePlus thumbnail;
	
	public ImageRecord(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	
	public ImagePlus getThumbnail() {
		return thumbnail;
	}
	
	public void setThumbnail(ImagePlus thumbnail) {
		this.thumbnail = thumbnail;
	}

	@Override
	public String toString() {
		return "ImageRecord [id=" + id + ", name=" + name + "]";
	}
	
	
}
