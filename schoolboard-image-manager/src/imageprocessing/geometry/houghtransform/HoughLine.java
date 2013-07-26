package imageprocessing.geometry.houghtransform;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.List;

public class HoughLine {

	private double rho, theta;
	private int val;
	private int rhoBase;
	private int x, y;
	
	public HoughLine(double theta, double rho, int val, int houghHeight) {
		this.rho = rho;
		this.theta = theta;
		this.val = val;
		rhoBase =(int)((houghHeight - 1) / 2.d);
		y = (int) Math.round(rhoBase + rho);
		x = (int) Math.round(theta);
	}
	
	public HoughLine(int x, int y, int val, int houghHeight){
		this.x = x;
		this.y = y;
		this.val = val;
		theta = x;
		rhoBase = (int)((houghHeight - 1) / 2.d);
		rho = y - rhoBase;
	}
	
    public void draw(BufferedImage image, int color) { 
    	// TODO
    } 
    
	public double getRho() {
		return rho;
	}

	public void setRho(double rho) {
		this.rho = rho;
		y = (int) Math.round(rhoBase + rho);
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	public int getVal() {
		return val;
	}

	public void setTheta(int val) {
		this.val = val;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
		this.theta = x; 
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
		rho = rhoBase + y;
	}
	
	public Point[] getImageLine(int imgW, int imgH){
		Point[] result = new Point[2];
		double sint = Math.sin(theta * Math.PI / 180), cost = Math
				.cos(theta * Math.PI / 180);
		
		double y1 = Math.floor(rho / sint);
		y1 = (y1 <= imgH && y1 >= 0) ? y1 : -1;
		double y2 = Math.floor((rho - imgW * cost) / sint);
		y2 = (y2 <= imgH && y2 >= 0) ? y2 : -1;
		double x1 = Math.floor(rho / cost);
		x1 = (x1 <= imgW && x1 >= 0) ? x1 : -1;
		double x2 = Math.floor((rho - imgH * sint) / cost);
		x2 = (x2 <= imgW && x2 >= 0) ? x2 : -1;
		
		int p = 0;
		if (y1 != -1) {
			result[p++] = new Point(0, (int) Math.round(y1));
		}
		if (y2 != -1) {
			result[p++] = new Point(imgW, (int) Math.round(y2));

		}
		if (x1 != -1 && p < 2) {
			result[p++] = new Point((int) Math.round(x1), 0);
		}
		if (x2 != -1 && p < 2) {
			result[p++] = new Point((int) Math.round(x2), imgH);
		}
		return result;
	}
	
	public Point intersection(HoughLine h){
		double sint1 = Math.sin(getTheta()*Math.PI/180), sint2 = Math.sin(h.getTheta()*Math.PI/180);
		double cost1 = Math.cos(getTheta()*Math.PI/180), cost2 = Math.cos(h.getTheta()*Math.PI/180);
		double a1 = - cost1 / sint1, a2 = - cost2 / sint2;
		double b1 = getRho() / sint1, b2 = h.getRho() / sint2;
		
		double x = (b1 - b2) / (a2 - a1);
		double y = a1*x + b1;
		
		return new Point((int)Math.round(x), (int)Math.round(y));
	}
	
	@Override
	public String toString() {
		return "theta: "+theta+" rho: "+rho+" votes: "+val;
	}
	
	public static class AscComparator implements Comparator<HoughLine>{

		@Override
		public int compare(HoughLine h1, HoughLine h2) {
			
			int result = 0;
			if(h1.val > h2.val){
				result = 1;
			} else if(h1.val < h2.val){
				result = -1;
			}
			return result;
		}
	}
	
	public static class DescComparator implements Comparator<HoughLine>{
		
		@Override
		public int compare(HoughLine h1, HoughLine h2) {
			int result = 0;
			if(h1.val < h2.val){
				result = 1;
			} else if(h1.val > h2.val){
				result = -1;
			}
			return result;
		}
	}
}
