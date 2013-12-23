package imagemanager;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

public class TextRegion extends Polygon {
	
	public TextRegion(Polygon poly){
		super(poly);
		
		for (Point point : getPoints()) {
			point.setRad(0);
		}
	}	
	
}
