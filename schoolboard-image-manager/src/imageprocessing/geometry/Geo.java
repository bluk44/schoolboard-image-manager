package imageprocessing.geometry;

import java.util.List;
import java.util.Set;

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
	// wektorowy
	public static double cp(Point p1, Point p2) {
		return p1.x * p2.y - p1.y * p2.x;
	}
	// skalarny
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
		return new Line(new Point(a), new Point(d.y, -d.x).addn(a));
	}
	
	public static double lgt(Point p){
		return Math.sqrt(dp(p, p));
	}
	
	public static double lgt(Point a, Point b){
		Point diff = Geo.sub(a, b);
		return Math.sqrt(Geo.dp(diff, diff));
	}
	
	public static double dist(Point a, Point b){
		return lgt(sub(a,b));
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
			p.set(l.o.addn(mlt(l.dir, s)));
		}
		return true;
	}

	public static boolean intersect(Segment s1, Segment s2, Point p) {
		if(!intersect(s1.getLine() , s2.getLine(), p)) return false;
		if(between(s1.p1, p, s1.p2) && between(s2.p1, p, s2.p2)) return true;
		return false;
	}
	
	public static boolean intersect(Segment s1, Segment s2){
		Point p = new Point();
		if(!intersect(s1.getLine() , s2.getLine(), p)) return false;
		if(between(s1.p1, p, s1.p2) && between(s2.p1, p, s2.p2)) return true;
		return false;
	}
	
	public static boolean intersect(Line l1, Segment s1, Point p){
		if(!intersect(l1 , s1.getLine(), p)) return false;
		if(between(s1.p1, p, s1.p2)) return true; 
		return false;
	}
	
	public static boolean intersect(Line l, Polygon poly, Set<Point> points){
		int nPoints = poly.getPoints().size();
		Point p = new Point();
		boolean r = false;
		for(int i = 0; i < nPoints; i++){
			if(intersect(l, poly.getSegment(i), p)){
				r = true;
				System.out.println("found intersection "+p);
				points.add(new Point(p));
			}
		}
		return r;
	}
	
	public static Point rzut(Point p, Line line){
		double a = dp(Geo.sub(p, line.o), line.dir) / dp(line.dir, line.dir);
		return Geo.add(Geo.mlt(line.dir, a), line.o);
	}
	
	public static boolean isInside(Point point, Polygon poly){
		List<Segment> sgmts = poly.getAllSegments();
		Segment ray = new Segment(new Point(0,0), point);

		int n = 0;
		for (Segment segment : sgmts)
			if(Geo.intersect(ray, segment)) ++n;

		if(n % 2 == 0) return false;
		return true;
	}
}
