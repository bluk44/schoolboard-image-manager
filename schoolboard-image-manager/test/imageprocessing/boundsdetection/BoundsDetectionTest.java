package imageprocessing.boundsdetection;

import imageprocessing.Util;
import imageprocessing.color.ColorConversion;
import imageprocessing.geometry.Segment;
import imageprocessing.geometry.drawing.DrawableBLine;
import imageprocessing.geometry.drawing.DrawableBSegment;
import imageprocessing.geometry.drawing.DrawablePerimeter;
import imageprocessing.geometry.houghtransform.HoughLine;
import imageprocessing.geometry.houghtransform.HoughMatrix;
import imageprocessing.matrix.MatrixB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import test.Test;
import testJcomps.BDTestComp;

public class BoundsDetectionTest {
	
	private static int THETA_VERTICAL = 30;
	private static int THETA_HORIZONTAL = 30;
	
	private static int RHO_NHOOD = 5;
	private static int THETA_NHOOD = 30;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load image edge
		BufferedImage bi = Util.readFromFile("images/rect_detection/canny_edges_1.0_5_10/whiteboard02.png");
		bi = ColorConversion.rgb2gray(bi);
		
		MatrixB edges = Util.grayToMatrixB(bi, 0);
		
		// create hough matrix
		HoughMatrix hm = new HoughMatrix(edges);
		BDTestComp comp = new BDTestComp(bi);
		
		List<HoughLine> vlines = hm.getVerticalLines(6, 5, THETA_HORIZONTAL, RHO_NHOOD, THETA_NHOOD);
		List<HoughLine> hlines = hm.getHorizontalLines(6, 5, THETA_VERTICAL, RHO_NHOOD, THETA_NHOOD);
		List<DrawableBLine> vblines = new ArrayList<DrawableBLine>(vlines.size());
		List<DrawableBLine> hblines = new ArrayList<DrawableBLine>(hlines.size());
		
		for (Iterator it = vlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(edges.getSizeX(), edges.getSizeY());
			BLine bl = new BLine(edges, ls, 5);
			DrawableBLine dbl = new DrawableBLine(bl);
			vblines.add(dbl);
		}
		
		for (Iterator it = hlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(edges.getSizeX(), edges.getSizeY());
			BLine bl = new BLine(edges, ls, 5);
			DrawableBLine dbl = new DrawableBLine(bl);
			hblines.add(dbl);
		}
		
		int n = 0;
		for(int vi1 = 0; vi1 < vlines.size(); vi1++){
			for(int vi2 = vi1+1; vi2 < vlines.size(); vi2++){
				for(int hi1 = 0; hi1 < vlines.size(); hi1++){
					for(int hi2 = hi1+1; hi2 < vlines.size(); hi2++){
						if(n % 10 == 1)
							try {
								System.in.read();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
						BLine vl1 = vblines.get(vi1).bline, vl2 = vblines.get(vi2).bline;
						BLine hl1  = hblines.get(hi1).bline, hl2 = hblines.get(hi2).bline;
						System.out.println("v1: "+ vi1+" vi2: "+vi2+" hi1: "+hi1+" hi2: "+hi2);
						showBounds(vl1, vl2, hl1, hl2, bi);
						++n;
					}
				}
			}
		}

//		showBounds(vblines.get(0).bline, vblines.get(1).bline, hblines.get(1).bline, hblines.get(2).bline, bi);

		
	}
	
	public static void showBounds(BLine vl1, BLine vl2, BLine hl1, BLine hl2, BufferedImage bgImage){
		
		BDTestComp comp = new BDTestComp(bgImage);
		
//		BSegment s1 = vl1.getBSegment(hl1, hl2);
//		DrawableBSegment ds1 = new DrawableBSegment(s1);
//	
//		BSegment s2 = vl2.getBSegment(hl1, hl2);
//		DrawableBSegment ds2 = new DrawableBSegment(s2);
//		
//		BSegment s3 = hl1.getBSegment(vl1, vl2);
//		DrawableBSegment ds3 = new DrawableBSegment(s3);
//	
//		BSegment s4 = hl2.getBSegment(vl1, vl2);
//		DrawableBSegment ds4 = new DrawableBSegment(s4);
//		
//		comp.addDrawable(ds1);
//		comp.addDrawable(ds2);
//		comp.addDrawable(ds3);
//		comp.addDrawable(ds4);
		
		try{
			BoardQuadrangle bq = new BoardQuadrangle(vl1, vl2, hl1, hl2);
			DrawablePerimeter dp = new DrawablePerimeter(bq);
			dp.setVerticesColor(Color.RED);
			dp.setEdgesColor(Color.BLUE);
			comp.addDrawable(dp);
			
			Test.showComponent(comp, "lulz");
			
		}catch(Exception ex){
			System.out.println(ex);
		}

	}
}
