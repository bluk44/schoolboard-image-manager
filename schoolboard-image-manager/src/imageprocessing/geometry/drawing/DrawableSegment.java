package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Segment;

import java.awt.Graphics;

public class DrawableSegment extends Drawable {

	protected Segment segment;
	protected java.awt.Point[] rectPoints = new java.awt.Point[2];
	
	public DrawableSegment(Segment segment){
		this.segment = segment;
	}
	
	@Override
	protected void resetAWTPoints() {
		rectPoints = new java.awt.Point[2];
		
		rectPoints[0] = getAwtPoint(Geo.add(origin, segment.p1));
		rectPoints[1] = getAwtPoint(Geo.add(origin, segment.p2));
	}
	
	@Override
	protected void drawObject(Graphics g) {
		g.drawLine(rectPoints[0].x, rectPoints[0].y, rectPoints[1].x, rectPoints[1].y);
		g.fillOval(rectPoints[0].x - 1, rectPoints[0].y - 1, 2, 2);
		g.fillOval(rectPoints[1].x - 1, rectPoints[1].y - 1, 2, 2);
		
	}
	
	

}
