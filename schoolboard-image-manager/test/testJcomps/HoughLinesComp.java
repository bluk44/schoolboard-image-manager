package testJcomps;

import imageprocessing.geometry.drawing.DrawableHoughLines;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class HoughLinesComp extends JComponent {
	private DrawableHoughLines hl;
	private BufferedImage bg;
	
	public HoughLinesComp(BufferedImage bg, DrawableHoughLines hl){
		this.bg = bg;
		this.hl = hl;
		setPreferredSize(new Dimension(bg.getWidth(), bg.getHeight()));
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, null);
		hl.draw(g);
	}

}
