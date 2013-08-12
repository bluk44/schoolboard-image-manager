package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Line;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.awt.Color;
import java.awt.Graphics;

public class DrawableSegment extends Drawable {

	protected Segment segment;
	protected java.awt.Point[] rectPoints = new java.awt.Point[2];
	
	public DrawableSegment(Segment segment){
		this.segment = segment;
	}
	
	@Override
	protected void resetAWTPoints() {
		
		Line[] bLines = new Line[4];
		bLines[0] = new Line(new Point(0, 0), new Point(0, 1));
		bLines[1] = new Line(new Point(clipBounds.getWidth(), 0), new Point(clipBounds.getWidth(), 1));
		bLines[2] = new Line(new Point(0, 0), new Point(1, 0));
		bLines[3] = new Line(new Point(0, clipBounds.getHeight()), new Point(1, clipBounds.getHeight()));
		
		Point p = new Point();
		int w = clipBounds.width, h = clipBounds.height, idx = 0;
		for (int i = 0; i < bLines.length; i++) {
			if(Geo.intersect(bLines[i], segment, p) && p.x >= 0 && p.x <= w && p.y >= 0 && p.y <= h){
				rectPoints[idx++] = getAwtPoint(p);
			}
		}
		
		if(idx == 0){
			rectPoints[0] = getAwtPoint(segment.p1);
			rectPoints[1] = getAwtPoint(segment.p2);
		}
	}
	
	@Override
	protected void drawObject(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawLine(rectPoints[0].x, rectPoints[0].y, rectPoints[1].x, rectPoints[1].y);
		g.fillOval(rectPoints[0].x - 1, rectPoints[0].y - 1, 2, 2);
		g.fillOval(rectPoints[1].x - 1, rectPoints[1].y - 1, 2, 2);
		
	}
	
	

}
