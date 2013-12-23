package imageprocessing.geometry;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Point extends Shape{
	
	public double x, y;
	{
		rad = 4;
	}
	public Point(){};
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point addn(Point p){
		this.x += p.x;
		this.y += p.y;
		
		return this;
	}
	
	public Point subn(Point p){
		this.x -= p.x;
		this.y -= p.y;
		
		return this;
	}
	
	public Point mlt(double d){
		this.x *= d;
		this.y *= d;
		
		return this;
	}
	
	public double cp(Point p){
		return x*p.y - y*p.x;
	}
	
	public double dp(Point p){
		return x*p.x + y*p.y;
	}
	
	public Point set(Point p){
		this.x = p.x;
		this.y = p.y;

		return this;
	}
		
	@Override
	public boolean equals(Object obj) {
		Point p = (Point)obj;
		if((Math.abs(this.x - p.x) < Geo.EPS) && (Math.abs(this.y - p.y) < Geo.EPS)) return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
	@Override
	public String toString() {
		return "("+x+" "+y+")";
	}

	@Override
	public void draw(BufferedImage canvas) {
		int y = (int)Math.round(this.y);
		int x = (int)Math.round(this.x);
		
		for(int i = y - rad; i <= y+rad; i++ ){
			for(int j = x - rad; j <= x + rad; j++){
				Shape.setPixelColor(canvas.getRaster(), color, j, i);
			}
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.fillOval((int)(x-rad/2.0), (int)(y-rad/2.0), rad, rad);
	}
	
	@Override
	public void resize(double scale) {
		this.mlt(scale);
	}

	@Override
	public void add(Point p) {
		this.x += p.x;
		this.y += p.y;
	}

	@Override
	public void sub(Point p) {
		this.x -= p.x;
		this.y -= p.y;
	}


}
