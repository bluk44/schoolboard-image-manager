package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Drawable {
	
	protected Rectangle clipBounds;
	protected Point origin = new Point(0,0);
	protected Color color = Color.RED;
	
	public final void draw(Graphics g){
		
		if(clipBounds == null || !clipBounds.equals(g.getClipBounds())){
			clipBounds = new Rectangle(g.getClipBounds());
			resetAWTPoints();
		}
		Color prevColor = g.getColor();
		g.setColor(color);
		drawObject(g);
		g.setColor(prevColor);
	}
	
	protected void resetAWTPoints(){
		
	}
	
	public void setColor(Color color){
		this.color = color;
	}
	
	protected abstract void drawObject(Graphics g);
	
	public static java.awt.Point getAwtPoint(Point p){
		
		int x = (int) Math.round(p.x);
		int y = (int) Math.round(p.y);
		return new java.awt.Point(x, y);
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
		resetAWTPoints();
	}
}
