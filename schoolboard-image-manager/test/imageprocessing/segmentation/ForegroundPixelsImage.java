package imageprocessing.segmentation;

import imageprocessing.Util;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

import test.Test;

public class ForegroundPixelsImage {

	private BufferedImage fgImage;
	private int[] map;
	private int imgW, imgH;
	public ForegroundPixelsImage(BufferedImage input, BufferedImage edges) {
		Raster inputRaster = input.getRaster();
		Raster edgesRaster = edges.getRaster();
		imgW = input.getWidth();
		imgH = input.getHeight();
		int nFg = 0;

		for (int i = 0; i < imgH; i++) {
			for (int j = 0; j < imgW; j++) {
				if(edgesRaster.getSample(j, i, 0) == 255){
					++nFg;
				}
			}
		}
		
		fgImage = new BufferedImage(nFg, 1, input.getType());
		
		WritableRaster fgRaster = fgImage.getRaster();
		map = new int[2*nFg];
		int nBands = inputRaster.getNumBands();
		int mapIdx = 0;
		for (int i = 0; i < imgH; i++) {
			for (int j = 0; j < imgW; j++) {
				if(edgesRaster.getSample(j, i, 0) == 255){
					map[mapIdx] = j;
					map[mapIdx+1] = i;
					int rIdx = (int) (mapIdx / 2.d);
					for(int b = 0; b < nBands; b++){
						fgRaster.setSample(rIdx, 0, b, inputRaster.getSample(j, i, b));

					}
					mapIdx += 2;
				}
			}
		}
	}
	
	public void setForegroundImage(BufferedImage fgImage){
		this.fgImage = fgImage;
	}
	
	public BufferedImage getForegroundImage(){
		return fgImage;
	}
	
	public BufferedImage getForegroundDisplay(){
		BufferedImage bi = new BufferedImage(imgW, imgH, fgImage.getType());
		WritableRaster r = bi.getRaster();
		WritableRaster fgr = fgImage.getRaster();
		int nBands = fgr.getNumBands();
		
		for(int i = 0; i < map.length; i+=2){
			for(int j = 0; j < nBands; j++){
				r.setSample(map[i], map[i+1], 0, fgr.getSample((int)(i / 2), 0, j));
			}
		}
		
		return bi;
	}
	
	public static void main(String[] args){
		BufferedImage in = Util.readFromFile("images/bg_remove/czarna-szkola/greyscale/bb2.png");
		BufferedImage dil = Util.readFromFile("images/bg_remove/czarna-szkola/dilation/bb2.png");
		BufferedImage thr = Util.readFromFile("images/bg_remove/czarna-szkola/final4/bb2_thr.png");
		ForegroundPixelsImage fag = new ForegroundPixelsImage(in, dil);
		//Util.writeToFile("images/bg_remove/czarna-szkola/final4/bb2.png", "png", fag.getForegroundImage());
		
		fag.setForegroundImage(thr);
		BufferedImage out = fag.getForegroundDisplay();
		Util.writeToFile("images/bg_remove/czarna-szkola/final4/bb2_out.png", "png", out);
		Test.showImage(in, "in");
		Test.showImage(out, "out");
	}

}
