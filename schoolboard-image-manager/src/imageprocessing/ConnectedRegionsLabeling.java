package imageprocessing;

import ij.process.ImageProcessor;
import imageprocessing.matrix.MatrixI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ConnectedRegionsLabeling {
		
	
	public static Results run(ImageProcessor ip, int BG){
		int sizeX = ip.getWidth();
		int sizeY = ip.getHeight();
		
		int[] samples = getPixelValues(ip);
		int[] labeled = new int[samples.length];
		int[] neighbours = new int[4];

		int nPtr = 0;
		int currentLabel = 1;
		DisjointSets labelSet = new DisjointSets(1);
		
		Map<Integer, Integer> regions = new HashMap<Integer, Integer>();
		
		// first run
		currentLabel = 1;
		for(int y=0;y<sizeY;y++){		
			for (int x = 0; x < sizeX; x++) {
				int currentPixel = samples[idx(sizeX, x, y)];
				if(currentPixel == BG) continue;
				nPtr = 0;

				if(x > 0 && labeled[idx(sizeX, x-1, y)] != BG) neighbours[nPtr++] = labeled[idx(sizeX, x-1, y)]; // W 
				if(y > 0 && labeled[idx(sizeX, x, y-1)] != BG) neighbours[nPtr++] = labeled[idx(sizeX, x, y-1)]; // N
				if(x > 0 && y > 0 && labeled[idx(sizeX, x-1, y-1)] != BG) neighbours[nPtr++] = labeled[idx(sizeX, x-1, y-1)]; // NW
				if(x < sizeX-1 && y > 0 && labeled[idx(sizeX, x+1, y-1)] != BG) neighbours[nPtr++] = labeled[idx(sizeX, x+1, y-1)]; // NE
				
				if(nPtr == 0){
					labeled[idx(sizeX, x,y)] = currentLabel++;
					labelSet.addElement();
				} else {
					int lPrev = neighbours[0];
					int lNow;
					int lMin = neighbours[0];
					for(int i=0; i<nPtr; i++){
						lNow = neighbours[i];
						if(lNow < lMin){
							lMin = lNow;
						}
						labelSet.union(lNow, lPrev);
					}
					labeled[idx(sizeX, x,y)] = lMin;
				}
			}
		}

		// second run

		for(int y=0;y<sizeY;y++){
			for (int x = 0; x < sizeX; x++) {
				if(labeled[idx(sizeX, x,y)] == 0) continue;
				int root = labelSet.find(labeled[idx(sizeX, x,y)]);
				labeled[idx(sizeX, x,y)] = root;
				Integer prevVal = regions.get(root);
				if(prevVal == null){
					regions.put(root, 1);
				} else {
					regions.put(root, prevVal+1);
				}
				
			}
		}
		
		int nreg = regions.size();
		MatrixI labeledImage = new MatrixI(labeled, sizeX, sizeY);

		return new Results(nreg, regions, labeledImage);
	}
		
	protected static int idx(int sizeX, int x, int y){
		return y*sizeX + x;
	}
	
	protected static void setElement(int sizeX, int[] samples, int x, int y, int val){
		samples[idx(sizeX, x, y)] = val;
	}
	
	protected static int[] getPixelValues(ImageProcessor ip){
		int sizeX = ip.getWidth();
		int sizeY = ip.getHeight();
		
		int[] pixelVals = new int[sizeX * sizeY];
		for(int i = 0; i < sizeY; i++){
			for(int j = 0; j < sizeX; j++){
				pixelVals[i*sizeX+j] = (int)ip.getPixelValue(j, i);
			}
		}
		
		return pixelVals;
	}
	
	protected static class DisjointSets {
		protected List<Integer> elements;
		
		public DisjointSets(){
			elements = new ArrayList<Integer>();
		}
		
		public DisjointSets(int initCap){
			elements = new ArrayList<Integer>(initCap);
			for (int i = 0; i < initCap; i++) {
				elements.add(-1);		}
		}
		
		public void addElement(){
			elements.add(-1);
		}
		/**
		 * finds root of tree
		 * @param el element 
		 * @return
		 */
		public int find(int el){
			if(elements.get(el) < 0){
				return el;
			} else {
				elements.set(el, find(elements.get(el)));
				return elements.get(el);
			}
		}
		
		public void union(int el1, int el2){
			int root1 = find(el1);
			int root2 = find(el2);
			
			if(root1 == root2) return;
			
			if(elements.get(root2) < elements.get(root1)){
				elements.set(root2, elements.get(root2) + elements.get(root1));
				elements.set(root1, root2);
			} else if(elements.get(root1) == elements.get(root2)){ 
				if(root1 < root2){
					elements.set(root1, elements.get(root1) + elements.get(root2));
					elements.set(root2, root1);		
				} else {
					elements.set(root2, elements.get(root2) + elements.get(root1));
					elements.set(root1, root2);
				}
			} else{
				
				elements.set(root1, elements.get(root1) + elements.get(root2));
				elements.set(root2, root1);			
			}
		}
		
	}
	
	public static class Results{
		private int nLabels;
		private Map<Integer, Integer> regions;
		private MatrixI labeledImage;
		
		public Results(int nLabels, Map<Integer, Integer> labelSize, MatrixI labeledImage){
			this.nLabels = nLabels;
			this.regions = labelSize;
			this.labeledImage = labeledImage;
		}
		
		public int getLabelCount(){
			return nLabels;
		}
		
		public Map<Integer, Integer> getRegions(){
			return regions;
		}
		
		public MatrixI getLabeledImage(){
			return labeledImage;
		}
		
	}
}
