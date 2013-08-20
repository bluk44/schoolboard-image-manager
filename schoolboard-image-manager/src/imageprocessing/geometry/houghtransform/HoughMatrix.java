package imageprocessing.geometry.houghtransform;

import imageprocessing.matrix.MatrixB;
import imageprocessing.matrix.MatrixI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HoughMatrix extends MatrixI {

	private int rho0Idx;
	
	public HoughMatrix(MatrixB edges){
		int maxRho = (int) Math.ceil(Math.sqrt(edges.getSizeX()
				* edges.getSizeX() + edges.getSizeY()
				* edges.getSizeY()));
		int rhoSize = 2 * maxRho + 1;
		this.sizeX = 180;
		this.sizeY = rhoSize;
		rho0Idx = (sizeY - 1) / 2;
		data = new int[sizeX*sizeY];
		
		double rad = Math.PI / 180.d;
		int rho = 0, thetaIdx = 0, rhoIdx = 0;
		for (int y = 0; y < edges.getSizeY(); y++) {
			for (int x = 0; x < edges.getSizeX(); x++) {
				if (edges.getElement(x, y)) {
					for (int th = 0; th < 180; th++) {
						rho = (int) Math.round(x * Math.cos(th * rad) + y
								* Math.sin(th * rad));
						rhoIdx = getRhoIdx(rho);
						thetaIdx = th;
						setElement(thetaIdx, rhoIdx,
								getElement(thetaIdx, rhoIdx) + 1);
					}
				}
			}
		}

	}
	
	public HoughMatrix(int sizeY) {
		
		super(180, sizeY);
		rho0Idx = (sizeY - 1) / 2;
	}
	
	public double getRho(int y){
		return y - rho0Idx;
	}
	
	public int getThetaIdx(double theta){
		return (int) Math.round(theta);
	}
	
	public int getRhoIdx(double rho){
		return  (int) Math.round(rho0Idx + rho);
	}
	public int getRhoZeroIdx(){
		return rho0Idx;
	}
	
	public HoughLine getHoughLine(int x, int y) {
		int votes = getElement(x, y);
		return new HoughLine(x, y, votes, sizeY);
	}

	public HoughLine getHoughLine(double theta, double rho) {
		int rIdx = (int) (rho0Idx + rho);
		int thetaIdx = (int) theta;
		int votes = getElement(thetaIdx, rIdx);
		return new HoughLine(theta, rho, votes, sizeY);
	}

	public void threshold(int t) {
		for (int i = 0; i < data.length; i++) {
			if (getElement(i) < t)
				setElement(i, 0);
		}
	}

	public void setNhood(int val, int x, int y, int rw, int rh) {
		int idxW = 0, idxH = 0;
		for (int i = y - rh; i <= y + rh; i++) {
			for (int j = x - rw; j <= x + rw; j++) {
				idxW = j;
				idxH = i;
				if(j < 0){
					idxW = sizeX + j;
					idxH = sizeY - 1 - idxH;
				} else if(j>sizeX - 1){
					idxW = j - sizeX;
					idxH = sizeY - 1 - idxH;
				}
				if(idxH >= 0 && idxH < sizeY){
					setElement(idxW, idxH, val);
				}
			}
		}
	}
	
	public  List<HoughLine> getLines(int threshold){
		List<HoughLine> lines = new ArrayList<HoughLine>();
		for(int i = 0; i < sizeY; i++){
			for(int j = 0; j < sizeX; j++){
				if(getElement(j,i) > threshold){
					lines.add(new HoughLine(j, i, getElement(j,i), sizeY));
				}
			}
		}
		
		return lines;
	}
	
	public void setValue(int val, double theta, double rho){
		setElement(getThetaIdx(theta), getRhoIdx(rho), val);
	}
	
	public List<HoughLine> getVerticalLines(int nLines, int minVotes, double thetaVlimit, int rw, int rh){
		int[] dataCopy = Arrays.copyOf(data, data.length);
		for(int i = 0; i < sizeY; i++){
			for(int j = 0; j < sizeX; j++){
				double theta = j;
				int v = getElement(j, i);
				if((!(theta >= 0 && theta < thetaVlimit) && !(theta >= 180 - thetaVlimit && theta < 180)) || (v < minVotes)){
					setElement(j,i,0);
				}
				
			}
		}
		
		List<HoughLine> lines = getLines(0);
		Collections.sort(lines, new HoughLine.DescComparator());
		List<HoughLine> vLines = new ArrayList<HoughLine>(lines.size());
		for (Iterator iterator = lines.iterator(); iterator.hasNext();) {
			if(nLines == 0) break;
			HoughLine line = (HoughLine) iterator.next();
				if(getElement(line.getX(), line.getY()) > 0){
					vLines.add(line);
					setNhood(0, line.getX(), line.getY(), rw, rh);
					--nLines;
				}
		}
		
		setData(dataCopy);
		return vLines;
	}
	
	public List<HoughLine> getHorizontalLines(int nLines, int minVotes, double thetaHLimit, int rw, int rh){
		int[] dataCopy = Arrays.copyOf(data, data.length);
		double lowTheta = 90 - thetaHLimit, hiTheta = 90 + thetaHLimit;
		if(lowTheta < 0 ) lowTheta = 0;
		if(hiTheta > 179) hiTheta = 179;
		
		int lowThetaIdx = (int) Math.round(lowTheta), hiThetaIdx = (int) Math.round(hiTheta);
		for(int i = 0; i < sizeY; i++){
			for(int j = 0; j < sizeX; j++){
				if(!(j >= lowThetaIdx && j <= hiThetaIdx) || getElement(j, i) < minVotes){
					setElement(j, i, 0);
				}
			}
		}
		
		List<HoughLine> lines = getLines(0);
		Collections.sort(lines, new HoughLine.DescComparator());
		List<HoughLine> hLines = new ArrayList<HoughLine>(lines.size());
		for (Iterator iterator = lines.iterator(); iterator.hasNext();){
			if(nLines == 0) break;
			HoughLine line = (HoughLine) iterator.next();
				if(getElement(line.getX(), line.getY()) > 0){
					hLines.add(line);
					setNhood(0, line.getX(), line.getY(), rw, rh);
					--nLines;
				}		
		}
	setData(dataCopy);	
	return hLines;
	}
}
