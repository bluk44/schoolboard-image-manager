package imageprocessing.geometry.houghtransform;

/**
 * kat 0 <= theta < 180 wspolrzedna pozioma x -> kat theta wspolrzedna pionowa y
 * -> odleglosc rho
 * 
 * @author Towariszcz_Admin
 * 
 */

public class HoughTransformParams {
	
	public int thetaVertical = 30;
	public int thetaHorizontal = 30;
	
	public int rhoNhood = 5;
	public int thetaNhood = 30;
	public int minVotes = 5;
	
}
