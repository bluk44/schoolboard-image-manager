package imageprocessing.boundsdetection;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.Util;
import imageprocessing.boundsdetection.BoardQuadrangle.SegmentsCrossingException;
import imageprocessing.geometry.Segment;
import imageprocessing.geometry.drawing.DrawableBLine;
import imageprocessing.geometry.drawing.DrawablePerimeter;
import imageprocessing.geometry.houghtransform.HoughLine;
import imageprocessing.geometry.houghtransform.HoughMatrix;
import imageprocessing.geometry.houghtransform.HoughTransformParams;
import imageprocessing.matrix.MatrixB;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import test.Test;
import testJcomps.BDTestComp;

public class BoundaryDetector {

	private double imageWidth, imageHeight;
	
	public QuadrangleParams qp = new QuadrangleParams();
	public HoughTransformParams htp = new HoughTransformParams();

	private MatrixB imageEdge;

	public HoughTransformParams getHoughTransformParams() {
		return htp;
	}

	public void setHoughTransformParams(HoughTransformParams houghTransformParams) {
		this.htp = houghTransformParams;
	}

	public MatrixB getImageEdge() {
		return imageEdge;
	}

	public void setImageEdge(MatrixB imageEdge) {
		this.imageEdge = imageEdge;
		imageWidth = imageEdge.getSizeX();
		imageHeight = imageEdge.getSizeY();
	}
	
	public void setImageEdge(ImagePlus ip){
		ImageProcessor ipr = ip.getProcessor();
		int w = ipr.getWidth(), h = ipr.getHeight();
		MatrixB edges = new MatrixB(w, h);
		for(int i = 0; i < h; i++){
			for(int j = 0; j < w; j++){
				if(ipr.get(j, i) != 0){
					edges.setElement(j, i, true);
				}
			}
		}
		
		setImageEdge(edges);
	}
	
	public BoardPerimeter detectBestQuadrangle() throws QuadrangleNotFoundException {
		if (imageEdge == null)
			return null;

		HoughMatrix hm = new HoughMatrix(imageEdge);
		Test.showImage(hm.getBufferedImage(), "houghMatrix");
		List<HoughLine> hlines = hm.getHorizontalLines(qp.nHorizontalLines, htp.minVotes, htp.thetaHorizontal, htp.rhoNhood, htp.thetaNhood);
		List<HoughLine> vlines = hm.getVerticalLines(qp.nVerticalLines, htp.minVotes, htp.thetaVertical, htp.rhoNhood, htp.thetaNhood);
		
		List<BLine> vblines = new ArrayList<BLine>(vlines.size());
		List<BLine> hblines = new ArrayList<BLine>(hlines.size());
		
		for (Iterator it = vlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(imageEdge.getSizeX(), imageEdge.getSizeY());
			BLine bl = new BLine(imageEdge, ls, qp.lineThickness, qp.minEdgeLength);
			vblines.add(bl);
		}
		
		for (Iterator it = hlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			Segment ls = hl.getSegment(imageEdge.getSizeX(), imageEdge.getSizeY());
			BLine bl = new BLine(imageEdge, ls, qp.lineThickness, qp.minEdgeLength);
			hblines.add(bl);
		}

		int n = 0;
		BoardQuadrangle bestQuadrangle = null;


		for(int vi1 = 0; vi1 < vlines.size(); vi1++){
			for(int vi2 = vi1+1; vi2 < vlines.size(); vi2++){
				for(int hi1 = 0; hi1 < vlines.size(); hi1++){
					for(int hi2 = hi1+1; hi2 < vlines.size(); hi2++){
						try{
							BLine vl1 = vblines.get(vi1), vl2 = vblines.get(vi2);
							BLine hl1  = hblines.get(hi1), hl2 = hblines.get(hi2);
							BoardQuadrangle bq = new BoardQuadrangle(vl1, vl2, hl1, hl2);
							if(verifyQuadrangle(bq)){
								
								if(bestQuadrangle == null || bestQuadrangle.getCoverage() < bq.getCoverage()){
									bestQuadrangle = bq;
								} 
							} else {
							}

//							Test.showComponent(comp, "quadrangle "+n);
						}catch(SegmentsCrossingException ex){
							
						}
						
//						++n;
//						if(n % 10 == 0){
//							try{
//								System.in.read();
//							}catch(Exception ex){
//								
//							}
//						}
					}
				}
			}
		}
		if(bestQuadrangle == null) throw new QuadrangleNotFoundException();
		return bestQuadrangle;
	}
	
	private boolean verifyQuadrangle(BoardQuadrangle quadrangle){
		// check position
		BSegment upper = quadrangle.getUpperHorizontalSegment();
		BSegment lower = quadrangle.getLowerHorizontalSegment();
		BSegment left = quadrangle.getLeftVerticalSegment();
		BSegment right = quadrangle.getRightVerticalSegment();
		
		if(upper.getMaxY() > qp.upperEdgeLmit * imageHeight){
			return false;
		}
		if(lower.getMinY() < imageHeight - qp.lowerEdgeLimit * imageHeight){
			return false;
		}
		if(left.getMaxX() > qp.leftEdgeLimit * imageWidth){
			return false;
		}
		if(right.getMinX() < imageWidth - qp.rightEdgeLmit * imageWidth){
			return false;
		}
		
		// check area
		if(quadrangle.getArea() < qp.minQuadrangleArea * imageWidth * imageHeight){
			return false;
		}
		
		return true;
	}
	
	public static void showBounds(BoardQuadrangle bq, BufferedImage bgImage, String title){
		
		BDTestComp comp = new BDTestComp(bgImage);
	
		try{
			DrawablePerimeter dp = new DrawablePerimeter(bq);
			dp.setVerticesColor(Color.RED);
			dp.setEdgesColor(Color.BLUE);
			comp.addDrawable(dp);
			
			Test.showComponent(comp, title);
			
		}catch(Exception ex){
			System.out.println(ex);
		}

	}
	
	public static void showHoughLines(List<BLine> lines, BufferedImage bgImage, String title){
		BDTestComp comp = new BDTestComp(bgImage);
		for (Iterator it = lines.iterator(); it.hasNext();) {
			BLine bLine = (BLine) it.next();
			DrawableBLine dbl = new DrawableBLine(bLine);
			comp.addDrawable(dbl);
		}
		
		Test.showComponent(comp, title);

	}
	
	public class QuadrangleNotFoundException extends Exception{
		
	}
}
