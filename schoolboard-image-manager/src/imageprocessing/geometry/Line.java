package imageprocessing.geometry;

public class Line {
	
 	public Point o;
	public Point dir;
	
	public Line(Point a, Point b){
		this.o = a;
		this.dir = b.sub(a);
	}
	
	@Override
	public String toString() {
		return "[o: "+o+" dir: "+dir+" ]";
	}
}
