package imageprocessing;

import imageprocessing.algorithm.DisjointSets;
import imageprocessing.algorithm.DisjointSetsForest;
import imageprocessing.geometry.Point;

public class DisjointSetsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DisjointSets<Point> ds = new DisjointSetsForest<Point>();

//		ds.makeSet(11);
//		ds.makeSet(11);
//		ds.makeSet(11);
//
//		ds.makeSet(33);
//
//		ds.makeSet(22);
//		ds.makeSet(22);
//
//		ds.union(0, 1);
//		ds.union(1, 2);
//
//		ds.union(4, 5);
//
//		System.out.println(ds.getSets());
//
//		ds.union(0, 4);
//
//		System.out.println(ds.getSets());
		
		ds.makeSet(new Point(5, 5));
		ds.makeSet(new Point(7, 5));
		ds.makeSet(new Point(7, 9));
		ds.makeSet(new Point(5, 9));
		
		System.out.println(ds.getSets());
	}

}
