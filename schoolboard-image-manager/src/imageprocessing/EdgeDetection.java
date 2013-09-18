package imageprocessing;

import ij.ImagePlus;
import imagescience.feature.Edges;
import imagescience.image.FloatImage;
import imagescience.image.Image;
import imagescience.segment.Thresholder;

public class EdgeDetection {
	
	public static void canny(ImagePlus ip, double smoothingScale, boolean suppress, double lower, double higher){
		
		Image imgScience = Image.wrap(ip);
		Image newImage = new FloatImage(imgScience);
		Edges edges = new Edges();
		newImage = edges.run(newImage, smoothingScale, suppress);
		
		Thresholder thres = new Thresholder();
		thres.hysteresis(newImage, lower, higher);
		
		ip.setImage(newImage.imageplus());
	}
}
