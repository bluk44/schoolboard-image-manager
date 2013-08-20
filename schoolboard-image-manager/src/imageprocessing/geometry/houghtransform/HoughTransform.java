package imageprocessing.geometry.houghtransform;

import imageprocessing.matrix.MatrixB;

import java.util.List;



/**
 * kat 0 <= theta < 180 wspolrzedna pozioma x -> kat theta wspolrzedna pionowa y
 * -> odleglosc rho
 * 
 * @author Towariszcz_Admin
 * 
 */

public class HoughTransform {
	
	private int thetaVertical = 30;
	private int thetaHorizontal = 30;
	
	private int rhoNhood = 5;
	private int thetaNhood = 30;
	private int minVotes = 5;
	
	private HoughMatrix houghMatrix;
	
	public HoughTransform(MatrixB imageEdge){
		houghMatrix  = new HoughMatrix(imageEdge);
	}
	
	public int getThetaVertical() {
		return thetaVertical;
	}

	public void setThetaVertical(int thetaVertical) {
		this.thetaVertical = thetaVertical;
	}

	public int getThetaHorizontal() {
		return thetaHorizontal;
	}

	public void setThetaHorizontal(int thetaHorizontal) {
		this.thetaHorizontal = thetaHorizontal;
	}

	public int getRhoNhood() {
		return rhoNhood;
	}

	public void setRhoNhood(int rhoNhood) {
		this.rhoNhood = rhoNhood;
	}

	public int getThetaNhood() {
		return thetaNhood;
	}

	public void setThetaNhood(int thetaNhood) {
		this.thetaNhood = thetaNhood;
	}
	
	public List<HoughLine> getVerticalHoughLines(int nLines){
		return houghMatrix.getVerticalLines(nLines, minVotes, thetaVertical, rhoNhood, thetaNhood);
	}
	
	public List<HoughLine> getHorizontalHoughLines(int nLines){
		return houghMatrix.getHorizontalLines(nLines, minVotes, thetaHorizontal, rhoNhood, thetaNhood);
	}
	
}
