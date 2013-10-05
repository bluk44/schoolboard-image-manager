package imageprocessing.geometry.houghtransform;

import java.awt.image.BufferedImage;

import test.Test;
import testJcomps.BDTestComp;

import imageprocessing.geometry.Segment;
import imageprocessing.geometry.drawing.DrawableSegment;


public class HoughLineTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int sizeX = 653, sizeY = 490;
		double theta = 178.0, rho = 0.0000001;
		BufferedImage img = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_3BYTE_BGR);
		HoughLine hl = new HoughLine(theta, rho, 1, sizeY);
		Segment s = hl.getSegment(sizeX, sizeY);
		DrawableSegment ds = new DrawableSegment(s);
		
		BDTestComp comp = new BDTestComp(img);
		comp.addDrawable(ds);
		Test.showComponent(comp, "hough line ");
		System.out.println();
	}

}
