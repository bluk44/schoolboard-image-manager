package imageprocessing.boundsdetection;

import imageprocessing.geometry.Point;
import imageprocessing.matrix.MatrixB;

import java.util.Iterator;
import java.util.List;

public abstract class BoardPerimeter {
		
	protected List<Point> vertices;
	protected List<BSegment> edges;
	
	protected double coverage;
		
	public List<Point> getVertices(){
		return vertices;
	}
	
	public List<BSegment> getEdges(){
		return edges;
	}
	
	public double getCoverage(){
		return coverage;
	}
}
