package imageprocessing.color;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import test.Test;

public class ColorConversion {

	public static BufferedImage rgb2gray(BufferedImage src) {
		BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(),
				BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster srcR = src.getRaster(), dstR = dst.getRaster();
		int h = src.getHeight(), w = src.getWidth();
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				int samp = 0;
				for (int k = 0; k < srcR.getNumBands(); k++) {
					samp += srcR.getSample(j, i, k);
				}
				samp /= srcR.getNumBands();
				dstR.setSample(j, i, 0, samp);
			}
		}
		dst.setData(dstR);
		return dst;
	}
	
	public static BufferedImage gray2rgb(BufferedImage src){
		BufferedImage dst = new BufferedImage(src.getWidth(), src.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		WritableRaster srcR = src.getRaster(), dstR = dst.getRaster();
		int h = src.getHeight(), w = src.getWidth();
		int sample = 0;
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				sample = srcR.getSample(j, i, 0);
				dstR.setSample(j, i, 0, sample);
				dstR.setSample(j, i, 1, sample);
				dstR.setSample(j, i, 2, sample);
			}
		}
		dst.setData(dstR);
		return dst;
	}
	
	public static double[] hsv2rgb(double hue, double sat, double val) {
		double red = 0, grn = 0, blu = 0;
		double i, f, p, q, t;
		double result[] = new double[3];

		if (val == 0) {
			red = 0;
			grn = 0;
			blu = 0;
		} else {
			hue /= 60;
			i = Math.floor(hue);
			f = hue - i;
			p = val * (1 - sat);
			q = val * (1 - (sat * f));
			t = val * (1 - (sat * (1 - f)));
			if (i == 0) {
				red = val;
				grn = t;
				blu = p;
			} else if (i == 1) {
				red = q;
				grn = val;
				blu = p;
			} else if (i == 2) {
				red = p;
				grn = val;
				blu = t;
			} else if (i == 3) {
				red = p;
				grn = q;
				blu = val;
			} else if (i == 4) {
				red = t;
				grn = p;
				blu = val;
			} else if (i == 5) {
				red = val;
				grn = p;
				blu = q;
			}
		}
		result[0] = red;
		result[1] = grn;
		result[2] = blu;
		return result;
	}

	private static double[] rgb2hsv(double red, double grn, double blu) {
		double hue, sat, val;
		double x, f, i;
		double result[] = new double[3];

		x = Math.min(Math.min(red, grn), blu);
		val = Math.max(Math.max(red, grn), blu);
		if (x == val) {
			hue = 0;
			sat = 0;
		} else {
			f = (red == x) ? grn - blu : ((grn == x) ? blu - red : red - grn);
			i = (red == x) ? 3 : ((grn == x) ? 5 : 1);
			hue = ((i - f / (val - x)) * 60) % 360;
			sat = ((val - x) / val);
		}
		result[0] = hue;
		result[1] = sat;
		result[2] = val;
		return result;
	}

	public static double[][] rgb2hsv(BufferedImage rgbImage) {
		double[][] samples = new double[3][rgbImage.getWidth()
				* rgbImage.getHeight()];
		for (int b = 0; b < rgbImage.getRaster().getNumBands(); b++) {
			for (int i = 0; i < rgbImage.getHeight(); i++) {
				for (int j = 0; j < rgbImage.getWidth(); j++) {
					samples[b][i * rgbImage.getWidth() + j] = rgbImage
							.getRaster().getSample(j, i, b);
				}
			}
		}
		double[][] samplesHSV = new double[3][rgbImage.getWidth()
				* rgbImage.getHeight()];
		for (int i = 0; i < samplesHSV[0].length; i++) {
			double[] hsv = ColorConversion.rgb2hsv(samples[0][i],
					samples[1][i], samples[2][i]);
			samplesHSV[0][i] = hsv[0];
			samplesHSV[1][i] = hsv[1];
			samplesHSV[2][i] = hsv[2];

		}
		BufferedImage hsvImage = new BufferedImage(rgbImage.getWidth(),
				rgbImage.getHeight(), rgbImage.getType());
		hsvImage.getRaster().setSamples(0, 0, hsvImage.getWidth(),
				hsvImage.getHeight(), 0, samplesHSV[0]);
		hsvImage.getRaster().setSamples(0, 0, hsvImage.getWidth(),
				hsvImage.getHeight(), 1, samplesHSV[1]);
		hsvImage.getRaster().setSamples(0, 0, hsvImage.getWidth(),
				hsvImage.getHeight(), 2, samplesHSV[2]);

		return samplesHSV;
	}

	public static BufferedImage hsv2rgb(double[][] samplesHSV, int imgW,
			int imgH, int type) {

		double[][] samplesRGB = new double[3][imgW * imgH];
		for (int i = 0; i < samplesRGB[0].length; i++) {
			double[] rgb = ColorConversion.hsv2rgb(samplesHSV[0][i],
					samplesHSV[1][i], samplesHSV[2][i]);

			samplesRGB[0][i] = rgb[0];
			samplesRGB[1][i] = rgb[1];
			samplesRGB[2][i] = rgb[2];

		}

		BufferedImage rgbImage = new BufferedImage(imgW, imgH, type);
		rgbImage.getRaster().setSamples(0, 0, imgW, imgH, 0, samplesRGB[0]);
		rgbImage.getRaster().setSamples(0, 0, imgW, imgH, 1, samplesRGB[1]);
		rgbImage.getRaster().setSamples(0, 0, imgW, imgH, 2, samplesRGB[2]);

		return rgbImage;
	}
	
	public static BufferedImage subtractBackground(BufferedImage src, BufferedImage bg){

		double[][] samplesHSV = ColorConversion.rgb2hsv(src);
		
		double[] Vchan = new double[bg.getWidth()*bg.getHeight()];
		bg.getRaster().getSamples(0, 0, bg.getWidth(), bg.getHeight(), 0, Vchan);
		
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				System.out.print(" "+Vchan[i*src.getWidth()+j]);
			}
			System.out.println();
		}
		
		double mean = 0;
		for (int i = 0; i < Vchan.length; i++) {
			mean += Vchan[i];
		}
		mean /= Vchan.length;
		for(int i = 0; i < samplesHSV[2].length; i++){
			samplesHSV[2][i] = samplesHSV[2][i] - Vchan[i] + mean;
		}
		
		BufferedImage brightness = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		brightness.getRaster().setSamples(0, 0, src.getWidth(), src.getHeight(), 0, samplesHSV[2]);
		Test.showImage(brightness, "brightness");
		return hsv2rgb(samplesHSV, src.getWidth(), src.getHeight(), src.getType());

	}
}