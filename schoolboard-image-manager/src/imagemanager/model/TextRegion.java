package imagemanager.model;

import imageprocessing.geometry.Polygon;

public class TextRegion {

	private Polygon poly;
		
	public TextRegion(Polygon poly){
		this.poly = poly;
	}
	
	public Polygon getPolygon(){
		return poly;
	}
	
}
