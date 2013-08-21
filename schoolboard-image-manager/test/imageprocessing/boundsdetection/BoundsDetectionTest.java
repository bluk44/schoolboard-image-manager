package imageprocessing.boundsdetection;

import imageprocessing.Util;
import imageprocessing.color.ColorConversion;
import imageprocessing.geometry.Segment;
import imageprocessing.geometry.drawing.DrawableBLine;
import imageprocessing.geometry.drawing.DrawableBSegment;
import imageprocessing.geometry.drawing.DrawablePerimeter;
import imageprocessing.geometry.houghtransform.HoughLine;
import imageprocessing.geometry.houghtransform.HoughMatrix;
import imageprocessing.geometry.houghtransform.HoughTransformParams;
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// load image edge
		BufferedImage bi = Util.readFromFile("images/rect_detection/canny_edges_1.0_5_10/whiteboard02.png");
		bi = ColorConversion.rgb2gray(bi);
		
		MatrixB edges = Util.grayToMatrixB(bi, 0);
		
//		// hpugh transform

		QuadrangleParams qp = new QuadrangleParams();
		HoughTransformParams htp = new HoughTransformParams();
		
		HoughMatrix hm = new HoughMatrix(edges);
		List<HoughLine> hlines = hm.getHorizontalLines(6, htp.minVotes, htp.thetaHorizontal, htp.rhoNhood, htp.thetaNhood);
		List<HoughLine> vlines = hm.getVerticalLines(6, htp.minVotes, htp.thetaVertical, htp.rhoNhood, htp.thetaNhood);
		
		List<DrawableBLine> vblines = new ArrayList<DrawableBLine>(vlines.size());
		List<DrawableBLine> hblines = new ArrayList<DrawableBLine>(hlines.size());
	
		for (Iterator it = vlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(edges.getSizeX(), edges.getSizeY());
			BLine bl = new BLine(edges, ls, qp.lineThickness, qp.minEdgeLength);
//			System.out.println(bl);
			vblines.add(new DrawableBLine(bl));
		}
//			System.out.println();
		for (Iterator it = hlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(edges.getSizeX(), edges.getSizeY());
			BLine bl = new BLine(edges, ls, qp.lineThickness, qp.minEdgeLength);
//			System.out.println(bl);
			hblines.add(new DrawableBLine(bl));
		}
//		System.out.println();
		
		BDTestComp comp = new BDTestComp(bi);
		System.out.println("h0 "+hblines.get(0).bline+" h1 "+hblines.get(1).bline);
		comp.addDrawable(hblines.get(0));
		comp.addDrawable(hblines.get(1));
//		comp.addDrawable(hblines.get(2));
//		comp.addDrawable(hblines.get(3));
//		comp.addDrawable(hblines.get(4));
//		comp.addDrawable(hblines.get(5));		
		
		System.out.println("v1 "+vblines.get(1).bline+" v2 "+vblines.get(2).bline);
//		comp.addDrawable(vblines.get(0));
		comp.addDrawable(vblines.get(1));
		comp.addDrawable(vblines.get(2));
//		comp.addDrawable(vblines.get(3));
//		comp.addDrawable(vblines.get(4));
//		comp.addDrawable(vblines.get(5));	
//		
		System.out.println();
		Test.showComponent(comp, "lulz");

//		showBounds(vblines.get(0).bline, vblines.get(1).bline, hblines.get(1).bline, hblines.get(2).bline, bi);

		// hough transform params
		BoundaryDetector bd = new BoundaryDetector();
		bd.setImageEdge(edges);
		bd.detectBestPerimeter(6, 6, bi);
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
