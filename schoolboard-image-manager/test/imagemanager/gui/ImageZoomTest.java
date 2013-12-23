package imagemanager.gui;

import imageprocessing.Util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

public class ImageZoomTest {
	static JFrame jf;
	public static void main(String[] args){
		TestImageComponent imc = new TestImageComponent(Util.readFromFile("images/blackboard/bb01.png"));
		jf = new JFrame();
		jf.add(imc);
		jf.setTitle(""+imc.getPreferredSize().width + " "+imc.getPreferredSize().height);
		//jf.setPreferredSize(new Dimension(800, 600));
		//jf.setPreferredSize(imc.getPreferredSize());
		jf.pack();
		jf.setVisible(true);
		
		imc.addPropertyChangeListener("preferredSize", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				System.out.println("pref size changed");
				Component source = (Component)evt.getSource();
				jf.setTitle(" "+source.getPreferredSize().width + " " + source.getPreferredSize().height);
				jf.setPreferredSize(new Dimension(source.getPreferredSize().width + 3, source.getPreferredSize().height + 30));

				jf.pack();
			}
		});
		
	}

	static class ImgComp extends Component{
		public BufferedImage image;
		
		public ImgComp(BufferedImage image){
			this.image = image;
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), 0, 0, image.getWidth() + image.getHeight(), image.getHeight() + image.getHeight(), null, null);
			//g.drawImage(image, 0, 0, image.getWidth() / 2, image.getHeight() / 2, 0, 0, image.getWidth(), image.getHeight(), null, null);
		
		}
	}
}

