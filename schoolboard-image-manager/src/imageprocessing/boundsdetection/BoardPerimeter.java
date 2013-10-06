package imageprocessing.boundsdetection;

import imageprocessing.geometry.Point;

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
	
}
