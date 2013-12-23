package imagemanager.gui;

import imageprocessing.ImageProcessing;
import imageprocessing.Util;
import imageprocessing.geometry.Point;
import imageprocessing.geometry.Polygon;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import test.Test;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class SourceImageComponentTest extends javax.swing.JFrame {
	private JScrollPane jScrollPane1;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static SourceImageComponent sic;
	public static void main(String[] args) {
		
		sic = new SourceImageComponent();
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(sic);
		JFrame frame = new JFrame("test");
		frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				sic.setPreferredSize(((JFrame)e.getSource()).getSize());
				//System.out.println(((JFrame)e.getSource()).getSize());
				
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Polygon dis = sic.getDistorted();
					Polygon fix = sic.getFixed();
					
					BufferedImage trans = ImageProcessing.extractBoardRegion(sic.getImage(), dis, fix);
					
					ImageComponent imc = new ImageComponent(trans);
					Test.showComponent(imc, "result");
				}
				if(e.getKeyCode() == KeyEvent.VK_0){
					sic.setSelectionMode();
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					sic.unsetSelectionMode();
				}
			}
		});
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);
	}
	
}
