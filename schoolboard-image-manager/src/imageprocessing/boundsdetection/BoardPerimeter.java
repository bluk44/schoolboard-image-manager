package imageprocessing.boundsdetection;

import imageprocessing.geometry.Point;
import imageprocessing.matrix.MatrixB;

import java.util.List;

public abstract class BoardPerimeter {
		
	protected List<Point> vertices;
	protected List<BSegment> edges;
	
	public List<Point> getVertices(){
		return vertices;
	}
	
	public List<BSegment> getEdges(){
		return edges;
	}
	
	
}
