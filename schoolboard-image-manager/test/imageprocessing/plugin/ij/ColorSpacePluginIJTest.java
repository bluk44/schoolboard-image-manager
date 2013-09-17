package imageprocessing.plugin.ij;

import imageprocessing.Util;
import imageprocessing.plugin.ColorSpacePlugin;

import java.awt.image.BufferedImage;

import test.Test;

public class ColorSpacePluginIJTest {
	
	public static void main(String[] args){
		ColorSpacePlugin colorSpacePlugin = new ColorSpacePluginIJ();
		BufferedImage rgbImage = Util.readFromFile("images/bg_remove/czarna-szkola/bb2.png");
		BufferedImage[] hsvChannels = colorSpacePlugin.getHSBchannels(rgbImage);
		
		//Test.showImage(hsvChannels[0], "h");
		//Test.showImage(hsvChannels[1], "s");
		//Test.showImage(hsvChannels[2], "v");
		
		BufferedImage merged = colorSpacePlugin.mergeHSBchannels(hsvChannels);
		
		Test.showImage(merged, "merged");
	}
}
