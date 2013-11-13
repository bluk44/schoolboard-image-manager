package imagemanager.gui;

import imagemanager.model.ImageRecord;
import imageprocessing.Util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class ThumbnailComponent extends JButton {

	// ustalane przez rodzica
	private Dimension thumbImageArea;

	// położenie obszaru miniaturki (zależne od rzeczywisttch wymiarow,
	// imageArea i marginesu)
	private int imageAreaX = 0, imageAreaY = 0;

	// gorny margines
	private final int margin = 10;

	// private BufferedImage originalImage;
	private ImageRecord imageRecord;
	private BufferedImage thumbnailImage;

	private int imageX, imageY;

	// public ThumbnailComponent(int thumbSize) {
	// originalImage = new BufferedImage(250,
	// 168, BufferedImage.TYPE_3BYTE_BGR);
	// setThumbImageArea(thumbSize);
	// }

	public ThumbnailComponent(ImageRecord imageRecord, int thumbSize) {
		this.imageRecord = imageRecord;
		setThumbImageArea(thumbSize);
	}

	public void setThumbImageArea(int imageAreaSize) {
		thumbImageArea = new Dimension(imageAreaSize, imageAreaSize);
		setMinimumSize(new Dimension(thumbImageArea.getSize().width + 2
				* margin, thumbImageArea.getSize().height + 2 * margin));
		resizeThumbImage();
	}

	private void resizeThumbImage() {
		int a = (imageRecord.getThumbnail().getWidth() > imageRecord
				.getThumbnail().getHeight()) ? imageRecord.getThumbnail()
				.getWidth() : imageRecord.getThumbnail().getHeight();
		double ratio = thumbImageArea.getWidth() / a;
		thumbnailImage = Util.resize(imageRecord.getThumbnail(), ratio);

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(imageAreaX, imageAreaY, thumbImageArea.width,
				thumbImageArea.height);
		g.drawImage(thumbnailImage, imageX, imageY, null);
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		imageAreaX = (width - thumbImageArea.width) / 2;
		imageAreaY = (height - thumbImageArea.height) / 2;
		// imageX = imageAreaX;
		// imageY = imageAreaY;
		imageX = (int) (imageAreaX + ((thumbImageArea.getWidth() - thumbnailImage
				.getWidth()) / 2.d));
		imageY = (int) (imageAreaY + ((thumbImageArea.getHeight() - thumbnailImage
				.getHeight()) / 2.d));

	}
	
	public Integer getImageId(){
		return imageRecord.getId();
	}
}
