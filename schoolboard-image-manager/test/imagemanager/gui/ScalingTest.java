package imagemanager.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;

public class ScalingTest {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("lulz");
		frame.add(new ImgComp());
		frame.pack();
		frame.setVisible(true);
	}

}

class ImgComp extends Component{
	
	public ImgComp(){
		setBackground(Color.RED);
		setPreferredSize(new Dimension(400, 400));
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		g.setColor(Color.RED);
		g.drawRect(1, 1, 398, 398);
	}
	
}