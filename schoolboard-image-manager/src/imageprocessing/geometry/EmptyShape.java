package imageprocessing.geometry;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class EmptyShape extends Shape {

	public void draw(BufferedImage canvas) {}
	public void draw(Graphics g) {}
	public void resize(double scale) {}
	public void add(Point p) {}
	public void sub(Point p) {}

}
