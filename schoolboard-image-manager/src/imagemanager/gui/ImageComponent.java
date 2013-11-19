package imagemanager.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class ImageComponent extends Component {

	public BufferedImage image;
	private static Dimension MAX_SIZE = new Dimension(400, 300);

	private Magnifier mag = null;

	public ImageComponent() {
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
				System.out.println(e.getButton());
				if (e.getButton() == MouseEvent.BUTTON1) {
					mag.increaseMag(e.getX(), e.getY());
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					mag.decreaseMag(e.getX(), e.getY());
				}
				// mag.increaseMag(image.getWidth() / 2, image.getHeight() / 2);
				repaint();
			}
		});

		image = new BufferedImage(600, 300, BufferedImage.TYPE_3BYTE_BGR);

	}

	public void setImage(BufferedImage image) {
		this.image = image;
		mag = new Magnifier();
		fitToImage();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintImage(g);
	}

	private void paintImage(Graphics g) {
		g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(),
				mag.srcRect.x, mag.srcRect.y,
				mag.srcRect.x + mag.srcRect.width, mag.srcRect.y
						+ mag.srcRect.height, null);
		mag.paintFrames(g);
	}

	private void fitToImage() {

		if (image.getWidth() > image.getHeight()) {
			if (image.getWidth() > MAX_SIZE.width) {
				double scale = (double) MAX_SIZE.width
						/ (double) image.getWidth();
				mag.setMag(scale);
				setPreferredSize(new Dimension((int)(image.getWidth() * scale), (int)(image.getHeight() * scale)));

			} else {
				setPreferredSize(new Dimension(image.getWidth(),
						image.getHeight()));
			}

		} else {
			if (image.getHeight() > MAX_SIZE.height) {
				double scale = (double) MAX_SIZE.height
						/ (double) image.getHeight();
				mag.setMag(scale);

				setPreferredSize(new Dimension((int)(image.getWidth() * scale), (int)(image.getHeight() * scale)));
			} else {
				setPreferredSize(new Dimension(image.getWidth(),
						image.getHeight()));
			}

		}
		System.out.println(getPreferredSize());
	}

	private class Magnifier {
		public Rectangle imgRect = new Rectangle(0, 0, image.getWidth(),
				image.getHeight());
		public Rectangle srcRect = new Rectangle(0, 0, image.getWidth(),
				image.getHeight());
		
		
		public double magStep = 0.2;
		public double actMag = 1.0;

		public int mapX0 = 5, mapY0 = 5;
		public double mapScale = 0.15;

		// public double rx = 0, ry = 0;

		public void increaseMag(int mouseX, int mouseY) {
			double realX = srcRect.x + mouseX / actMag;
			double realY = srcRect.y + mouseY / actMag;
			System.out.println(" realX: "+realX+" realY: "+realY);

			actMag += magStep;
			// rx = realX; ry = realY;

			countRects(realX, realY);
		}

		public void decreaseMag(int mouseX, int mouseY) {
			double realX = srcRect.x + mouseX / actMag;
			double realY = srcRect.y + mouseY / actMag;
			System.out.println(" realX: "+realX+" realY: "+realY);

			actMag -= magStep;
			// rx = realX; ry = realY;

			countRects(realX, realY);
		}

		public void setMag(double scale) {
			actMag = scale;
			double x = imgRect.width / 2;
			double y = imgRect.height / 2;
			countRects(x, y);
		}

		private void countRects(double realX, double realY) {
			double newWidth = imgRect.getWidth() / actMag, newHeight = image
					.getHeight() / actMag;
			double newX0 = realX - newWidth / 2, newX1 = realX + newWidth / 2;
			double newY0 = realY - newHeight / 2, newY1 = realY + newHeight / 2;

			if (newX0 < 0) {
				newX1 -= newX0;
				newX0 = 0;
			} else if (newX1 > image.getWidth()) {
				newX0 -= (newX1 - image.getWidth());
				newX1 = image.getWidth();
			}

			if (newY0 < 0) {
				newY1 -= newY0;
				newY0 = 0;
			} else if (newY1 > image.getHeight()) {
				newY0 -= (newY1 - image.getHeight());
				newY1 = image.getHeight();
			}

			srcRect = new Rectangle((int) newX0, (int) newY0,
					(int) (newX1 - newX0), (int) (newY1 - newY0));
		}

		public void paintFrames(Graphics g) {
			Color prv = g.getColor();
			g.setColor(Color.BLACK);
			g.drawRect(mapX0, mapY0, (int) (imgRect.width * mapScale),
					(int) (imgRect.height * mapScale));

			g.setColor(Color.RED);
			g.drawRect((int) (mapX0 + srcRect.x * mapScale),
					(int) (mapY0 + srcRect.y * mapScale),
					(int) (srcRect.width * mapScale),
					(int) (srcRect.height * mapScale));

			// g.setColor(Color.BLUE);
			// g.fillOval(mapX0 + (int)(rx * mapScale), mapY0 + (int)(ry *
			// mapScale), 5, 5);
			g.setColor(prv);
		}

	}
}
