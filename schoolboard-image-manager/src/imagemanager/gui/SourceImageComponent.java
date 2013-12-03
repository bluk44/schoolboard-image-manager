package imagemanager.gui;

import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;
import imageprocessing.geometry.drawing.DrawablePolygon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class SourceImageComponent extends JComponent {

	Polygon distortedPoly;
	Polygon fixedPoly;
	
	BufferedImage image;
	
	protected static final int COMP_WIDTH = 800;
	protected static final int COMP_HEIGHT = 600;

	private double scale = 1.0;
	private boolean boardSelected = false;
	
	private boolean boardSelectionMode = false;
	private Point[] boardPoints = new Point[4];
	private int pIdx;
	public SourceImageComponent(){
		this(Util.readFromFile("images/blackboard/bb31.png"));
	}
	
	public SourceImageComponent(BufferedImage image) {
		this.image = image;
		
		double sw = (double)image.getWidth() / COMP_WIDTH;
		double sh = (double)image.getHeight() / COMP_HEIGHT;
		scale = sw < sh ? sw : sh;
		
		setPreferredSize(new Dimension(COMP_WIDTH, COMP_HEIGHT));
		
		addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			
			@Override
			public void mouseClicked(MouseEvent e) {

				if(boardSelectionMode){
					if(pIdx < 3){

						boardPoints[pIdx++] = new Point(e.getX(), e.getY());
						repaint();
					}else if(pIdx == 3){ 
						boardPoints[pIdx++] = new Point(e.getX(), e.getY());
						calculateBoardRegions();
						boardSelected = true;
						boardSelectionMode = false;
						repaint();
					}
				} else if(boardSelected){
					Point p = new Point(e.getX(), e.getY());
					int idx = distortedPoly.getNearestPointIdx(p);
					distortedPoly.setPoint(idx, p);
					calculateFixed();
					repaint();
				}
			}
		});
		
		addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("source comp key pressed");				
			}
		});
	}

	public void setBoardSelectMode(){
		boardPoints = new Point[4];
		pIdx = 0;
		boardSelectionMode = true;
		deleteBoardRegion();
	}
	
	public void unsetBoardSelectMode(){
		boardPoints = new Point[4];
		pIdx = 0;
		boardSelectionMode = false;	
		repaint();
	}
	
	public void deleteBoardRegion(){
		distortedPoly = null;
		boardSelected = false;
		repaint();
	}
	
	public void setBoardRealSize(Polygon boardPoly) {
		unsetBoardSelectMode();
		Polygon poly = new Polygon(boardPoly);
		poly.resize(1/scale);
		this.distortedPoly = poly;
		calculateFixed();
		boardSelected = true;
		repaint();
	}
	
	public boolean isBoardSelected(){
		return boardSelected;
	}
	
	public boolean isBoardSelectMode(){
		return boardSelectionMode;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public Polygon getDistortedRealSize(){
		Polygon poly = new Polygon(distortedPoly);
		poly.resize(scale);
		return poly;
	}
	
	public Polygon getFixedRealSize(){
		Polygon poly = new Polygon(fixedPoly);
		poly.resize(scale);
		return poly;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, COMP_WIDTH, COMP_HEIGHT, 0, 0,
				(int) (COMP_WIDTH * scale), (int) (COMP_HEIGHT * scale), null);
		
		g.setColor(Color.RED);

		if(boardSelectionMode){
		for(int i = 0; i < pIdx; i++)
		g.fillOval((int)boardPoints[i].x-2, (int)boardPoints[i].y-2, 4, 4);
		}
		if(!boardSelected) return;
		
		g.setColor(Color.RED);
		drawPoly(distortedPoly, g);
		
		g.setColor(Color.GREEN);
		drawPoly(fixedPoly, g);
		
	}
	
	private void calculateBoardRegions() {
		distortedPoly = new Polygon(boardPoints);
		calculateFixed();
	}
	
	private void calculateFixed(){
		double x1 = (distortedPoly.getPoint(0).x + distortedPoly.getPoint(3).x) / 2.0;
		double x2 = (distortedPoly.getPoint(1).x + distortedPoly.getPoint(2).x) / 2.0;
		double y1 = (distortedPoly.getPoint(0).y + distortedPoly.getPoint(1).y) / 2.0;
		double y2 = (distortedPoly.getPoint(2).y + distortedPoly.getPoint(3).y) / 2.0;
		
		Polygon fixedPoly = new Polygon(new Point(x1, y1), new Point(x2, y1), new Point(x2, y2), new Point(x1, y2));
		this.fixedPoly = fixedPoly;
	}
	
	private void drawPoly(Polygon poly, Graphics g){
		g.drawLine((int) poly.getPoint(0).x,
				(int) poly.getPoint(0).y,
				(int) poly.getPoint(1).x,
				(int) poly.getPoint(1).y);
		g.drawLine((int) poly.getPoint(1).x,
				(int) poly.getPoint(1).y,
				(int) poly.getPoint(2).x,
				(int) poly.getPoint(2).y);
		g.drawLine((int) poly.getPoint(2).x,
				(int) poly.getPoint(2).y,
				(int) poly.getPoint(3).x,
				(int) poly.getPoint(3).y);
		g.drawLine((int) poly.getPoint(0).x,
				(int) poly.getPoint(0).y,
				(int) poly.getPoint(3).x,
				(int) poly.getPoint(3).y);
	}
}
