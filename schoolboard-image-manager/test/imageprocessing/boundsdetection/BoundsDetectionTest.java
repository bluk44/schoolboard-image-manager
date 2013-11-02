package imageprocessing.boundsdetection;

import imageprocessing.Util;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.color.ColorConversion;
import imageprocessing.geometry.Segment;
import imageprocessing.geometry.drawing.DrawableBLine;
import imageprocessing.geometry.drawing.DrawablePerimeter;
import imageprocessing.geometry.houghtransform.HoughLine;
import imageprocessing.geometry.houghtransform.HoughMatrix;
import imageprocessing.geometry.houghtransform.HoughTransformParams;
import imageprocessing.matrix.MatrixB;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
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
		//BufferedImage bi = Util.readFromFile("images/whiteboard/complex_background/canny_s1l5h10_wb06.png"); 
		// [FAIL] zle oznaczona prawa krawedz, brak wykrycia wlasciwej krawedzi przez algorytm cannyego 
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/complex_background/canny_s1l5h10_wb11.png");
		// [FAIL] zla lokalizacja, tablica w innym miejscu niz podane w parametrach 
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/complex_background/canny_s1l5h10_wb22.png"); 
		// [FAIL] zle oznaczona lewa krawedz, brak wykrycia wlasciwej krawedzi przez algorytm cannyego
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb07.png"); 
		// [SUCCESS] 
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb10.png"); 
		// [FAIL] zle oznaczona gorna linia, zbyt mala szerokosc linii (5)
		// [SUCCESS] jesli grubosc linii 20
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb15.png"); 
		// [FAIL] brak wykrycia wlasciwej krawedzi przez algorytm cannyego
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb16.png");
		// [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb19.png"); 
		// [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb20.png"); 
		// [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb21.png"); 
		// [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb23.png"); 
		// [FAIL] brak wykrycia wlasciwej krawedzi przez algorytm cannyego
		//BufferedImage bi = Util.readFromFile("images/whiteboard/hi_skewness/canny_s1l5h10_wb25.png"); 
		// [SUCCESS]
		
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb01.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb02.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb03.png"); 
		// [FAIL] brak wykrycia wlasciwej krawedzi przez algorytm cannyego
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb04.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb05.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb08.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb09.png"); 
		// za malo linii do sformowania czworokata 
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb12.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb13.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb14.png"); // OK [SUCCESS]
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb17.png");
		// za malo linii do sformowania czworokata 
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb18.png");
		// za malo linii do sformowania czworokata 
		//BufferedImage bi = Util.readFromFile("images/whiteboard/low_skewness/canny_s1l5h10_wb24.png"); 
		// [SUCCESS]



		//BufferedImage bi = Util.readFromFile("images/blackboard/lulz/canny_s1l5h10_bb01.png");

		String bbPath = "images/blackboard/canny_s1l5h10_bb";
		int i = 6;
		if(i < 10) bbPath += "0"+i+".png"; 
		else bbPath += i+".png";
		BufferedImage bi = Util.readFromFile(bbPath);
		bi = ColorConversion.rgb2gray(bi);
		// 3 11 13 19 
		// 27 28 31
		// zly czworokat
		
		MatrixB edges = Util.grayToMatrixB(bi, 0);
		
//		// hough transform

		QuadrangleParams qp = new QuadrangleParams();
		HoughTransformParams htp = new HoughTransformParams();
		
		HoughMatrix hm = new HoughMatrix(edges);
		List<HoughLine> hlines = hm.getHorizontalLines(6, htp.minVotes, htp.thetaHorizontal, htp.rhoNhood, htp.thetaNhood);
		List<HoughLine> vlines = hm.getVerticalLines(6, htp.minVotes, htp.thetaVertical, htp.rhoNhood, htp.thetaNhood);
		
//		DrawableHoughLines dhl = new DrawableHoughLines(vlines, hlines);
//		HoughLinesComp hlc = new HoughLinesComp(hm.getBufferedImage(), dhl);
//		Test.showComponent(hlc, "lol");
		
		List<DrawableBLine> vblines = new ArrayList<DrawableBLine>(vlines.size());
		List<DrawableBLine> hblines = new ArrayList<DrawableBLine>(hlines.size());
	
		for (Iterator it = vlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(edges.getSizeX(), edges.getSizeY());
			BLine bl = new BLine(edges, ls, qp.lineThickness, qp.minEdgeLength);
			vblines.add(new DrawableBLine(bl));
		}

		for (Iterator it = hlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(edges.getSizeX(), edges.getSizeY());
			BLine bl = new BLine(edges, ls, qp.lineThickness, qp.minEdgeLength);
			hblines.add(new DrawableBLine(bl));
		}
		
		BDTestComp comp = new BDTestComp(edges.getBufferedImage());
		for (Iterator it = hblines.iterator(); it.hasNext();) {
			DrawableBLine drawableBLine = (DrawableBLine) it.next();
			comp.addDrawable(drawableBLine);
		}
		for (Iterator it = vblines.iterator(); it.hasNext();) {
			DrawableBLine drawableBLine = (DrawableBLine) it.next();
			comp.addDrawable(drawableBLine);
		}
		comp.addDrawable(hblines.get(1));
		Test.showComponent(comp, "houghLines");
//		Test.showImage(bi, "edges");
		BoundaryDetector bd = new BoundaryDetector();
		bd.setImageEdge(edges);
		BoardQuadrangle bq;
		try {
			bq = (BoardQuadrangle) bd.detectBestQuadrangle();
			showQuadrangle(bq, bi);
		} catch (QuadrangleNotFoundException e) {
			System.out.println("quadrangle not found");
		}
		
	}
	
	public static void showQuadrangle(BoardQuadrangle bq, BufferedImage bgImage){
		BDTestComp comp = new BDTestComp(bgImage);
		DrawablePerimeter dp = new DrawablePerimeter(bq);
		dp.setVerticesColor(Color.RED);
		dp.setEdgesColor(Color.BLUE);
		comp.addDrawable(dp);
		
		Test.showComponent(comp, "best quadrangle");

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
