package imageprocessing.geometry.drawing;

import imageprocessing.boundsdetection.BLine;
import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Line;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

public class DrawableBLine extends Drawable{
	
	public BLine bline;
	java.awt.Point[] linePoints;
	java.awt.Point[][] segmentPoints;
	
	Color lineColor = Color.blue;
	Color segmentColor = Color.red;
	
	public DrawableBLine(BLine bline){
		this.bline = bline;
	}
	
	@Override
	protected void resetAWTPoints() {
		if(clipBounds == null) return;
		
		linePoints = new java.awt.Point[2];
		Line[] bLines = new Line[4];
		bLines[0] = new Line(new Point(0, 0), new Point(0, 1));
		bLines[1] = new Line(new Point(clipBounds.getWidth(), 0), new Point(clipBounds.getWidth(), 1));
		bLines[2] = new Line(new Point(0, 0), new Point(1, 0));
		bLines[3] = new Line(new Point(0, clipBounds.getHeight()), new Point(1, clipBounds.getHeight()));
		
		Point p = new Point();
		Line line = new Line(bline.getLeadingLine());
		line.add(origin);
		int w = clipBounds.width, h = clipBounds.height, idx = 0;
		for (int i = 0; i < bLines.length; i++) {
			if(Geo.intersect(line, bLines[i], p) && p.x >= 0 && p.x <= w && p.y >= 0 && p.y <= h){
				linePoints[idx++] = getAwtPoint(p);
			}
		}
		
		segmentPoints = new	java.awt.Point[bline.getEdgeSegments().size()][2];
		List<Segment> segments = bline.getEdgeSegments();
		
		int sidx = 0;
		for (Iterator it = segments.iterator(); it.hasNext();) {
			Segment segment = (Segment) it.next();
			segmentPoints[sidx][0] = getAwtPoint(Geo.add(origin, segment.p1));
			segmentPoints[sidx][1] = getAwtPoint(Geo.add(origin, segment.p2));
			++sidx;
		}
		
	}
	
	@Override
	protected void drawObject(Graphics g) {
		// rysowanie linii
		g.setColor(lineColor);
		g.drawLine(linePoints[0].x, linePoints[0].y, linePoints[1].x, linePoints[1].y);
		
		// rysowanie odcinkow
		g.setColor(segmentColor);
		for(int i = 0; i < segmentPoints.length; i++){
			g.setColor(segmentColor);
			g.drawLine(segmentPoints[i][0].x , segmentPoints[i][0].y, segmentPoints[i][1].x, segmentPoints[i][1].y);
//			g.setColor(Color.BLUE);
//			g.fillOval(5 + segmentPoints[i][0].x - 1, segmentPoints[i][0].y - 1, 2, 2);
//			g.setColor(Color.RED);
//			g.fillOval(5 + segmentPoints[i][1].x - 1, segmentPoints[i][1].y - 1, 2, 2);
//			g.setColor(Color.GREEN);
//			g.drawLine(5+ segmentPoints[i][0].x, segmentPoints[i][0].y+2, 5+segmentPoints[i][1].x, segmentPoints[i][1].y-2);

		}
	}

}
