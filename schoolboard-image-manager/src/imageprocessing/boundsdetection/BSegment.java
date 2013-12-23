package imageprocessing.boundsdetection;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.awt.Color;
import java.awt.image.BufferedImage;
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
	
	public void resize(double scale){
		for (Iterator it = edgeSegments.iterator(); it.hasNext();) {
			Segment s = (Segment) it.next();
			s.resize(scale);
		}
	}
	
	public double getEdgeLength(){
		double el = 0;
		for (Iterator it = edgeSegments.iterator(); it.hasNext();) {
			Segment s = (Segment) it.next();
			el += s.getLength();
		}
		
		return el;
	}
	
	public void setParams(Color color, int thickness){
		for (Iterator it = edgeSegments.iterator(); it.hasNext();) {
			Segment segment = (Segment) it.next();
			segment.setColor(color);
			segment.setRad(thickness);
		}
	}
	
	public void draw(BufferedImage canvas) {
		for (Iterator iterator = edgeSegments.iterator(); iterator.hasNext();) {
			Segment es = (Segment) iterator.next();
			es.draw(canvas);
		}
	}
}
