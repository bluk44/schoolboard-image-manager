package imageprocessing.geometry;

public class GeoTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	betweenTest();
		intersectSegSegTest();
	}
	
	public static void betweenTest(){
		Point p1 = new Point(1,1), p2 = new Point(7, 4), p3 = new Point(3, 2);
		
		System.out.println(Geo.between(p1, p3, p2));
	}
	
	public static void intersectSegSegTest(){
		Segment s1 = new Segment(new Point(0,0), new Point(8,4));
		Segment s2 = new Segment(new Point(2,3), new Point(6,1));
		Point b = new Point(0,0);
		
		System.out.println(Geo.intersect(s2, s1, b));
		System.out.println(b);
	}
	
	
}
