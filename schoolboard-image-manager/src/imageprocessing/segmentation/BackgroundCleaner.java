package imageprocessing.segmentation;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.CSConverter;
import imageprocessing.CSConverter.Conversion;
import imageprocessing.CSConverter.UnsupportedConversionException;
import imageprocessing.Filters;
import imageprocessing.Util;

import java.awt.Color;

/**
 * Oddziela tło od pisma.
 * 
 * Zdjęcie wejściowe przetwarzane jest w kilku etapach:
 * 
 * - wyrównanie oświetlenia.<br/>
 * Zapobieba powstawaniu ciemnych plam przy progowaniu dużych obiektów
 * pierwszoplanowych. Polega na zamianie przestrzeni kolorów z RGB na HSB oraz
 * odjęciu niskich częstotliwości z kanału B który reprezentuje jasność, na
 * koniec ponowna zamiana na RGB.
 * 
 * - wykrycie krawędzi.<br/>
 * Celem jest zlokalizowanie pisma, czyli zmian oświetlenia o wysokiej
 * częstotliwości, efektem działania jest czarno-biały obraz z dokładnie
 * określonymi krawędziami znaków. Zastosowano metodę Cannyego, dla zdjęcia
 * czarnej tablicy określono wyższe progi intensywności ze względu na smugi po
 * wycieraniu gąbką.
 * 
 * - dylacja kwawędzi.<br/>
 * Polega na pogrubieniu wykrytych wcześniej krawędzi tak aby powstały obszary
 * jak najdokładniej obejmujące pismo. Promień dylacji jest obliczany na
 * podstawie stosunku grubośći pisma do wysokości tablicy, który wyznaczony jest
 * doświadczalnie.
 * 
 * - oznaczanie połączonych obszarów.<br/>
 * Poprawia dokładność progowania, każda litera lub grpua połączonych liter może
 * być progowana osobno.
 * 
 * - poprawa histogramu.<br/>
 * Polega na skupieniu histogramu wokół dwuch wartości średich: jednej dla tła,
 * drugiej dla pierwszego planu, pozwala to odkładniej określić próg tła,
 * wykonywane dla każdej grupy pikseli osobno.
 * 
 * - binaryzacja obrazu.<br/>
 * Szukanie wartości progowej dla każdej grupy pikseli.
 * 
 * 
 * @author Lucas Budkowski
 * 
 */
public abstract class BackgroundCleaner {
	/**
	 * Promień maski wyrówniującej oświetnelie
	 */
	private static double LUM_CORRECTION_BLUR_RADIUS = 30;
	
	/**
	 * Stosunek grubości pisma do wysokości tablicy
	 */
	protected static double DILATION_RATIO;
	
	/**
	 * Obliczony promień dylacji 
	 */
	protected float dilation = 1.f;
	
	/**
	 *  Funkcja z poszczególnymi krokami procesu
	 *  
	 * @param image Zdjęcie tablicy
	 */
	public final void run(ImagePlus image) {
		correctIllumination(image);
		ImagePlus foreground = image.duplicate();
		separateForeground(foreground);
		assingColors(image, foreground);
	}

	private void correctIllumination(ImagePlus image) {
		try {
			CSConverter.run(Conversion.COLOR_RGB, image);
			CSConverter.run(Conversion.STACK_HSB, image);

			ImageProcessor brightness = image.getStack().getProcessor(3);
			ImageProcessor blurred = Util
					.copy(image.getStack().getProcessor(3));

			Filters.gauss(blurred, LUM_CORRECTION_BLUR_RADIUS);
			subBChan(brightness, blurred);

			CSConverter.run(Conversion.COLOR_RGB, image);
		} catch (UnsupportedConversionException e) {
			e.printStackTrace();
		}
	}

	protected void calculateDilationRadius(int boardHeight) {
		dilation = (float) (boardHeight * DILATION_RATIO);
	}

	public abstract void separateForeground(ImagePlus rgbImage);

	public abstract void assingColors(ImagePlus original, ImagePlus foreground);

	public double getLumCorrectionBlurRadius() {
		return LUM_CORRECTION_BLUR_RADIUS;
	}

	public void setLumCorrectionBlurRadius(double r) {
		BackgroundCleaner.LUM_CORRECTION_BLUR_RADIUS = r;
	}
	
	/**
	 * Odejmuje niskie częstotliwosci oświetlenia
	 *  
	 * @param ipBR kanał jasności oryginalnego zdjęcia 
	 * @param ipGA kanał jasności rozmytego zdjęcia
	 */
	private void subBChan(ImageProcessor ipBR, ImageProcessor ipGA) {
		int w = ipBR.getWidth(), h = ipBR.getHeight();

		float[] lum = new float[w * h];
		float[] blur = new float[w * h];

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				lum[i * w + j] = ipBR.getPixelValue(j, i);
			}
		}

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				blur[i * w + j] = ipGA.getPixelValue(j, i);
			}
		}

		float mean = 0;
		for (int i = 0; i < blur.length; i++) {
			mean += blur[i];
		}
		mean = Math.round(mean /= blur.length);

		for (int i = 0; i < lum.length; i++) {
			float val = lum[i] - blur[i] + mean;
			lum[i] = val < 255 ? val : 255;
			lum[i] = lum[i] > 0 ? lum[i] : 0;

		}

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				ipBR.putPixelValue(j, i, lum[i * w + j]);
			}
		}

	}
}
