package testJcomps;

import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;
import imageprocessing.geometry.drawing.DrawablePolygon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class TransformationTestComp extends JComponent{
	
	protected BufferedImage image;
	protected DrawablePolygon dis, fix;
	
	public TransformationTestComp(BufferedImage image){
		this.image = image;
		dis = new DrawablePolygon(new Polygon(new Point (50, 50), new Point(100, 50), 
				new Point(100, 100), new Point(50, 100)));
		dis.setColor(Color.BLUE);
		
		fix = new DrawablePolygon(new Polygon(new Point (50, 50), new Point(100, 50), 
				new Point(100, 100), new Point(50, 100)));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, null);
		dis.draw(g);
		fix.draw(g);
	}
	
	public void print(){
		int imgW = image.getWidth(), imgH = image.getHeight();
		for(int b = 0;b < image.getRaster().getNumBands(); b++){
			
			int[] samples = new int[imgH*imgW];
			image.getRaster().getSamples(0, 0, imgW, imgH, b, samples);
			System.out.println("band "+b+": ");
			for (int i = 0; i < imgH; i++) {
				for (int j = 0; j < imgW; j++) {
					System.out.format("%04d ",samples[i*imgW+j]);
				}
				System.out.println();
			}
		}
	}
	private void fitToImage(){
		if(image == null){
			return;
		}
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		repaint();
	}
}
