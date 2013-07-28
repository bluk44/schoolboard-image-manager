package imageprocessing.geometry;

public class Point {
	
	public double x, y;
	
	public Point(){};
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point add(Point p){
		this.x += p.x;
		this.y += p.y;
		
		return this;
	}
	
	public Point sub(Point p){
		this.x -= p.x;
		this.y -= p.y;
		
		return this;
	}
	
	public Point mlt(double d){
		this.x *= d;
		this.y *= d;
		
		return this;
	}
	
	public double cp(Point p){
		return x*p.y - y*p.x;
	}
	
	public double dp(Point p){
		return x*p.x + y*p.y;
	}
	
	public Point set(Point p){
		this.x = p.x;
		this.y = p.y;

		return this;
	}
	
	public boolean equals(Point p) {
		if((this.x - p.x < Geo.EPS) && (this.y - p.y < Geo.EPS)) return true;
		else return false;
	}
	
	@Override
	public String toString() {
		return "[x: "+x+" y: "+y+"]";
	}
}
