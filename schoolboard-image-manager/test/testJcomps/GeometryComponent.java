package testJcomps;

import imageprocessing.geometry.drawing.Drawable;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

public class GeometryComponent extends JComponent {
		
	protected List<Drawable> drawables = new ArrayList<Drawable>();
	protected BufferedImage bg;

	public List<Drawable> getDrawables() {
		return drawables;
	}

	public void setDrawables(List<Drawable> drawables) {
		this.drawables = drawables;
	}

	public BufferedImage getBg() {
		return bg;
	}

	public void setBg(BufferedImage bg) {
		this.bg = bg;
		setPreferredSize(new Dimension(bg.getWidth(), bg.getHeight()));
	}
	
	public void addDrawable(Drawable d){
		drawables.add(d);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
		for (Iterator it = drawables.iterator(); it.hasNext();) {
			Drawable d = (Drawable) it.next();
			d.draw(g);
		}
	}

}
