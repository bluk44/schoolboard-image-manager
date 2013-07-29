package imageprocessing.geometry.houghtransform;

import java.awt.Point;

public class HoughLineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HoughLine hl = new HoughLine(0.d, 100.d, 1, 100);
		hl.getLine(30, 100);
		System.out.println();
	}

}
