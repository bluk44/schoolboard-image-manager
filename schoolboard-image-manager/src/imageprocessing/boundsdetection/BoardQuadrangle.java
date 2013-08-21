package imageprocessing.boundsdetection;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;
import imageprocessing.matrix.MatrixB;

import java.util.ArrayList;
import java.util.Iterator;

public class BoardQuadrangle extends BoardPerimeter {
	
	private double area;
	
	public BoardQuadrangle(BLine vl1, BLine vl2, BLine hl1, BLine hl2) throws SegmentsCrossingException{
		BSegment[] vseg = new BSegment[2];
		vseg[0] = vl1.getBSegment(hl1, hl2);
		vseg[1] = vl2.getBSegment(hl1, hl2);
		
		if(vseg[0].p1.x > vseg[1].p1.x){
			BSegment tmp = vseg[0];
			vseg[0] = vseg[1];
			vseg[1] = tmp;
		}
		if(vseg[1].p2.x < vseg[0].p2.x) throw new SegmentsCrossingException("vertical segments crossing: ");
		
		BSegment[] hseg = new BSegment[2];
		hseg[0] = hl1.getBSegment(vl1, vl2);
		hseg[1] = hl2.getBSegment(vl1, vl2);
		
		if(hseg[0].p1.y > hseg[1].p1.y){
			BSegment tmp = hseg[0];
			hseg[0] = hseg[1];
			hseg[1] = tmp;
		}
		if(hseg[1].p2.y < hseg[0].p2.y) throw new SegmentsCrossingException("horizontal segments crossing: ");
		
		vertices = new ArrayList<Point>(4);
		edges = new ArrayList<BSegment>(4);
		
		vertices.add(vseg[0].p1);
		vertices.add(hseg[0].p2);
		vertices.add(vseg[1].p2);
		vertices.add(hseg[1].p1);
		
		edges.add(vseg[0]);
		edges.add(hseg[0]);
		edges.add(vseg[1]);
		edges.add(hseg[1]);
		
		Point o = vertices.get(1);
		
		Point p1 = Geo.sub(vertices.get(0), o);
		Point p2 = Geo.sub(vertices.get(2), o);
		
		area = Math.abs(Geo.cp(p1, p2));
		
		double totalLength = 0;
		double edgeLength = 0;
		for (Iterator it = edges.iterator(); it.hasNext();) {
			BSegment bs = (BSegment) it.next();
			totalLength += bs.getLength();
			edgeLength += bs.getEdgeLength();
		}
		
		coverage = edgeLength / totalLength;
	}
	
	public BSegment getUpperHorizontalSegment(){
		return edges.get(1);
	}
	
	public BSegment getLowerHorizontalSegment(){
		return edges.get(3);
	}
	
	public BSegment getLeftVerticalSegment(){
		return edges.get(0);
	}
	
	public BSegment getRightVerticalSegment(){
		return edges.get(2);
	}
	
	public double getArea(){
		return area;
	}
	
	public double perimeterLength(){
		double len = 0;
		for (Iterator it = edges.iterator(); it.hasNext();) {
			BSegment bs = (BSegment) it.next();
			len += bs.getLength();
		}
		
		return len;
	}
	
	class SegmentsCrossingException extends Exception{

		public SegmentsCrossingException(String string) {
			super(string);		
		}
	}
}
