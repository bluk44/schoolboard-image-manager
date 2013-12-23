package imageprocessing.boundsdetection;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

public abstract class BoardPerimeter {
		
	protected List<Point> vertices;
	protected List<BSegment> edges;
	
	protected double coverage;
		
	public List<Point> getVertices(){
		return vertices;
	}
	
	public Point getPoint(int i){
		return vertices.get(i);
	}
	
	public Point getNewPoint(int i){
		return new Point(vertices.get(i));
	}
	
	public List<BSegment> getEdges(){
		return edges;
	}
	
	public double getCoverage(){
		return coverage;
	}
	
	public void setParams(Color sgmtColor, Color ptsColor, int segRad, int ptsRad){
		for (Iterator it = edges.iterator(); it.hasNext();) {
			BSegment segment = (BSegment) it.next();
			segment.setParams(sgmtColor, segRad);
		}
		
		for (Iterator it = vertices.iterator(); it.hasNext();) {
			Point p = (Point) it.next();
			p.setColor(ptsColor);
			p.setRad(ptsRad);
		}
	}
	
	public void draw(BufferedImage canvas){
		for (Iterator it = edges.iterator(); it.hasNext();) {
			BSegment e = (BSegment) it.next();
			e.draw(canvas);
		}
		
		for (Iterator it = vertices.iterator(); it.hasNext();) {
			Point p = (Point) it.next();
			p.draw(canvas);
		}
	}
	
	public void resize(double scale){
		for (Iterator it = vertices.iterator(); it.hasNext();) {
			Point p = (Point) it.next();
			p.resize(scale);
		}
		
		for (Iterator it = edges.iterator(); it.hasNext();) {
			BSegment s = (BSegment) it.next();
			s.resize(scale);
		}
	}
	
}
