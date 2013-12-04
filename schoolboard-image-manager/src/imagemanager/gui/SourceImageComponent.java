package imagemanager.gui;

import imageprocessing.Util;
import imageprocessing.geometry.EmptyShape;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.PointSet;
import imageprocessing.geometry.Polygon;
import imageprocessing.geometry.Shape;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SourceImageComponent extends ImageComponent {


	private boolean boardSelected = false;
	private boolean selectionMode = false;

	{
		initShapes();
	}

	public SourceImageComponent() {
		this(Util.readFromFile("images/blackboard/bb31.png"));

	}

	public SourceImageComponent(BufferedImage image) {
		super(image);

		addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Point p = new Point(e.getX(), e.getY());

				if (selectionMode) {				
					PointSet pts = (PointSet) shapes.get(0);

					if (pts.getNextIdx() < 4 && isPointOnImage(p)) {
						pts.addPoint(p);
						if (pts.getNextIdx() == 4) {
							createBoardRegion();
							
						}
						repaint();
					}


				} 
				else if (boardSelected && isPointOnImage(p)) {
					Polygon dis = (Polygon)shapes.get(1);
					
					int idx = dis.getNearestPointIdx(p);
					dis.setPoint(idx, p);
					createFixedBoardRegion();
					repaint();
				}
			}
		});
	}

	public void setSelectionMode() {
		initShapes();
		boardSelected = false;
		selectionMode = true;
		repaint();
	}

	public void unsetSelectionMode() {
		initShapes();
		boardSelected = false;	
		selectionMode = false;
		repaint();
	}
		
	private void createBoardRegion() {
		Polygon dis = ((PointSet) shapes.get(0)).getPolygon();
		((PointSet) shapes.get(0)).reset();
		dis.setColor(Color.RED);
		shapes.set(1, dis);
		createFixedBoardRegion();
	}
	
	private void createFixedBoardRegion() {
		Polygon dist = (Polygon) shapes.get(1);

		double x1 = (dist.getPoint(0).x + dist.getPoint(3).x) / 2.0;
		double x2 = (dist.getPoint(1).x + dist.getPoint(2).x) / 2.0;
		double y1 = (dist.getPoint(0).y + dist.getPoint(1).y) / 2.0;
		double y2 = (dist.getPoint(2).y + dist.getPoint(3).y) / 2.0;

		Polygon fixed = new Polygon(new Point(x1, y1), new Point(x2, y1),
				new Point(x2, y2), new Point(x1, y2));
		fixed.setColor(Color.green);
		shapes.set(2, fixed);
		
		boardSelected = true;
		selectionMode = false;
	}
	

	public void setAutodetectedBoard(Polygon boardPoly) {
		initShapes();
		shapes.add(boardPoly);
		createFixedBoardRegion();
	}
	
	
	public boolean isBoardSelected() {
		return boardSelected;
	}

	public boolean isBoardSelectMode() {
		return selectionMode;
	}

	public BufferedImage getImage() {
		return image;
	}

	public Polygon getDistorted() {
		if(shapes.get(1) instanceof Polygon) return (Polygon) getShape(1);
		
		return null;
	}

	public Polygon getFixed() {
		if(shapes.get(2) instanceof Polygon) return (Polygon) getShape(2);
		
		return null;
	}


	private void initShapes() {
		shapes = new ArrayList<Shape>();
		PointSet ps = new PointSet();
		ps.setColor(Color.RED);
		shapes.add(ps);
		shapes.add(new EmptyShape());
		shapes.add(new EmptyShape());
	}

	private boolean isPointOnImage(Point p) {
		double w = image.getWidth() * scale, h = image.getHeight() * scale;

		if (p.x < o.x || p.x > o.x + w || p.y < o.y || p.y > o.y + h)
			return false;

		return true;
	}

}
