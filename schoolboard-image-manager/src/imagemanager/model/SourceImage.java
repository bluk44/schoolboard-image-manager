package imagemanager.model;

import ij.ImagePlus;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.awt.image.BufferedImage;

public class SourceImage {
	
	private Integer id;
	private BufferedImage pixels;
	
	public SourceImage(Integer id, BufferedImage pixels) {
		super();
		this.id = id;
		this.pixels = pixels;
	}
	
	public Integer getId() {
		return id;
	}
	
	public BufferedImage getPixels() {
		return pixels;
	}
	
} 
