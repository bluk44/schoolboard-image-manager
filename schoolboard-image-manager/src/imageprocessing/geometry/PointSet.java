package imageprocessing.geometry;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

public class PointSet extends Shape {
	
	protected ArrayList<Point> points = new ArrayList<Point>();
	
	public int getNextIdx(){
		return points.size();
	}
	
	public void addPoint(Point p){
		points.add(p);
	}
	
	public void reset(){
		points = new ArrayList<Point>();
	}
	
	public Polygon getPolygon(){
		Polygon poly = new Polygon();
		for (Point p : points) poly.addVertex(new Point(p));
		
		return poly;
	}
	
	@Override
	public void draw(BufferedImage canvas) {
		for (Point p : points) p.draw(canvas);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for (Point p : points) p.draw(g);
	}

	@Override
	public void resize(double scale) {
		for (Point p : points) p.resize(scale);
	}

	@Override
	public void add(Point p) {
		for (Point pp : points) pp.add(p);
	}

	@Override
	public void sub(Point p) {
		for (Point pp : points) pp.sub(p);
	}

}
