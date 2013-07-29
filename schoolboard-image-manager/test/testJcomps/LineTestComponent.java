package testJcomps;

import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Line;
import imageprocessing.geometry.Point;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

public class LineTestComponent extends JComponent {

	public List<Line> lines;
	public BufferedImage bg;
	
	public LineTestComponent(List<Line> lines, BufferedImage bg){
		this.lines = lines;
		this.bg = bg;
		setPreferredSize(new Dimension(bg.getWidth(), bg.getHeight()));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
		int w = bg.getWidth(), h = bg.getHeight();
		Line[] rect = new Line[4];
		
		rect[0] = new Line(new Point(0,0), new Point(1,0));
		rect[1] = new Line(new Point(0,h), new Point(1,h));
		rect[2] = new Line(new Point(0,0), new Point(0,1));
		rect[3] = new Line(new Point(w,0), new Point(w,1));
		
		for (Iterator it = lines.iterator(); it.hasNext();) {
			Line l = (Line) it.next();
			Point p = new Point(0,0);
			Point[] pts = new Point[2];
			int pidx = 0;
			for (int i = 0; i < rect.length; i++) {
				boolean intersects = Geo.intersect(l, rect[i], p);
				if(intersects && p.x <= w && p.y <= h && p.x >= 0 && p.y >= 0){
					pts[pidx++] = new Point(p);
				}			
			}
			g.setColor(Color.RED);
			g.drawLine((int)Math.round(pts[0].x), (int)Math.round(pts[0].y), (int)Math.round(pts[1].x), (int)Math.round(pts[1].y));

		}
	}
}
