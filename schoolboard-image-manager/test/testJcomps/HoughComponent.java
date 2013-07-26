package testJcomps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

public class HoughComponent extends JComponent{
	List<Point[]> lines;
	BufferedImage img;
	List<Point> intersections;
	
	public HoughComponent(BufferedImage img, List<Point[]> lines){
		this.img = img;
		this.lines = lines;
		this.setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}
	
	public void setIntersections(List<Point> intersections){
		this.intersections = intersections;
	}
	
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.drawImage(img, 0, 0, null);
		for(int i = 0; i < lines.size(); i++){
			Point[] line = lines.get(i);
			g.setColor(Color.BLUE);
			g.drawLine(line[0].x, line[0].y, line[1].x, line[1].y);
		}
		g.setColor(Color.RED);
		if(intersections != null)
		for (Iterator iterator = intersections.iterator(); iterator.hasNext();) {
			Point p = (Point) iterator.next();
			g.drawRect(p.x - 1, p.y - 1, 2, 2);
		}
	}
}
