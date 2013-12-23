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
		dis = new DrawablePolygon(new Polygon(new Point (97, 91.4), new Point(542, 107), 
				new Point(542, 398.2), new Point(97, 413.72)));
		updateFix();
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
		updateFix();
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
	
	private void updateFix(){
		double x1 = (dis.getPoint(0).x + dis.getPoint(3).x) / 2.0;
		double x2 = (dis.getPoint(1).x + dis.getPoint(2).x) / 2.0;
		double y1 = (dis.getPoint(0).y + dis.getPoint(1).y) / 2.0;
		double y2 = (dis.getPoint(2).y + dis.getPoint(3).y) / 2.0;
		
		DrawablePolygon fixed = new DrawablePolygon(new Polygon(new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2)));
		this.fix = fixed;
		
	}
	public void setDis(Polygon poly){
		this.dis = new DrawablePolygon(poly);
	}
	
	public void setFix(Polygon poly){
		this.fix = new DrawablePolygon(poly);
	}
	
	public Polygon getDistorted(){
		return dis.getPolygon();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(image != null) g.drawImage(image, 0, 0, null);
		dis.setColor(Color.RED);
		fix.setColor(Color.GREEN);
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
}
