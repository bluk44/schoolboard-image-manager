package imageprocessing.geometry.drawing;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.Color;
import java.awt.Graphics;

public class DrawablePolygon extends Drawable {
	
	protected Polygon poly;
	protected java.awt.Point[] polyPoints; 
	
	public DrawablePolygon(Polygon poly){
		this.poly = poly;
	}
	
	public void setPoint(int i, Point p){
		poly.setPoint(i, p);
		resetAWTPoints();
	}
	
	public Point getPoint(int i){
		return poly.getPoint(i);
	}
	
	@Override
	protected void resetAWTPoints() {
		int nPoints = poly.getPoints().size();
		polyPoints = new java.awt.Point[nPoints];
		for (int i = 0; i < nPoints; i++) {
			Point p = Geo.add(origin, poly.getPoint(i));
			polyPoints[i] = getAwtPoint(p);
		}
	}
	
	@Override
	protected void drawObject(Graphics g) {
		for(int i = 0; i < polyPoints.length - 1; i++){
			g.drawLine(polyPoints[i].x, polyPoints[i].y, polyPoints[i+1].x, polyPoints[i+1].y);
			g.fillOval(polyPoints[i].x - 1, polyPoints[i].y - 1, 2, 2);
			g.fillOval(polyPoints[i+1].x - 1, polyPoints[i+1].y - 1, 2, 2);
		}
		
		g.drawLine(polyPoints[polyPoints.length - 1].x, polyPoints[polyPoints.length - 1].y, polyPoints[0].x, polyPoints[0].y);
		g.fillOval(polyPoints[polyPoints.length - 1].x - 1, polyPoints[polyPoints.length - 1].y - 1, 2, 2);
		g.fillOval(polyPoints[0].x - 1, polyPoints[0].y - 1, 2, 2);
	}
	
	
}
