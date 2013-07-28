package imageprocessing.geometry;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class Geo {

	private Geo() {
	};

	public static double EPS = 0.00000001;
	
	public static boolean isZero(double d){
		return (d < EPS) && (d > -EPS);
	}
	
	public static Point add(Point p1, Point p2) {
		return new Point(p1.x + p2.x, p1.y + p2.y);
	}

	public static Point sub(Point p1, Point p2) {
		return new Point(p1.x - p2.x, p1.y - p2.y);
	}

	public static Point mlt(Point p, double d) {
		return new Point(p.x * d, p.y * d);
	}

	public static double cp(Point p1, Point p2) {
		return p1.x * p2.y - p1.y * p2.x;
	}
	
	public static double dp(Point p1, Point p2){
		return p1.x * p2.x + p1.y * p2.y;
	}
	
	public static boolean parallel(Point p1, Point p2){
		return isZero(cp(p1,p2));
	}
	
	public static boolean parallel(Line l, Line k){
		return parallel(l.dir, k.dir);
	}

	public static Line perp(Point a, Point d){
		return new Line(new Point(a), new Point(d.y, -d.x).add(a));
	}
	
	public static boolean between(Point a, Point m, Point b){
		if(!parallel(sub(m, a), sub(b, a))) return false;
		return (dp(sub(m, a), sub(m, b)) < EPS); 
	}
	
	public static boolean intersect(Line k, Line l, Point p) {
		if (Math.abs(cp(k.dir, l.dir)) < EPS)
			return false;
		if (p != null) {
			double s = cp(k.dir, sub(k.o, l.o)) / cp(k.dir, l.dir);
			p.set(l.o.add(mlt(l.dir, s)));
		}
		return true;
	}

	public static boolean intersect(Segment s1, Segment s2, Point p) {
		if(!intersect(s1.getLine() , s2.getLine(), p)) return false;
		if(between(s1.p1, p, s1.p2) && between(s2.p1, p, s2.p2)) return true;
		return false;
	}
	
	public static boolean intersect(Line l1, Segment s1, Point p){
		if(!intersect(l1 , s1.getLine(), p)) return false;
		if(between(s1.p1, p, s1.p2)) return true; 
		return false;
	}
	
	public static boolean intersect(Line l, Polygon poly, List<Point> points){
		int nPoints = poly.getPoints().size();
		Point p = new Point();
		boolean r = false;
		for(int i = 0; i < nPoints; i++){
			if(intersect(l, poly.getSegment(i), p)){
				r = true;
				points.add(new Point(p));
			}
		}
		
		return r;
	}
}
