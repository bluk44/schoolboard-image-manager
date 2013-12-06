package imagemanager.model;

import imageprocessing.geometry.Polygon;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BoardImage{
	
	private Integer sourceId;
	
	private Polygon srcQuadrangle;
	private BufferedImage pixels;
	
	private Color background, foregorund;
	private List<TextRegion> textRegions;  
	
	public BoardImage(Integer sourceId, Polygon srcQuadrangle,
			BufferedImage pixels) {
		super();
		
		this.sourceId = sourceId;
		this.srcQuadrangle = srcQuadrangle;
		this.pixels = pixels;
	}
	
	public Integer getSourceId() {
		return sourceId;
	}
	
	public Polygon getPerimeter() {
		return srcQuadrangle;
	}
	
	public BufferedImage getPixels() {
		return pixels;
	}
	
	public List<TextRegion> getTextRegions(){
		return textRegions;
	}
	
	public void createTextRegions(List<Polygon> textPolys){
		textRegions = new ArrayList<TextRegion>(textPolys.size());
		
		for (Polygon polygon : textPolys) {
			textRegions.add(new TextRegion(polygon));
		}
		
	}

}
