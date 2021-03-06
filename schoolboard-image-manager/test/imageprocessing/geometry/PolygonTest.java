package imageprocessing.geometry;


public class PolygonTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Polygon p = new Polygon(new Point(10, 12), new Point(13, 7), new Point(21, 7), new Point(18, 12));
//		System.out.println(calculateArea(p));
		
		Polygon square = new Polygon(new Point(10, 7), new Point(17, 7), new Point(17, 30), new Point(10, 30));
		
		System.out.println(Geo.isInside(new Point(13, 9), square));
 	}
	
	private static double calculateArea(Polygon poly){
		Segment s1 = poly.getSegment(0);
		Segment s2 = poly.getSegment(1);
		
		Point p1 = Geo.sub(s1.p2, s1.p1);
		Point p2 = Geo.sub(s2.p2, s2.p1);
		
		return Geo.cp(p1, p2);
	}

}
