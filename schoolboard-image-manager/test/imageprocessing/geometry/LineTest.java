package imageprocessing.geometry;

public class LineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		intersectionTest();
	}
	
	public static void intersectionTest(){
		Line k = new Line(new Point(11,1), new Point(2,23));
		Line l = new Line(new Point(722,4), new Point(8,445));
		
		Point p = new Point(0,0);
		boolean t = Geo.intersect(k, l, p);
		System.out.println(t+" "+p);
		
		
		
	}
}
