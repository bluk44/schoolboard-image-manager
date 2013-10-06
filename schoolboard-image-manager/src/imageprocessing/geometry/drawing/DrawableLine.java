package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Line;
import imageprocessing.geometry.Point;

import java.awt.Graphics;

public class DrawableLine extends Drawable {

	protected Line line;
	protected java.awt.Point[] rectPoints = new java.awt.Point[2];
	
	public DrawableLine(Line line) {
		super();
		this.line = line;
	}
	
	@Override
	protected void drawObject(Graphics g) {
		g.drawLine(rectPoints[0].x, rectPoints[0].y, rectPoints[1].x, rectPoints[1].y);
		
	}
	
	@Override
	protected void resetAWTPoints() {
		if(clipBounds == null) return;
		
		Line[] bLines = new Line[4];
		bLines[0] = new Line(new Point(0, 0), new Point(0, 1));
		bLines[1] = new Line(new Point(clipBounds.getWidth(), 0), new Point(clipBounds.getWidth(), 1));
		bLines[2] = new Line(new Point(0, 0), new Point(1, 0));
		bLines[3] = new Line(new Point(0, clipBounds.getHeight()), new Point(1, clipBounds.getHeight()));
		
		Point p = new Point();
		Line line = new Line(this.line);
		line.add(origin);
		int w = clipBounds.width, h = clipBounds.height, idx = 0;
		for (int i = 0; i < bLines.length; i++) {
			if(Geo.intersect(line, bLines[i], p) && p.x >= 0 && p.x <= w && p.y >= 0 && p.y <= h){
				rectPoints[idx++] = getAwtPoint(p);
			}
		}
	
	}
}
