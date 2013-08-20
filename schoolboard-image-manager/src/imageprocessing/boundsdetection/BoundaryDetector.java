package imageprocessing.boundsdetection;

import imageprocessing.matrix.MatrixB;

public class BoundaryDetector {
	
	private double imageWidth, imageHeight;
	private double upperEdgeLmit = 0.3, lowerEdgeLimit = 0.3, leftEdgeLimit = 0.3, rightEdgeLmit = 0.3;
	private double minQuadrangleArea = 0.5;
	
	private int nlinesVertical = 6, nlinesHorizontal = 6;
	private int theta_vertical = 30;
	private int theta_horizontal = 30;
	private int rho_nhood = 5;
	private int theta_nhood = 30;
	
	private MatrixB imageEdge;
	private BoardQuadrangle quadrangle;
	
	public BoundaryDetector(MatrixB imageEdge) {
		this.imageEdge = imageEdge;
		imageWidth = imageEdge.getSizeX();
		imageHeight = imageEdge.getSizeY();
		
	}

	public double getLowerEdgeLimit() {
		return lowerEdgeLimit;
	}

	public void setLowerEdgeLimit(double lowerEdgeLimit) {
		this.lowerEdgeLimit = lowerEdgeLimit;
	}

	public double getLeftEdgeLimit() {
		return leftEdgeLimit;
	}

	public void setLeftEdgeLimit(double leftEdgeLimit) {
		this.leftEdgeLimit = leftEdgeLimit;
	}

	public double getMinQuadrangleArea() {
		return minQuadrangleArea;
	}

	public void setMinQuadrangleArea(double minQuadrangleArea) {
		this.minQuadrangleArea = minQuadrangleArea;
	}

	public double getUpperEdgeLmit() {
		return upperEdgeLmit;
	}

	public void setUpperEdgeLmit(double upperEdgeLmit) {
		this.upperEdgeLmit = upperEdgeLmit;
	}

	public double getRightEdgeLmit() {
		return rightEdgeLmit;
	}

	public void setRightEdgeLmit(double rightEdgeLmit) {
		this.rightEdgeLmit = rightEdgeLmit;
	}
	
	
}
