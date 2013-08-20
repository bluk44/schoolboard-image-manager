package imageprocessing.boundsdetection;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.util.List;

public class BSegment {
	
	public Point p1, p2;
	public List<Segment> edgeSegments;
	
	public BSegment(Point p1, Point p2, List<Segment> edgeSegments){
		this.p1 = p1;
		this.p2 = p2;
		this.edgeSegments = edgeSegments;
	}
	
}
