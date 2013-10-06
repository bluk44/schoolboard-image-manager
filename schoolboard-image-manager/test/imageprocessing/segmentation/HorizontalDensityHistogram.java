package imageprocessing.segmentation;

import imageprocessing.Util;
import imageprocessing.color.ColorConversion;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import test.Test;

public class HorizontalDensityHistogram extends JComponent {
	
	int maxDens = 0;
	int[] histogram;
	BufferedImage img;
	
	public HorizontalDensityHistogram(BufferedImage img){
		this.img = img;
		maxDens = img.getWidth();
		histogram = new int[img.getHeight()];
		for(int i = 0; i < img.getHeight(); i++){
			for(int j = 0; j < img.getWidth(); j++){
				if(img.getRaster().getSample(j, i, 0) == 0){
					++histogram[i];
				}
			}
		}
		
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		setBackground(Color.WHITE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		for(int i = 0; i < histogram.length; i++){
//			g.drawLine(0, 2*i, histogram[i], 2*i);
//			g.drawLine(0, 2*i+1, histogram[i], 2*i+1);
			g.drawLine(0, i, histogram[i], i);
		}
	}
	
	public static void main(String[] args) {
		BufferedImage img = Util.readFromFile("/home/boodex/PracaDyplomowa2012-2013/Slajdy/roi3.png");
		img = ColorConversion.rgb2gray(img);

		BufferedImage img2 = Util.readFromFile("/home/boodex/PracaDyplomowa2012-2013/Slajdy/roi33_6.png");
		img2 = ColorConversion.rgb2gray(img2);	
		Test.showImage(img, "roi3");
		Test.showImage(img2, "roi3_6");

		HorizontalDensityHistogram hist = new HorizontalDensityHistogram(img);
		HorizontalDensityHistogram hist2 = new HorizontalDensityHistogram(img2);

		Test.showComponent(hist, "dens histogram 0");
		Test.showComponent(hist2, "dens histogram 6");
	//	Util.writeToFile("/home/boodex/PracaDyplomowa2012-2013/Slajdy/hostogram-10.png", "png", img);
	//	Util.writeToFile("/home/boodex/PracaDyplomowa2012-2013/Slajdy/hostogram0.png", "png", img2);
	}

}
