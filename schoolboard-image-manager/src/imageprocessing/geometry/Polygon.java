package imageprocessing.geometry;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Polygon extends Shape {

	protected List<Point> points;

	public Polygon() {
		points = new ArrayList<Point>();
	}

	public Polygon(Point... points) {
		this.points = new ArrayList<Point>(points.length);

		for (int i = 0; i < points.length; i++) {
			this.points.add(points[i]);
		}
	}

	public Polygon(List<Point> points) {
		this.points = points;
	}

	public Polygon(Polygon other) {
		points = new ArrayList<Point>(other.getPoints().size());
		
		for (Point point : other.points) points.add(new Point(point));
	}

	public List<Point> getPoints() {
		return points;
	}

	public Point getPoint(int i) {
		return points.get(i);
	}

	public void setPoint(int i, Point p) {
		points.set(i, p);
	}

	public void addVertex(Point p) {
		points.add(p);
	}

	public void addNewVertex(Point p){
		points.add(new Point(p));
	}
	
	public Segment getSegment(int i) {
		Point p1 = points.get(i);
		Point p2 = null;
		if (i == points.size() - 1)
			p2 = points.get(0);
		else
			p2 = points.get(i + 1);
		return new Segment(p1, p2);
	}

	public List<Segment> getAllSegments(){
		List<Segment> segments = new ArrayList<Segment>(points.size());
		
		for(int i = 0; i < points.size() - 1; i++)
			segments.add(new Segment(points.get(i), points.get(i+1)));
		
		segments.add(new Segment(points.get(points.size()-1), points.get(0)));
		
		return segments;
	}
	public int getNearestPointIdx(Point p) {
		int minRangeIdx = -1;
		double lgt = 9999999;
		int i = 0;
		for (Iterator it = points.iterator(); it.hasNext();) {
			Point pp = (Point) it.next();
			double cl = Geo.lgt(pp, p);
			if (cl < lgt) {
				lgt = cl;
				minRangeIdx = i;
			}
			i++;
		}

		return minRangeIdx;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	@Override
	public void setRad(int rad) {
		for (Point pts : points) {
			pts.setRad(rad);
		}
		
		super.setRad(rad);
	}
	
	@Override
	public String toString() {
		return points.toString();
	}

	@Override
	public void draw(BufferedImage canvas) {
		for (int i = 0; i < points.size() - 1; i++) {
			Shape.drawLine(canvas, color, rad, points.get(i), points.get(i + 1));
		}
		Shape.drawLine(canvas, color, rad, points.get(0),
				points.get(points.size() - 1));

	}

	@Override
	public void resize(double scale) {
		for (Iterator it = points.iterator(); it.hasNext();) {
			Point p = (Point) it.next();
			p.mlt(scale);
		}
		
	}
	
	public void resize(Point vector){
		for (Point point : points) {
			point.x = point.x*vector.x;
			point.y = point.y*vector.y;
		}
	}
	
	@Override
	public void add(Point p) {
		for (Iterator it = points.iterator(); it.hasNext();) {
			Point pp = (Point) it.next();
			pp.add(p);
		}
	}

	@Override
	public void sub(Point p) {
		for (Iterator it = points.iterator(); it.hasNext();) {
			Point pp = (Point) it.next();
			pp.sub(p);
		}
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		for (Iterator it = points.iterator(); it.hasNext();) {
			Point vertex = (Point) it.next();
			vertex.draw(g);
		}

		for (int i = 0; i < points.size() - 1; i++) {
			g.drawLine((int) points.get(i).x, (int) points.get(i).y,
					(int) points.get(i + 1).x, (int) points.get(i + 1).y);
		}

		g.drawLine((int) points.get(0).x, (int) points.get(0).y,
				(int) points.get(points.size() - 1).x,
				(int) points.get(points.size() - 1).y);
	}

}
