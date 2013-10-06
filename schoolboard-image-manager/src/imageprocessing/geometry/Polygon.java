package imageprocessing.geometry;

import java.util.ArrayList;
import java.util.List;

public class Polygon {
	
	protected List<Point> points;
	
	public Polygon(){
		points = new ArrayList<Point>();
	}
	
	public Polygon(Point... points){
		this.points = new ArrayList<Point>(points.length);
		
		for (int i = 0; i < points.length; i++) {
			this.points.add(points[i]);
		}		
	}
	
	public Polygon(int nPoints){
		points = new ArrayList<Point>(nPoints);		
	}
	
	public Polygon(List<Point> points){
		this.points = points;
	}
	
	public List<Point> getPoints(){
		return points;
	}
	
	public Point getPoint(int i){
		return points.get(i);
	}
	
	public void setPoint(int i, Point p){
		points.set(i, p);
	}
	
	public Segment getSegment(int i){
		Point p1 = points.get(i);
		Point p2 = null;
		if(i == points.size() -1)
			p2 = points.get(0);
		else 
			p2 = points.get(i+1);
		return new Segment(p1, p2);
	}
	
	public void setPoints(List<Point> points){
		this.points = points;
	}
	
	@Override
	public String toString() {
		return " p1 "+points.get(0)+" p2 "+points.get(1)+" p3 "+points.get(2)+" p4 "+points.get(3);
	}
}
