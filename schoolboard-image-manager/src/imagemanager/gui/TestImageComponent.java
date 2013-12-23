package imagemanager.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

public class TestImageComponent extends Component {
	public BufferedImage image;
	
	protected static int COMP_MAX_WIDTH = 800;
	protected static int COMP_MAX_HEIGHT = 600;

	protected Rectangle srcRect;
	protected Rectangle dstRect; // x0 = 0, y0 = 0

	protected double magStep = 0.2;
	protected double scale = 1.0;

	protected double mapScale = 0.1;
	private int srX = 5, srY = 5;

	private boolean magActive = false;
	private boolean dragActive = false;
	
	public TestImageComponent(BufferedImage img) {
		this.image = img;
		
		setScale(1.0);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!magActive)
					return;
				if (e.getButton() == MouseEvent.BUTTON1) {
					Point absCoords = getAbsCoords(e.getX(), e.getY());
					scale += magStep;

					setScale(scale, absCoords.x, absCoords.y);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					Point absCoords = getAbsCoords(e.getX(), e.getY());
					scale -= magStep;

					setScale(scale, absCoords.x, absCoords.y);
				}
				Component src = (Component) e.getSource();
				src.getParent().dispatchEvent(e);

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (!dragActive)
					return;
//				int[] absCoords = getAbsCoords(e.getX(), e.getY());
//				moveSrcRectangle(srcRect.x + srcRect.width / 2 - absCoords[0],
//						srcRect.y + srcRect.height / 2 - absCoords[1]);
//				System.out.println("new ox "
//						+ (srcRect.x + srcRect.width / 2 - absCoords[0])
//						+ " new oy "
//						+ (srcRect.y + srcRect.height / 2 - absCoords[1]));
//				repaint();
				
				Point abs = getAbsCoords(e.getX(), e.getY());
				int dx = srcRect.x + srcRect.width / 2 - abs.x;
				int dy = srcRect.y + srcRect.height / 2 - abs.y;
				
				//System.out.println("dx "+dx+" dy "+dy);
				System.out.println("mouse x "+e.getX()+" mouse y "+e.getY());
				srcRect.x +=(int) (dx / 4);
				srcRect.y +=(int) (dy / 4);
				
				if(srcRect.x < 0 ) srcRect.x = 0;
				if(srcRect.x > image.getWidth() - srcRect.width) srcRect.x = image.getWidth() - srcRect.width;
				
