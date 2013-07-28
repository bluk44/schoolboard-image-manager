package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Point;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Drawable {
	
	protected Rectangle clipBounds;
	
	public final void draw(Graphics g){
		Color prevColor = g.getColor();
		if(clipBounds == null || !clipBounds.equals(g.getClipBounds())){
			clipBounds = new Rectangle(g.getClipBounds());
			resetPoints();
		}		
		drawObject(g);
		g.setColor(prevColor);
	}
	
	protected void resetPoints(){
		
	}
	
	protected abstract void drawObject(Graphics g);
	
	protected java.awt.Point getAwtPoint(Point p){
		
		int x = (int) Math.round(p.x);
		int y = (int) Math.round(p.y);
		return new java.awt.Point(x, y);
	}
}
