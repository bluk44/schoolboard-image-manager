package imageprocessing.plugin;

import java.awt.image.BufferedImage;

public abstract class ColorSpacePlugin {
	
	public abstract BufferedImage[] getHSBchannels(BufferedImage rgbImage);
	
	public abstract BufferedImage mergeHSBchannels(BufferedImage[] channels);
	
}
