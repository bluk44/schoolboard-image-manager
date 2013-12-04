package imagemanager.gui;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;
import imageprocessing.geometry.Shape;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;

public class ImageComponent extends JComponent {

	protected static final int PREF_WIDTH = 800;
	protected static final int PREF_HEIGHT = 600;

	protected BufferedImage image;
	protected ArrayList<Shape> shapes = new ArrayList<Shape>();

	protected Point o = new Point(0, 0);
	protected double scale = 1.0;

	public ImageComponent(BufferedImage image) {
		this.image = image;
		setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));

	}
	
	public void addShape(Shape shape){
		shape.resize(scale);
		shape.add(o);
		
		shapes.add(shape);
	}
	
	public Shape getShape(int idx){
		Shape s = shapes.get(idx);
		s.sub(o);
		s.resize(1/scale);
		
		return s;
	}
	protected void calculateScale() {

		Point po = new Point(o);
		double oldScale = scale;

		for (Iterator it = shapes.iterator(); it.hasNext();) {
			Shape shape = (Shape) it.next();
			shape.sub(po);
		}

		double prefWidth = getPreferredSize().getWidth();
		double prefHeight = getPreferredSize().getHeight();

		double ox, oy;
		if (image.getWidth() <= prefWidth && image.getHeight() <= prefHeight) {
			ox = (prefWidth - image.getWidth()) / 2;
			oy = (prefHeight - image.getHeight()) / 2;

		} else {

			double sw = prefWidth / image.getWidth();
			double sh = prefHeight / image.getHeight();

			if (sw < sh) {
				scale = sw;
				ox = 0;
				oy = (prefHeight - image.getHeight() * scale) / 2;
			} else {
				scale = sh;
				ox = (prefWidth - image.getWidth() * scale) / 2;
				oy = 0;
			}

		}
		o = new Point(ox, oy);

		for (Iterator it = shapes.iterator(); it.hasNext();) {
			Shape shape = (Shape) it.next();
			shape.resize(scale / oldScale);
			shape.add(o);
		}
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		calculateScale();
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getPreferredSize().width, h = getPreferredSize().height;
		g.drawImage(image, (int) (o.x), (int) (o.y), (int) (o.x + w),
				(int) (o.y + h), 0, 0, (int) (w / scale), (int) (h / scale),
				null);

		for (Iterator it = shapes.iterator(); it.hasNext();) {
			Shape s = (Shape) it.next();
			s.draw(g);
		}

	}
}
