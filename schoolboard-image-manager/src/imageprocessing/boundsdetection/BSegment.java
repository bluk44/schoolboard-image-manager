package imageprocessing.boundsdetection;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.util.Iterator;
import java.util.List;

public class BSegment {
	
	public Point p1, p2;
	public List<Segment> edgeSegments;
	
	public BSegment(Point p1, Point p2, List<Segment> edgeSegments){
		this.p1 = p1;
		this.p2 = p2;
		this.edgeSegments = edgeSegments;
	}
	
	public double getMaxX(){
		return Math.max(p1.x, p2.x);
	}
	
	public double getMaxY(){
		return Math.max(p1.y, p2.y);
	}
	
	public double getMinX(){
		return Math.min(p1.x, p2.x);
	}
	
	public double getMinY(){
		return Math.min(p1.y, p2.y);
	}
	
	public double getLength(){
		return Geo.lgt(p1, p2);
	}
	
	public double getEdgeLength(){
		double el = 0;
		for (Iterator it = edgeSegments.iterator(); it.hasNext();) {
			Segment s = (Segment) it.next();
			el += s.getLength();
		}
		
		return el;
	}
}
