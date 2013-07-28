package imageprocessing.geometry;

public class Line {
	
 	public Point o;
	public Point dir;
	
	public Line(double x1, double y1, double x2, double y2){
		this(new Point(x1, y1), new Point(x2, y2));
	}
	
	public Line(Point a, Point b){
		this.o = a;
		this.dir = b.sub(a);
	}
	
	@Override
	public String toString() {
		return "[o: "+o+" dir: "+dir+" ]";
	}
}
