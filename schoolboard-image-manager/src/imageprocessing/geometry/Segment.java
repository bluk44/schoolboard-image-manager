package imageprocessing.geometry;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Segment extends Shape{

	public Point p1, p2;
	
	public Segment(double x1, double y1, double x2, double y2){
		this(new Point(x1, y1), new Point(x2, y2));
	}
	
	public Segment(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Segment(Segment s){
		p1 = new Point(s.p1);
		p2 = new Point(s.p2);
	}
	
	public Line getLine(){
		return new Line(new Point(p1), new Point(p2));
	}
	
	public double getLength(){
		return Geo.lgt(p1, p2);
	}

	@Override
	public void draw(BufferedImage canvas) {
		Shape.drawLine(canvas, color, rad, p1, p2);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		p1.draw(g);
		p2.draw(g);
		g.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
	}
	
	@Override
	public void resize(double scale) {
		p1.mlt(scale);
		p2.mlt(scale);
	}

	@Override
	public void add(Point p) {
		p1.add(p);
		p2.add(p);
	}

	@Override
	public void sub(Point p) {
		p1.sub(p);
		p2.sub(p);
	}


}
