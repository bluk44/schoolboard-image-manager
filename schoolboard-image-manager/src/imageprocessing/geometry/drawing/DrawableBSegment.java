package imageprocessing.geometry.drawing;

import imageprocessing.boundsdetection.BSegment;
import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Segment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

public class DrawableBSegment extends Drawable {
	
	public BSegment bsegment;
	java.awt.Point p1, p2;
	java.awt.Point[][] segmentPoints;
	Color lineColor = Color.BLUE;
	Color segmentColor = Color.RED;
	
	public DrawableBSegment(BSegment bsegment){
		this.bsegment = bsegment;
	}
	
	@Override
	protected void drawObject(Graphics g) {
		// rysowanie linii
		g.setColor(lineColor);
		g.drawLine(p1.x, p1.y, p2.x, p2.y);
		
		// rysowanie odcinkow
		g.setColor(segmentColor);
		for(int i = 0; i < segmentPoints.length; i++){
			g.setColor(segmentColor);
			g.drawLine(segmentPoints[i][0].x , segmentPoints[i][0].y, segmentPoints[i][1].x, segmentPoints[i][1].y);

		}
	}

	@Override
	protected void resetAWTPoints() {
		p1 = getAwtPoint(Geo.add(origin, bsegment.p1));
		p2 = getAwtPoint(Geo.add(origin, bsegment.p2));
		
		segmentPoints = new java.awt.Point[bsegment.edgeSegments.size()][2];
		
		List<Segment> edgeSegments = bsegment.edgeSegments;
		int sidx = 0;
		for (Iterator it = edgeSegments.iterator(); it.hasNext();) {
			Segment segment = (Segment) it.next();
			segmentPoints[sidx][0] = getAwtPoint(Geo.add(origin, segment.p1));
			segmentPoints[sidx][1] = getAwtPoint(Geo.add(origin, segment.p2));
			++sidx;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
