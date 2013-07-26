package testJcomps;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class ImageComponent extends JComponent {
	
	protected BufferedImage image;
	
	public ImageComponent(){
		
	}
	
	public ImageComponent(File imageFile) throws IOException{
		loadImage(imageFile);
	}
	
	public ImageComponent(BufferedImage image){
		setImage(image);
	}
	
	public void loadImage(File imageFile) throws IOException{
		image = ImageIO.read(imageFile);
		fitToImage();
	}
	
	public void setImage(BufferedImage image){
		this.image = image;
		fitToImage();
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, null);
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
	private void fitToImage(){
		if(image == null){
			return;
		}
		setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		repaint();
	}
}
