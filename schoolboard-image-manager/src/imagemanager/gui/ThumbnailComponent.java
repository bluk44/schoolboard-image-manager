package imagemanager.gui;

import ij.process.ImageProcessor;
import imageprocessing.Util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class ThumbnailComponent extends JButton {

	private Dimension imageArea;
	private int imageAreaX, imageAreaY;
	private final int margin = 10;

	private ImageProcessor originalImage;
	private Image thumbnailImage;
	
	private int imageX, imageY;
	
	private static int DEF_COMP_WIDTH = 100, DEF_COMP_HEIGHT = 100;
	
	public ThumbnailComponent(){
		originalImage  = Util.convertToImageProcessor(new BufferedImage(250, 168, BufferedImage.TYPE_3BYTE_BGR));
		rescale(0.25);
	}
	
	public ThumbnailComponent(BufferedImage image) {
		originalImage = Util.convertToImageProcessor(image);
		rescale(0.7);
	}

	public void rescale(double factor) {
		createThumbnail(factor);
		fitThumbArea();
		changeSize();
	}

	protected void createThumbnail(double factor) {
		
		thumbnailImage = originalImage.resize((int) (originalImage.getWidth()*factor)).getBufferedImage();
	}
	protected void fitThumbArea() {
		int a = (thumbnailImage.getHeight(null) < thumbnailImage.getWidth(null)) ? thumbnailImage
				.getWidth(null) : thumbnailImage.getHeight(null);
		imageArea = new Dimension(a, a);
		imageX = (imageArea.width - thumbnailImage.getWidth(null)) / 2;
		imageY = (imageArea.height - thumbnailImage.getHeight(null)) / 2;
	}

	protected void changeSize() {
	//	System.out.println(getPreferredSize());
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
