package imagemanager.gui;

import imageprocessing.geometry.Point;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageComponent extends JComponent{
	
	protected BufferedImage image;
	
	protected static final int PREF_WIDTH = 800;
	protected static final int PREF_HEIGHT = 600;
	
	protected Point o;
	
	protected double scale;
	
	public ImageComponent(BufferedImage image){
		this.image = image;
		setPreferredSize(new Dimension(PREF_WIDTH, PREF_HEIGHT));
	}
	
	protected void calculateScale(){
		double prefWidth = getPreferredSize().getWidth();
		double prefHeight = getPreferredSize().getHeight();
		
		double ox, oy;
		if(image.getWidth() <= prefWidth && image.getHeight() <= prefHeight){
			ox = (prefWidth - image.getWidth()) / 2;
			oy = (prefHeight - image.getHeight()) / 2;
			
		} else {
			
			double sw = prefWidth / image.getWidth();
			double sh = prefHeight / image.getHeight();
			
			if(sw < sh){
				scale = sw;
				ox = 0;
				oy = (prefHeight - image.getHeight() * scale) / 2;
			} else{
				scale = sh;
				ox = (prefWidth - image.getWidth() * scale) / 2;
				oy = 0;
			}
							
		}
		o = new Point(ox, oy);
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
		g.drawImage(image, (int)(o.x), (int)(o.y), (int)(o.x + w),(int)(o.y + h), 0, 0,
				(int) (w / scale), (int) (h / scale), null);
//		g.drawImage(image, (int)(o.x + 0), (int)(o.y + 0), (int)(o.x + w), (int)(o.y + h), (int)(o.x + 0), (int)(o.y + 0),
//		(int) (o.x + w * scale), (int) (o.y + h * scale), null);
	}
}
