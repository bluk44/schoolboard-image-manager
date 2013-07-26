package imageprocessing.geometry;

public class Segment {

	public Point p1, p2;

	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Segment(Segment s){
		p1 = new Point(s.p1);
		p2 = new Point(s.p2);
	}
	
	public Line getLine(){
		return new Line(new Point(p1), new Point(p2));
	}
}
