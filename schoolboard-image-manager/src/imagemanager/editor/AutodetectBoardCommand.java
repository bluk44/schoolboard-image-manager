package imagemanager.editor;

import java.awt.image.BufferedImage;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imagemanager.SourceImage;
import imageprocessing.CSConverter;
import imageprocessing.EdgeDetection;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.Util;
import imageprocessing.boundsdetection.BoardQuadrangle;
import imageprocessing.boundsdetection.BoundaryDetector;
import imageprocessing.boundsdetection.BoundaryDetector.QuadrangleNotFoundException;
import imageprocessing.matrix.MatrixB;
import imageprocessing.plugin.ij.AWTImageWrapper;


public class AutodetectBoardCommand implements ImageEditCommand {
	
	private double scale;
	private SourceImage sourceImage;
	
	private double lower = 5.0, higher = 10.0;
	private boolean suppress = true;
	private double smoothing = 1.0;
	
	public AutodetectBoardCommand(SourceImage sourceImage, double scale){
		this.scale = scale;
		this.sourceImage = sourceImage;
	}
	
	@Override
	public void execute() {
		
		try {
			BufferedImage src = sourceImage.getPixels();
			ImagePlus scaled = AWTImageWrapper.toImagePlus(Util.resize(src, scale));
			CSConverter.run(Conversion.GRAY_8, scaled);
			
			EdgeDetection.canny(scaled, 1.0, true, 5, 10);
			MatrixB imageEdge = new MatrixB(scaled.getBufferedImage());
			BoundaryDetector bd = new BoundaryDetector();
			bd.setImageEdge(imageEdge);
			
			BoardQuadrangle bq = bd.detectBestQuadrangle();
			
			
		} catch (UnsupportedConversionException e) {e.printStackTrace();} 
		  catch (QuadrangleNotFoundException e) {e.printStackTrace();}
		

	}

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public double getLower() {
		return lower;
	}

	public void setLower(double lower) {
		this.lower = lower;
	}

	public double getHigher() {
		return higher;
	}

	public void setHigher(double higher) {
		this.higher = higher;
	}

	public double getSmoothing() {
		return smoothing;
	}

	public void setSmoothing(double smoothing) {
		this.smoothing = smoothing;
	}

	public boolean isSuppress() {
		return suppress;
	}

	public void setSuppress(boolean suppress) {
		this.suppress = suppress;
	}

}
