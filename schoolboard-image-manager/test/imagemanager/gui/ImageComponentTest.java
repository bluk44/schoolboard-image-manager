package imagemanager.gui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import test.Test;
import imageprocessing.Util;

public class ImageComponentTest {
	static ImageComponent ic = new ImageComponent(Util.readFromFile("images/bg_remove/biala-szkola/sk01.png"));

	public static void main(String[] args){
		
		JScrollPane sp = new JScrollPane();
		sp.setViewportView(ic);
		JFrame frame = new JFrame("test");
//		frame.addPropertyChangeListener(new PropertyChangeListener() {
//			
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				System.out.println("changes");
//			}
//		});
		frame.addComponentListener(new ComponentListener() {
			
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void componentResized(ComponentEvent e) {
				ic.setPreferredSize(((JFrame)e.getSource()).getSize());
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
		frame.add(sp);
		frame.pack();
		frame.setVisible(true);	}
	
}