				if(srcRect.y < 0 ) srcRect.y = 0;
				if(srcRect.y > image.getHeight() - srcRect.height) srcRect.y = image.getHeight() - srcRect.height;				
				repaint();
			}
		});
	}

	public boolean isMagActive() {
		return magActive;
	}
	
	public double getScale(){
		return scale;
	}
	
	public void setMagActive(boolean magActive) {
		this.magActive = magActive;
	}

	public boolean isDragActive() {
		return dragActive;
	}

	public void setDragActive(boolean dragActive) {
		this.dragActive = dragActive;
	}

	/**
	 * 
	 * @param scale
	 *            skala oryginalnego obrazka
	 * @param absCenterX
	 * @param absCenterY
	 *            pozycja kursora na oryginalnym obrazku
	 */
	public void setScale(double scale, int absCenterX, int absCenterY) {
		this.scale = scale;
		calculateRects(absCenterX, absCenterY);
		fitComponentSize();
	}

	public void setScale(double scale) {
		setScale(scale, image.getWidth() / 2, image.getHeight() / 2);
	}
	
	public void setMagStep(double magStep) {
		this.magStep = magStep;
	}
	
	public void fitToMaxSize(){
		double sw = image.getWidth() / COMP_MAX_WIDTH;
		double sh = image.getHeight() / COMP_MAX_HEIGHT;
		double scale = sw < sh ? sw : sh;
		
		setScale(scale);
	}
	
	@Override
	public void paint(Graphics g) {
		System.out.println("image comp paint called");
		g.drawImage(image, dstRect.x, dstRect.y, dstRect.x + dstRect.width,
				dstRect.y + dstRect.height, srcRect.x, srcRect.y, srcRect.x
						+ srcRect.width, srcRect.y + srcRect.height, null);
		paintRects(g);

	}

	private void paintRects(Graphics g) {
		// obwodka zdjecia w skali
		g.setColor(Color.RED);
		g.drawRect(srX, srY, (int) (image.getWidth() * mapScale),
				(int) (image.getHeight() * mapScale));

		// prostokat zrodlowy w skali
		g.setColor(Color.GREEN);
		g.drawRect((int) (srX + srcRect.x * mapScale), (int) (srY + srcRect.y
				* mapScale), (int) (srcRect.width * mapScale),
				(int) (srcRect.height * mapScale));

	}

	protected void calculateRects(int absCenterX, int absCenterY) {
		int scaledImgW = (int) Math.round(image.getWidth() * scale);
		int scaledImgH = (int) Math.round(image.getHeight() * scale);

		if (scaledImgW <= COMP_MAX_WIDTH && scaledImgH <= COMP_MAX_WIDTH) {

			srcRect = new Rectangle(0, 0, image.getWidth(), image.getHeight());
			dstRect = new Rectangle(0, 0, scaledImgW, scaledImgH);

		} else {
			int dstRectW = (scaledImgW > COMP_MAX_WIDTH) ? COMP_MAX_WIDTH
					: scaledImgW;
			int dstRectH = (scaledImgH > COMP_MAX_HEIGHT) ? COMP_MAX_HEIGHT
					: scaledImgH;

			// obliczanie wycinka zdjecia zrodlowego
			double srcRectW = dstRectW / scale;
			double srcRectH = dstRectH / scale;

			srcRect = calculateSrcRect(absCenterX, absCenterY, srcRectW,
					srcRectH);
			dstRect = new Rectangle(0, 0, (int) (srcRect.width * scale),
					(int) (srcRect.height * scale));

		}

	}

	private Rectangle calculateSrcRect(int absCenterX, int absCenterY,
			double width, double height) {
		double x0 = absCenterX - width / 2, x1 = absCenterX + width / 2;
		double y0 = absCenterY - height / 2, y1 = absCenterY + height / 2;

		if (x0 < 0) {
			x1 -= x0;
			x0 = 0;
		} else if (x1 > image.getWidth()) {
			x0 -= (x1 - image.getWidth());
			x1 = image.getWidth();
		}

		if (y0 < 0) {
			y1 -= y0;
			y0 = 0;
		} else if (y1 > image.getHeight()) {
			y0 -= (y1 - image.getHeight());
			y1 = image.getHeight();
		}

		return new Rectangle((int) Math.round(x0), (int) Math.round(y0),
				(int) Math.round((x1 - x0)), (int) Math.round(y1 - y0));
	}

	private void moveSrcRectangle(int absCenterX, int absCenterY) {
		double x0 = absCenterX - srcRect.width / 2, x1 = absCenterX
				+ srcRect.width / 2;
		double y0 = absCenterY - srcRect.height / 2, y1 = absCenterY
				+ srcRect.height / 2;

		if (x0 < 0) {
			x1 -= x0;
			x0 = 0;
		} else if (x1 > image.getWidth()) {
			x0 -= (x1 - image.getWidth());
			x1 = image.getWidth();
		}

		if (y0 < 0) {
			y1 -= y0;
			y0 = 0;
		} else if (y1 > image.getHeight()) {
			y0 -= (y1 - image.getHeight());
			y1 = image.getHeight();
		}

		srcRect.x = (int) Math.round(x0);
		srcRect.y = (int) Math.round(y0);
	}

	private void fitComponentSize() {

		setPreferredSize(new Dimension(dstRect.width, dstRect.height));
		repaint();
	}

	private Point getAbsCoords(int compX, int compY) {
		double x0 = srcRect.x * scale;
		double y0 = srcRect.y * scale;

		double cx = x0 + compX;
		double cy = y0 + compY;

		return new Point((int) Math.round(cx / scale), (int) Math.round(cy / scale));
	}
	
}
