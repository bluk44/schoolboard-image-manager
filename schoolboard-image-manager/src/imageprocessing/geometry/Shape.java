package imageprocessing.geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public abstract class Shape {
	
	protected Color color = Color.WHITE;
	protected int rad = 0;
	
	public abstract void draw(BufferedImage canvas);
	public abstract void draw(Graphics g);
	public abstract void resize(double scale);
	public abstract void add(Point p);
	public abstract void sub(Point p);
	
	public static void drawLine(BufferedImage canvas, Color color, int rad, Point p1, Point p2){
		int x1 = (int)Math.round(p1.x), y1 = (int)Math.round(p1.y);
		int x2 = (int)Math.round(p2.x), y2 = (int)Math.round(p2.y);
		
		x1 = (x1 == canvas.getWidth()) ? x1 -1 : x1;
		x2 = (x2 == canvas.getWidth()) ? x2 -1 : x2;
		
		y1 = (y1 == canvas.getHeight()) ? y1 -1 : y1;
		y2 = (y2 == canvas.getHeight()) ? y2 -1 : y2;
		
		// zmienne pomocnicze
		int d, dx, dy, ai, bi, xi, yi;
		int x = x1, y = y1;
		
		// ustalenie kierunku rysowania
		if (x1 < x2) {
			xi = 1;
			dx = x2 - x1;
		} else {
			xi = -1;
			dx = x1 - x2;
		}
		// ustalenie kierunku rysowania
		if (y1 < y2) {
			yi = 1;
			dy = y2 - y1;
		} else {
			yi = -1;
			dy = y1 - y2;
		}
		
		// oś wiodąca OX
		if (dx > dy) {
//			System.out.println("leading x");

			ai = (dy - dx) * 2;
			bi = dy * 2;
			d = bi - dx;
			// pętla po kolejnych x
			while (x != x2) {
				// test współczynnika
				if (d >= 0) {
					x += xi;
					y += yi;
					d += ai;
				} else {
					d += bi;
					x += xi;
				}
				for(int i = -rad; i <= rad; i++){
					for(int b = 0; b < canvas.getRaster().getNumBands(); b++){
						if(y+i < 0 || y+i >= canvas.getWidth()) continue;
//						canvas.getRaster().setSample(x, y+i, b, 255);
						setPixelColor(canvas.getRaster(), color, x, y+i);

					}
				}
			}
		}
		// oś wiodąca OY
		else {
//			System.out.println("leading y");

			ai = (dx - dy) * 2;
			bi = dx * 2;
			d = bi - dy;
			// pętla po kolejnych y
			while (y != y2) {
				// test współczynnika
				if (d >= 0) {
					x += xi;
					y += yi;
					d += ai;
				} else {
					d += bi;
					y += yi;
				}
				for(int i = -rad; i <= rad; i++){
					for(int b = 0; b < canvas.getRaster().getNumBands(); b++){
						if(x+i < 0 || x+i >= canvas.getWidth()) continue;
//						canvas.getRaster().setSample(x+i, y, b, 255);
						setPixelColor(canvas.getRaster(), color, x+i, y);
					}
				}
			}
		}
	}
	
	public static void setPixelColor(WritableRaster r, Color c, int x, int y){
		if(x < 0 || y < 0 || x > r.getWidth() - 1 || y > r.getHeight() - 1) return;
		
		if(r.getNumBands() == 3){
			r.setSample(x, y, 0, c.getRed());
			r.setSample(x, y, 1, c.getGreen());
			r.setSample(x, y, 2, c.getBlue());
		} else if(r.getNumBands() == 4){
			r.setSample(x, y, 0, c.getRed());
			r.setSample(x, y, 1, c.getGreen());
			r.setSample(x, y, 2, c.getBlue());
			r.setSample(x, y, 3, c.getBlue());			
		} else {
			for(int i = 0; i < r.getNumBands(); i++){
				r.setSample(x, y, i, 128);
			}
		}
		
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getRad() {
		return rad;
	}

	public void setRad(int rad) {
		this.rad = rad;
	}
	
}
