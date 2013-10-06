package imageprocessing.segmentation;

import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

import java.awt.Point;
import java.util.List;

public abstract class SkewCompensation {

	protected ByteProcessor image;
	protected ByteProcessor correctedImage;
	protected List<List<Point>> ROIs;

	public SkewCompensation(List<List<Point>> ROIs, ByteProcessor image) {
		super();
		this.ROIs = ROIs;
		this.image = image;
	}

	public ImageProcessor getCorrectedImage() {
		return correctedImage;
	}

	public void run() {
		double rotation = 0.d, bestAngle = 0.d;
		double rotStep = -1;
		boolean cont = true;
		double prevStdev = 0;
		int tendence = 0, prevTendence = 0; // szukamy najwiekszego odchylenia 1
		
		int ii = 0;
		while (cont && ii < 30) {
			correctedImage = new ByteProcessor(image, false);// - stdev rosnie, -1 stdev maleje
			correctedImage.rotate(rotation);
			
			int imgH = correctedImage.getHeight();
			int imgW = correctedImage.getWidth();
			int[] histogram = new int[imgH];
			double mean = 0, stdev = 0;
			int nonwhitePixels = 0;
			for (int i = 0; i < imgH; i++) {
				for (int j = 0; j < imgW; j++) {
					if (correctedImage.get(j, i) != 255) {
						histogram[i]++;
						mean++;
						nonwhitePixels++;
					}
				}
			}

			mean /= histogram.length;
			for (int i = 0; i < histogram.length; i++) {
				stdev += (mean - histogram[i]) * (mean - histogram[i]);
			}
			stdev = Math.sqrt(stdev / histogram.length);
			System.out.println("angle: " + rotation);
//			System.out.println("nonwhite pixels: "+nonwhitePixels); 
//			System.out.println("mean: " + mean);
			System.out.println("stdev: " + stdev);
			if(prevStdev == 0){
				prevStdev = stdev;
				rotation += rotStep;
				continue;
			}
			double dstDev = prevStdev - stdev;
			if (dstDev > 0) {
				tendence = -1;
			} else {
				tendence = 1;
			}
//			System.out.println("dstDev: " + dstDev);
			System.out.println("tendence: " + tendence);
			System.out.println("prevTendence: " + prevTendence);
			if (prevTendence == 1 && tendence == -1) {
				// przekroczenie max stdev;
				bestAngle = rotation - rotStep;
				cont = false;
			}

			if (tendence == -1) {
				// z³y kierunek obrotu
				rotStep = -rotStep;
				System.out.println("zmiana kroku obrotu na: " + rotStep);

			}

			prevStdev = stdev;
			prevTendence = tendence;
			rotation += rotStep;
			++ii;
		}
		System.out.println("best rotation angle = " + rotation);
		correctedImage = new ByteProcessor(image, false);
		correctedImage.rotate(rotation);
		
		int imgH = correctedImage.getHeight();
		int imgW = correctedImage.getWidth();
		int[] histogram = new int[imgH];
		for (int i = 0; i < imgH; i++) {
			for (int j = 0; j < imgW; j++) {
				if (correctedImage.get(j, i) != 255) {
					histogram[i]++;
				}
			}
		}
		printHistogram(histogram);
	}

	private void printHistogram(int[] histogram) {
		for (int i = 0; i < histogram.length; i++) {
			System.out.println("line " + i + ": " + histogram[i]);
		}
	}
}