package imagemanager.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

public class ThumbnailComponent extends JButton {

	private Dimension imageArea;
	private int imageAreaX, imageAreaY;
	private final int margin = 10;
	private Image originalImage = new BufferedImage(300, 150,
			BufferedImage.TYPE_INT_RGB);
	private Image thumbnailImage;
	private int imageX, imageY;

	private static int DEF_COMP_WIDTH = 100, DEF_COMP_HEIGHT = 100;

	public ThumbnailComponent() {
		// try {
		// originalImage = ImageIO.read(new File("luj.png"));
		// thumbnailImage = new BufferedImage(1, 1,
		// ((BufferedImage) (originalImage)).getType());
		// setPreferredSize(new Dimension(DEF_COMP_WIDTH, DEF_COMP_HEIGHT));
		// createThumbnail(0.25);
		// fitThumbArea();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		thumbnailImage = new BufferedImage(1, 1,
				((BufferedImage) (originalImage)).getType());
		setPreferredSize(new Dimension(DEF_COMP_WIDTH, DEF_COMP_HEIGHT));
		createThumbnail(0.25);
		fitThumbArea();
	}

	public void rescale(double factor) {
		createThumbnail(factor);
		fitThumbArea();
		changeSize();
	}

	protected void createThumbnail(double factor) {
		thumbnailImage = originalImage.getScaledInstance(
				(int) (originalImage.getWidth(null) * factor), -1,
				BufferedImage.SCALE_FAST);
	}

	protected void fitThumbArea() {
		int a = (thumbnailImage.getHeight(null) < thumbnailImage.getWidth(null)) ? thumbnailImage
				.getWidth(null) : thumbnailImage.getHeight(null);
		imageArea = new Dimension(a, a);
		imageX = (imageArea.width - thumbnailImage.getWidth(null)) / 2;
		imageY = (imageArea.height - thumbnailImage.getHeight(null)) / 2;
	}

	protected void changeSize() {
		setPreferredSize(new Dimension(imageArea.width + margin,
				imageArea.height + margin));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(imageAreaX, imageAreaY, imageArea.width, imageArea.height);
		g.drawImage(thumbnailImage, imageAreaX + imageX, imageAreaY + imageY,
				null);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		imageAreaX = (width - imageArea.width) / 2;
		imageAreaY = (height - imageArea.height) / 2;
	}

}
