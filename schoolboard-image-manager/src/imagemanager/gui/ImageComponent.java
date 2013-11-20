package imagemanager.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.python.antlr.PythonParser.continue_stmt_return;

public class ImageComponent extends Component {
	public BufferedImage image;

	protected static int COMP_MAX_WIDTH = 800;
	protected static int COMP_MAX_HEIGHT = 600;

	protected Rectangle srcRect;
	protected Rectangle dstRect; // x0 = 0, y0 = 0
	
	protected double magStep = 0.2;
	protected double scale = 1.0;

	protected double mapScale = 0.1;
	private int srX = 5, srY = 5;

	public ImageComponent(BufferedImage img) {
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
				if (e.getButton() == MouseEvent.BUTTON1) {					
					double x0 = srcRect.x * scale;
					double y0 = srcRect.y * scale;
					
					double cx = x0 + e.getX();
					double cy = y0 + e.getY();
					
					int absx = (int)Math.round(cx / scale);
					int absy = (int)Math.round(cy / scale);
					scale += magStep;

					setScale(scale, absx, absy);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					double x0 = srcRect.x * scale;
					double y0 = srcRect.y * scale;
					
					double cx = x0 + e.getX();
					double cy = y0 + e.getY();
					
					int absx = (int)Math.round(cx / scale);
					int absy = (int)Math.round(cy / scale);
					scale -= magStep;

					setScale(scale, absx, absy);
				}
			}
		});
	}

	/**
	 * 
	 * @param scale skala oryginalnego obrazka
	 * @param absCenterX 
	 * @param absCenterY pozycja kursora na oryginalnym obrazku
	 */
	public void setScale(double scale, int absCenterX, int absCenterY) {
		this.scale = scale;
		calculateRects(absCenterX, absCenterY);
		fitComponentSize();
	}

	public void setScale(double scale) {
		setScale(scale, image.getWidth() / 2, image.getHeight() / 2);
	}
	
	public void setMagStep(double magStep){
		this.magStep = magStep;
	}
	
	@Override
	public void paint(Graphics g) {
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
		g.drawRect((int)(srX + srcRect.x * mapScale) ,(int)(srY + srcRect.y * mapScale),
				(int) (srcRect.width * mapScale),
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

			srcRect = calculateSrcRect(absCenterX, absCenterY, srcRectW, srcRectH);
			dstRect = new Rectangle(0, 0, (int) (srcRect.width * scale),
					(int) (srcRect.height * scale));

		}

	}

	private Rectangle calculateSrcRect(int absCenterX, int absCenterY, double width,
			double height) {
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

		return new Rectangle((int) Math.round(x0), (int) Math.round(y0), (int) Math.round((x1 - x0)),
				(int) Math.round(y1 - y0));
	}

	private void fitComponentSize() {

		setPreferredSize(new Dimension(dstRect.width, dstRect.height));
		repaint();
	}
}
