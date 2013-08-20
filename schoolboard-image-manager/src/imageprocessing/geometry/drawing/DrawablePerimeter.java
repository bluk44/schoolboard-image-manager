package imageprocessing.geometry.drawing;

import imageprocessing.boundsdetection.BSegment;
import imageprocessing.boundsdetection.BoardPerimeter;
import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Segment;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawablePerimeter extends Drawable {

	protected BoardPerimeter bp;
	protected List<java.awt.Point> vertices;
	protected List<java.awt.Point[][]> segments;
	
	protected Color verticesColor, edgesColor;
	
	public DrawablePerimeter(BoardPerimeter bp) {
		super();
		this.bp = bp;
	}
	@Override
	protected void resetAWTPoints() {
		vertices = new ArrayList<java.awt.Point>(bp.getVertices().size());
		segments = new ArrayList<java.awt.Point[][]>(bp.getEdges().size());
		List<Point> bpvertices = bp.getVertices();
		List<BSegment> bpedges = bp.getEdges();
		
		for (Iterator it = bpvertices.iterator(); it.hasNext();) {
			Point pt = (Point) it.next();
			vertices.add(getAwtPoint(Geo.add(pt, origin)));
		}
		
		for (Iterator it = bpedges.iterator(); it.hasNext();) {
			BSegment bsegment = (BSegment) it.next();
			int idx = 0;
			java.awt.Point[][] es = new java.awt.Point[bsegment.edgeSegments.size()][2];
			for (Iterator it2 = bsegment.edgeSegments.iterator(); it2.hasNext();) {
				Segment seg = (Segment) it2.next();
				es[idx++]  = new java.awt.Point[] {getAwtPoint(Geo.add(origin, seg.p1)), getAwtPoint(Geo.add(origin, seg.p2))};
			}
			segments.add(es);

		}
	}
	
	@Override
	protected void drawObject(Graphics g) {
		// draw vertices
		g.setColor(verticesColor);
		for (Iterator it = vertices.iterator(); it.hasNext();) {
			java.awt.Point pt = (java.awt.Point) it.next();
			g.drawOval(pt.x - 1, pt.y - 1, 2, 2);
			
		}
		
		// draw edges
		g.setColor(edgesColor);
		for (Iterator it = segments.iterator(); it.hasNext();) {
			java.awt.Point[][] segment = (java.awt.Point[][]) it.next();

			for(int i = 0; i < segment.length; i++){
				g.drawLine(segment[i][0].x, segment[i][0].y, segment[i][1].x, segment[i][1].y);
			}
		}
		
	}
	public Color getVerticesColor() {
		return verticesColor;
	}
	public void setVerticesColor(Color verticesColor) {
		this.verticesColor = verticesColor;
	}
	public Color getEdgesColor() {
		return edgesColor;
	}
	public void setEdgesColor(Color edgesColor) {
		this.edgesColor = edgesColor;
	}

}
