package testJcomps;

import imageprocessing.geometry.Geo;
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
	public DrawablePolygon dis, fix;
	
	public TransformationTestComp(){};
	
	public TransformationTestComp(BufferedImage image){
		this.image = image;
		dis = new DrawablePolygon(new Polygon(new Point (102, 127), new Point(535, 110), 
				new Point(535, 437), new Point(99, 405)));
		dis.setColor(Color.BLUE);
		
		fix = new DrawablePolygon(new Polygon(new Point (100, 125), new Point(535, 125), 
				new Point(535, 405), new Point(100, 405)));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(image != null) g.drawImage(image, 0, 0, null);
		if(dis != null) dis.draw(g);
		if(fix != null) fix.draw(g);
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
	}
	
	public void updateDis(Point p){
		double minLength = 999999999, length = 0; 
		int	i = 0, idx = 0;
		for(int k = 0; k < 4; k++){
			length = Geo.dist(dis.getPoint(k), p);
			if(length < minLength){
				minLength = length;
				idx = k;
			}
			
		}
		
		dis.setPoint(idx, p);
		repaint();
	}
	
	public void updateFix(Point p){
		double minLength = 999999999, length = 0; 
		int	i = 0, idx = 0;
		for(int k = 0; k < 4; k++){
			length = Geo.dist(fix.getPoint(k), p);
			if(length < minLength){
				minLength = length;
				idx = k;
			}
			
		}
		
		fix.setPoint(idx, p);
		repaint();
	}
	
	public Polygon getDistorted(){
		return dis.getPolygon();
	}
}
