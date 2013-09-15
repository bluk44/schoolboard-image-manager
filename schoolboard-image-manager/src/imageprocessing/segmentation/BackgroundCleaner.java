package imageprocessing.segmentation;

import java.awt.image.BufferedImage;

public abstract class BackgroundCleaner {
	
	private double gaussianBlurRadius = 10;
	
	public final BufferedImage run(BufferedImage image){
		image = clearLuminance(image);
		image = clearBackground(image);
		
		return image;
	}
	
	public BufferedImage clearLuminance(BufferedImage image){
		return null; // TODO
	}
	
	public abstract BufferedImage clearBackground(BufferedImage image);

	public double getGaussianBlurRadius() {
		return gaussianBlurRadius;
	}

	public void setGaussianBlurRadius(double gaussianBlurRadius) {
		this.gaussianBlurRadius = gaussianBlurRadius;
	}
}
