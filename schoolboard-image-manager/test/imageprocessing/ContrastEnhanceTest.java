package imageprocessing;

import ij.ImagePlus;
import ij.plugin.ContrastEnhancer;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import imageprocessing.ConnectedRegionsLabeling.Region;
import imageprocessing.ConnectedRegionsLabeling.Results;
import imageprocessing.plugin.ij.AWTImageWrapper;

public class ContrastEnhanceTest {
	
	static String img1 = "images/bg_remove/biala-szkola/eq_light/gauss/single_region2.png";
	static String mask1 = "images/bg_remove/biala-szkola/mask/high_contrast_area_mask.png";
	
	static String img2 = "images/bg_remove/biala-szkola/eq_light/gauss/high_contrast_area.png";
	static String mask2 = "images/bg_remove/biala-szkola/mask/high_contrast_area_mask.png";	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		ImagePlus image = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/biala-szkola/eq_light/gauss/single_region2.png"));
//		ImagePlus mask = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/biala-szkola/mask/single_region2_mask.png"));

//		ImagePlus image = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/biala-szkola/eq_light/gauss/single_region.png"));
//		ImagePlus mask = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/biala-szkola/mask/single_region_mask.png"));
		
		//ImagePlus image = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/bin_test.png"));
		//ImagePlus image = AWTImageWrapper.toImagePlus(Util.readFromFile("images/bg_remove/bin_test2.png"));		
		
//		MatrixI labeledImage = new MatrixI(image.getWidth(), image.getHeight());
//		
//		for(int i = 0; i < labeledImage.getLength(); i++)
//			labeledImage.setElement(i, 1);
//		ContrastEnhance.run(image, labeledImage);
		
//		Results r = ConnectedRegionsLabeling.run(mask.getProcessor(), 0);
//		
//		ContrastEnhance.run(image, r.getLabeledImage(), 2.5);
//		
//		image.show();
		
//		for(int i = 0; i < image.getHeight(); i++){
//			for(int j = 0; j < image.getWidth(); j++){
//				System.out.print((int)image.getProcessor().getPixelValue(j, i)+" ");
//			}
//			System.out.println();
//		}
		ImagePlus textRegion = AWTImageWrapper.toImagePlus(Util.readFromFile(img2));
		ImagePlus mask = AWTImageWrapper.toImagePlus(Util.readFromFile(mask2));
		
		Results r = ConnectedRegionsLabeling.run(mask.getProcessor(), 0);
		Region reg = r.getRegion(1);
		
		reg.setImage(textRegion.getProcessor());
		int[] pixels = reg.getPixels();
//		for(int i = 0; i < 64; i ++){
//			System.out.print(" "+pixels[i]);
//		}
		ContrastEnhance.equalizeHistogram(pixels, false);
		Thresholding.IJisodata(pixels);
//		System.out.println();
//		for(int i = 0; i < 64; i ++){
//			System.out.print(" "+pixels[i]);
//		}
//		pixels = new int[pixels.length];
		reg.setPixels(pixels);
		textRegion.show();
		//reg.printRegion();
		
		//ContrastEnhance.equalizeHistogram(textRegion);
		//textRegion.show();
		//Util.writeToFile("images/bg_remove/test45_bright_eq_hist.png", "png", textRegion.getBufferedImage());
		enchancePluginTest();
	}
	
	public static void enchancePluginTest(){
		ImagePlus textRegion = AWTImageWrapper.toImagePlus(Util.readFromFile(img2));
		ImagePlus mask = AWTImageWrapper.toImagePlus(Util.readFromFile(mask2));
		
		Results r = ConnectedRegionsLabeling.run(mask.getProcessor(), 0);
		Region reg = r.getRegion(1);
		
		reg.setImage(textRegion.getProcessor());
		int[] pixels = reg.getPixels();
		
		ImageProcessor ip = new ByteProcessor(pixels.length, 1);
		for(int i = 0; i < pixels.length; i++){
			ip.putPixelValue(i, 0, pixels[i]);
		}
		
		ContrastEnhancer ce = new ContrastEnhancer();
		ce.equalize(ip);
		for(int i = 0; i < pixels.length; i++){
			pixels[i] = (int)ip.getPixelValue(i, 0);
		}	
		Thresholding.IJisodata(pixels);
		reg.setPixels(pixels);
		textRegion.show();
	}

}
