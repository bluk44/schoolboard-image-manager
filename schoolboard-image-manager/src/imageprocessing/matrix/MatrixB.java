package imageprocessing.matrix;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class MatrixB {
	protected int sizeX, sizeY;
	protected boolean[] data;

	public MatrixB(int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		data = new boolean[sizeX * sizeY];
	}

	public MatrixB(boolean[] data, int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;

		this.data = new boolean[sizeX * sizeY];

		for (int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
	}

	public MatrixB(BufferedImage image) {
		Raster r = image.getRaster();
		this.data = new boolean[image.getWidth() * image.getHeight()];
		int w = r.getWidth(), h = r.getHeight();
		sizeX = w;
		sizeY = h;
		System.out.println(r.getNumBands());
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {
				for(int b = 0; b < r.getNumBands(); b++)
				if(r.getSample(j, i, b) != 0 ) setElement(j, i, true);
			}
		}
		
	}

	public boolean getElementSafe(int x, int y) {
		if (x < sizeX && y < sizeY && x >= 0 && y >= 0)
			return getElement(x, y);
		else
			return false;
	}

	public boolean getElement(int x, int y) {
		return data[y * sizeX + x];
	}

	public boolean getElement(int pos) {
		return data[pos];
	}

	public void setElement(int x, int y, boolean element) {
		data[y * sizeX + x] = element;
	}

	public void setElement(int pos, boolean element) {
		data[pos] = element;
	}

	public boolean[] getContents() {
		return data;
	}

	public void setContents(boolean[] data) {
		for (int i = 0; i < data.length; i++) {
			this.data[i] = data[i];
		}
	}

	public int getSizeX() {
		return sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public int getLength() {
		return data.length;
	}

	public MatrixB subMatrix(int x, int y, int sizeX, int sizeY) {
		return new MatrixB(data, sizeX, sizeY);

	}

	public MatrixB transpone() {
		MatrixB T = new MatrixB(getSizeY(), getSizeX());
		for (int i = 0; i < getSizeY(); i++) {
			for (int j = 0; j < getSizeX(); j++) {
				T.setElement(i, j, getElement(j, i));
			}
		}

		return T;
	}

	public void print() {
		for (int i = 0; i < sizeY; i++) {
			for (int j = 0; j < sizeX; j++) {
				System.out.print(getElement(j, i) + " ");
			}
			System.out.println();
		}
	}

	public void printRow(int r) {
		for (int i = 0; i < sizeX; i++) {
			System.out.print(getElement(i, r) + " ");
		}
	}

	public void printCol(int c) {
		for (int i = 0; i < sizeY; i++) {
			System.out.println(getElement(c, i) + " ");
		}
	}

	public BufferedImage getBufferedImage() {
		BufferedImage b = new BufferedImage(getSizeX(), getSizeY(),
				BufferedImage.TYPE_BYTE_GRAY);
		for (int i = 0; i < getSizeY(); i++) {
			for (int j = 0; j < getSizeX(); j++) {
				if (getElement(j, i)) {
					b.getRaster().setSample(j, i, 0, 255);
				}
			}
		}
		return b;
	}
	
	public BufferedImage getBufferedImage(int imageType) {
		BufferedImage b = new BufferedImage(getSizeX(), getSizeY(),
				imageType);
		for (int i = 0; i < getSizeY(); i++) {
			for (int j = 0; j < getSizeX(); j++) {
				if (getElement(j, i)) {
					for(int k = 0; k < b.getRaster().getNumBands(); k++)
					b.getRaster().setSample(j, i, k, 255);
				}
			}
		}
		return b;
	}
}
