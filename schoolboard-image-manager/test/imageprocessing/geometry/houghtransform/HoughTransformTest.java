package imageprocessing.geometry.houghtransform;

import imageprocessing.Util;
import imageprocessing.geometry.Line;
import imageprocessing.matrix.MatrixB;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import testJcomps.LineTestComponent;

public class HoughTransformTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String path1 = "images/houghtransform-test/single_line.png";
		BufferedImage img = Util.readFromFile(path1);
		MatrixB edges = Util.grayToMatrixB(img, 0);
		
		HoughMatrix h = new HoughMatrix(edges);
		List<HoughLine> lines = h.getVerticalLines(1, 0, 30, 5, 5);
		List<Line> l = new ArrayList<Line>();
		
		l.add(lines.get(0).getLine(img.getWidth(), img.getHeight()));
//		double w = img.getWidth(), hh = img.getHeight();
//		Line line = lines.get(0).getLine(img.getWidth(), img.getHeight());
//		Point p = new Point(0,0);

		JComponent comp = new LineTestComponent(l, img);
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(comp);
		JFrame frame = new JFrame("hough transform test");
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
	}

}
