package imageprocessing.boundsdetection;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Line;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;
import imageprocessing.geometry.drawing.Drawable;
import imageprocessing.matrix.MatrixB;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BLine {

	protected Segment leadingSegment;
	protected Line leadingLine;
	protected List<Segment> edgeSegments;
	protected int thickness;
	protected double minEdgeLength;

	protected Point start, end;
	protected boolean val = false, inSegment = false;
	int r = 0;

	public BLine(MatrixB imgEdge, Segment leadingSegment, int thickness, double minEdgeLength) {
				double dx = Math.abs(leadingSegment.p1.x - leadingSegment.p2.x);
		double dy = Math.abs(leadingSegment.p1.y - leadingSegment.p2.y);
		
		this.minEdgeLength = minEdgeLength;
		this.thickness = thickness;
		
		if ((dx > dy && leadingSegment.p1.x > leadingSegment.p2.x) || (dy >= dx && leadingSegment.p1.y > leadingSegment.p2.y)) {
			Point tmp = leadingSegment.p1;
			leadingSegment.p1 = leadingSegment.p2;
			leadingSegment.p2 = tmp;
		}

		this.leadingSegment = leadingSegment;
		leadingLine = new Line(new Point(leadingSegment.p1), new Point(leadingSegment.p2));

		r = thickness / 2;

		java.awt.Point p1 = Drawable.getAwtPoint(leadingSegment.p1);
		java.awt.Point p2 = Drawable.getAwtPoint(leadingSegment.p2);

		findEdgeSegments(imgEdge, p1.x, p1.y, p2.x, p2.y);
	}

	private void findEdgeSegments(MatrixB imgEdge, int x1, int y1, int x2, int y2) {
		edgeSegments = new ArrayList<Segment>();
		val = false;
		inSegment = false;

		// zmienne pomocnicze
		int d, dx, dy, ai, bi, xi, yi;
		int x = x1, y = y1;
		// ustalenie kierunku rysowania
		if (x1 < x2) {
			xi = 1;
			dx = x2 - x1;
		} else {
			xi = -1;
			dx = x1 - x2;
		}
		// ustalenie kierunku rysowania
		if (y1 < y2) {
			yi = 1;
			dy = y2 - y1;
		} else {
			yi = -1;
			dy = y1 - y2;
		}
		// pierwszy piksel
		val = imgEdge.getElement(x, y);
		checkSegment(x, y);

		// oś wiodąca OX
		if (dx > dy) {
//			System.out.println("leading x");

			ai = (dy - dx) * 2;
			bi = dy * 2;
			d = bi - dx;
			// pętla po kolejnych x
			while (x != x2) {
				// test współczynnika
				if (d >= 0) {
					x += xi;
					y += yi;
					d += ai;
				} else {
					d += bi;
					x += xi;
				}
				val = getValue(imgEdge, x, y, 'x');
				checkSegment(x, y);
			}
		}
		// oś wiodąca OY
		else {
//			System.out.println("leading y");

			ai = (dx - dy) * 2;
			bi = dx * 2;
			d = bi - dy;
			// pętla po kolejnych y
			while (y != y2) {
				// test współczynnika
				if (d >= 0) {
					x += xi;
					y += yi;
					d += ai;
				} else {
					d += bi;
					y += yi;
				}
				val = getValue(imgEdge, x, y, 'y');

				checkSegment(x, y);
			}
		}
	}

	public BSegment getBSegment(BLine l1, BLine l2) {
		List<Segment> sgmt = new ArrayList<Segment>();
		Point p1 = new Point();
		Geo.intersect(l1.leadingLine, leadingLine, p1);

		Point p2 = new Point();
		Geo.intersect(l2.leadingLine, leadingLine, p2);

		double dx = Math.abs(p1.x - p2.x);
		double dy = Math.abs(p1.y - p2.y);

		if ((dx > dy && p1.x > p2.x) || (dy >= dx && p1.y > p2.y)) {
			Point tmp = p1;
			p1 = p2;
			p2 = tmp;
		}

		for (Iterator it = edgeSegments.iterator(); it.hasNext();) {
			Segment s = (Segment) it.next();

			if (Geo.between(p1, s.p1, p2) && Geo.between(p1, s.p2, p2)) {
				sgmt.add(new Segment(s));
			} else if (Geo.between(s.p1, p1, s.p2) && Geo.between(s.p1, p2, s.p2)) {
				sgmt.add(new Segment(new Point(p1), new Point(p2)));
			} else if (Geo.between(p1, s.p1, p2) && Geo.between(s.p1, p2, s.p2)) {
				sgmt.add(new Segment(new Point(s.p1), new Point(p2)));
			} else if (Geo.between(s.p1, p1, s.p2) && Geo.between(p1, s.p2, p2)){
				sgmt.add(new Segment(new Point(p1), new Point(s.p2)));
			}
		}
		
		return new BSegment(p1, p2, sgmt);
	}

	private boolean getValue(MatrixB imgEdge, int x, int y, char leading) {
		if (leading == 'x') {

			for (int j = y - r; j <= y + r; j++) {
				if (imgEdge.getElementSafe(x, j)) {
					return true;
				}
			}
			return false;

		} else if (leading == 'y') {
			for (int j = x - r; j <= x + r; j++) {
				if (imgEdge.getElementSafe(j, y)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	private void checkSegment(int x, int y) {
		if (val && !inSegment) {
			inSegment = true;
			start = Geo.rzut(new Point(x, y), leadingLine);
		} else if (!val && inSegment) {
			end = Geo.rzut(new Point(x, y), leadingLine);
			double length = Geo.lgt(start, end);
			if (length >= minEdgeLength) {
				Segment segment = new Segment(start, end);
				edgeSegments.add(segment);
			}

			start = null;
			end = null;
			inSegment = false;
		}
	}

	public Line getLeadingLine() {
		return leadingLine;
	}

	public List<Segment> getEdgeSegments() {
		return edgeSegments;
	}

	public Segment getLeadingSegment() {
		return leadingSegment;
	}
	
	@Override
	public String toString() {
		return "bl a: "+leadingSegment.p1 +" b: "+leadingSegment.p2;
	}
}
