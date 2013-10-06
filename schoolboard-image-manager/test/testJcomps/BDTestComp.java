package testJcomps;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.drawing.Drawable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

public class BDTestComp extends JComponent{
	
	private BufferedImage bgImage;
	private List<Drawable> drawables = new ArrayList<Drawable>();
	private Point origin;
	
	public BDTestComp(BufferedImage bgImage){
		this.bgImage = bgImage;
		this.setPreferredSize(new Dimension(1000, 1000));
		
		double ox = (getPreferredSize().getWidth() - bgImage.getWidth()) / 2;
		double oy = (getPreferredSize().getHeight() - bgImage.getHeight()) / 2;
		
		origin = new Point(ox, oy);
	}
	
	public void addDrawable(Drawable d){
		drawables.add(d);
		d.setOrigin(origin);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(bgImage, (int)Math.round(origin.x), (int)Math.round(origin.y), null);
		
		for (Iterator it = drawables.iterator(); it.hasNext();) {
			Drawable d = (Drawable) it.next();
			d.draw(g);
		}
	}
	
	public Image getBufferedImage(){
	 	return this.createImage(16, 16);
	}
		
}
