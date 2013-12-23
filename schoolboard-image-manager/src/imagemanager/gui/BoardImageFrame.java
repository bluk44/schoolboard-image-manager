package imagemanager.gui;

import imagemanager.BoardImage;
import imagemanager.TextRegion;
import imageprocessing.Util;
import imageprocessing.geometry.Geo;
import imageprocessing.geometry.Point;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import test.Test;

public class BoardImageFrame extends JFrame {
	
	private ImageComponent ic;
	private BoardImage boardImage;
	
	// miejsce na pasek
	private int dy = -30;
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				BoardImageFrame inst = new BoardImageFrame(Util.readFromFile("images/bg_remove/binarized/bin.png"));
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public BoardImageFrame(BufferedImage image){
//		super();
//		ic = new ImageComponent(image);
//		ic.setBounds(0, 0, ImageComponent.PREF_WIDTH, ImageComponent.PREF_HEIGHT);
//		initGUI();
//		initListeners();
		Test.showImage(image, "");
	}
	
	public BoardImageFrame(BoardImage boardImage){
		super();
		this.boardImage = boardImage;
		initImageComponent();
		initGUI();
		initListeners();

	}
	
	private void initImageComponent(){
		ic = new ImageComponent(boardImage.getPixels());
		ic.setBounds(0, 0, ImageComponent.PREF_WIDTH, ImageComponent.PREF_HEIGHT);
		
		for (TextRegion textRegion : boardImage.getTextRegions()) {
			ic.addShape(textRegion);
		}
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().add(ic);
			
			pack();
			this.setSize(800, 600);
		} catch (Exception e) {e.printStackTrace();}
	}
	
	private void initListeners(){
		
		addComponentListener(new ComponentListener() {

			public void componentShown(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				Dimension d = ((JFrame)e.getSource()).getSize();
				d.setSize(d.width, d.height+dy);
				ic.setPreferredSize(d);				}
		});
		
		addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				System.out.println("mouse clicked");
				Point p = new Point(e.getX(), e.getY()+dy);
				int n = 0;
				for (TextRegion textRegion : boardImage.getTextRegions()) {

					if(Geo.isInside(p, textRegion)){
						textRegion.setColor(Color.RED);
					}
					++n;
				}
				repaint();
			}
		});
	}
}
