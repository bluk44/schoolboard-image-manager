package imageprocessing.geometry.drawing;

import imageprocessing.geometry.houghtransform.HoughLine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DrawableHoughLines extends Drawable {
	
	List<HoughLine> vlines, hlines;
	List<java.awt.Point> vLines, hLines;
	private Color vColor = Color.RED;
	private Color hColor = Color.BLUE;
	
	public DrawableHoughLines(List<HoughLine> vlines, List<HoughLine> hlines){
		this.vlines = vlines;
		this.hlines = hlines;
	}
	
	@Override
	protected void drawObject(Graphics g) {
		g.setColor(vColor);
		for (Iterator it = vLines.iterator(); it.hasNext();) {
			java.awt.Point pt = (java.awt.Point) it.next();
			g.drawRect(pt.x - 4, pt.y - 4, 8, 8);
		}
		g.setColor(hColor);
		for (Iterator it = hLines.iterator(); it.hasNext();) {
			java.awt.Point pt = (java.awt.Point) it.next();
			g.drawRect(pt.x - 4, pt.y - 4, 8, 8);
		}
	}
	
	@Override
	protected void resetAWTPoints() {
		vLines = new ArrayList<java.awt.Point>(vlines.size());
		hLines = new ArrayList<java.awt.Point>(hlines.size());
		
		for (Iterator it = vlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			vLines.add(new java.awt.Point((int)origin.x + hl.getX(), (int)origin.y + hl.getY()));
		}
		
		for (Iterator it = hlines.iterator(); it.hasNext();) {
			HoughLine hl = (HoughLine) it.next();
			hLines.add(new java.awt.Point((int)origin.x + hl.getX(), (int)origin.y + hl.getY()));
		}
		
	}

}
