package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Point;

import java.awt.Graphics;

public class DrawablePoint extends Drawable {
	
	protected Point p;
	protected java.awt.Point awtPoint;
	
	public DrawablePoint(Point p){
		this.p = p;
		awtPoint = getAwtPoint(p);
	}
	
	@Override
	protected void drawObject(Graphics g) {
		g.drawRect(awtPoint.x - 1, awtPoint.y - 1, 2, 2);
		
	}
	
}
