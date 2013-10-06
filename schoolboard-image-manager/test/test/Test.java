package test;

import java.awt.image.BufferedImage;
import java.util.Scanner;

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
	
	public static void showComponent(JComponent comp, String title, int x, int y, int width, int height){
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(comp);
		JFrame frame = new JFrame(title);
		frame.setBounds(x, y, width, height);
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void showImage(BufferedImage image, String title){
		ImageComponent ic = new ImageComponent(image);
		showComponent(ic, title);
	}
	
	public static void main(String[] args){
		int i = 0;
		Scanner scanner = new Scanner(System.in);
		try {
			while(i >= 0){
			i = scanner.nextInt();
			System.out.println(i);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
