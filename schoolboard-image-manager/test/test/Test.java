package test;

import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import testJcomps.ImageComponent;

public class Test {

	public static void showComponent(JComponent comp, String title){
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(comp);
		JFrame frame = new JFrame(title);
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void showImage(BufferedImage image, String title){
		ImageComponent ic = new ImageComponent(image);
		showComponent(ic, title);
	}
}
