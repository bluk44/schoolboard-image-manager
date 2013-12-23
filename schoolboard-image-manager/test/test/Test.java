package test;

import imagemanager.gui.ImageComponent;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Test {

	public static void showComponent(JComponent comp, String title){
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(comp);
		JFrame frame = new JFrame(title);
		frame.add(sp);
		frame.addComponentListener(new Listener(comp));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void showComponent(JComponent comp, String title, int x, int y, int width, int height){
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(comp);
		JFrame frame = new JFrame(title);
		frame.setBounds(x, y, width, height);
		frame.add(sp);
		frame.addComponentListener(new Listener(comp));
		frame.pack();
		frame.setVisible(true);
	}
	
	public static void showImage(BufferedImage image, String title){
		ImageComponent ic = new ImageComponent(image);
		showComponent(ic, title);
	}
	
	public static void printImage(BufferedImage image){
		for(int i = 0; i < image.getHeight(); i++){
			for (int j = 0; j < image.getWidth(); j++) {
				if(image.getRGB(j, i) == Color.WHITE.getRGB())
				System.out.print("+");
				else if(image.getRGB(j, i) == Color.BLACK.getRGB())
				System.out.print("-");
				else if(image.getRGB(j, i) == Color.GRAY.getRGB())
				System.out.print("*");
				else 
					System.out.print("?");				
			}
			System.out.println();
		}
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
	
	static class Listener implements ComponentListener{
		
		JComponent comp;
		public Listener(JComponent comp){
			this.comp = comp;
		}
		
		@Override
		public void componentResized(ComponentEvent e) {
			Dimension d = ((JFrame)e.getSource()).getSize();
			d.setSize(d.width, d.height-100);
			comp.setPreferredSize(d);			
		}
		public void componentMoved(ComponentEvent e) {}
		public void componentShown(ComponentEvent e) {}
		public void componentHidden(ComponentEvent e) {}
		
	}
}
