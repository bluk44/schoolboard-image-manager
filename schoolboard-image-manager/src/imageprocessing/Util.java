package imageprocessing;


import ij.process.ByteProcessor;
import ij.process.ColorProcessor;
import ij.process.ImageProcessor;
import imageprocessing.matrix.MatrixB;
import imageprocessing.matrix.MatrixI;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * klasa z podstawowymi operacjami na zdjeciach
 * @author Towariszcz_Admin
 *
 */
public abstract class Util {
	
	public static BufferedImage copy(BufferedImage src){
		BufferedImage copy = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
		src.copyData(copy.getRaster());
		
		return copy;
	}
	
	public static ImageProcessor copy(ImageProcessor source){
		
		if(source instanceof ColorProcessor){
			int[] pixels = Arrays.copyOf((int[]) source.getPixels(), ((int[])source.getPixels()).length);
			return new ColorProcessor(source.getWidth(), source.getHeight(), pixels);
			
		} else if(source instanceof ByteProcessor){
			byte[] pixels = Arrays.copyOf((byte[]) source.getPixels(), ((byte[])source.getPixels()).length);
			return new ByteProcessor(source.getWidth(), source.getHeight(), pixels);
		} else return null;
	}
	
	public static BufferedImage readFromFile(String filename){
		try {
			BufferedImage image = ImageIO.read(new File(filename));
			return image;
		} catch (IOException e) {
			System.out.println("unable to open "+filename);
		}
		return null;
	}
	
	public static BufferedImage readFromFile(File f){
		try {
			BufferedImage image = ImageIO.read(f);
			return image;
		} catch (IOException e) {
			System.out.println("unable to open "+f.getName());
		}
		return null;
	}
	
	public static void writeToFile(String filename, String formatName, BufferedImage img){
		try {
			File newFile = new File(filename);
			newFile.createNewFile();
			ImageIO.write(img, formatName, newFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ImageProcessor convertToImageProcessor(BufferedImage source){
		int numBands = source.getData().getNumBands();
		ImageProcessor ip = null;
		switch(numBands){
		case 1:
			ip = new ByteProcessor(source);
			break;
		case 3:
			ip = new ColorProcessor(source);
			break;
		case 4:
			ip = new ColorProcessor(source);
			break;			
		}
		ip.setRoi(new Rectangle(source.getWidth(), source.getHeight()));
		return ip;
	}
	
	public static MatrixI grayToMatrixI(BufferedImage grayscaleImg){
		MatrixI matrix = new MatrixI(grayscaleImg.getWidth(), grayscaleImg.getHeight());
		int sizeX = grayscaleImg.getWidth(), sizeY = grayscaleImg.getHeight();
		Raster r = grayscaleImg.getRaster();
		for (int i = 0; i < sizeY; i++){
			for(int j = 0; j < sizeX; j++){
				matrix.setElement(j, i, r.getSample(j, i, 0));
			}
		}
		return matrix;
	}
		
	public static MatrixB grayToMatrixB(BufferedImage grayscaleImg, int threshold){
		MatrixB matrix = new MatrixB(grayscaleImg.getWidth(), grayscaleImg.getHeight());
		int sizeX = grayscaleImg.getWidth(), sizeY = grayscaleImg.getHeight();
		Raster r = grayscaleImg.getRaster();
		for (int i = 0; i < sizeY; i++){
			for(int j = 0; j < sizeX; j++){
				if(r.getSample(j, i, 0) > threshold)
				matrix.setElement(j, i, true);
			}
		}
		return matrix;
	}
	
	public static void inverse(BufferedImage src) {
		WritableRaster srcR = src.getRaster();
		int h = src.getHeight(), w = src.getWidth();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {

				for (int k = 0; k < srcR.getNumBands(); k++) {
					int samp = srcR.getSample(j, i, k);
					srcR.setSample(j, i, k, 255 - samp);
				}
			}
		}

	}
	
	public static float[] conv(int[] samples, int sampW, int sampH, float[][] mask) {

		int rh = (mask.length - 1) / 2;
		int rw = (mask[0].length - 1) / 2;
		int h = sampH - 2 * rh, w = sampW - 2 * rw;
		float[] nsamp = new float[sampW * sampH];

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int sy = rh * sampW + i * sampW + rw, sx = j, dsy = sampW, dsx = 1;
				float sum = 0;
				for (int k = -rh; k <= rh; k++) {
					for (int l = -rw; l <= rw; l++) {
						sum += samples[sy + k * dsy + sx + l * dsx]
								* mask[rh+k][rw+l];
					}
					nsamp[sy + sx] = sum;
				}
			}
		}
		return nsamp;
	}
	
	public static float[] conv1D(int[] samples, int sampW, int sampH, float[] mask){
		float[] nsamp = new float[sampW * sampH];
		 
		return nsamp;
	}
}
