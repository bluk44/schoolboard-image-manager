package imageprocessing.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Line extends Shape {

	public Point o;
	public Point dir;

	public Line(double x1, double y1, double x2, double y2) {
		this(new Point(x1, y1), new Point(x2, y2));
	}

	public Line(Point a, Point b) {
		this.o = a;
		this.dir = b.subn(a);
	}

	public Line(Line line) {
		this.o = new Point(line.o);
		this.dir = new Point(line.dir);
	}

	@Override
	public String toString() {
		return "[o: " + o + " dir: " + dir + " ]";
	}

	@Override
	public void draw(BufferedImage canvas) {
		Polygon poly = new Polygon(new Point(0, 0), new Point(
				canvas.getWidth(), 0), new Point(canvas.getWidth(),
				canvas.getHeight()), new Point(0, canvas.getHeight()));

		if (o.y == 0 && dir.y == 0 && (o.x - dir.x) != 0){
			Line.drawLine(canvas, color, rad, new Point(0, 0),
					new Point(canvas.getWidth()-1, 0));
			return;
		}
		
		if (o.x == 0 && dir.x == 0 && (o.y - dir.y) != 0){
			Line.drawLine(canvas, color, rad, new Point(0, 0),
					new Point(0, canvas.getHeight() -1 ));
			return;
		}		
		
		Set<Point> points = new HashSet<Point>();
		if (Geo.intersect(this, poly, points)) {
			for (Iterator it = points.iterator(); it.hasNext();) {
				Point point = (Point) it.next();
				System.out.println(point);
			}
			Point[] pts = points.toArray(new Point[] {});
			Line.drawLine(canvas, color, rad, pts[0], pts[1]);
		}

	}

	@Override
	public void resize(double scale) {
		o.mlt(scale);
		dir.mlt(scale);
	}
	
	@Override
	public void add(Point p) {
		this.o.add(p);
	}
	
	@Override
	public void sub(Point p) {
		this.o.sub(p);
	}

	@Override
	public void draw(Graphics g) {
		System.out.println("TODO draw line");
	}
}
