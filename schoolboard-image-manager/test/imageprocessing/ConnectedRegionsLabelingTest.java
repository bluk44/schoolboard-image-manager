package imageprocessing;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import imageprocessing.ConnectedRegionsLabeling.Results;
import imageprocessing.plugin.ij.AWTImageWrapper;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConnectedRegionsLabelingTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ImagePlus ipl = AWTImageWrapper.toImagePlus(Util.readFromFile("comps_test2.png"));
		ImageProcessor ip = ipl.getProcessor();
		long start = System.currentTimeMillis();
		Results r = ConnectedRegionsLabeling.run(ip, 0);
		long stop = System.currentTimeMillis();

		System.out.println("process lasted "+ (stop - start) / 1000.d + " seconds");
		System.out.println("labels count "+ r.getLabelCount());
		r.getLabeledImage().print(System.out);
		
		Map<Integer, Integer> regions = r.getRegions();
		Set<Integer> keys = regions.keySet();
		for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			System.out.println(" "+key+" "+regions.get(key));
		}
	}
	
}