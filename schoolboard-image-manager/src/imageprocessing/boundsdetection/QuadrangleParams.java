package imageprocessing.boundsdetection;

public class QuadrangleParams {
	public double upperEdgeLmit = 0.3, lowerEdgeLimit = 0.3, leftEdgeLimit = 0.3, rightEdgeLmit = 0.3;
	public double minQuadrangleArea = 0.1;
	
	public int lineThickness = 20;
	public double minEdgeLength = 3;
	
	public int nVerticalLines = 6, nHorizontalLines = 6;
}
